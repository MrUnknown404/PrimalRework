package mrunknown404.primalrework.utils.helpers;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockRayTraceResult;

public class RayTraceH {
	public static BlockRayTraceResult rayTrace(Entity entity, float partial, boolean allowLiquid) {
		return (BlockRayTraceResult) entity.pick(5, partial, allowLiquid);
	}
}
