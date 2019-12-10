package mrunknown404.primalrework.tileentity;

import mrunknown404.primalrework.init.ModBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityCharcoalPitMaster extends TileEntity implements ITickable {
	
	private static final int MAX_BURN_TIME = 6000;
	private int burnTime;
	
	@SuppressWarnings("deprecation")
	@Override
	public void update() {
		if (burnTime > 0) {
			burnTime--;
			
			if (burnTime == 0) {
				for (int z = -1; z < 2; z++) {
					for (int x = -1; x < 2; x++) {
						BlockPos npos = pos.add(x, 0, z);
						world.setBlockState(npos, ModBlocks.CHARCOAL_BLOCK.getDefaultState());
						
						if (world.getBlockState(npos.up()).getBlock() == Blocks.GRASS) {
							world.setBlockState(npos.up(), ModBlocks.GRASS_SLAB.getStateFromMeta(0));
						} else if (world.getBlockState(npos.up()).getBlock() == ModBlocks.MUSHROOM_GRASS) {
							world.setBlockState(npos.up(), ModBlocks.MUSHROOM_GRASS_SLAB.getStateFromMeta(0));
						} else {
							world.setBlockState(npos.up(), ModBlocks.DIRT_SLAB.getStateFromMeta(0));
						}
					}
				}
			}
		} else {
			burnTime = MAX_BURN_TIME;
		}
	}
	
	public boolean isBurning() {
		return burnTime > 0;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound c) {
		super.writeToNBT(c);
		c.setInteger("BurnTime", (short) burnTime);
		
		return c;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound c) {
		super.readFromNBT(c);
		
		burnTime = c.getInteger("BurnTime");
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbt = new NBTTagCompound();
		return writeToNBT(nbt);
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return new SPacketUpdateTileEntity(pos, 0, nbt);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		readFromNBT(packet.getNbtCompound());
	}
}
