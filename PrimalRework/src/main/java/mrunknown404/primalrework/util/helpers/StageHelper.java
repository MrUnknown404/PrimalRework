package mrunknown404.primalrework.util.helpers;

import java.util.HashMap;
import java.util.Map;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.util.ItemStackWrapper;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.jei.JEICompat;
import mrunknown404.primalrework.world.StageWorldSaveData;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class StageHelper {
	
	private static final Map<ItemStackWrapper, EnumStage> STAGED_ITEM_CACHE = new HashMap<ItemStackWrapper, EnumStage>();
	
	private static EnumStage stage = EnumStage.stage0;
	private static World world;
	
	private static EnumStage getItemStage(ItemStackWrapper item) {
		return STAGED_ITEM_CACHE.get(item);
	}
	
	public static EnumStage getItemStage(ItemStack item) {
		return getItemStage(new ItemStackWrapper(item));
	}
	
	public static EnumStage getItemStage(Item item) {
		return getItemStage(new ItemStackWrapper(item));
	}
	
	public static EnumStage getItemStage(Block block) {
		return getItemStage(new ItemStackWrapper(block));
	}
	
	public static EnumStage getItemStage(Item item, int meta) {
		return getItemStage(new ItemStackWrapper(item, meta));
	}
	
	public static EnumStage getItemStage(Block block, int meta) {
		return getItemStage(new ItemStackWrapper(block, meta));
	}
	
	public static void setStage(EnumStage stage) {
		StageHelper.stage = stage;
		StageWorldSaveData.load(world).markDirty();
		if (Main.isJEILoaded()) {
			JEICompat.setupRecipesAndItems();
		}
	}
	
	public static EnumStage getStage() {
		return stage;
	}
	
	public static void setStageFromNBT(EnumStage stage) {
		StageHelper.stage = stage;
		if (Main.isJEILoaded()) {
			JEICompat.setupRecipesAndItems();
		}
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
		
		STAGED_ITEM_CACHE.put(new ItemStackWrapper(item, meta), stage);
		if (Main.isJEILoaded()) {
			JEICompat.ITEM_STAGE_MAP.get(stage).add(new ItemStack(item, 1, meta));
		}
	}
	
	public static void addStagedItem(EnumStage stage, Item item) {
		addStagedItem(stage, item, 0);
	}
	
	public static void addStagedItem(EnumStage stage, Block block, int meta) {
		if (new ItemStack(block, 1, meta).isEmpty()) {
			System.out.println("EMPTY: " + block.getUnlocalizedName());
			return;
		}
		
		STAGED_ITEM_CACHE.put(new ItemStackWrapper(block, meta), stage);
		if (Main.isJEILoaded()) {
			JEICompat.ITEM_STAGE_MAP.get(stage).add(new ItemStack(block, 1, meta));
		}
	}
	
	public static void addStagedItem(EnumStage stage, Block block) {
		addStagedItem(stage, block, 0);
	}
}
