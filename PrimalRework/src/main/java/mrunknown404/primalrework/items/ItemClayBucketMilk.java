package mrunknown404.primalrework.items;

import mrunknown404.primalrework.init.ModCreativeTabs;
import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.items.util.ItemDamageableBase;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;

public class ItemClayBucketMilk extends ItemDamageableBase {
	
	public ItemClayBucketMilk() {
		super("clay_bucket_milk", ModCreativeTabs.PRIMALREWORK_FOOD, EnumToolMaterial.clay, EnumStage.stage1);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entity) {
		if (!world.isRemote) {
			entity.curePotionEffects(stack);
		}
		
		if (entity instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) entity;
			CriteriaTriggers.CONSUME_ITEM.trigger(player, stack);
			player.addStat(StatList.getObjectUseStats(this));
		}
		
		if (entity instanceof EntityPlayer && !((EntityPlayer) entity).isCreative()) {
			stack.damageItem(1, entity);
		} else {
			return stack;
		}
		
		return stack.isEmpty() ? ItemStack.EMPTY : new ItemStack(ModItems.CLAY_BUCKET_EMPTY, 1, stack.getItemDamage());
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 32;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.DRINK;
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new FluidBucketWrapper(stack);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		playerIn.setActiveHand(hand);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(hand));
	}
}
