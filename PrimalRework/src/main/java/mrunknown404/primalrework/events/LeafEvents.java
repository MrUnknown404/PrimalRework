package mrunknown404.primalrework.events;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import mrunknown404.primalrework.blocks.SBLeaves;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.NeighborNotifyEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

public class LeafEvents {
	private static final Cache<BlockPos, Integer> CACHE_BROKEN_BLOCKS = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS).maximumSize(256).build();
	private static final Random R = new Random();
	
	@SubscribeEvent
	public void tick(ServerTickEvent e) {
		if (e.side == LogicalSide.SERVER && e.phase == TickEvent.Phase.END) {
			LeafTickScheduler.tick();
		}
	}
	
	@SubscribeEvent
	public void onBreak(BreakEvent e) {
		if (!e.getWorld().isClientSide() && !(e.getPlayer() instanceof FakePlayer)) {
			CACHE_BROKEN_BLOCKS.put(e.getPos(), 0);
		}
	}
	
	@SubscribeEvent
	public void onNotifyNeighbors(NeighborNotifyEvent e) {
		if (CACHE_BROKEN_BLOCKS.getIfPresent(e.getPos()) != null) {
			ServerWorld world = (ServerWorld) e.getWorld();
			BlockState notifierState = e.getState();
			
			if (notifierState.getBlock().isAir(notifierState, world, e.getPos())) {
				for (Direction dir : e.getNotifiedSides()) {
					BlockPos offPos = e.getPos().offset(dir.getNormal());
					
					if (world.isLoaded(offPos) && world.getBlockState(offPos).getBlock() instanceof SBLeaves) {
						CACHE_BROKEN_BLOCKS.put(offPos, 0);
						LeafTickScheduler.PLANNED.add(new ScheduledTick(world, offPos, 10 + R.nextInt(10)));
					}
				}
			}
		}
	}
	
	private static class LeafTickScheduler {
		private static final List<ScheduledTick> PLANNED = new ArrayList<ScheduledTick>(), QUEUE = new ArrayList<ScheduledTick>();
		
		private static void tick() {
			if (!PLANNED.isEmpty()) {
				QUEUE.addAll(PLANNED);
				PLANNED.clear();
			}
			
			for (Iterator<ScheduledTick> it = QUEUE.iterator(); it.hasNext();) {
				ScheduledTick st = it.next();
				
				if (st.tick-- <= 0) {
					if (st.world.isLoaded(st.pos)) {
						BlockState state = st.world.getBlockState(st.pos);
						if (state.getBlock() instanceof SBLeaves) {
							state.tick(st.world, st.pos, st.world.getRandom());
							state.randomTick(st.world, st.pos, st.world.getRandom());
						}
					}
					
					it.remove();
				}
			}
		}
	}
	
	private static class ScheduledTick {
		private final ServerWorld world;
		private final BlockPos pos;
		private int tick;
		
		private ScheduledTick(ServerWorld world, BlockPos pos, int tick) {
			this.world = world;
			this.pos = pos;
			this.tick = tick;
		}
	}
}
