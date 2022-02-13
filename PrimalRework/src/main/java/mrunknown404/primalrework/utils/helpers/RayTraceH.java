package mrunknown404.primalrework.utils.helpers;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockRayTraceResult;

public class RayTraceH {
	public static BlockRayTraceResult rayTrace(float partial, boolean allowLiquid) {
		return (BlockRayTraceResult) Minecraft.getInstance().getCameraEntity().pick(5, partial, allowLiquid);
	}
}
