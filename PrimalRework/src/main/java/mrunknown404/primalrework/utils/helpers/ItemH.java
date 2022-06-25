package mrunknown404.primalrework.utils.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mrunknown404.primalrework.blocks.utils.StagedBlock;
import mrunknown404.primalrework.items.utils.SIBlock;
import mrunknown404.primalrework.items.utils.SIDamageable;
import mrunknown404.primalrework.items.utils.SIFood;
import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.BlockInfo;
import mrunknown404.primalrework.utils.Cache;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.enums.ToolType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;

public class ItemH {
	public static boolean isFood(Item item) {
		return item.getFoodProperties() != null;
	}
	
	public static boolean isBlock(Item item) {
		return item instanceof SIBlock || item instanceof BlockItem;
	}
	
	public static boolean isDamageable(Item item) {
		return item instanceof SIDamageable;
	}
	
	public static int getMaxDamage(SIDamageable item) {
		return item.toolMat.durability;
	}
	
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
		}
		
		if (TOOLTIP_CACHE.is(stack)) {
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
				if (ItemH.isBlock(item) || ItemH.isDamageable(item) || ItemH.isFood(item)) {
					list.add(StringTextComponent.EMPTY);
				}
			}
			
			if (ItemH.isFood(item)) {
				SIFood food = (SIFood) item;
				
				list.add(WordH.string(WordH.toPrintableNumber(food.nutrition) + " ").append(WordH.translate("tooltips.food.nutrition")).withStyle(WordH.STYLE_GRAY));
				list.add(WordH.string(WordH.toPrintableNumber(food.nutrition * food.saturation) + " ").append(WordH.translate("tooltips.food.saturation"))
						.withStyle(WordH.STYLE_GRAY));
			} else if (ItemH.isBlock(item)) {
				StagedBlock block = ((SIBlock) item).getBlock();
				List<HarvestInfo> infos = BlockH.getBlockHarvestInfos(block);
				
				BlockInfo blockInfo = block.blockInfo;
				float hardness = blockInfo.getHardness();
				float blast = blockInfo.getBlast();
				
				list.add(hardness != -1 ? WordH.string(WordH.toPrintableNumber(hardness) + " ").append(WordH.translate("tooltips.block.hardness")) :
						WordH.translate("tooltips.block.unbreakable"));
				list.add(blast != -1 ? WordH.string(WordH.toPrintableNumber(blast) + " ").append(WordH.translate("tooltips.block.blast")) :
						WordH.translate("tooltips.block.unexplodable"));
				
				list.add(StringTextComponent.EMPTY);
				list.add(WordH.translate("tooltips.require.following").withStyle(WordH.STYLE_GRAY));
				for (HarvestInfo info : infos) {
					list.add(WordH.translate("tooltips.require.level").append(WordH.string(" " + info.toolMat.level + " " + info.toolType.getName())).withStyle(WordH.STYLE_GRAY));
				}
			} else {
				String dmg = WordH.toPrintableNumber(item.toolType.baseDamage + item.toolMat.extraDamage);
				String atkSpd = WordH.toPrintableNumber(item.toolType.swingSpeed + 4);
				String mineSpd = WordH.toPrintableNumber(item.toolMat.speed);
				
				if (ItemH.isDamageable(item)) {
					SIDamageable di = (SIDamageable) item;
					list.add(WordH.string((ItemH.getMaxDamage(di) - stack.getDamageValue()) + "/" + ItemH.getMaxDamage(di) + " ")
							.append(WordH.translate("tooltips.stat.durability")).withStyle(WordH.STYLE_GRAY));
				}
				
				if (item.toolType != ToolType.NONE) {
					list.add(StringTextComponent.EMPTY);
					list.add(WordH.string(dmg + " ").append(WordH.translate("tooltips.stat.damage")).withStyle(WordH.STYLE_GRAY));
					list.add(WordH.string(atkSpd + " ").append(WordH.translate("tooltips.stat.speed.attack")).withStyle(WordH.STYLE_GRAY));
					list.add(WordH.string(mineSpd + " ").append(WordH.translate("tooltips.stat.speed.mine")).withStyle(WordH.STYLE_GRAY));
					list.add(WordH.translate("tooltips.stat.level").append(WordH.string(" " + item.toolMat.level + " " + item.toolType.getName())).withStyle(WordH.STYLE_GRAY));
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
