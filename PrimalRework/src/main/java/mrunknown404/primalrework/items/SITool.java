package mrunknown404.primalrework.items;

import java.util.Random;

import mrunknown404.primalrework.helpers.RayTraceH;
import mrunknown404.primalrework.items.utils.SIDamageable;
import mrunknown404.primalrework.registries.PRBlocks;
import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.registries.PRItems;
import mrunknown404.primalrework.utils.enums.EnumStage;
import mrunknown404.primalrework.utils.enums.EnumToolMaterial;
import mrunknown404.primalrework.utils.enums.EnumToolType;
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

public class SITool extends SIDamageable {
	public SITool(EnumStage stage, EnumToolType toolType, EnumToolMaterial toolMat) {
		super(toolMat.toString() + "_" + toolType.toString(), stage, toolType, toolMat, PRItemGroups.TOOLS, toolType == EnumToolType.saw ? ItemType.handheld_rod : ItemType.handheld);
	}
	
	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity entity0, LivingEntity entity1) {
		stack.hurtAndBreak(1, entity1, (e) -> {
			e.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
		});
		return true;
	}
	
	@Override
	public boolean mineBlock(ItemStack stack, World world, BlockState block, BlockPos pos, LivingEntity entity) {
		if (!world.isClientSide && block.getDestroySpeed(world, pos) != 0) {
			stack.hurtAndBreak(1, entity, (e) -> {
				e.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
			});
		}
		return true;
	}
	
	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		if (toolType == EnumToolType.axe) {
			player.startUsingItem(hand);
			return ActionResult.consume(player.getItemInHand(hand));
		}
		
		return super.use(world, player, hand);
	}
	
	@Override
	public void releaseUsing(ItemStack stack, World world, LivingEntity entity, int partialTicks) {
		if (toolType == EnumToolType.axe && entity instanceof PlayerEntity) {
			if (getUseDuration(stack) - partialTicks > 22) {
				PlayerEntity player = (PlayerEntity) entity;
				
				BlockRayTraceResult ray = RayTraceH.rayTrace(player, partialTicks);
				if (ray != null) {
					BlockPos pos = ray.getBlockPos();
					Block b = world.getBlockState(pos).getBlock();
					
					if (!world.isClientSide) {
						world.addFreshEntity(new ItemEntity(world, ray.getLocation().x, ray.getLocation().y, ray.getLocation().z,
								new ItemStack(PRItems.BARK.get(), 2 + new Random().nextInt(3))));
						world.playSound(null, pos, SoundEvents.WOOD_BREAK, SoundCategory.BLOCKS, 1, 0);
					}
					
					if (!player.isCreative()) {
						stack.hurtAndBreak(1, player, (e) -> {
							e.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
						});
					}
					
					if (b == Blocks.OAK_LOG) {
						world.setBlock(pos, PRBlocks.STRIPPED_OAK_LOG.get().defaultBlockState().setValue(BlockStateProperties.AXIS,
								world.getBlockState(pos).getValue(BlockStateProperties.AXIS)), 11);
					} else if (b == Blocks.SPRUCE_LOG) {
						world.setBlock(pos, PRBlocks.STRIPPED_SPRUCE_LOG.get().defaultBlockState().setValue(BlockStateProperties.AXIS,
								world.getBlockState(pos).getValue(BlockStateProperties.AXIS)), 11);
					} else if (b == Blocks.BIRCH_LOG) {
						world.setBlock(pos, PRBlocks.STRIPPED_BIRCH_LOG.get().defaultBlockState().setValue(BlockStateProperties.AXIS,
								world.getBlockState(pos).getValue(BlockStateProperties.AXIS)), 11);
					} else if (b == Blocks.JUNGLE_LOG) {
						world.setBlock(pos, PRBlocks.STRIPPED_JUNGLE_LOG.get().defaultBlockState().setValue(BlockStateProperties.AXIS,
								world.getBlockState(pos).getValue(BlockStateProperties.AXIS)), 11);
					} else if (b == Blocks.ACACIA_LOG) {
						world.setBlock(pos, PRBlocks.STRIPPED_ACACIA_LOG.get().defaultBlockState().setValue(BlockStateProperties.AXIS,
								world.getBlockState(pos).getValue(BlockStateProperties.AXIS)), 11);
					} else if (b == Blocks.DARK_OAK_LOG) {
						world.setBlock(pos, PRBlocks.STRIPPED_DARK_OAK_LOG.get().defaultBlockState().setValue(BlockStateProperties.AXIS,
								world.getBlockState(pos).getValue(BlockStateProperties.AXIS)), 11);
					}
				}
			}
		}
		
		super.releaseUsing(stack, world, entity, partialTicks);
	}
	
	@Override
	public ActionResultType useOn(ItemUseContext context) {
		if (toolType == EnumToolType.shovel) {
			World w = context.getLevel();
			BlockPos pos = context.getClickedPos();
			Block b = w.getBlockState(pos).getBlock();
			
			if ((b == Blocks.DIRT || b == Blocks.GRASS_BLOCK) && context.getClickedFace() != Direction.DOWN && w.isEmptyBlock(pos.above())) {
				w.playSound(context.getPlayer(), pos, SoundEvents.HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
				
				if (!w.isClientSide) {
					w.setBlock(pos, Blocks.GRASS_PATH.defaultBlockState(), 11);
					context.getItemInHand().hurtAndBreak(1, context.getPlayer(), (e) -> {
						e.broadcastBreakEvent(context.getHand());
					});
				}
				
				return ActionResultType.sidedSuccess(w.isClientSide);
			}
		} else if (toolType == EnumToolType.hoe) {
			World w = context.getLevel();
			BlockPos pos = context.getClickedPos();
			Block b = w.getBlockState(pos).getBlock();
			
			if ((b == Blocks.DIRT || b == Blocks.GRASS_BLOCK || b == Blocks.GRASS_PATH) && context.getClickedFace() != Direction.DOWN && w.isEmptyBlock(pos.above())) {
				w.playSound(context.getPlayer(), pos, SoundEvents.HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
				
				if (!w.isClientSide) {
					w.setBlock(pos, Blocks.FARMLAND.defaultBlockState(), 11);
					context.getItemInHand().hurtAndBreak(1, context.getPlayer(), (e) -> {
						e.broadcastBreakEvent(context.getHand());
					});
				}
				
				return ActionResultType.sidedSuccess(w.isClientSide);
			}
		}
		
		return super.useOn(context);
	}
	
	@Override
	public int getUseDuration(ItemStack stack) {
		return toolType == EnumToolType.axe ? 72000 : 0;
	}
	
	@Override
	public UseAction getUseAnimation(ItemStack stack) {
		return toolType == EnumToolType.axe ? UseAction.BOW : UseAction.NONE;
	}
	
	@Override
	public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
		return toolType == EnumToolType.axe;
	}
}
