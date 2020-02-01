package mrunknown404.primalrework.init;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.entity.EntityPigeon;
import mrunknown404.unknownlibs.entity.EntityInfo;

public class ModEntities {
	public static final List<EntityInfo> ENTITIES = new ArrayList<EntityInfo>();
	
	public static final EntityInfo PIGEON = addEntity(new EntityInfo("pigeon", EntityPigeon.class, 100, 32, 3289650, 6579300));
	
	private static EntityInfo addEntity(EntityInfo e) {
		ENTITIES.add(e);
		return e;
	}
}
