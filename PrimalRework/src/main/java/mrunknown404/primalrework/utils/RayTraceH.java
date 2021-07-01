package mrunknown404.primalrework.utils;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

public class RayTraceH {
	public static BlockRayTraceResult rayTrace(Entity entity, float partialTicks) {
		Vector3d eye = entity.getEyePosition(partialTicks), look = entity.getLookAngle();
		RayTraceResult ray = entity.getCommandSenderWorld()
				.clip(new RayTraceContext(eye, eye.add(look.x * 5, look.y * 5, look.z * 5), RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, entity));
		
		if (ray instanceof BlockRayTraceResult) {
			return (BlockRayTraceResult) ray;
		}
		
		return null;
	}
}
