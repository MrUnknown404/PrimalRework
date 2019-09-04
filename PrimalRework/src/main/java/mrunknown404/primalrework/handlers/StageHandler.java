package mrunknown404.primalrework.handlers;

import mrunknown404.primalrework.world.StageWorldSaveData;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class StageHandler {
	
	private static Stage stage;
	private static World world;
	
	public static void setStage(Stage stage) {
		StageHandler.stage = stage;
		StageWorldSaveData.load(world).markDirty();
	}
	
	public static Stage getStage() {
		return stage;
	}
	
	public static void setStageFromNBT(Stage stage) {
		StageHandler.stage = stage;
	}
	
	public static boolean hasAccessToStage(Stage stage) {
		return stage.id <= StageHandler.stage.id;
	}
	
	public static void setWorld(World world) {
		StageHandler.world = world;
		StageWorldSaveData.load(world);
	}
	
	public enum Stage {
		stage0("before", 0),
		stage1("early_stone", 1),
		stage2("late_stone", 2);
		
		public final ITextComponent name;
		public final int id;
		
		private Stage(String name, int id) {
			this.name = new TextComponentTranslation("stage." + name + ".name");
			this.id = id;
		}
		
		public String getName() {
			return name.getUnformattedText();
		}
		
		public static String[] getStringList() {
			String[] strs = new String[values().length];
			
			for (int i = 0; i < values().length; i++) {
				strs[i] = values()[i].toString();
			}
			
			return strs;
		}
	}
}
