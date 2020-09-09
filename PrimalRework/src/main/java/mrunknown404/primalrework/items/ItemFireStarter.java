package mrunknown404.primalrework.items;

import javax.annotation.Nullable;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.blocks.BlockDenseLog;
import mrunknown404.primalrework.init.InitBlocks;
import mrunknown404.primalrework.init.InitItems;
import mrunknown404.primalrework.items.util.ItemDamageable;
import mrunknown404.primalrework.network.message.FireStarterMessage;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFireStarter extends ItemDamageable {
	
	public ItemFireStarter() {
		super("fire_starter", EnumToolMaterial.wood, EnumStage.stage1);
		
		addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			@Override
			public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
				if (entity != null) {
					return entity.getActiveItemStack().getItem() != InitItems.FIRE_STARTER ? 0f : (stack.getMaxItemUseDuration() - entity.getItemInUseCount()) / 20f / 4f;
				}
				
				return 0f;
			}
		});
		
		addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			@Override
			public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
				return entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1f : 0f;
			}
		});
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		player.setActiveHand(hand);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entity, int timeLeft) {
		if (entity instanceof EntityPlayer && getMaxItemUseDuration(stack) - timeLeft >= getMaxItemUseDuration(stack) / 3600) {
			EntityPlayer player = (EntityPlayer) entity;
			
			RayTraceResult ray = rayTrace(world, player, false);
			if (ray != null && ray.typeOfHit == RayTraceResult.Type.BLOCK) {
				BlockPos clickPos = ray.getBlockPos();
				
				if (world.isBlockModifiable(player, clickPos)) {
					BlockPos targetPos = clickPos.offset(ray.sideHit);
					
					if (player.canPlayerEdit(targetPos, ray.sideHit, stack)) {
						boolean flag = world.getBlockState(targetPos.down(2)).getBlock() == InitBlocks.DENSE_LOG && BlockDenseLog.isValidCharcoalPit(world, targetPos.down(2));
						
						Main.NETWORK.sendToServer(new FireStarterMessage(player, targetPos, player.getActiveHand(), flag));
					}
				}
			}
		}
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 288000;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}
}
