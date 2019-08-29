package mrunknown404.primalrework.entity.ai;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.pathfinding.PathNavigateGround;

public class EntityAITemptFlying extends EntityAIBase {
	
	private final EntityCreature temptedEntity;
	private final double speed;
	private double targetX;
	private double targetY;
	private double targetZ;
	private double pitch;
	private double yaw;
	private EntityPlayer temptingPlayer;
	private int delayTemptCounter;
	private boolean isRunning;
	private final Set<Item> temptItem;
	private final boolean scaredByPlayerMovement;
	
	public EntityAITemptFlying(EntityCreature entity, boolean scaredByPlayer, double speed, Item... items) {
		this.temptedEntity = entity;
		this.speed = speed;
		this.temptItem = Sets.newHashSet(items);
		this.scaredByPlayerMovement = scaredByPlayer;
		this.setMutexBits(3);
		
		if (!(entity.getNavigator() instanceof PathNavigateFlying)) {
			throw new IllegalArgumentException("Unsupported mob type for TemptGoalFlying");
		}
	}
	
	public boolean shouldExecute() {
		if (delayTemptCounter > 0) {
			--delayTemptCounter;
			return false;
		} else {
			temptingPlayer = temptedEntity.world.getClosestPlayerToEntity(temptedEntity, 10.0D);
			
			return temptingPlayer == null ? false : isTempting(temptingPlayer.getHeldItemMainhand()) || isTempting(temptingPlayer.getHeldItemOffhand());
		}
	}
	
	private boolean isTempting(ItemStack stack) {
		return temptItem.contains(stack.getItem());
	}
	
	public boolean shouldContinueExecuting() {
		if (scaredByPlayerMovement) {
			if (temptedEntity.getDistanceSq(temptingPlayer) < 36.0D) {
				if (temptingPlayer.getDistanceSq(targetX, targetY, targetZ) > 0.01D) {
					return false;
				}
				
				if (Math.abs((double) temptingPlayer.rotationPitch - pitch) > 5.0D || Math.abs((double) temptingPlayer.rotationYaw - yaw) > 5.0D) {
					return false;
				}
			} else {
				targetX = temptingPlayer.posX;
				targetY = temptingPlayer.posY;
				targetZ = temptingPlayer.posZ;
			}
			
			pitch = (double) temptingPlayer.rotationPitch;
			yaw = (double) temptingPlayer.rotationYaw;
		}
		
		return shouldExecute();
	}
	
	public void startExecuting() {
		targetX = temptingPlayer.posX;
		targetY = temptingPlayer.posY;
		targetZ = temptingPlayer.posZ;
		isRunning = true;
	}
	
	public void resetTask() {
		temptingPlayer = null;
		temptedEntity.getNavigator().clearPath();
		delayTemptCounter = 100;
		isRunning = false;
	}
	
	public void updateTask() {
		temptedEntity.getLookHelper().setLookPositionWithEntity(temptingPlayer, (float) (temptedEntity.getHorizontalFaceSpeed() + 20), (float) temptedEntity.getVerticalFaceSpeed());
		
		if (temptedEntity.getDistanceSq(temptingPlayer) < 6.25D) {
			temptedEntity.getNavigator().clearPath();
		} else {
			temptedEntity.getNavigator().tryMoveToEntityLiving(temptingPlayer, speed);
		}
	}
	
	public boolean isRunning() {
		return isRunning;
	}
}
