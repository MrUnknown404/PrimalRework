package mrunknown404.primalrework.helpers;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.network.NetworkHandler;
import mrunknown404.primalrework.network.packets.PacketSyncStage;
import mrunknown404.primalrework.stage.storage.IStageData;
import mrunknown404.primalrework.stage.storage.StageDataProvider;
import mrunknown404.primalrework.utils.enums.EnumStage;
import net.minecraft.world.World;

public class StageH {
	private static EnumStage stage;
	private static World world;
	
	public static void setWorld(World world) {
		StageH.world = world;
	}
	
	public static void loadWorld() {
		IStageData data = world.getCapability(StageDataProvider.capability).orElseThrow(IllegalStateException::new);
		StageH.stage = data.getStage();
	}
	
	public static void setStage(EnumStage stage) {
		IStageData data = world.getCapability(StageDataProvider.capability).orElseThrow(IllegalStateException::new);
		data.setStage(stage);
		StageH.stage = data.getStage();
		
		NetworkHandler.sendPacketToAll(new PacketSyncStage(StageH.stage));
	}
	
	public static void loadStage(EnumStage stage) {
		StageH.stage = stage;
	}
	
	public static boolean hasAccessToStage(EnumStage stage) {
		return StageH.stage.id >= stage.id;
	}
	
	public static EnumStage getNextStage() {
		return stage == EnumStage.no_show ? stage : EnumStage.values()[stage.ordinal() + 1];
	}
	
	public static EnumStage getStage() {
		return stage;
	}
	
	public static List<EnumStage> getAllPrevStages() {
		List<EnumStage> list = new ArrayList<EnumStage>();
		for (EnumStage stage : EnumStage.values()) {
			list.add(stage);
			if (stage.id >= StageH.stage.id) {
				break;
			}
		}
		
		return list;
	}
}
