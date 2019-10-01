package mrunknown404.primalrework.items;

import java.util.Map.Entry;

import javax.annotation.Nullable;

import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.items.util.ItemDamageableBase;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;

public class ItemClayBucket extends ItemDamageableBase {

	protected final Fluid fluid;
	
	public ItemClayBucket(@Nullable Fluid fluid) {
		super(fluid != null ? "clay_bucket_" + fluid.getName() : "clay_bucket_empty", EnumToolMaterial.clay, EnumStage.stage1);
		this.fluid = fluid;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		RayTraceResult ray = rayTrace(world, player, isEmpty());
		ItemStack item = player.getHeldItem(hand);
		ActionResult<ItemStack> ret = ForgeEventFactory.onBucketUse(player, world, item, ray);
		if (ret != null) {
			return ret;
		}
		
		if (ray == null) {
			return new ActionResult<ItemStack>(EnumActionResult.PASS, item);
		} else if (ray.typeOfHit != RayTraceResult.Type.BLOCK) {
			return new ActionResult<ItemStack>(EnumActionResult.PASS, item);
		} else {
			BlockPos pos = ray.getBlockPos();
			
			if (!world.isBlockModifiable(player, pos)) {
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, item);
			} else if (isEmpty()) {
				if (player.canPlayerEdit(pos.offset(ray.sideHit), ray.sideHit, item)) {
					for (Entry<Fluid, ItemClayBucket> f : ModItems.CLAY_BUCKETS.entrySet()) {
						if (f.getKey() != null && f.getKey().getBlock() != null && f.getKey().getBlock() == world.getBlockState(pos).getBlock()) {
							world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
							player.playSound(f.getKey().getFillSound(), 1f, 1f);
							player.addStat(StatList.getObjectUseStats(this));
							
							return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, fillBucket(item, player, f.getValue()));
						}
					}
					
					return new ActionResult<ItemStack>(EnumActionResult.FAIL, item);
				} else {
					return new ActionResult<ItemStack>(EnumActionResult.FAIL, item);
				}
			} else {
				BlockPos pos1 = world.getBlockState(pos).getBlock().isReplaceable(world, pos) && ray.sideHit == EnumFacing.UP ? pos : pos.offset(ray.sideHit);
				
				if (!player.canPlayerEdit(pos1, ray.sideHit, item)) {
					return new ActionResult<ItemStack>(EnumActionResult.FAIL, item);
				} else if (tryPlaceContainedLiquid(player, world, pos1)) {
					if (player instanceof EntityPlayerMP) {
						CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos1, item);
					}
					
					player.addStat(StatList.getObjectUseStats(this));
					
					if (player.isCreative()) {
						return new ActionResult<>(EnumActionResult.SUCCESS, item);
					} else {
						item.damageItem(1, player);
						
						if (item.isEmpty()) {
							return new ActionResult<>(EnumActionResult.SUCCESS, ItemStack.EMPTY);
						} else {
							return new ActionResult<>(EnumActionResult.SUCCESS, new ItemStack(ModItems.CLAY_BUCKET_EMPTY, 1, item.getItemDamage()));
						}
					}
				} else {
					return new ActionResult<ItemStack>(EnumActionResult.FAIL, item);
				}
			}
		}
	}
	
	private ItemStack fillBucket(ItemStack emptyBucket, EntityPlayer player, Item fullBucket) {
		if (player.isCreative()) {
			return emptyBucket;
		} else {
			return new ItemStack(fullBucket, 1, emptyBucket.getItemDamage());
		}
	}
	
	private boolean tryPlaceContainedLiquid(@Nullable EntityPlayer player, World world, BlockPos pos) {
		if (fluid != null) {
			IBlockState state = world.getBlockState(pos);
			boolean isSolid = state.getMaterial().isSolid();
			boolean isReplaceable = state.getBlock().isReplaceable(world, pos);
			
			if (!world.isAirBlock(pos) && isSolid && !isReplaceable) {
				return false;
			} else {
				if (world.provider.doesWaterVaporize() && fluid.doesVaporize(new FluidStack(fluid, Fluid.BUCKET_VOLUME))) {
					world.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
					
					for (int k = 0; k < 8; ++k) {
						world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, (double) pos.getX() + Math.random(), (double ) pos.getY() + Math.random(),
								(double) pos.getZ() + Math.random(), 0, 0, 0);
					}
				} else {
					if (!world.isRemote && (!isSolid || isReplaceable) && !state.getMaterial().isLiquid()) {
						world.destroyBlock(pos, true);
					}
					
					world.playSound(player, pos, fluid.getEmptySound(), SoundCategory.BLOCKS, 1f, 1f);
					world.setBlockState(pos, fluid.getBlock().getDefaultState(), 11);
					world.getBlockState(pos).neighborChanged(world, pos, world.getBlockState(pos).getBlock(), pos);
				}
				
				return true;
			}
		} else {
			return false;
		}
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
		return new FluidBucketWrapper(stack);
	}
	
	public boolean isEmpty() {
		return fluid == null;
	}
}
