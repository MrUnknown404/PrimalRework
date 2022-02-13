package mrunknown404.primalrework.events;

import java.util.Random;

import mrunknown404.primalrework.network.NetworkHandler;
import mrunknown404.primalrework.network.packets.PacketSyncStage;
import mrunknown404.primalrework.registries.PRBlocks;
import mrunknown404.primalrework.registries.PRItems;
import mrunknown404.primalrework.utils.NoAdvancementManager;
import mrunknown404.primalrework.utils.helpers.RayTraceH;
import mrunknown404.primalrework.utils.helpers.StageH;
import mrunknown404.primalrework.world.savedata.WSDQuests;
import mrunknown404.primalrework.world.savedata.WSDStage;
import net.minecraft.block.Block;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.resources.DataPackRegistries;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent.FarmlandTrampleEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class MiscEvents {
	private static final Random R = new Random();
	
	@SubscribeEvent
	public void onWorldStart(WorldEvent.Load e) {
		if (e.getWorld() instanceof ServerWorld) {
			WSDStage.get((ServerWorld) e.getWorld());
			WSDQuests.get((ServerWorld) e.getWorld());
		}
	}
	
	@SubscribeEvent
	public void onPlayerJoin(PlayerLoggedInEvent e) {
		NetworkHandler.sendPacketToTarget((ServerPlayerEntity) e.getPlayer(), new PacketSyncStage(StageH.getStage()));
	}
	
	@SubscribeEvent
	public void onTrample(FarmlandTrampleEvent e) {
		e.setCanceled(true);
	}
	
	@SubscribeEvent
	public void onBonemeal(BonemealEvent e) {
		e.setCanceled(true);
	}
	
	@SubscribeEvent
	public void onBlockPunch(PlayerInteractEvent.LeftClickBlock e) {
		if (e.getPlayer().isCreative() || e.getPlayer().swinging) {
			return;
		}
		
		Block b = e.getWorld().getBlockState(e.getPos()).getBlock();
		
		if (b == PRBlocks.STONE.get() || b == PRBlocks.COBBLESTONE.get()) {
			BlockRayTraceResult result = RayTraceH.rayTrace(1, false);
			ItemStack item = e.getItemStack();
			Vector3d hit = result.getLocation();
			Item itemToAdd = null;
			int count = 1 + R.nextInt(1);
			
			if (item.getItem() == PRItems.FLINT.get()) {
				e.getWorld().playSound(e.getPlayer(), e.getPos(), SoundEvents.STONE_BREAK, SoundCategory.PLAYERS, 1, 2);
				itemToAdd = PRItems.KNAPPED_FLINT.get();
			} else if (item.getItem() == PRItems.KNAPPED_FLINT.get()) {
				e.getWorld().playSound(e.getPlayer(), e.getPos(), SoundEvents.STONE_BREAK, SoundCategory.PLAYERS, 1, 2);
				itemToAdd = PRItems.FLINT_POINT.get();
			} else if (item.getItem() == Items.BONE) {
				e.getWorld().playSound(e.getPlayer(), e.getPos(), SoundEvents.STONE_BREAK, SoundCategory.PLAYERS, 1, 2);
				itemToAdd = PRItems.BONE_SHARD.get();
			} else if (item.getItem() == PRItems.BONE_SHARD.get()) {
				e.getWorld().playSound(e.getPlayer(), e.getPos(), SoundEvents.STONE_BREAK, SoundCategory.PLAYERS, 1, 2);
				itemToAdd = Items.BONE_MEAL;
			}
			
			if (itemToAdd != null && !e.getWorld().isClientSide && R.nextInt(3) == 0) {
				item.shrink(1);
				e.getWorld().addFreshEntity(new ItemEntity(e.getWorld(), hit.x, hit.y, hit.z, new ItemStack(itemToAdd, count)));
				
				if (item.getItem() == Items.BONE && R.nextInt(3) == 0) {
					e.getWorld().addFreshEntity(new ItemEntity(e.getWorld(), hit.x, hit.y, hit.z, new ItemStack(Items.BONE_MEAL)));
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onReload(AddReloadListenerEvent e) {
		ObfuscationReflectionHelper.setPrivateValue(DataPackRegistries.class, e.getDataPackRegistries(), new NoAdvancementManager(e.getDataPackRegistries().getPredicateManager()),
				"advancements");
	}
}
