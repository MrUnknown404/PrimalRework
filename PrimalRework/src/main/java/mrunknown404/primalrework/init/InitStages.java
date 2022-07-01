package mrunknown404.primalrework.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.Cache;
import mrunknown404.primalrework.utils.helpers.StageH;
import net.minecraftforge.fml.RegistryObject;

public class InitStages {
	private static final Map<Byte, Supplier<Stage>> STAGE_MAP = new HashMap<Byte, Supplier<Stage>>();
	
	public static final RegistryObject<Stage> STAGE_0 = register((byte) 0);
	public static final RegistryObject<Stage> STAGE_1 = register((byte) 1);
	public static final RegistryObject<Stage> STAGE_2 = register((byte) 2);
	public static final RegistryObject<Stage> STAGE_3 = register((byte) 3);
	public static final RegistryObject<Stage> STAGE_4 = register((byte) 4);
	
	public static final RegistryObject<Stage> DO_LATER = register((byte) (Byte.MAX_VALUE - 1));
	public static final RegistryObject<Stage> NO_SHOW = register(Byte.MAX_VALUE);
	
	public static RegistryObject<Stage> register(String name, byte id, Supplier<Stage> stage) {
		RegistryObject<Stage> s = InitRegistry.stage(name, stage);
		STAGE_MAP.put(id, s);
		return s;
	}
	
	public static RegistryObject<Stage> register(byte b) {
		Supplier<Stage> stage = () -> new Stage(b);
		RegistryObject<Stage> s = InitRegistry.stage("stage_" + b, stage);
		STAGE_MAP.put(b, s);
		return s;
	}
	
	public static Stage byID(byte id) {
		return STAGE_MAP.get(id).get();
	}
	
	private static final Cache<Byte, List<Stage>> PREV_STAGE_CACHE = new Cache<Byte, List<Stage>>();
	
	public static List<Stage> getStagesBeforeCurrent(boolean includeCurrentStage) {
		Stage curStage = StageH.getStage();
		if (PREV_STAGE_CACHE.is(curStage.id)) {
			return PREV_STAGE_CACHE.get();
		}
		
		List<Stage> stages = new ArrayList<Stage>();
		byte curStageID = curStage.id;
		for (RegistryObject<Stage> stage : InitRegistry.getStages()) {
			if (stage.get().id < curStage.id) {
				stages.add(stage.get());
			}
		}
		
		if (includeCurrentStage) {
			stages.add(curStage);
		}
		
		PREV_STAGE_CACHE.set(curStageID, stages);
		return stages;
	}
	
	public static Stage getNextStage() {
		Stage curStage = StageH.getStage();
		byte lastID = Byte.MAX_VALUE;
		
		for (RegistryObject<Stage> stage : InitRegistry.getStages()) {
			if (stage.get().id > curStage.id && stage.get().id < lastID) {
				lastID = stage.get().id;
			}
		}
		
		return STAGE_MAP.get(lastID).get();
	}
}
