package mrunknown404.primalrework.init;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import mrunknown404.primalrework.api.registry.PRRegistries;
import mrunknown404.primalrework.api.registry.PRRegistryObject;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.Cache;
import mrunknown404.primalrework.world.savedata.WSDStage;

public class InitStages {
	private static final Cache<Byte, List<Stage>> PREV_STAGE_CACHE = Cache.create();
	private static final Map<Byte, Stage> STAGE_MAP = new HashMap<Byte, Stage>();
	
	public static final PRRegistryObject<Stage> STAGE_BEFORE = register((byte) 0, "before");
	public static final PRRegistryObject<Stage> STAGE_STONE = register((byte) 1, "stone");
	public static final PRRegistryObject<Stage> STAGE_COPPER = register((byte) 2, "copper");
	public static final PRRegistryObject<Stage> STAGE_BRONZE = register((byte) 3, "bronze");
	public static final PRRegistryObject<Stage> STAGE_IRON = register((byte) 4, "iron");
	public static final PRRegistryObject<Stage> STAGE_STEAM = register((byte) 5, "steam");
	public static final PRRegistryObject<Stage> STAGE_STEEL = register((byte) 6, "steel");
	public static final PRRegistryObject<Stage> STAGE_BASIC_ELECTRIC = register((byte) 7, "basic_electric");
	public static final PRRegistryObject<Stage> STAGE_ELECTRIC = register((byte) 8, "electric");
	
	private static PRRegistryObject<Stage> register(byte id, String name) {
		return InitRegistry.stage("stage_" + name, () -> new Stage(id));
	}
	
	static void load() {
		PRRegistries.STAGES.forEach(s -> STAGE_MAP.put(s.get().id, s.get()));
	}
	
	public static Stage byID(byte id) {
		return STAGE_MAP.get(id);
	}
	
	public static List<Stage> getStagesBeforeCurrent(boolean includeCurrentStage) {
		Stage curStage = WSDStage.getStage();
		return PREV_STAGE_CACHE.computeIfAbsent(curStage.id,
				() -> PRRegistries.STAGES.stream().map(s -> s.get()).filter(s -> includeCurrentStage ? s.id <= curStage.id : s.id < curStage.id).collect(Collectors.toList()));
	}
	
	public static Stage getNextStage() {
		return getStageAfter(WSDStage.getStage());
	}
	
	public static Stage getStageAfter(Stage stage) {
		Stage curStage = stage;
		byte lastID = Byte.MAX_VALUE;
		
		for (PRRegistryObject<Stage> s : PRRegistries.STAGES) {
			if (s.get().id > curStage.id && s.get().id < lastID) {
				lastID = s.get().id;
			}
		}
		
		return STAGE_MAP.get(lastID);
	}
}
