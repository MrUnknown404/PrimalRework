package mrunknown404.primalrework.handlers;

import mrunknown404.primalrework.util.EnumStage;
import mrunknown404.primalrework.util.jei.JEICompat;
import mrunknown404.primalrework.world.StageWorldSaveData;
import net.minecraft.world.World;

public class StageHandler {
	
	private static EnumStage stage = EnumStage.stage0;
	private static World world;
	
	public static void setStage(EnumStage stage) {
		StageHandler.stage = stage;
		StageWorldSaveData.load(world).markDirty();
		JEICompat.setupRecipes();
	}
	
	public static EnumStage getStage() {
		return stage;
	}
	
	public static void setStageFromNBT(EnumStage stage) {
		StageHandler.stage = stage;
		JEICompat.setupRecipes();
	}
	
	public static boolean hasAccessToStage(EnumStage stage) {
		return stage.id <= StageHandler.stage.id;
	}
	
	public static void setWorld(World world) {
		StageHandler.world = world;
		StageWorldSaveData.load(world);
	}
}
