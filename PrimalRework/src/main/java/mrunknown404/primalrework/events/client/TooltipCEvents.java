package mrunknown404.primalrework.events.client;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.helpers.BlockH;
import mrunknown404.primalrework.helpers.ItemH;
import mrunknown404.primalrework.helpers.MathH;
import mrunknown404.primalrework.helpers.StageH;
import mrunknown404.primalrework.helpers.WordH;
import mrunknown404.primalrework.items.utils.SIDamageable;
import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.utils.Cache;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.enums.EnumStage;
import mrunknown404.primalrework.utils.enums.EnumToolMaterial;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class TooltipCEvents {
	private static Cache<ItemStack, List<ITextComponent>> tooltipCache = new Cache<ItemStack, List<ITextComponent>>() {
		@Override
		public boolean is(ItemStack key) {
			return this.key == null || key == null ? false : this.key.equals(key, false);
		}
	};
	
	public static final Style STYLE_GRAY = Style.EMPTY.withColor(Color.fromLegacyFormat(TextFormatting.GRAY));
	public static final Style STYLE_LIGHT_PURPLE = Style.EMPTY.withColor(Color.fromLegacyFormat(TextFormatting.LIGHT_PURPLE));
	
	@SubscribeEvent
	public void onTooltip(ItemTooltipEvent e) {
		ItemStack stack = e.getItemStack();
		
		e.getToolTip().clear();
		e.getToolTip().addAll(getTooltips(stack));
		tooltipCache.set(stack, e.getToolTip());
	}
	
	public static List<ITextComponent> getTooltips(ItemStack stack) {
		if (tooltipCache.is(stack)) {
			return tooltipCache.get();
		}
		
		IFormattableTextComponent name = (IFormattableTextComponent) stack.getHoverName();
		Item item = stack.getItem();
		List<ITextComponent> list = new ArrayList<ITextComponent>();
		
		if (stack.getMaxStackSize() != 1) {
			list.add(name.append(WordH.string(" [" + stack.getCount() + "/" + stack.getMaxStackSize() + "]").withStyle(Style.EMPTY.withItalic(false))));
		} else {
			list.add(name);
		}
		
		EnumStage stage = ItemH.getStage(item);
		if (StageH.hasAccessToStage(stage)) {
			if (item instanceof StagedItem) {
				List<ITextComponent> temp = ((StagedItem) item).getTooltips();
				if (!temp.isEmpty()) {
					list.addAll(temp);
					if (ItemH.isBlock(item) || ItemH.isDamageable(item) || ItemH.isFood(item)) {
						list.add(StringTextComponent.EMPTY);
					}
				}
			}
			
			if (ItemH.isFood(item)) {
				Food food = item.getFoodProperties();
				
				int nutrition = ObfuscationReflectionHelper.getPrivateValue(Food.class, food, "nutrition");
				float saturation = ObfuscationReflectionHelper.getPrivateValue(Food.class, food, "saturationModifier");
				
				list.add(WordH.string(nutrition + " ").append(WordH.translate("tooltips.food.nutrition")).withStyle(STYLE_GRAY));
				list.add(WordH.string(MathH.roundTo(nutrition * saturation, 2) + " ").append(WordH.translate("tooltips.food.saturation")).withStyle(STYLE_GRAY));
			} else if (ItemH.isBlock(item)) {
				Block block = BlockH.getBlockFromItem(item);
				List<HarvestInfo> infos = BlockH.getBlockHarvestInfos(block);
				
				AbstractBlock.Properties prop = ObfuscationReflectionHelper.getPrivateValue(AbstractBlock.class, block, "properties");
				float hardness = MathH.roundTo(ObfuscationReflectionHelper.getPrivateValue(AbstractBlock.Properties.class, prop, "destroyTime"), 2);
				float blast = MathH.roundTo(ObfuscationReflectionHelper.getPrivateValue(AbstractBlock.Properties.class, prop, "explosionResistance"), 2);
				
				if (hardness != -1) {
					list.add(WordH.string(((String.valueOf(hardness).split("\\.")[1].length() < 2) ? hardness + "0" : hardness) + " ")
							.append(WordH.translate("tooltips.block.hardness")).withStyle(STYLE_GRAY));
				} else {
					list.add(WordH.translate("tooltips.block.unbreakable").withStyle(STYLE_GRAY));
				}
				
				if (blast < 1000) {
					list.add(WordH.string(((String.valueOf(blast).split("\\.")[1].length() < 2) ? blast + "0" : blast) + " ").append(WordH.translate("tooltips.block.blast"))
							.withStyle(STYLE_GRAY));
				} else {
					list.add(WordH.translate("tooltips.block.unexplodable").withStyle(STYLE_GRAY));
				}
				
				list.add(StringTextComponent.EMPTY);
				list.add(WordH.translate("tooltips.require.following").withStyle(STYLE_GRAY));
				for (HarvestInfo info : infos) {
					list.add(WordH.translate("tooltips.require.level").append(WordH.string(" " + info.toolMat.level + " " + info.toolType.getName())).withStyle(STYLE_GRAY));
				}
			} else {
				EnumToolType toolType = ItemH.getItemToolType(item);
				EnumToolMaterial toolMat = ItemH.getItemToolMaterial(item);
				float dmg = MathH.roundTo((toolType.baseDamage + toolMat.extraDamage), 2);
				float atkSpd = MathH.roundTo(toolType.swingSpeed + 4, 2);
				float mineSpd = MathH.roundTo(toolMat.speed, 2);
				
				if (ItemH.isDamageable(item)) {
					SIDamageable di = (SIDamageable) item;
					list.add(WordH.string((ItemH.getMaxDamage(di) - stack.getDamageValue()) + "/" + ItemH.getMaxDamage(di) + " ")
							.append(WordH.translate("tooltips.stat.durability")).withStyle(STYLE_GRAY));
				}
				
				if (toolType != EnumToolType.none) {
					boolean dmgCheck = String.valueOf(dmg).split("\\.")[1].length() < 2;
					boolean atkSpdCheck = String.valueOf(atkSpd).split("\\.")[1].length() < 2;
					boolean mineSpdCheck = String.valueOf(mineSpd).split("\\.")[1].length() < 2;
					
					list.add(StringTextComponent.EMPTY);
					list.add(WordH.string((dmgCheck ? dmg + "0" : dmg) + " ").append(WordH.translate("tooltips.stat.damage")).withStyle(STYLE_GRAY));
					list.add(WordH.string((atkSpdCheck ? atkSpd + "0" : atkSpd) + " ").append(WordH.translate("tooltips.stat.speed.attack")).withStyle(STYLE_GRAY));
					list.add(WordH.string((mineSpdCheck ? mineSpd + "0" : mineSpd) + " ").append(WordH.translate("tooltips.stat.speed.mine")).withStyle(STYLE_GRAY));
					list.add(WordH.translate("tooltips.stat.level").append(WordH.string(" " + toolMat.level + " " + toolType.getName())).withStyle(STYLE_GRAY));
				}
			}
			
			list.add(StringTextComponent.EMPTY);
			list.add(WordH.translate("tooltips.require.stage").withStyle(STYLE_LIGHT_PURPLE).append(WordH.string(" " + stage.getName())));
		} else {
			list.add(WordH.translate("tooltips.unknown").withStyle(STYLE_GRAY));
		}
		
		return list;
	}
}
