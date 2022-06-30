package mrunknown404.primalrework.utils.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import mrunknown404.primalrework.blocks.utils.StagedBlock;
import mrunknown404.primalrework.items.utils.SIBlock;
import mrunknown404.primalrework.items.utils.SIDamageable;
import mrunknown404.primalrework.items.utils.SIFood;
import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.BlockInfo;
import mrunknown404.primalrework.utils.Cache;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.enums.Element;
import mrunknown404.primalrework.utils.enums.ToolType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;

public class ItemH {
	private static final Cache<ItemStack, List<ITextComponent>> TOOLTIP_CACHE = new Cache<ItemStack, List<ITextComponent>>() {
		@Override
		public boolean is(ItemStack key) {
			return this.key == null || key == null ? false : this.key.equals(key, false);
		}
	};
	
	private static final List<ITextComponent> UNKNOWN_ITEM = Arrays.asList(WordH.string("???"));
	
	public static List<ITextComponent> getTooltips(ItemStack stack) {
		if (!(stack.getItem() instanceof StagedItem)) {
			return UNKNOWN_ITEM;
		} else if (TOOLTIP_CACHE.is(stack)) {
			return TOOLTIP_CACHE.get();
		}
		
		IFormattableTextComponent name = (IFormattableTextComponent) stack.getHoverName();
		StagedItem item = (StagedItem) stack.getItem();
		List<ITextComponent> list = new ArrayList<ITextComponent>();
		
		if (stack.getMaxStackSize() != 1) {
			list.add(name.append(WordH.string(" [" + stack.getCount() + "/" + stack.getMaxStackSize() + "]").withStyle(Style.EMPTY.withItalic(false))));
		} else {
			list.add(name);
		}
		
		Stage stage = item.stage.get();
		if (StageH.hasAccessToStage(stage)) {
			List<ITextComponent> temp = item.getTooltips();
			if (!temp.isEmpty()) {
				list.addAll(temp);
				if (item instanceof SIBlock || item instanceof SIDamageable || item instanceof SIFood) {
					list.add(StringTextComponent.EMPTY);
				}
			}
			
			if (item instanceof SIFood) {
				SIFood food = (SIFood) item;
				
				list.add(WordH.string(WordH.toPrintableNumber(food.nutrition) + " ").append(WordH.translate("tooltips.food.nutrition")).withStyle(WordH.STYLE_GRAY));
				list.add(WordH.string(WordH.toPrintableNumber(food.nutrition * food.saturation) + " ").append(WordH.translate("tooltips.food.saturation"))
						.withStyle(WordH.STYLE_GRAY));
			} else if (item instanceof SIBlock) {
				StagedBlock block = ((SIBlock) item).getBlock();
				List<HarvestInfo> infos = BlockH.getBlockHarvestInfos(block);
				
				BlockInfo blockInfo = block.blockInfo;
				float hardness = blockInfo.getHardness();
				float blast = blockInfo.getBlast();
				
				list.add(hardness != -1 ? WordH.string(WordH.toPrintableNumber(hardness) + " ").append(WordH.translate("tooltips.block.hardness")) :
						WordH.translate("tooltips.block.unbreakable"));
				list.add(blast != -1 ? WordH.string(WordH.toPrintableNumber(blast) + " ").append(WordH.translate("tooltips.block.blast")) :
						WordH.translate("tooltips.block.unexplodable"));
				
				if (!item.getElements().isEmpty()) {
					String s = "";
					for (Entry<Element, Integer> entry : item.getElements().entrySet()) {
						s += entry.getKey() == Element.UNKNOWN || entry.getValue() == 1 ? entry.getKey().atomicSymbol() :
								entry.getKey().atomicSymbol() + Element.subscript(entry.getValue());
					}
					list.add(WordH.string(s).withStyle(WordH.STYLE_GREEN));
				}
				
				list.add(StringTextComponent.EMPTY);
				list.add(WordH.translate("tooltips.require.following").withStyle(WordH.STYLE_GRAY));
				for (HarvestInfo info : infos) {
					list.add(WordH.translate("tooltips.require.level").append(WordH.string(" " + info.toolMat.level + " " + info.toolType.getName())).withStyle(WordH.STYLE_GRAY));
				}
			} else {
				String dmg = WordH.toPrintableNumber(item.toolType.baseDamage + item.toolMat.extraDamage);
				String atkSpd = WordH.toPrintableNumber(item.toolType.swingSpeed + 4);
				String mineSpd = WordH.toPrintableNumber(item.toolMat.speed);
				
				if (item instanceof SIDamageable) {
					SIDamageable di = (SIDamageable) item;
					list.add(WordH.string((di.toolMat.durability - stack.getDamageValue()) + "/" + di.toolMat.durability + " ").append(WordH.translate("tooltips.stat.durability"))
							.withStyle(WordH.STYLE_GRAY));
				}
				
				if (item.toolType != ToolType.NONE) {
					list.add(StringTextComponent.EMPTY);
					list.add(WordH.string(dmg + " ").append(WordH.translate("tooltips.stat.damage")).withStyle(WordH.STYLE_GRAY));
					list.add(WordH.string(atkSpd + " ").append(WordH.translate("tooltips.stat.speed.attack")).withStyle(WordH.STYLE_GRAY));
					list.add(WordH.string(mineSpd + " ").append(WordH.translate("tooltips.stat.speed.mine")).withStyle(WordH.STYLE_GRAY));
					list.add(WordH.translate("tooltips.stat.level").append(WordH.string(" " + item.toolMat.level + " " + item.toolType.getName())).withStyle(WordH.STYLE_GRAY));
				}
			}
			
			if (!(item instanceof SIBlock)) {
				if (!item.getElements().isEmpty()) {
					String s = "";
					for (Entry<Element, Integer> entry : item.getElements().entrySet()) {
						s += entry.getKey() == Element.UNKNOWN || entry.getValue() == 1 ? entry.getKey().atomicSymbol() :
								entry.getKey().atomicSymbol() + Element.subscript(entry.getValue());
					}
					list.add(WordH.string(s).withStyle(WordH.STYLE_GREEN));
				}
			}
			
			list.add(StringTextComponent.EMPTY);
			list.add(WordH.translate("tooltips.require.stage").withStyle(WordH.STYLE_LIGHT_PURPLE).append(WordH.string(" " + stage.getName())));
		} else {
			list.add(WordH.translate("tooltips.unknown").withStyle(WordH.STYLE_GRAY));
		}
		
		TOOLTIP_CACHE.set(stack, list);
		return list;
	}
}
