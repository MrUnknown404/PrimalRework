package mrunknown404.primalrework.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityDryingTable extends TileEntityBase implements ITickable {
	public EnumFacing facing;
	
	public TileEntityDryingTable() {
		super(9);
	}
	
	@Override
	public void update() {
		//TODO make work
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}
	
	public void markForUpdate() {
		IBlockState state = this.getWorld().getBlockState(this.getPos());
		getWorld().notifyBlockUpdate(this.getPos(), state, state, 2);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setString("facing", facing.toString());
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		facing = EnumFacing.valueOf(nbt.getString("facing").toUpperCase());
	}
	
	@Override
	public String getName() {
		return "container.drying_table";
	}
}
