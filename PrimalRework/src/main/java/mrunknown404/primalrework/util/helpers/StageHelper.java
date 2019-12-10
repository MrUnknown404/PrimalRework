package mrunknown404.primalrework.util.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.jei.JEICompat;
import mrunknown404.primalrework.world.StageWorldSaveData;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class StageHelper {
	
	public static final Map<EnumStage, List<ItemStack>> ITEM_STAGE_MAP = new HashMap<EnumStage, List<ItemStack>>();
	
	private static EnumStage stage = EnumStage.stage0;
	private static World world;
	
	static {
		for (EnumStage stage : EnumStage.values()) {
			ITEM_STAGE_MAP.put(stage, new ArrayList<ItemStack>());
		}
	}
	
	public static void setStage(EnumStage stage) {
		StageHelper.stage = stage;
		StageWorldSaveData.load(world).markDirty();
		JEICompat.setupRecipesAndItems();
	}
	
	public static EnumStage getStage() {
		return stage;
	}
	
	public static void setStageFromNBT(EnumStage stage) {
		StageHelper.stage = stage;
		JEICompat.setupRecipesAndItems();
	}
	
	public static boolean hasAccessToStage(EnumStage stage) {
		return StageHelper.stage.id >= stage.id;
	}
	
	public static void setWorld(World world) {
		StageHelper.world = world;
		StageWorldSaveData.load(world);
	}
	
	public static void addStagedItem(EnumStage stage, Item item, int meta) {
		if (new ItemStack(item, 1, meta).isEmpty()) {
			System.out.println("EMPTY: " + item.getUnlocalizedName());
			return;
		}
		
		ITEM_STAGE_MAP.get(stage).add(new ItemStack(item, 1, meta));
	}
	
	public static void addStagedItem(EnumStage stage, Item item) {
		addStagedItem(stage, item, 0);
	}
	
	public static void addStagedItem(EnumStage stage, Block block, int meta) {
		if (new ItemStack(block, 1, meta).isEmpty()) {
			System.out.println("EMPTY: " + block.getUnlocalizedName());
			return;
		}
		
		ITEM_STAGE_MAP.get(stage).add(new ItemStack(block, 1, meta));
	}
	
	public static void addStagedItem(EnumStage stage, Block block) {
		addStagedItem(stage, block, 0);
	}
}
