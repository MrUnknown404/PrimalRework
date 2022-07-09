package mrunknown404.primalrework.utils.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mrunknown404.primalrework.blocks.StagedBlock;
import mrunknown404.primalrework.items.SIBlock;
import mrunknown404.primalrework.items.SIDamageable;
import mrunknown404.primalrework.items.SIFood;
import mrunknown404.primalrework.items.StagedItem;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.Cache;
import mrunknown404.primalrework.utils.PRConfig;
import mrunknown404.primalrework.utils.enums.Element;
import mrunknown404.primalrework.utils.enums.ToolType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

public class ItemH {
	private static final Cache<ItemStack, List<ITextComponent>> TOOLTIP_CACHE = new Cache<ItemStack, List<ITextComponent>>() {
		@Override
		public boolean is(ItemStack key) {
			return this.key == null || key == null ? false : this.key.equals(key, false);
		}
	};
	
	public static final List<ITextComponent> UNKNOWN_ITEM = Arrays.asList(WordH.translate("tooltips.unknown").withStyle(TextFormatting.GRAY));
	
	public static List<ITextComponent> getTooltips(ItemStack stack) {
		if (!(stack.getItem() instanceof StagedItem)) {
			return UNKNOWN_ITEM;
		} else if (TOOLTIP_CACHE.is(stack)) {
			return TOOLTIP_CACHE.get();
		}
		
		IFormattableTextComponent name = (IFormattableTextComponent) stack.getHoverName();
		StagedItem item = (StagedItem) stack.getItem();
		List<ITextComponent> list = new ArrayList<ITextComponent>();
		
		final PRConfig.Client config = PRConfig.CLIENT;
		
		Stage stage = item.stage.get();
		if (stage.hasAccessToCurrentStage()) {
			list.add(config.tooltips_showStackSize.get() && stack.getMaxStackSize() != 1 ?
					name.copy().append(WordH.string(" [" + stack.getCount() + "/" + stack.getMaxStackSize() + "]").withStyle(Style.EMPTY.withItalic(false))) :
					name);
			
			List<ITextComponent> temp = item.getTooltips();
			if (!temp.isEmpty()) {
				list.addAll(temp);
				
				if (item instanceof SIBlock && config.tooltips_showBlockInfo.get()) {
					list.add(StringTextComponent.EMPTY);
				} else if (item instanceof SIDamageable && config.tooltips_showDurability.get()) {
					list.add(StringTextComponent.EMPTY);
				} else if (item instanceof SIFood && config.tooltips_showFoodNutrients.get()) {
					list.add(StringTextComponent.EMPTY);
				}
			}
			
			if (item instanceof SIFood && PRConfig.CLIENT.tooltips_showFoodNutrients.get()) {
				SIFood food = (SIFood) item;
				
				list.add(WordH.string(String.valueOf(food.nutrition)).append(WordH.translate("tooltips.food.nutrition")).withStyle(TextFormatting.GRAY));
				list.add(WordH.string(WordH.formatNumber(1, food.nutrition * food.saturation) + " ").append(WordH.translate("tooltips.food.saturation"))
						.withStyle(TextFormatting.GRAY));
			} else if (item instanceof SIBlock) {
				if (config.tooltips_showBlockInfo.get()) {
					StagedBlock block = ((SIBlock) item).getBlock();
					float hardness = block.blockInfo.getHardness();
					float blast = block.blockInfo.getBlast();
					
					list.add(hardness >= 0 ? WordH.string(WordH.formatNumber(1, hardness) + " ").append(WordH.translate("tooltips.block.hardness")) :
							WordH.translate("tooltips.block.unbreakable"));
					list.add(blast >= 0 ? WordH.string(WordH.formatNumber(1, blast) + " ").append(WordH.translate("tooltips.block.blast")) :
							WordH.translate("tooltips.block.unexplodable"));
					
					list.add(StringTextComponent.EMPTY);
					list.add(WordH.translate("tooltips.require.following").withStyle(TextFormatting.GRAY));
					BlockH.getBlockHarvestInfos(block).forEach(info -> list.add(WordH.translate("tooltips.require.level")
							.append(WordH.string(" " + info.toolMat.level + " " + info.toolType.getName())).withStyle(TextFormatting.GRAY)));
				}
			} else {
				if (item instanceof SIDamageable && config.tooltips_showDurability.get()) {
					SIDamageable di = (SIDamageable) item;
					list.add(WordH.string((di.toolMat.durability - stack.getDamageValue()) + "/" + di.toolMat.durability + " ").append(WordH.translate("tooltips.stat.durability"))
							.withStyle(TextFormatting.GRAY));
				}
				
				if (item.toolType != ToolType.NONE && config.tooltips_showToolStats.get()) {
					if (config.tooltips_showDurability.get()) {
						list.add(StringTextComponent.EMPTY);
					}
					
					list.add(WordH.string(WordH.formatNumber(2, item.toolType.baseDamage + item.toolMat.extraDamage) + " ").append(WordH.translate("tooltips.stat.damage"))
							.withStyle(TextFormatting.GRAY));
					list.add(WordH.string(WordH.formatNumber(2, item.toolType.swingSpeed + 4) + " ").append(WordH.translate("tooltips.stat.speed.attack"))
							.withStyle(TextFormatting.GRAY));
					list.add(WordH.string(WordH.formatNumber(2, item.toolMat.speed) + " ").append(WordH.translate("tooltips.stat.speed.mine")).withStyle(TextFormatting.GRAY));
					list.add(WordH.translate("tooltips.stat.level").append(WordH.string(" " + item.toolMat.level + " " + item.toolType.getName())).withStyle(TextFormatting.GRAY));
				}
			}
			
			if (list.size() > 1 && (config.tooltips_showAtomicSymbolsInTooltips.get() || config.tooltips_showStage.get())) {
				list.add(StringTextComponent.EMPTY);
			}
			
			if (!item.getElements().isEmpty() && config.tooltips_showAtomicSymbolsInTooltips.get()) {
				final StringBuilder s = new StringBuilder();
				item.getElements().forEach((k, v) -> s.append(v == 1 ? k.atomicSymbol() : k.atomicSymbol() + Element.subscript(v)));
				list.add(WordH.string(s.toString()).withStyle(TextFormatting.GREEN));
			}
			
			if (config.tooltips_showStage.get()) {
				list.add(WordH.translate("tooltips.require.stage").withStyle(TextFormatting.LIGHT_PURPLE).append(WordH.string(" " + stage.getName())));
			}
		} else {
			list.addAll(UNKNOWN_ITEM);
		}
		
		TOOLTIP_CACHE.set(stack, list);
		return list;
	}
}
