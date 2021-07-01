package mrunknown404.primalrework.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import mrunknown404.primalrework.utils.enums.EnumToolMaterial;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class HarvestInfo {
	public static final HarvestInfo AXE_MIN = new HarvestInfo(EnumToolType.axe, EnumToolMaterial.clay);
	public static final HarvestInfo HOE_MIN = new HarvestInfo(EnumToolType.hoe, EnumToolMaterial.clay);
	public static final HarvestInfo KNIFE_MIN = new HarvestInfo(EnumToolType.knife, EnumToolMaterial.clay);
	public static final HarvestInfo PICKAXE_MIN = new HarvestInfo(EnumToolType.pickaxe, EnumToolMaterial.clay);
	public static final HarvestInfo SAW_MIN = new HarvestInfo(EnumToolType.saw, EnumToolMaterial.clay);
	public static final HarvestInfo SHEARS_MIN = new HarvestInfo(EnumToolType.shears, EnumToolMaterial.clay);
	public static final HarvestInfo SHOVEL_MIN = new HarvestInfo(EnumToolType.shovel, EnumToolMaterial.clay);
	public static final HarvestInfo SWORD_MIN = new HarvestInfo(EnumToolType.sword, EnumToolMaterial.clay);
	
	public static final HarvestInfo HAND = new HarvestInfo(EnumToolType.none, EnumToolMaterial.hand);
	public static final HarvestInfo UNBREAKABLE = new HarvestInfo(EnumToolType.none, EnumToolMaterial.unbreakable);
	
	public final EnumToolType toolType;
	public final EnumToolMaterial toolMat;
	public final List<DropInfo> drops = new ArrayList<DropInfo>();
	
	public HarvestInfo(EnumToolType toolType, EnumToolMaterial toolMat, DropInfo... drops) {
		this.toolType = toolType;
		this.toolMat = toolMat;
		
		for (DropInfo drop : drops) {
			this.drops.add(drop);
		}
	}
	
	public boolean hasDrops() {
		return !drops.isEmpty();
	}
	
	public static class DropInfo {
		private static final Random R = new Random();
		public static final DropInfo NONE = new DropInfo(() -> null);
		
		private final Supplier<Item> result;
		private final int min, max, chance;
		
		public DropInfo(Supplier<Item> result, int min, int max, int chance) {
			this.result = result;
			this.min = min;
			this.max = max;
			this.chance = chance;
		}
		
		public DropInfo(Supplier<Item> result, int min, int max) {
			this(result, min, max, 100);
		}
		
		public DropInfo(Supplier<Item> result, int chance) {
			this(result, 1, 1, chance);
		}
		
		public DropInfo(Supplier<Item> result) {
			this(result, 1, 1, 100);
		}
		
		public DropInfo(Item result, int min, int max) {
			this(() -> result, min, max, 100);
		}
		
		public DropInfo(Item result, int chance) {
			this(() -> result, 1, 1, chance);
		}
		
		public DropInfo(Item result) {
			this(() -> result, 1, 1, 100);
		}
		
		public ItemStack getItem() {
			if (result == null) {
				return ItemStack.EMPTY;
			}
			
			if (R.nextInt(100) + 1 <= chance) {
				int count = max == min ? max : R.nextInt(max - min) + min;
				return new ItemStack(result.get(), count);
			}
			
			return ItemStack.EMPTY;
		}
	}
}
