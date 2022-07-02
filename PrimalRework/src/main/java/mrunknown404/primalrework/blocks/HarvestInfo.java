package mrunknown404.primalrework.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mrunknown404.primalrework.items.ISIProvider;
import mrunknown404.primalrework.utils.enums.ToolMaterial;
import mrunknown404.primalrework.utils.enums.ToolType;
import net.minecraft.item.ItemStack;

public class HarvestInfo {
	public static final HarvestInfo AXE_MIN = new HarvestInfo(ToolType.AXE, ToolMaterial.CLAY);
	public static final HarvestInfo HOE_MIN = new HarvestInfo(ToolType.HOE, ToolMaterial.CLAY);
	public static final HarvestInfo KNIFE_MIN = new HarvestInfo(ToolType.KNIFE, ToolMaterial.CLAY);
	public static final HarvestInfo PICKAXE_MIN = new HarvestInfo(ToolType.PICKAXE, ToolMaterial.CLAY);
	public static final HarvestInfo SHEARS_MIN = new HarvestInfo(ToolType.SHEARS, ToolMaterial.CLAY);
	public static final HarvestInfo SHOVEL_MIN = new HarvestInfo(ToolType.SHOVEL, ToolMaterial.CLAY);
	public static final HarvestInfo SWORD_MIN = new HarvestInfo(ToolType.SWORD, ToolMaterial.CLAY);
	
	public static final HarvestInfo HAND = new HarvestInfo(ToolType.NONE, ToolMaterial.HAND);
	public static final HarvestInfo UNBREAKABLE = new HarvestInfo(ToolType.NONE, ToolMaterial.UNBREAKABLE);
	
	public final ToolType toolType;
	public final ToolMaterial toolMat;
	public final List<DropInfo> drops = new ArrayList<DropInfo>();
	
	public HarvestInfo(ToolType toolType, ToolMaterial toolMat, DropInfo... drops) {
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
		public static final DropInfo NONE = of(() -> null);
		
		private final ISIProvider result;
		private final int min, max, chance;
		
		private DropInfo(ISIProvider result, int min, int max, int chance) {
			this.result = result;
			this.min = min;
			this.max = max;
			this.chance = chance;
		}
		
		public static DropInfo of(ISIProvider result, int min, int max) {
			return new DropInfo(result, min, max, 100);
		}
		
		public static DropInfo of(ISIProvider result, int chance) {
			return new DropInfo(result, 1, 1, chance);
		}
		
		public static DropInfo of(ISIProvider result) {
			return new DropInfo(result, 1, 1, 100);
		}
		
		public ItemStack getItem() {
			if (result == null) {
				return ItemStack.EMPTY;
			}
			
			if (R.nextInt(100) + 1 <= chance) {
				int count = max == min ? max : R.nextInt(max - min) + min;
				return new ItemStack(result.getStagedItem(), count);
			}
			
			return ItemStack.EMPTY;
		}
	}
}
