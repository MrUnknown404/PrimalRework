package mrunknown404.primalrework.init;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.entity.EntityPigeon;
import mrunknown404.primalrework.entity.render.RenderPigeon;
import mrunknown404.primalrework.util.EntityInfo;
import mrunknown404.primalrework.util.EntityRenderFactory;

public class ModEntities {
	public static final List<EntityInfo> ENTITIES = new ArrayList<EntityInfo>();
	
	private static final EntityInfo PIGEON = new EntityInfo("pigeon", EntityPigeon.class, 100, 32, 3289650, 6579300, new RenderPigeon(null));
}
