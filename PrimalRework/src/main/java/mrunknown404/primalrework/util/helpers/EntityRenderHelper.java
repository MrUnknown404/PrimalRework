package mrunknown404.primalrework.util.helpers;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import mrunknown404.primalrework.entity.EntityItemDrop;
import mrunknown404.primalrework.entity.EntityPigeon;
import mrunknown404.primalrework.entity.render.RenderItemDrop;
import mrunknown404.primalrework.entity.render.RenderPigeon;
import mrunknown404.unknownlibs.entity.render.RenderBase;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class EntityRenderHelper {
	public static final Map<Class<? extends EntityLiving>, RenderBase<? extends EntityLiving>> ENTITY_RENDERS = new HashMap<Class<? extends EntityLiving>, RenderBase<? extends EntityLiving>>();
	
	public static final RenderBase<? extends EntityLiving> PIGEON = addRender(EntityPigeon.class, new RenderPigeon());
	
	public static void registerEntityRenderers() {
		for (Entry<Class<? extends EntityLiving>, RenderBase<? extends EntityLiving>> info : ENTITY_RENDERS.entrySet()) {
			mrunknown404.unknownlibs.entity.render.EntityRenderHelper.registerRender(info.getKey(), (RenderBase<? super EntityLiving>) info.getValue());
		}
		
		RenderingRegistry.registerEntityRenderingHandler(EntityItemDrop.class, new RenderItemDrop.Factory());
	}
	
	private static RenderBase<? extends EntityLiving> addRender(Class<? extends EntityLiving> clazz, RenderBase<? extends EntityLiving> render) {
		ENTITY_RENDERS.put(clazz, render);
		return render;
	}
}
