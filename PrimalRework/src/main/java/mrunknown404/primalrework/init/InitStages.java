package mrunknown404.primalrework.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mrunknown404.primalrework.api.registry.PRRegistries;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.Cache;
import mrunknown404.primalrework.world.savedata.WSDStage;
import net.minecraftforge.fml.RegistryObject;

public class InitStages {
	private static final Map<Byte, Stage> STAGE_MAP = new HashMap<Byte, Stage>();
	
	public static final RegistryObject<Stage> STAGE_0 = register((byte) 0);
	public static final RegistryObject<Stage> STAGE_1 = register((byte) 1);
	public static final RegistryObject<Stage> STAGE_2 = register((byte) 2);
	public static final RegistryObject<Stage> STAGE_3 = register((byte) 3);
	public static final RegistryObject<Stage> STAGE_4 = register((byte) 4);
	
	public static final RegistryObject<Stage> DO_LATER = register((byte) (Byte.MAX_VALUE - 1));
	public static final RegistryObject<Stage> NO_SHOW = register(Byte.MAX_VALUE);
	
	private static RegistryObject<Stage> register(byte id) {
		return InitRegistry.stage("stage_" + id, () -> new Stage(id));
	}
	
	static void load() {
		PRRegistries.STAGES.getValues().forEach(s -> STAGE_MAP.put(s.id, s));
	}
	
	public static Stage byID(byte id) {
		return STAGE_MAP.get(id);
	}
	
	private static final Cache<Byte, List<Stage>> PREV_STAGE_CACHE = new Cache<Byte, List<Stage>>();
	
	public static List<Stage> getStagesBeforeCurrent(boolean includeCurrentStage) {
		Stage curStage = WSDStage.getStage();
		if (PREV_STAGE_CACHE.is(curStage.id)) {
			return PREV_STAGE_CACHE.get();
		}
		
		List<Stage> stages = new ArrayList<Stage>();
		byte curStageID = curStage.id;
		for (Stage stage : PRRegistries.STAGES.getValues()) {
			if (stage.id < curStage.id) {
				stages.add(stage);
			}
		}
		
		if (includeCurrentStage) {
			stages.add(curStage);
		}
		
		PREV_STAGE_CACHE.set(curStageID, stages);
		return stages;
	}
	
	public static Stage getNextStage() {
		return getStageAfter(WSDStage.getStage());
	}
	
	public static Stage getStageAfter(Stage stage) {
		Stage curStage = stage;
		byte lastID = Byte.MAX_VALUE;
		
		for (Stage s : PRRegistries.STAGES.getValues()) {
			if (s.id > curStage.id && s.id < lastID) {
				lastID = s.id;
			}
		}
		
		return STAGE_MAP.get(lastID);
	}
}
