package mrunknown404.primalrework.helpers;

import mrunknown404.primalrework.items.utils.SIBlock;
import mrunknown404.primalrework.items.utils.SIDamageable;
import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.stage.VanillaRegistry;
import mrunknown404.primalrework.utils.enums.EnumStage;
import mrunknown404.primalrework.utils.enums.EnumToolMaterial;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

@SuppressWarnings("deprecation")
public class ItemH {
	public static EnumToolType getItemToolType(Item item) {
		return item instanceof StagedItem ? ((StagedItem) item).toolType : VanillaRegistry.getToolType(item);
	}
	
	public static EnumToolMaterial getItemToolMaterial(Item item) {
		return item instanceof StagedItem ? ((StagedItem) item).toolMat : VanillaRegistry.getToolMaterial(item);
	}
	
	public static EnumStage getStage(Item item) {
		return item instanceof StagedItem ? ((StagedItem) item).stage : VanillaRegistry.getStage(item);
	}
	
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
}
