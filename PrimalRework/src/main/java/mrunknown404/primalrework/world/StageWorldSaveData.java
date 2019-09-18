package mrunknown404.primalrework.world;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.handlers.StageHandler;
import mrunknown404.primalrework.util.EnumStage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class StageWorldSaveData extends WorldSavedData {

	private static final String DATA_NAME = Main.MOD_ID + "_StageData";
	
	public StageWorldSaveData() {
		super(DATA_NAME);
	}
	
	public StageWorldSaveData(String name) {
		super(name);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		StageHandler.setStageFromNBT(EnumStage.valueOf(nbt.getString("Stage")));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound c) {
		c.setString("Stage", StageHandler.getStage().toString());
		return c;
	}
	
	public static StageWorldSaveData load(World world) {
		MapStorage storage = world.getMapStorage();
		StageWorldSaveData instance = (StageWorldSaveData) world.getMapStorage().getOrLoadData(StageWorldSaveData.class, DATA_NAME);
		
		if (instance == null) {
			instance = new StageWorldSaveData();
			storage.setData(DATA_NAME, instance);
		}
		
		return instance;
	}
}
