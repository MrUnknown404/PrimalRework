package mrunknown404.primalrework.entity;

import java.lang.reflect.Field;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class EntityItemDrop extends EntityItem {
	
	private int age;
	private int pickupDelay;
	
	public EntityItemDrop(World world) {
		super(world);
		hoverStart = rand.nextFloat();
	}
	
	public EntityItemDrop(World world, double x, double y, double z, ItemStack stack) {
		super(world, x, y, z, stack);
		hoverStart = rand.nextFloat();
		
		Field age = ReflectionHelper.findField(EntityItem.class, "age", "field_70292_b");
		Field pickupDelay = ReflectionHelper.findField(EntityItem.class, "pickupDelay", "field_145804_b");
		age.setAccessible(true);
		pickupDelay.setAccessible(true);
		
		try {
			this.age = age.getInt(this);
			this.pickupDelay = pickupDelay.getInt(this);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public EntityItemDrop(EntityItem original) {
		this(original.world, original.posX, original.posY, original.posZ, original.getItem());
		
		NBTTagCompound oldT = new NBTTagCompound();
		original.writeEntityToNBT(oldT);
		readEntityFromNBT(oldT);
		
		String thrower = original.getThrower();
		Entity entity = (thrower == null || thrower.length() == 0) ? null : original.world.getPlayerEntityByName(thrower);
		double tossSpd = entity != null && entity.isSprinting() ? 1.75 : 1;
		
		motionX = original.motionX * tossSpd;
		motionY = original.motionY * tossSpd;
		motionZ = original.motionZ * tossSpd;
	}
	
	@Override
	public void onUpdate() {
		if (getItem().getItem().onEntityItemUpdate(this)) {
			return;
		}
		
		if (getItem().isEmpty()) {
			setDead();
		} else {
			if (!world.isRemote) {
				setFlag(6, isGlowing());
			}
			
			onEntityUpdate();
			
			if (pickupDelay > 0 && pickupDelay != 32767) {
				--pickupDelay;
			}
			
			prevPosX = posX;
			prevPosY = posY;
			prevPosZ = posZ;
			double d0 = motionX;
			double d1 = motionY;
			double d2 = motionZ;
			
			if (!hasNoGravity()) {
				if (checkInWater()) {
					IBlockState bsAbove = world.getBlockState(new BlockPos(MathHelper.floor(posX), MathHelper.floor(posY) + 1, MathHelper.floor(posZ)));
					boolean liqAbove = bsAbove.getBlock() instanceof BlockLiquid || bsAbove.getBlock() instanceof IFluidBlock;
					
					if (!liqAbove) {
						if (posY <= MathHelper.floor(posY + 0.25)) { //FIXME fix this mess if possible
							motionY += Math.min(0.01D, 0.01D - motionY);
						} else {
							if (motionY < 0) {
								motionY /= 2;
							}
							motionY += 0.0025;
							motionY = MathHelper.clamp(motionY, Integer.MIN_VALUE, 0.05);
						}
					} else {
						if (motionY < 0) {
							motionY /= 2;
						}
						motionY += 0.0025;
						motionY = MathHelper.clamp(motionY, Integer.MIN_VALUE, 0.05);
					}
					
					motionX = MathHelper.clamp(motionX, -0.1D, 0.1D);
					motionZ = MathHelper.clamp(motionZ, -0.1D, 0.1D);
				} else {
					motionY -= 0.03999999910593033D;
				}
				
			}
			
			if (world.isRemote) {
				noClip = false;
			} else {
				noClip = pushOutOfBlocks(posX, (getEntityBoundingBox().minY + getEntityBoundingBox().maxY) / 2.0D, posZ);
			}
			
			move(MoverType.SELF, motionX, motionY, motionZ);
			boolean flag = (int) prevPosX != (int) posX || (int) prevPosY != (int) posY || (int) prevPosZ != (int) posZ;
			
			if (flag || ticksExisted % 25 == 0) {
				if (world.getBlockState(new BlockPos(this)).getMaterial() == Material.LAVA) {
					motionY = 0.20000000298023224D;
					motionX = (rand.nextFloat() - rand.nextFloat()) * 0.2F;
					motionZ = (rand.nextFloat() - rand.nextFloat()) * 0.2F;
					playSound(SoundEvents.ENTITY_GENERIC_BURN, 0.4F, 2.0F + rand.nextFloat() * 0.4F);
				}
				
				if (!world.isRemote) {
					searchForOtherItemsNearby();
				}
			}
			
			float f = 0.98F;
			
			if (onGround) {
				BlockPos underPos = new BlockPos(MathHelper.floor(posX), MathHelper.floor(getEntityBoundingBox().minY) - 1, MathHelper.floor(posZ));
				IBlockState underState = world.getBlockState(underPos);
				f = underState.getBlock().getSlipperiness(underState, world, underPos, this) * 0.98F;
			}
			
			motionX *= f;
			motionY *= 0.9800000190734863D;
			motionZ *= f;
			
			if (onGround) {
				motionY *= -0.5D;
			}
			
			if (age != -32768) {
				++age;
			}
			
			handleWaterMovement();
			
			if (!world.isRemote) {
				double d3 = motionX - d0;
				double d4 = motionY - d1;
				double d5 = motionZ - d2;
				double d6 = d3 * d3 + d4 * d4 + d5 * d5;
				
				if (d6 > 0.01D) {
					isAirBorne = true;
				}
			}
			
			ItemStack item = getItem();
			
			if (!world.isRemote && age >= lifespan) {
				int hook = ForgeEventFactory.onItemExpire(this, item);
				if (hook < 0) {
					setDead();
				} else {
					lifespan += hook;
				}
			}
			if (item.isEmpty()) {
				setDead();
			}
		}
	}
	
	private boolean checkInWater() {
		AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
		int i = MathHelper.floor(axisalignedbb.minX);
		int j = MathHelper.ceil(axisalignedbb.maxX);
		int k = MathHelper.floor(axisalignedbb.minY);
		int l = MathHelper.ceil(axisalignedbb.minY + 0.1d);
		int i1 = MathHelper.floor(axisalignedbb.minZ);
		int j1 = MathHelper.ceil(axisalignedbb.maxZ);
		boolean flag = false;
		double waterLevel = Double.MIN_VALUE;
		BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();
		
		try {
			for (int k1 = i; k1 < j; ++k1) {
				for (int l1 = k; l1 < l; ++l1) {
					for (int i2 = i1; i2 < j1; ++i2) {
						blockpos$pooledmutableblockpos.setPos(k1, l1, i2);
						IBlockState iblockstate = this.world.getBlockState(blockpos$pooledmutableblockpos);
						
						if (iblockstate.getMaterial() == Material.WATER) {
							float f = BlockLiquid.getLiquidHeight(iblockstate, this.world, blockpos$pooledmutableblockpos);
							waterLevel = Math.max(f, waterLevel);
							flag |= axisalignedbb.minY < f;
						}
					}
				}
			}
		} finally {
			blockpos$pooledmutableblockpos.release();
		}
		
		return flag;
	}
	
	private void searchForOtherItemsNearby() {
		for (EntityItemDrop entityitem : world.getEntitiesWithinAABB(EntityItemDrop.class, getEntityBoundingBox().grow(0.5D, 0.0D, 0.5D))) {
			combineItems(entityitem);
		}
	}
	
	private boolean combineItems(EntityItemDrop other) {
		if (other == this) {
			return false;
		} else if (other.isEntityAlive() && isEntityAlive()) {
			ItemStack itemstack = getItem();
			ItemStack itemstack1 = other.getItem();
			
			if (pickupDelay != 32767 && other.pickupDelay != 32767) {
				if (age != -32768 && other.age != -32768) {
					if (itemstack1.getItem() != itemstack.getItem()) {
						return false;
					} else if (itemstack1.hasTagCompound() ^ itemstack.hasTagCompound()) {
						return false;
					} else if (itemstack1.hasTagCompound() && !itemstack1.getTagCompound().equals(itemstack.getTagCompound())) {
						return false;
					} else if (itemstack1.getItem() == null) {
						return false;
					} else if (itemstack1.getItem().getHasSubtypes() && itemstack1.getMetadata() != itemstack.getMetadata()) {
						return false;
					} else if (itemstack1.getCount() < itemstack.getCount()) {
						return other.combineItems(this);
					} else if (itemstack1.getCount() + itemstack.getCount() > itemstack1.getMaxStackSize()) {
						return false;
					} else if (!itemstack.areCapsCompatible(itemstack1)) {
						return false;
					} else {
						itemstack1.grow(itemstack.getCount());
						other.pickupDelay = Math.max(other.pickupDelay, pickupDelay);
						other.age = Math.min(other.age, age);
						other.setItem(itemstack1);
						setDead();
						return true;
					}
				}
				
				return false;
			}
			
			return false;
		} else {
			return false;
		}
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setShort("Age", (short) age);
		compound.setShort("PickupDelay", (short) pickupDelay);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		age = compound.getShort("Age");
		
		if (compound.hasKey("PickupDelay")) {
			pickupDelay = compound.getShort("PickupDelay");
		}
	}
	
	@Override
	public void onCollideWithPlayer(EntityPlayer entityIn) {
		if (!world.isRemote) {
			if (pickupDelay > 0) {
				return;
			}
			
			ItemStack itemstack = getItem();
			Item item = itemstack.getItem();
			int i = itemstack.getCount();
			
			int hook = ForgeEventFactory.onItemPickup(this, entityIn);
			if (hook < 0)
				return;
			ItemStack clone = itemstack.copy();
			
			if (pickupDelay <= 0 && (getOwner() == null || lifespan - age <= 200 || getOwner().equals(entityIn.getName())) &&
					(hook == 1 || i <= 0 || entityIn.inventory.addItemStackToInventory(itemstack) || clone.getCount() > getItem().getCount())) {
				clone.setCount(clone.getCount() - getItem().getCount());
				FMLCommonHandler.instance().firePlayerItemPickupEvent(entityIn, this, clone);
				
				if (itemstack.isEmpty()) {
					entityIn.onItemPickup(this, i);
					setDead();
					itemstack.setCount(i);
				}
				
				entityIn.addStat(StatList.getObjectsPickedUpStats(item), i);
			}
		}
	}
	
	@Override
	public int getAge() {
		return age;
	}
	
	@Override
	public void setDefaultPickupDelay() {
		pickupDelay = 10;
	}
	
	@Override
	public void setNoPickupDelay() {
		pickupDelay = 0;
	}
	
	@Override
	public void setInfinitePickupDelay() {
		pickupDelay = 32767;
	}
	
	@Override
	public void setPickupDelay(int ticks) {
		pickupDelay = ticks;
	}
	
	@Override
	public boolean cannotPickup() {
		return pickupDelay > 0;
	}
	
	@Override
	public void setNoDespawn() {
		age = -6000;
	}
	
	@Override
	public void makeFakeItem() {
		setInfinitePickupDelay();
		age = getItem().getItem().getEntityLifespan(getItem(), world) - 1;
	}
	
	@Override
	public float getCollisionBorderSize() {
		return MathHelper.clamp(0.2f * (onGround ? 1f : 2f), 0.01f, 1f);
	}
}
