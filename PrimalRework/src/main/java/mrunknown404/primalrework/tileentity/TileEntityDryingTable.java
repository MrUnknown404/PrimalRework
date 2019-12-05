package mrunknown404.primalrework.tileentity;

import mrunknown404.primalrework.init.ModRecipes;
import mrunknown404.primalrework.recipes.DryingTableRecipe;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityDryingTable extends TileEntityBase implements ITickable {
	public EnumFacing facing;
	private int[] dryingProgress = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	
	public TileEntityDryingTable() {
		super(9);
	}
	
	@Override
	public void update() {
		if (dryingProgress.length == 0) {
			return;
		}
		
		for (int i = 0; i < getSizeInventory(); i++) {
			ItemStack item = getStackInSlot(i);
			int curDryingProgress = dryingProgress[i];
			
			if (!item.isEmpty()) {
				if (ModRecipes.doesItemHaveDryingTableRecipe(item)) {
					DryingTableRecipe r = ModRecipes.getDryingTableRecipeFromInput(item);
					if (!world.isRemote) {
						dryingProgress[i] = ++curDryingProgress;
						
						if (curDryingProgress >= r.getDryTime()) {
							dryingProgress[i] = 0;
							setInventorySlotContents(i, r.getOutput());
							markForUpdate();
						}
					}
				} else {
					if (!world.isRemote) {
						dryingProgress[i] = 0;
					}
				}
			} else {
				if (!world.isRemote) {
					dryingProgress[i] = 0;
				}
			}
		}
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}
	
	public void markForUpdate() {
		IBlockState state = getWorld().getBlockState(this.getPos());
		getWorld().notifyBlockUpdate(getPos(), state, state, 2);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setString("facing", facing.toString());
		nbt.setIntArray("dryingProgress", dryingProgress);
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		facing = EnumFacing.valueOf(nbt.getString("facing").toUpperCase());
		dryingProgress = nbt.getIntArray("dryingProgress");
	}
	
	public int[] getDryProgress() {
		return dryingProgress;
	}
	
	@Override
	public String getName() {
		return "container.drying_table";
	}
}
