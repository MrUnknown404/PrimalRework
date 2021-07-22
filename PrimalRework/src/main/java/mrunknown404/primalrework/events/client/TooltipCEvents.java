package mrunknown404.primalrework.events.client;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.items.utils.SIBlock;
import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.stage.StageH;
import mrunknown404.primalrework.stage.VanillaRegistry;
import mrunknown404.primalrework.utils.Cache;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.MathH;
import mrunknown404.primalrework.utils.enums.EnumStage;
import mrunknown404.primalrework.utils.enums.EnumToolMaterial;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
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
		
		List<ITextComponent> list = e.getToolTip();
		
		list = getTooltips(stack);
		tooltipCache.set(stack, list);
	}
	
	public static List<ITextComponent> getTooltips(ItemStack stack) {
		if (tooltipCache.is(stack)) {
			return tooltipCache.get();
		}
		
		IFormattableTextComponent name = (IFormattableTextComponent) stack.getDisplayName();
		Item item = stack.getItem();
		List<ITextComponent> list = new ArrayList<ITextComponent>();
		
		boolean isStaged = (item instanceof StagedItem);
		
		if (stack.getMaxStackSize() != 1) {
			list.add(name.append(string(" [" + stack.getCount() + "/" + stack.getMaxStackSize() + "]").withStyle(Style.EMPTY.withItalic(false))));
		} else {
			list.add(name);
		}
		
		EnumStage stage = isStaged ? ((StagedItem) item).stage : VanillaRegistry.getStage(item);
		if (StageH.hasAccessToStage(stage)) {
			if (isStaged) {
				List<ITextComponent> temp = ((StagedItem) item).getTooltips();
				if (!temp.isEmpty()) {
					list.addAll(temp);
					if (item instanceof BlockItem || item instanceof SIBlock || stack.getMaxDamage() > 0) {
						list.add(StringTextComponent.EMPTY);
					}
				}
			}
			
			if (item.getFoodProperties() != null) {
				Food food = item.getFoodProperties();
				
				int nutrition = ObfuscationReflectionHelper.getPrivateValue(Food.class, food, "nutrition");
				float saturation = ObfuscationReflectionHelper.getPrivateValue(Food.class, food, "saturationModifier");
				
				list.add(string(nutrition + " ").append(translate("tooltips.food.nutrition")).withStyle(STYLE_GRAY));
				list.add(string(MathH.roundTo(nutrition * saturation, 2) + " ").append(translate("tooltips.food.saturation")).withStyle(STYLE_GRAY));
			} else if (item instanceof SIBlock || item instanceof BlockItem) {
				Block block = item instanceof SIBlock ? ((SIBlock) item).getBlock() : ((BlockItem) item).getBlock();
				List<HarvestInfo> infos = isStaged ? new ArrayList<HarvestInfo>(((SIBlock) item).getBlock().getHarvest().values()) :
						VanillaRegistry.getHarvestInfos(((BlockItem) item).getBlock());
				
				AbstractBlock.Properties prop = ObfuscationReflectionHelper.getPrivateValue(AbstractBlock.class, block, "properties");
				float hardness = MathH.roundTo(ObfuscationReflectionHelper.getPrivateValue(AbstractBlock.Properties.class, prop, "destroyTime"), 2);
				float blast = MathH.roundTo(ObfuscationReflectionHelper.getPrivateValue(AbstractBlock.Properties.class, prop, "explosionResistance"), 2);
				
				if (hardness != -1) {
					list.add(string(((String.valueOf(hardness).split("\\.")[1].length() < 2) ? hardness + "0" : hardness) + " ").append(translate("tooltips.block.hardness"))
							.withStyle(STYLE_GRAY));
				} else {
					list.add(translate("tooltips.block.unbreakable").withStyle(STYLE_GRAY));
				}
				
				if (blast < 1000) {
					list.add(string(((String.valueOf(blast).split("\\.")[1].length() < 2) ? blast + "0" : blast) + " ").append(translate("tooltips.block.blast"))
							.withStyle(STYLE_GRAY));
				} else {
					list.add(translate("tooltips.block.unexplodable").withStyle(STYLE_GRAY));
				}
				
				list.add(StringTextComponent.EMPTY);
				list.add(translate("tooltips.require.following").withStyle(STYLE_GRAY));
				for (HarvestInfo info : infos) {
					list.add(translate("tooltips.require.level").append(string(" " + info.toolMat.level + " " + info.toolType.getName())).withStyle(STYLE_GRAY));
				}
			} else {
				EnumToolType toolType = isStaged ? ((StagedItem) item).toolType : VanillaRegistry.getToolType(item);
				EnumToolMaterial toolMat = isStaged ? ((StagedItem) item).toolMat : VanillaRegistry.getToolMaterial(item);
				float dmg = MathH.roundTo((toolType.baseDamage + toolMat.extraDamage), 2);
				float atkSpd = MathH.roundTo(toolType.swingSpeed + 4, 2);
				float mineSpd = MathH.roundTo(toolMat.speed, 2);
				
				if (stack.getMaxDamage() > 0) {
					list.add(string((stack.getMaxDamage() - stack.getDamageValue()) + "/" + stack.getMaxDamage() + " ").append(translate("tooltips.stat.durability"))
							.withStyle(STYLE_GRAY));
				}
				
				if (toolType != EnumToolType.none) {
					boolean dmgCheck = String.valueOf(dmg).split("\\.")[1].length() < 2;
					boolean atkSpdCheck = String.valueOf(atkSpd).split("\\.")[1].length() < 2;
					boolean mineSpdCheck = String.valueOf(mineSpd).split("\\.")[1].length() < 2;
					
					list.add(StringTextComponent.EMPTY);
					list.add(string((dmgCheck ? dmg + "0" : dmg) + " ").append(translate("tooltips.stat.damage")).withStyle(STYLE_GRAY));
					list.add(string((atkSpdCheck ? atkSpd + "0" : atkSpd) + " ").append(translate("tooltips.stat.speed.attack")).withStyle(STYLE_GRAY));
					list.add(string((mineSpdCheck ? mineSpd + "0" : mineSpd) + " ").append(translate("tooltips.stat.speed.mine")).withStyle(STYLE_GRAY));
					list.add(translate("tooltips.stat.level").append(string(" " + toolMat.level + " " + toolType.getName())).withStyle(STYLE_GRAY));
				}
			}
			
			list.add(StringTextComponent.EMPTY);
			list.add(translate("tooltips.require.stage").withStyle(STYLE_LIGHT_PURPLE).append(string(" " + stage.getName())));
		} else {
			list.add(translate("tooltips.unknown").withStyle(STYLE_GRAY));
		}
		
		return list;
	}
	
	//These are just used to make things neater
	private static StringTextComponent string(String str) {
		return new StringTextComponent(str);
	}
	
	//These are just used to make things neater
	private static TranslationTextComponent translate(String str) {
		return new TranslationTextComponent(str);
	}
}
