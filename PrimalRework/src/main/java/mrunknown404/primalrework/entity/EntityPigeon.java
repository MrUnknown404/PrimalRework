package mrunknown404.primalrework.entity;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityPigeon extends EntityParrot {

	public EntityPigeon(World worldIn) {
		super(worldIn);
		setSize(0.5f, 0.5f);
	}
	
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		return null;
	}
	
	@Override
	public float getEyeHeight() {
		return this.height * 0.8F;
	}
	
	/*
	@Override
	public SoundEvent getAmbientSound() {
		return super.getAmbientSound();
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return super.getHurtSound(source);
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return super.getDeathSound();
	}*/
}
