package mrunknown404.primalrework.tileentity;

import mrunknown404.primalrework.init.InitBlocks;
import mrunknown404.primalrework.init.InitItems;
import mrunknown404.primalrework.init.InitRecipes;
import mrunknown404.primalrework.inventory.container.ContainerCharcoalKiln;
import mrunknown404.primalrework.inventory.container.ContainerClayFurnace;
import mrunknown404.primalrework.items.ItemOreNugget;
import mrunknown404.primalrework.util.enums.EnumAlloy;
import mrunknown404.unknownlibs.tileentity.TileEntityInventory;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

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
			if (InitRecipes.isItemClayFurnaceFuel(fuel)) {
				if (input.getItem() == InitItems.CLAY_VESSEL) {
					if (!input.hasTagCompound() || !input.getTagCompound().hasKey("isLiquid")) {
						NBTTagCompound c = input.hasTagCompound() ? input.getTagCompound() : new NBTTagCompound();
						c.setBoolean("isLiquid", false);
						input.setTagCompound(c);
					}
					
					if (!input.getTagCompound().getBoolean("isLiquid")) {
						if (cookTime == 0) {
							cookTime = MAX_COOK_TIME;
							fuel.shrink(1);
							world.notifyBlockUpdate(pos, InitBlocks.CHARCOAL_KILN.getDefaultState(), InitBlocks.CHARCOAL_KILN.getDefaultState(), 3);
							world.scheduleBlockUpdate(pos, InitBlocks.CHARCOAL_KILN, 0, 0);
						}
					}
				}
			}
		}
		
		if (input.isEmpty() || !output.isEmpty()) {
			cookTime = 0;
			world.notifyBlockUpdate(pos, InitBlocks.CHARCOAL_KILN.getDefaultState(), InitBlocks.CHARCOAL_KILN.getDefaultState(), 3);
			world.scheduleBlockUpdate(pos, InitBlocks.CHARCOAL_KILN, 0, 0);
		}
		
		if (cookTime > 0) {
			cookTime--;
			
			if (cookTime == 0) {
				IItemHandler inv = input.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
				
				EnumAlloy alloy = null;
				int units = 0;
				for (int i = 0; i < inv.getSlots(); i++) {
					if (inv.getStackInSlot(i).isEmpty()) {
						continue;
					}
					
					ItemOreNugget item = (ItemOreNugget) inv.getStackInSlot(i).getItem();
					if (alloy == null) {
						alloy = item.getAlloy();
					}
					units += item.getOreValue().units;
				}
				
				ItemStack stack = new ItemStack(InitItems.CLAY_VESSEL);
				NBTTagCompound tag = new NBTTagCompound();
				tag.setBoolean("isLiquid", true);
				tag.setInteger("units", units);
				tag.setString("alloy", alloy.toString());
				stack.setTagCompound(tag);
				
				setInventorySlotContents(ContainerClayFurnace.SLOT_OUTPUT, stack);
				setInventorySlotContents(ContainerClayFurnace.SLOT_INPUT, ItemStack.EMPTY);
				world.notifyBlockUpdate(pos, InitBlocks.CHARCOAL_KILN.getDefaultState(), InitBlocks.CHARCOAL_KILN.getDefaultState(), 3);
				world.scheduleBlockUpdate(pos, InitBlocks.CHARCOAL_KILN, 0, 0);
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
