package mrunknown404.primalrework.events;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import mrunknown404.primalrework.utils.LeafTickScheduler;
import mrunknown404.primalrework.utils.helpers.BlockH;
import net.minecraft.block.Block;
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
	private static final Cache<BlockPos, Integer> brokenBlockCache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS).maximumSize(200).build();
	private static final Random R = new Random();
	
	@SubscribeEvent
	public void tick(ServerTickEvent e) {
		if (e.side == LogicalSide.SERVER && e.phase == TickEvent.Phase.END) {
			LeafTickScheduler.tick();
		}
	}
	
	@SubscribeEvent
	public void onBreak(BreakEvent e) {
		if (!(e.getPlayer() instanceof FakePlayer) && !e.getWorld().isClientSide()) {
			brokenBlockCache.put(e.getPos(), 0);
		}
	}
	
	@SubscribeEvent
	public void onNotifyNeighbors(NeighborNotifyEvent e) { //TODO move this into SBLeaves
		if (brokenBlockCache.getIfPresent(e.getPos()) != null) {
			ServerWorld world = (ServerWorld) e.getWorld();
			
			BlockState notifierState = e.getState();
			Block b = notifierState.getBlock();
			
			if (b.isAir(notifierState, world, e.getPos())) {
				for (Direction direction : e.getNotifiedSides()) {
					BlockPos offPos = e.getPos().offset(direction.getNormal());
					
					if (world.isLoaded(offPos)) {
						BlockState state = world.getBlockState(offPos);
						
						if (BlockH.isLeaves(state.getBlock())) {
							brokenBlockCache.put(offPos, 0);
							LeafTickScheduler.schedule(world, offPos, 10 + R.nextInt(5));
						}
					}
				}
			}
		}
	}
}
