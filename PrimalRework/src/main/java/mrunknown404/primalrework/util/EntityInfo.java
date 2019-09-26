package mrunknown404.primalrework.util;

import mrunknown404.primalrework.init.ModEntities;
import net.minecraft.entity.EntityLiving;

public class EntityInfo {
	public final String name;
	public final Class<? extends EntityLiving> clazz;
	public final int id, range, color1, color2;
	
	public EntityInfo(String name, Class<? extends EntityLiving> clazz, int id, int range, int color1, int color2) {
		this.name = name;
		this.clazz = clazz;
		this.id = id;
		this.range = range;
		this.color1 = color1;
		this.color2 = color2;
		
		ModEntities.ENTITIES.add(this);
	}
}
