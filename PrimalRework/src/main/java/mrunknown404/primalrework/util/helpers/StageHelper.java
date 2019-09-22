package mrunknown404.primalrework.util.helpers;

import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.jei.JEICompat;
import mrunknown404.primalrework.world.StageWorldSaveData;
import net.minecraft.world.World;

public class StageHelper {
	
	private static EnumStage stage = EnumStage.stage0;
	private static World world;
	
	public static void setStage(EnumStage stage) {
		StageHelper.stage = stage;
		StageWorldSaveData.load(world).markDirty();
		JEICompat.setupRecipes();
	}
	
	public static EnumStage getStage() {
		return stage;
	}
	
	public static void setStageFromNBT(EnumStage stage) {
		StageHelper.stage = stage;
		JEICompat.setupRecipes();
	}
	
	public static boolean hasAccessToStage(EnumStage stage) {
		return stage.id <= StageHelper.stage.id;
	}
	
	public static void setWorld(World world) {
		StageHelper.world = world;
		StageWorldSaveData.load(world);
	}
}
