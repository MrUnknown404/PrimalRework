package mrunknown404.primalrework.utils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class LeafTickScheduler {
	private static final List<ScheduledTick> PLANNED = new ArrayList<ScheduledTick>();
	private static final List<ScheduledTick> SCHEDULED = new ArrayList<ScheduledTick>();
	
	public static void schedule(ServerWorld world, BlockPos pos, int delay) {
		PLANNED.add(new ScheduledTick(world, pos, delay));
	}
	
	public static void tick() {
		if (!PLANNED.isEmpty()) {
			SCHEDULED.addAll(PLANNED);
			PLANNED.clear();
		}
		
		Iterator<ScheduledTick> it = SCHEDULED.iterator();
		while (it.hasNext()) {
			ScheduledTick st = it.next();
			
			if (--st.tick <= 0) {
				it.remove();
				
				ServerWorld world = st.worldReference.get();
				if (world != null && world.isLoaded(st.pos)) {
					BlockState state = world.getBlockState(st.pos);
					
					if (state.getBlock() instanceof LeavesBlock) {
						state.tick(world, st.pos, world.getRandom());
						state.randomTick(world, st.pos, world.getRandom());
					}
				}
			}
		}
	}
	
	private static class ScheduledTick {
		private final WeakReference<ServerWorld> worldReference;
		private final BlockPos pos;
		private int tick;
		
		public ScheduledTick(ServerWorld worldObj, BlockPos pos, int tick) {
			this.worldReference = new WeakReference<ServerWorld>(worldObj);
			this.pos = pos;
			this.tick = tick;
		}
	}
}
