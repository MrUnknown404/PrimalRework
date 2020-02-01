package mrunknown404.primalrework.blocks;

import mrunknown404.primalrework.blocks.util.BlockDirectionalBase;
import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.tileentity.TileEntityCraftingStump;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import mrunknown404.unknownlibs.utils.DoubleValue;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class BlockCraftingStump extends BlockDirectionalBase implements ITileEntityProvider {
	
	private static final AxisAlignedBB bb = new AxisAlignedBB(2.05 / 16, 0, 2.05 / 16, 14.05 / 16, 15.05f / 16, 14.05 / 16);
	
	public BlockCraftingStump() {
		super("crafting_stump", Material.WOOD, SoundType.WOOD, BlockRenderLayer.CUTOUT, 2f, 2f, bb, bb, EnumStage.stage1,
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.axe, EnumToolMaterial.flint));
		hasTileEntity = true;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		}
		
		EnumFacing direction = state.getValue(FACING);
		ItemStack stack = player.getHeldItemMainhand();
		
		if (world.getTileEntity(pos) instanceof TileEntityCraftingStump) {
			TileEntityCraftingStump tile = (TileEntityCraftingStump) world.getTileEntity(pos);
			if (facing.getIndex() == 1) {
				if (stack.getItem() != ModItems.FLINT_CRAFTING_HAMMER) {
					if (getSlotClicked(direction, hitX, hitZ) == -1) {
						return true;
					}
					
					if (!stack.isEmpty() && tile.getStackInSlot(getSlotClicked(direction, hitX, hitZ)).isEmpty()) {
						if (!world.isRemote) {
							tile.setInventorySlotContents(getSlotClicked(direction, hitX, hitZ), stack.splitStack(1));
							tile.setStrikes(0);
							tile.markForUpdate();
							return true;
						}
					} else {
						ItemStack stack2 = tile.getStackInSlot(getSlotClicked(direction, hitX, hitZ));
						if (!stack2.isEmpty()) {
							if (!player.inventory.addItemStackToInventory(stack2)) {
								ForgeHooks.onPlayerTossEvent(player, stack2, false);
							}
							
							tile.setInventorySlotContents(getSlotClicked(direction, hitX, hitZ), ItemStack.EMPTY);
							tile.setStrikes(0);
							tile.markForUpdate();
						}
					}
				} else if (!stack.isEmpty() && stack.getItem() == ModItems.FLINT_CRAFTING_HAMMER) {
					tile.setStrikes(tile.getStrikes() + 1);
					stack.damageItem(1, player);
					tile.setHit(true);
					tile.markForUpdate();
				}
			}
		}
		
		return true;
	}
	
	private int getSlotClicked(EnumFacing direction, float hitX, float hitZ) {
		hitX *= 16;
		hitZ *= 16;
		
		if (hitX >= 3.5f && hitX <= 6.5f && hitZ >= 3.5f && hitZ <= 6.5f) {
			if (direction == EnumFacing.NORTH) {
				return 8;
			} else if (direction == EnumFacing.EAST) {
				return 2;
			} else if (direction == EnumFacing.WEST) {
				return 6;
			} else if (direction == EnumFacing.SOUTH) {
				return 0;
			}
		} else if (hitX >= 6.5f && hitX <= 9.5f && hitZ >= 3.5f && hitZ <= 6.5f) {
			if (direction == EnumFacing.NORTH) {
				return 7;
			} else if (direction == EnumFacing.EAST) {
				return 5;
			} else if (direction == EnumFacing.WEST) {
				return 3;
			} else if (direction == EnumFacing.SOUTH) {
				return 1;
			}
		} else if (hitX >= 9.5f && hitX <= 12.5f && hitZ >= 3.5f && hitZ <= 6.5f) {
			if (direction == EnumFacing.NORTH) {
				return 6;
			} else if (direction == EnumFacing.EAST) {
				return 8;
			} else if (direction == EnumFacing.WEST) {
				return 0;
			} else if (direction == EnumFacing.SOUTH) {
				return 2;
			}
		} else if (hitX >= 3.5f && hitX <= 6.5f && hitZ >= 6.5f && hitZ <= 9.5f) {
			if (direction == EnumFacing.NORTH) {
				return 5;
			} else if (direction == EnumFacing.EAST) {
				return 1;
			} else if (direction == EnumFacing.WEST) {
				return 7;
			} else if (direction == EnumFacing.SOUTH) {
				return 3;
			}
		} else if (hitX >= 6.5f && hitX <= 9.5f && hitZ >= 6.5f && hitZ <= 9.5f) {
			return 4;
		} else if (hitX >= 9.5f && hitX <= 12.5f && hitZ >= 6.5f && hitZ <= 9.5f) {
			if (direction == EnumFacing.NORTH) {
				return 3;
			} else if (direction == EnumFacing.EAST) {
				return 7;
			} else if (direction == EnumFacing.WEST) {
				return 1;
			} else if (direction == EnumFacing.SOUTH) {
				return 5;
			}
		} else if (hitX >= 3.5f && hitX <= 6.5f && hitZ >= 9.5f && hitZ <= 12.5f) {
			if (direction == EnumFacing.NORTH) {
				return 2;
			} else if (direction == EnumFacing.EAST) {
				return 0;
			} else if (direction == EnumFacing.WEST) {
				return 8;
			} else if (direction == EnumFacing.SOUTH) {
				return 6;
			}
		} else if (hitX >= 6.5f && hitX <= 9.5f && hitZ >= 9.5f && hitZ <= 12.5f) {
			if (direction == EnumFacing.NORTH) {
				return 1;
			} else if (direction == EnumFacing.EAST) {
				return 3;
			} else if (direction == EnumFacing.WEST) {
				return 5;
			} else if (direction == EnumFacing.SOUTH) {
				return 7;
			}
		} else if (hitX >= 9.5f && hitX <= 12.5f && hitZ >= 9.5f && hitZ <= 12.5f) {
			if (direction == EnumFacing.NORTH) {
				return 0;
			} else if (direction == EnumFacing.EAST) {
				return 6;
			} else if (direction == EnumFacing.WEST) {
				return 2;
			} else if (direction == EnumFacing.SOUTH) {
				return 8;
			}
		}
		
		return -1;
	}
	
	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		if (!world.isRemote) {
			TileEntityCraftingStump tile = (TileEntityCraftingStump) world.getTileEntity(pos);
			if (tile != null) {
				InventoryHelper.dropInventoryItems(world, pos, tile);
				
				for (int i = 0; i < tile.getSizeInventory(); ++i) {
					if (!tile.getStackInSlot(i).isEmpty()) {
						tile.setInventorySlotContents(i, ItemStack.EMPTY);
					}
				}
				
				NBTTagCompound nbt = new NBTTagCompound();
				tile.writeToNBT(nbt);
				world.removeTileEntity(pos);
			}
		}
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(world, pos, state, placer, stack);
		((TileEntityCraftingStump) world.getTileEntity(pos)).facing = state.getValue(FACING);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCraftingStump();
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
}
