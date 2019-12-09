package mrunknown404.primalrework.tileentity;

import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.inventory.ContainerCharcoalKiln;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityCharcoalKiln extends TileEntityBase implements ITickable {
	
	public static final int MAX_COOK_TIME = 6000;
	private int cookTime;
	
	public TileEntityCharcoalKiln() {
		super(10);
	}
	
	@Override
	public void update() {
		if (isBurning()) {
			if (cookTime > 0) {
				cookTime--;
				
				if (!hasAll()) {
					cookTime = 0;
					world.notifyBlockUpdate(pos, ModBlocks.CHARCOAL_KILN.getDefaultState(), ModBlocks.CHARCOAL_KILN.getDefaultState(), 3);
					world.scheduleBlockUpdate(pos, ModBlocks.CHARCOAL_KILN, 0, 0);
					return;
				}
				
				if (cookTime == 0) {
					ItemStack item = getStackInSlot(ContainerCharcoalKiln.SLOT_OUTPUT);
					setInventorySlotContents(ContainerCharcoalKiln.SLOT_OUTPUT, new ItemStack(Items.COAL, item.isEmpty() ? 9 : item.getCount() + 9, 1));
					
					for (int i = 0; i < 9; i++) {
						setInventorySlotContents(i, ItemStack.EMPTY);
					}
					
					world.notifyBlockUpdate(pos, ModBlocks.CHARCOAL_KILN.getDefaultState(), ModBlocks.CHARCOAL_KILN.getDefaultState(), 3);
					world.scheduleBlockUpdate(pos, ModBlocks.CHARCOAL_KILN, 0, 0);
				}
			}
		} else {
			if (!isEmpty()) {
				if (hasAll()) {
					cookTime = MAX_COOK_TIME;
					world.notifyBlockUpdate(pos, ModBlocks.CHARCOAL_KILN.getDefaultState(), ModBlocks.CHARCOAL_KILN.getDefaultState(), 3);
					world.scheduleBlockUpdate(pos, ModBlocks.CHARCOAL_KILN, 0, 0);
				}
			}
		}
	}
	
	private boolean hasAll() {
		for (int i = 0; i < 9; i++) {
			ItemStack item = getStackInSlot(i);
			if (item.isEmpty()) {
				return false;
			}
		}
		
		return true;
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
			for (ItemStack s : OreDictionary.getOres("logWood")) {
				if (OreDictionary.itemMatches(s, stack, false)) {
					return true;
				}
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
		return "container.charcoal_kiln";
	}
}
