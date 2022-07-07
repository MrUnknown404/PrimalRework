package mrunknown404.primalrework.items;

import java.util.Random;
import java.util.function.Supplier;

import mrunknown404.primalrework.init.InitBlocks;
import mrunknown404.primalrework.init.InitItems;
import mrunknown404.primalrework.init.InitItemGroups;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.ToolMaterial;
import mrunknown404.primalrework.utils.enums.ToolType;
import mrunknown404.primalrework.utils.helpers.BlockH;
import mrunknown404.primalrework.utils.helpers.RayTraceH;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.UseAction;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class SISimpleTool extends SIDamageable {
	public SISimpleTool(Supplier<Stage> stage, ToolType toolType, ToolMaterial toolMat) {
		super(stage, toolType, toolMat, InitItemGroups.TOOLS, ItemType.handheld);
	}
	
	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity entity0, LivingEntity entity1) {
		stack.hurtAndBreak(1, entity1, (e) -> e.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
		return true;
	}
	
	@Override
	public boolean mineBlock(ItemStack stack, World world, BlockState block, BlockPos pos, LivingEntity entity) {
		if (!world.isClientSide && block.getDestroySpeed(world, pos) != 0) {
			stack.hurtAndBreak(1, entity, (e) -> e.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
		}
		return true;
	}
	
	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		if (toolType == ToolType.AXE) {
			player.startUsingItem(hand);
			return ActionResult.consume(player.getItemInHand(hand));
		}
		
		return super.use(world, player, hand);
	}
	
	@Override
	public void releaseUsing(ItemStack stack, World world, LivingEntity entity, int partialTicks) {
		if (toolType == ToolType.AXE && entity instanceof PlayerEntity) {
			if (getUseDuration(stack) - partialTicks > 22) {
				PlayerEntity player = (PlayerEntity) entity;
				
				BlockRayTraceResult ray = RayTraceH.rayTrace(partialTicks, false);
				if (ray != null) {
					BlockPos pos = ray.getBlockPos();
					Block b = world.getBlockState(pos).getBlock();
					
					if (!world.isClientSide) {
						world.addFreshEntity(new ItemEntity(world, ray.getLocation().x, ray.getLocation().y, ray.getLocation().z,
								new ItemStack(InitItems.BARK.get(), 2 + new Random().nextInt(3))));
						world.playSound(null, pos, SoundEvents.WOOD_BREAK, SoundCategory.BLOCKS, 1, 0);
					}
					
					if (!player.isCreative()) {
						stack.hurtAndBreak(1, player, (e) -> e.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
					}
					
					if (b == InitBlocks.OAK_LOG.get()) {
						world.setBlock(pos, InitBlocks.STRIPPED_OAK_LOG.get().defaultBlockState().setValue(BlockStateProperties.AXIS,
								world.getBlockState(pos).getValue(BlockStateProperties.AXIS)), 11);
					} else if (b == InitBlocks.SPRUCE_LOG.get()) {
						world.setBlock(pos, InitBlocks.STRIPPED_SPRUCE_LOG.get().defaultBlockState().setValue(BlockStateProperties.AXIS,
								world.getBlockState(pos).getValue(BlockStateProperties.AXIS)), 11);
					} else if (b == InitBlocks.BIRCH_LOG.get()) {
						world.setBlock(pos, InitBlocks.STRIPPED_BIRCH_LOG.get().defaultBlockState().setValue(BlockStateProperties.AXIS,
								world.getBlockState(pos).getValue(BlockStateProperties.AXIS)), 11);
					} else if (b == InitBlocks.JUNGLE_LOG.get()) {
						world.setBlock(pos, InitBlocks.STRIPPED_JUNGLE_LOG.get().defaultBlockState().setValue(BlockStateProperties.AXIS,
								world.getBlockState(pos).getValue(BlockStateProperties.AXIS)), 11);
					} else if (b == InitBlocks.ACACIA_LOG.get()) {
						world.setBlock(pos, InitBlocks.STRIPPED_ACACIA_LOG.get().defaultBlockState().setValue(BlockStateProperties.AXIS,
								world.getBlockState(pos).getValue(BlockStateProperties.AXIS)), 11);
					} else if (b == InitBlocks.DARK_OAK_LOG.get()) {
						world.setBlock(pos, InitBlocks.STRIPPED_DARK_OAK_LOG.get().defaultBlockState().setValue(BlockStateProperties.AXIS,
								world.getBlockState(pos).getValue(BlockStateProperties.AXIS)), 11);
					}
				}
			}
		}
		
		super.releaseUsing(stack, world, entity, partialTicks);
	}
	
	@Override
	public ActionResultType useOn(ItemUseContext context) {
		if (toolType == ToolType.SHOVEL) {
			World w = context.getLevel();
			BlockPos pos = context.getClickedPos();
			Block b = w.getBlockState(pos).getBlock();
			
			if ((BlockH.canSupportPlant(b)) && context.getClickedFace() != Direction.DOWN && w.isEmptyBlock(pos.above())) {
				w.playSound(context.getPlayer(), pos, SoundEvents.HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
				
				if (!w.isClientSide) {
					w.setBlock(pos, Blocks.GRASS_PATH.defaultBlockState(), 11); //TODO switch to my grass path
					context.getItemInHand().hurtAndBreak(1, context.getPlayer(), (e) -> e.broadcastBreakEvent(context.getHand()));
				}
				
				return ActionResultType.sidedSuccess(w.isClientSide);
			}
		} else if (toolType == ToolType.HOE) {
			World w = context.getLevel();
			BlockPos pos = context.getClickedPos();
			Block b = w.getBlockState(pos).getBlock();
			
			if ((BlockH.canSupportPlant(b) || b == Blocks.GRASS_PATH) && context.getClickedFace() != Direction.DOWN && w.isEmptyBlock(pos.above())) { //TODO switch to my grass path
				w.playSound(context.getPlayer(), pos, SoundEvents.HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
				
				if (!w.isClientSide) {
					w.setBlock(pos, Blocks.FARMLAND.defaultBlockState(), 11); //TODO switch to my farmland
					context.getItemInHand().hurtAndBreak(1, context.getPlayer(), (e) -> e.broadcastBreakEvent(context.getHand()));
				}
				
				return ActionResultType.sidedSuccess(w.isClientSide);
			}
		}
		
		return super.useOn(context);
	}
	
	@Override
	public int getUseDuration(ItemStack stack) {
		return toolType == ToolType.AXE ? 72000 : 0;
	}
	
	@Override
	public UseAction getUseAnimation(ItemStack stack) {
		return toolType == ToolType.AXE ? UseAction.BOW : UseAction.NONE;
	}
	
	@Override
	public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
		return toolType == ToolType.AXE;
	}
}
