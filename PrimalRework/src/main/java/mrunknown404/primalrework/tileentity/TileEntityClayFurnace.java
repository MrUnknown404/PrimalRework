package mrunknown404.primalrework.tileentity;

import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.init.ModRecipes;
import mrunknown404.primalrework.inventory.container.ContainerCharcoalKiln;
import mrunknown404.primalrework.inventory.container.ContainerClayFurnace;
import mrunknown404.unknownlibs.tileentity.TileEntityInventory;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public class TileEntityClayFurnace extends TileEntityInventory implements ITickable {
	
	public static final int MAX_COOK_TIME = 6000;
	private int cookTime;
	
	public TileEntityClayFurnace() {
		super(3);
	}
	
	@Override
	public void update() {
		ItemStack fuel = getStackInSlot(ContainerClayFurnace.SLOT_FUEL);
		ItemStack input = getStackInSlot(ContainerClayFurnace.SLOT_INPUT);
		ItemStack output = getStackInSlot(ContainerClayFurnace.SLOT_OUTPUT);
		
		if (!fuel.isEmpty() && !input.isEmpty() && output.isEmpty()) {
			if (ModRecipes.isItemClayFurnaceFuel(fuel)) {
				if (input.getItem() == ModItems.CLAY_VESSEL) {
					if (!input.hasTagCompound() || !input.getTagCompound().hasKey("isLiquid")) {
						NBTTagCompound c = input.hasTagCompound() ? input.getTagCompound() : new NBTTagCompound();
						c.setBoolean("isLiquid", false);
						input.setTagCompound(c);
					}
					
					if (!input.getTagCompound().getBoolean("isLiquid")) {
						if (cookTime == 0) {
							cookTime = MAX_COOK_TIME;
							fuel.shrink(1);
							world.notifyBlockUpdate(pos, ModBlocks.CHARCOAL_KILN.getDefaultState(), ModBlocks.CHARCOAL_KILN.getDefaultState(), 3);
							world.scheduleBlockUpdate(pos, ModBlocks.CHARCOAL_KILN, 0, 0);
						}
					}
				}
			}
		}
		
		if (input.isEmpty() || !output.isEmpty()) {
			cookTime = 0;
			world.notifyBlockUpdate(pos, ModBlocks.CHARCOAL_KILN.getDefaultState(), ModBlocks.CHARCOAL_KILN.getDefaultState(), 3);
			world.scheduleBlockUpdate(pos, ModBlocks.CHARCOAL_KILN, 0, 0);
		}
		
		if (cookTime > 0) {
			cookTime--;
			
			if (cookTime == 0) {
				input.getTagCompound().setBoolean("isLiquid", true);
				setInventorySlotContents(ContainerClayFurnace.SLOT_OUTPUT, input);
				setInventorySlotContents(ContainerClayFurnace.SLOT_INPUT, ItemStack.EMPTY);
				world.notifyBlockUpdate(pos, ModBlocks.CHARCOAL_KILN.getDefaultState(), ModBlocks.CHARCOAL_KILN.getDefaultState(), 3);
				world.scheduleBlockUpdate(pos, ModBlocks.CHARCOAL_KILN, 0, 0);
			}
		}
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
