package mrunknown404.primalrework.blocks;

import mrunknown404.primalrework.blocks.util.BlockBase;
import mrunknown404.primalrework.tileentity.TileEntityDryingTable;
import mrunknown404.primalrework.util.DoubleValue;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
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
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class BlockDryingTable extends BlockBase implements ITileEntityProvider {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	private static final AxisAlignedBB bb = new AxisAlignedBB(1.05 / 16, 0, 1.05 / 16, 15.05 / 16, 13.05f / 16, 15.05 / 16);
	
	public BlockDryingTable() {
		super("drying_table", Material.WOOD, SoundType.WOOD, BlockRenderLayer.CUTOUT_MIPPED, 2f, 2f, bb, bb, EnumStage.stage1,
				new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.axe, EnumToolMaterial.flint));
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		hasTileEntity = true;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		}
		
		EnumFacing direction = state.getValue(FACING);
		ItemStack stack = player.getHeldItemMainhand();
		
		if (world.getTileEntity(pos) instanceof TileEntityDryingTable) {
			TileEntityDryingTable tile = (TileEntityDryingTable) world.getTileEntity(pos);
			if (facing.getIndex() == 1) {
				if (getSlotClicked(direction, hitX, hitZ) == -1) {
					return true;
				}
				
				if (!stack.isEmpty() && tile.getStackInSlot(getSlotClicked(direction, hitX, hitZ)).isEmpty()) {
					if (!world.isRemote) {
						tile.setInventorySlotContents(getSlotClicked(direction, hitX, hitZ), stack.splitStack(1));
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
						tile.markForUpdate();
					}
				}
			}
		}
		
		return true;
	}
	
	private int getSlotClicked(EnumFacing direction, float hitX, float hitZ) {
		hitX *= 16;
		hitZ *= 16;
		
		if (hitX >= 2f && hitX <= 6f && hitZ >= 2f && hitZ <= 6f) {
			if (direction == EnumFacing.NORTH) {
				return 8;
			} else if (direction == EnumFacing.EAST) {
				return 2;
			} else if (direction == EnumFacing.WEST) {
				return 6;
			} else if (direction == EnumFacing.SOUTH) {
				return 0;
			}
		} else if (hitX >= 6f && hitX <= 10f && hitZ >= 2f && hitZ <= 6f) {
			if (direction == EnumFacing.NORTH) {
				return 7;
			} else if (direction == EnumFacing.EAST) {
				return 5;
			} else if (direction == EnumFacing.WEST) {
				return 3;
			} else if (direction == EnumFacing.SOUTH) {
				return 1;
			}
		} else if (hitX >= 10f && hitX <= 14f && hitZ >= 2f && hitZ <= 6f) {
			if (direction == EnumFacing.NORTH) {
				return 6;
			} else if (direction == EnumFacing.EAST) {
				return 8;
			} else if (direction == EnumFacing.WEST) {
				return 0;
			} else if (direction == EnumFacing.SOUTH) {
				return 2;
			}
		} else if (hitX >= 2f && hitX <= 6f && hitZ >= 6f && hitZ <= 10f) {
			if (direction == EnumFacing.NORTH) {
				return 5;
			} else if (direction == EnumFacing.EAST) {
				return 1;
			} else if (direction == EnumFacing.WEST) {
				return 7;
			} else if (direction == EnumFacing.SOUTH) {
				return 3;
			}
		} else if (hitX >= 6f && hitX <= 10f && hitZ >= 6f && hitZ <= 10f) {
			return 4;
		} else if (hitX >= 10f && hitX <= 14f && hitZ >= 6f && hitZ <= 10f) {
			if (direction == EnumFacing.NORTH) {
				return 3;
			} else if (direction == EnumFacing.EAST) {
				return 7;
			} else if (direction == EnumFacing.WEST) {
				return 1;
			} else if (direction == EnumFacing.SOUTH) {
				return 5;
			}
		} else if (hitX >= 2f && hitX <= 6f && hitZ >= 10f && hitZ <= 14f) {
			if (direction == EnumFacing.NORTH) {
				return 2;
			} else if (direction == EnumFacing.EAST) {
				return 0;
			} else if (direction == EnumFacing.WEST) {
				return 8;
			} else if (direction == EnumFacing.SOUTH) {
				return 6;
			}
		} else if (hitX >= 6f && hitX <= 10f && hitZ >= 10f && hitZ <= 14f) {
			if (direction == EnumFacing.NORTH) {
				return 1;
			} else if (direction == EnumFacing.EAST) {
				return 3;
			} else if (direction == EnumFacing.WEST) {
				return 5;
			} else if (direction == EnumFacing.SOUTH) {
				return 7;
			}
		} else if (hitX >= 10f && hitX <= 14f && hitZ >= 10f && hitZ <= 14f) {
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
			TileEntityDryingTable tile = (TileEntityDryingTable) world.getTileEntity(pos);
			if (tile instanceof TileEntityDryingTable) {
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
		((TileEntityDryingTable) world.getTileEntity(pos)).facing = state.getValue(FACING);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {FACING});
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityDryingTable();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing facing = EnumFacing.getFront(meta);
		if (facing.getAxis() == EnumFacing.Axis.Y) {
			facing = EnumFacing.NORTH;
		}
		
		return getDefaultState().withProperty(FACING, facing);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = 0;
		meta = meta | state.getValue(FACING).getIndex();
		
		return meta;
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
}
