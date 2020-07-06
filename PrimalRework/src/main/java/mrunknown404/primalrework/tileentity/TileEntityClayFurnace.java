package mrunknown404.primalrework.tileentity;

import mrunknown404.primalrework.inventory.container.ContainerCharcoalKiln;
import mrunknown404.unknownlibs.tileentity.TileEntityInventory;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public class TileEntityClayFurnace extends TileEntityInventory implements ITickable {
	
	public static final int MAX_COOK_TIME = 100; //TODO figure out
	private int cookTime;
	
	public TileEntityClayFurnace() {
		super(3);
	}
	
	@Override
	public void update() {
		//TODO write this
	}
	
	public boolean isBurning() {
		return cookTime > 0;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound c) {
		super.writeToNBT(c);
		c.setInteger("CookTime", (short) cookTime);
		
		return c;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound c) {
		super.readFromNBT(c);
		cookTime = c.getInteger("CookTime");
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 1;
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index < ContainerCharcoalKiln.SLOT_OUTPUT) {
			if (stack.getItem() == Items.COAL && stack.getMetadata() == 1) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public void setField(int id, int value) {
		cookTime = value;
	}
	
	public int getCookTime() {
		return cookTime;
	}
	
	@Override
	public String getName() {
		return "container.clay_furnace";
	}
}
