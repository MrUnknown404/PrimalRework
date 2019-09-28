package mrunknown404.primalrework.items.util;

import java.util.Random;

import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import mrunknown404.primalrework.util.helpers.HarvestHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDirt.DirtType;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class ItemToolBase extends ItemDamageableBase {

	private final EnumToolType toolType;
	private final EnumToolMaterial harvestLevel;
	
	public ItemToolBase(String name, EnumToolType toolType, EnumToolMaterial harvestLevel, EnumStage stage) {
		super(name, harvestLevel, stage);
		this.toolType = toolType;
		this.harvestLevel = harvestLevel;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (toolType == EnumToolType.axe) {
			player.setActiveHand(hand);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
		}
		
		return super.onItemRightClick(world, player, hand);
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entity, int timeLeft) {
		if (toolType == EnumToolType.axe) {
			if (entity instanceof EntityPlayer && getMaxItemUseDuration(stack) - timeLeft >= getMaxItemUseDuration(stack) / 900) {
				EntityPlayer player = (EntityPlayer) entity;
				
				RayTraceResult ray = rayTrace(world, player, false);
				if (ray != null && ray.typeOfHit == RayTraceResult.Type.BLOCK) {
					BlockPos clickPos = ray.getBlockPos();
					IBlockState b = world.getBlockState(clickPos);
					
					if (b.getBlock() == Blocks.LOG) {
						if (!world.isRemote) {
							world.spawnEntity(new EntityItem(world, ray.hitVec.x, ray.hitVec.y, ray.hitVec.z, new ItemStack(ModItems.BARK, 2 + new Random().nextInt(3))));
							world.playSound(null, clickPos, SoundEvents.BLOCK_WOOD_BREAK, SoundCategory.BLOCKS, 1, 0);
							
							if (!player.isCreative()) {
								stack.damageItem(1, player);
							}
						}
						
						switch (b.getValue(BlockOldLog.VARIANT)) {
							case BIRCH:
								world.setBlockState(clickPos, ModBlocks.STRIPPED_BIRCH_LOG.getDefaultState().withProperty(BlockLog.LOG_AXIS, b.getValue(BlockLog.LOG_AXIS)), 11);
								break;
							case JUNGLE:
								world.setBlockState(clickPos, ModBlocks.STRIPPED_JUNGLE_LOG.getDefaultState().withProperty(BlockLog.LOG_AXIS, b.getValue(BlockLog.LOG_AXIS)), 11);
								break;
							case OAK:
								world.setBlockState(clickPos, ModBlocks.STRIPPED_OAK_LOG.getDefaultState().withProperty(BlockLog.LOG_AXIS, b.getValue(BlockLog.LOG_AXIS)), 11);
								break;
							case SPRUCE:
								world.setBlockState(clickPos, ModBlocks.STRIPPED_SPRUCE_LOG.getDefaultState().withProperty(BlockLog.LOG_AXIS, b.getValue(BlockLog.LOG_AXIS)), 11);
								break;
							default:
								world.setBlockState(clickPos, ModBlocks.STRIPPED_OAK_LOG.getDefaultState(), 11);
								break;
						}
					} else if (b.getBlock() == Blocks.LOG2) {
						if (!world.isRemote) {
							world.spawnEntity(new EntityItem(world, ray.hitVec.x, ray.hitVec.y, ray.hitVec.z, new ItemStack(ModItems.BARK, 2 + new Random().nextInt(3))));
							world.playSound(null, clickPos, SoundEvents.BLOCK_WOOD_BREAK, SoundCategory.BLOCKS, 1, 0);
							
							if (!player.isCreative()) {
								stack.damageItem(1, player);
							}
						}
						
						switch (b.getValue(BlockNewLog.VARIANT)) {
							case ACACIA:
								world.setBlockState(clickPos, ModBlocks.STRIPPED_ACACIA_LOG.getDefaultState().withProperty(BlockLog.LOG_AXIS, b.getValue(BlockLog.LOG_AXIS)), 11);
								break;
							case DARK_OAK:
								world.setBlockState(clickPos, ModBlocks.STRIPPED_DARK_OAK_LOG.getDefaultState().withProperty(BlockLog.LOG_AXIS, b.getValue(BlockLog.LOG_AXIS)), 11);
								break;
							default:
								world.setBlockState(clickPos, ModBlocks.STRIPPED_OAK_LOG.getDefaultState(), 11);
								break;
						}
					}
				}
			}
		}
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		if (toolType == EnumToolType.axe) {
			return 18000;
		}
		
		return 0;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		if (toolType == EnumToolType.axe) {
			return EnumAction.BOW;
		}
		
		return EnumAction.NONE;
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (toolType == EnumToolType.knife || toolType == EnumToolType.sword || toolType == EnumToolType.axe) {
			stack.damageItem(1, attacker);
		} else {
			stack.damageItem(2, attacker);
		}
		
		return true;
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
		if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0) {
			if (HarvestHelper.canBreak(state.getBlock(), stack.getItem())) {
				stack.damageItem(1, entityLiving);
			} else {
				stack.damageItem(2, entityLiving);
			}
		}
		
		return true;
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (toolType == EnumToolType.shovel) {
			ItemStack itemstack = player.getHeldItem(hand);
			
			if (!player.canPlayerEdit(pos.offset(facing), facing, itemstack)) {
				return EnumActionResult.FAIL;
			} else {
				IBlockState iblockstate = world.getBlockState(pos);
				Block block = iblockstate.getBlock();
				
				if (facing != EnumFacing.DOWN && world.getBlockState(pos.up()).getMaterial() == Material.AIR && block == Blocks.GRASS) {
					IBlockState iblockstate1 = Blocks.GRASS_PATH.getDefaultState();
					world.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
					
					if (!world.isRemote) {
						world.setBlockState(pos, iblockstate1, 11);
						itemstack.damageItem(1, player);
					}
					
					return EnumActionResult.SUCCESS;
				} else {
					return EnumActionResult.PASS;
				}
			}
		} else if (toolType == EnumToolType.hoe) {
			ItemStack itemstack = player.getHeldItem(hand);
			
			if (!player.canPlayerEdit(pos.offset(facing), facing, itemstack)) {
				return EnumActionResult.FAIL;
			} else {
				int hook = ForgeEventFactory.onHoeUse(itemstack, player, world, pos);
				if (hook != 0) {
					return hook > 0 ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
				}
				
				IBlockState iblockstate = world.getBlockState(pos);
				Block block = iblockstate.getBlock();
				
				if (facing != EnumFacing.DOWN && world.isAirBlock(pos.up())) {
					if (block == Blocks.GRASS || block == Blocks.GRASS_PATH) {
						setBlock(itemstack, player, world, pos, Blocks.FARMLAND.getDefaultState());
						return EnumActionResult.SUCCESS;
					}
					
					if (block == Blocks.DIRT) {
						if (iblockstate.getValue(BlockDirt.VARIANT) == DirtType.DIRT) {
							setBlock(itemstack, player, world, pos, Blocks.FARMLAND.getDefaultState());
								return EnumActionResult.SUCCESS;
						} else if (iblockstate.getValue(BlockDirt.VARIANT) == DirtType.COARSE_DIRT) {
							setBlock(itemstack, player, world, pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
								return EnumActionResult.SUCCESS;
						}
					}
				}
				
				return EnumActionResult.PASS;
			}
		}
		
		return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
	}
	
	private void setBlock(ItemStack stack, EntityPlayer player, World world, BlockPos pos, IBlockState state) {
		world.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
		
		if (!world.isRemote) {
			world.setBlockState(pos, state, 11);
			stack.damageItem(1, player);
		}
	}
	
	@Override
	public boolean canDisableShield(ItemStack stack, ItemStack shield, EntityLivingBase entity, EntityLivingBase attacker) {
		return toolType == EnumToolType.axe;
	}
	
	@Override
	public EnumToolType getToolType() {
		return toolType;
	}
	
	@Override
	public EnumToolMaterial getHarvestLevel() {
		return harvestLevel;
	}
}
