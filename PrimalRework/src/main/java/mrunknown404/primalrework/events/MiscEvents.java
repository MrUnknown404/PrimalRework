package mrunknown404.primalrework.events;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

import com.google.gson.JsonElement;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.init.InitBlocks;
import mrunknown404.primalrework.init.InitItems;
import mrunknown404.primalrework.network.packets.toclient.PSyncAllQuests;
import mrunknown404.primalrework.network.packets.toclient.PSyncStage;
import mrunknown404.primalrework.utils.helpers.RayTraceH;
import mrunknown404.primalrework.world.savedata.WSDQuestStates;
import mrunknown404.primalrework.world.savedata.WSDStage;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.block.Block;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.DataPackRegistries;
import net.minecraft.resources.IResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
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
		if (((World) e.getWorld()).dimension() != World.OVERWORLD || !(e.getWorld() instanceof ServerWorld)) {
			return;
		}
		
		MinecraftServer server = ((ServerWorld) e.getWorld()).getServer();
		WSDStage.get(server);
		WSDQuestStates.get(server);
	}
	
	@SubscribeEvent
	public void onPlayerJoin(PlayerLoggedInEvent e) {
		PlayerEntity player = e.getPlayer();
		
		PrimalRework.NETWORK.sendPacketToTarget((ServerPlayerEntity) player, new PSyncStage(WSDStage.getStage()));
		PrimalRework.NETWORK.sendPacketToTarget((ServerPlayerEntity) player, PSyncAllQuests.create());
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
		
		if (b == InitBlocks.STONE.get() || b == InitBlocks.COBBLESTONE.get()) {
			ItemStack item = e.getItemStack();
			Item itemToAdd = null;
			
			if (item.getItem() == InitItems.FLINT.get()) {
				itemToAdd = InitItems.KNAPPED_FLINT.get();
			} else if (item.getItem() == InitItems.KNAPPED_FLINT.get()) {
				itemToAdd = InitItems.FLINT_POINT.get();
			} else if (item.getItem() == Items.BONE) {
				itemToAdd = InitItems.BONE_SHARD.get();
			} else if (item.getItem() == InitItems.BONE_SHARD.get()) {
				itemToAdd = Items.BONE_MEAL; //TODO switch to my bone dust
			}
			
			if (itemToAdd != null) {
				e.getWorld().playSound(e.getPlayer(), e.getPos(), SoundEvents.STONE_BREAK, SoundCategory.PLAYERS, 1, 2);
				
				try (World w = e.getWorld()) {
					if (w.isClientSide && R.nextInt(3) == 0) {
						item.shrink(1);
						
						Vector3d hit = RayTraceH.rayTrace(e.getPlayer(), 1, false).getLocation();
						e.getWorld().addFreshEntity(new ItemEntity(e.getWorld(), hit.x, hit.y, hit.z, new ItemStack(itemToAdd, 1 + R.nextInt(1))));
						
						if (item.getItem() == Items.BONE && R.nextInt(3) == 0) { //TODO switch to my bone
							e.getWorld().addFreshEntity(new ItemEntity(e.getWorld(), hit.x, hit.y, hit.z, new ItemStack(Items.BONE_MEAL))); //TODO switch to my bone dust
						}
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onReload(AddReloadListenerEvent e) {
		ObfuscationReflectionHelper.setPrivateValue(DataPackRegistries.class, e.getDataPackRegistries(), new AdvancementManager(e.getDataPackRegistries().getPredicateManager()) {
			@Override
			protected void apply(Map<ResourceLocation, JsonElement> p_212853_1_, IResourceManager p_212853_2_, IProfiler p_212853_3_) {
				
			}
		}, "field_240958_h_"); //advancements
	}
}
