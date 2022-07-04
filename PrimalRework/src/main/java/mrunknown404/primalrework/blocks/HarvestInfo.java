package mrunknown404.primalrework.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import mrunknown404.primalrework.api.utils.ISIProvider;
import mrunknown404.primalrework.init.InitToolMaterials;
import mrunknown404.primalrework.utils.enums.ToolMaterial;
import mrunknown404.primalrework.utils.enums.ToolType;
import net.minecraft.item.ItemStack;

public class HarvestInfo {
	public static final HarvestInfo AXE_MIN = new HarvestInfo(ToolType.AXE, InitToolMaterials.CLAY);
	public static final HarvestInfo HOE_MIN = new HarvestInfo(ToolType.HOE, InitToolMaterials.CLAY);
	public static final HarvestInfo KNIFE_MIN = new HarvestInfo(ToolType.KNIFE, InitToolMaterials.CLAY);
	public static final HarvestInfo PICKAXE_MIN = new HarvestInfo(ToolType.PICKAXE, InitToolMaterials.CLAY);
	public static final HarvestInfo SHEARS_MIN = new HarvestInfo(ToolType.SHEARS, InitToolMaterials.CLAY);
	public static final HarvestInfo SHOVEL_MIN = new HarvestInfo(ToolType.SHOVEL, InitToolMaterials.CLAY);
	public static final HarvestInfo SWORD_MIN = new HarvestInfo(ToolType.SWORD, InitToolMaterials.CLAY);
	
	public static final HarvestInfo HAND = new HarvestInfo(ToolType.NONE, InitToolMaterials.HAND);
	public static final HarvestInfo UNBREAKABLE = new HarvestInfo(ToolType.NONE, InitToolMaterials.UNBREAKABLE);
	
	public final ToolType toolType;
	public final ToolMaterial toolMat;
	public final List<DropInfo> drops = new ArrayList<DropInfo>();
	
	public HarvestInfo(ToolType toolType, Supplier<ToolMaterial> toolMat, DropInfo... drops) {
		this.toolType = toolType;
		this.toolMat = toolMat.get();
		
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
			return result == null ? ItemStack.EMPTY :
					R.nextInt(100) + 1 <= chance ? new ItemStack(result.getStagedItem(), max == min ? max : R.nextInt(max - min) + min) : ItemStack.EMPTY;
		}
	}
}
