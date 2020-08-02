package mrunknown404.primalrework.world.storage;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.helpers.StageHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class WorldSaveDataStage extends WorldSavedData {
	
	private static final String DATA_NAME = Main.MOD_ID + "_StageData";
	
	public WorldSaveDataStage() {
		super(DATA_NAME);
	}
	
	public WorldSaveDataStage(String name) {
		super(name);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		StageHelper.setStageFromNBT(EnumStage.valueOf(nbt.getString("Stage")));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound c) {
		c.setString("Stage", StageHelper.getStage().toString());
		return c;
	}
	
	public static WorldSaveDataStage load(World world) {
		MapStorage storage = world.getMapStorage();
		WorldSaveDataStage instance = (WorldSaveDataStage) world.getMapStorage().getOrLoadData(WorldSaveDataStage.class, DATA_NAME);
		
		if (instance == null) {
			instance = new WorldSaveDataStage();
			storage.setData(DATA_NAME, instance);
		}
		
		return instance;
	}
}
