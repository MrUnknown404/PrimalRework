package mrunknown404.primalrework.entity;

import mrunknown404.primalrework.entity.ai.EntityAITemptFlying;
import mrunknown404.primalrework.handlers.SoundHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollow;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWaterFlying;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityFlyHelper;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityFlying;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityPigeon extends EntityAnimal implements EntityFlying {

	private float flap;
	private float flapSpeed;
	private float oFlapSpeed;
	private float oFlap;
	private float flapping = 1.0F;
	
	public EntityPigeon(World worldIn) {
		super(worldIn);
		setSize(0.5f, 0.5f);
		moveHelper = new EntityFlyHelper(this);
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		calculateFlapping();
	}
	
	@Override
	protected void initEntityAI() {
		tasks.addTask(0, new EntityAIPanic(this, 1.25D));
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(2, new EntityAIMate(this, 1.0D));
		tasks.addTask(3, new EntityAITemptFlying(this, false, 1, Items.BREAD));
		//tasks.addTask(4, new EntityAIFollowParent(this, 1.25D));
		tasks.addTask(5, new EntityAIWanderAvoidWaterFlying(this, 1.0D));
		tasks.addTask(5, new EntityAIFollow(this, 1.0D, 3.0F, 7.0F));
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
		getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.6D);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
	}
	
	@Override
	protected PathNavigate createNavigator(World worldIn) {
		PathNavigateFlying pathnavigateflying = new PathNavigateFlying(this, worldIn);
		pathnavigateflying.setCanOpenDoors(false);
		pathnavigateflying.setCanFloat(true);
		pathnavigateflying.setCanEnterDoors(true);
		return pathnavigateflying;
	}
	
	private void calculateFlapping() {
		oFlap = flap;
		oFlapSpeed = flapSpeed;
		flapSpeed = (float) ((double) flapSpeed + (double) (onGround ? -1 : 4) * 0.3D);
		flapSpeed = MathHelper.clamp(flapSpeed, 0.0F, 1.0F);
		
		if (!onGround && flapping < 1.0F) {
			flapping = 1.0F;
		}
		
		flapping = (float) ((double) flapping * 0.9D);
		
		if (!onGround && motionY < 0.0D) {
			motionY *= 0.6D;
		}
		
		flap += flapping * 2.0F;
	}
	
	@Override
	public boolean getCanSpawnHere() {
		BlockPos blockpos = new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.getEntityBoundingBox().minY), MathHelper.floor(this.posZ));
		Block block = this.world.getBlockState(blockpos.down()).getBlock();
		return block instanceof BlockLeaves || block == Blocks.GRASS || block instanceof BlockLog || block == Blocks.AIR && this.world.getLight(blockpos) > 8 && super.getCanSpawnHere();
	}
	
	@Override public void fall(float distance, float damageMultiplier) {}
	@Override protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {}
	
	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return stack.getItem() == Items.BREAD;
	}
	
	@Override
	public float getEyeHeight() {
		return this.height * 0.8F;
	}
	
	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return new EntityPigeon(world);
	}
	
	@Override
	protected ResourceLocation getLootTable() {
		return LootTableList.ENTITIES_PARROT;
	}
	
	@Override
	public SoundEvent getAmbientSound() {
		return SoundHandler.ENTITY_PIGEON_AMBIENT;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundHandler.ENTITY_PIGEON_HURT;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return SoundHandler.ENTITY_PIGEON_DEATH;
	}
	
	@Override
	protected void playStepSound(BlockPos pos, Block blockIn) {
		playSound(SoundEvents.ENTITY_PARROT_STEP, 0.15F, 1.0F);
	}
	
	@Override
	protected float playFlySound(float p_191954_1_) {
		playSound(SoundEvents.ENTITY_PARROT_FLY, 0.75F, 1.0F);
		return p_191954_1_ + flapSpeed / 2.0F;
	}
	
	@Override
	protected boolean makeFlySound() {
		return true;
	}
	
	@Override
	protected float getSoundPitch() {
		return (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F;
	}
	
	@Override
	public SoundCategory getSoundCategory() {
		return SoundCategory.NEUTRAL;
	}
	
	public boolean isFlying() {
		return !onGround;
	}
}
