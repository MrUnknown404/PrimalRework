package mrunknown404.primalrework.tileentity;

import mrunknown404.primalrework.init.ModRecipes;
import mrunknown404.primalrework.inventory.container.ContainerFirePit;
import mrunknown404.unknownlibs.tileentity.TileEntityInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public class TileEntityFirePit extends TileEntityInventory implements ITickable {
	
	private int cookTime, totalCookTime, burnTime, totalBurnTime;
	
	public TileEntityFirePit() {
		super(2);
	}
	
	@Override
	public void update() {
		if (!isBurning()) {
			if (ModRecipes.isItemFirePitFuel(inv.get(ContainerFirePit.SLOT_FUEL))) {
				ItemStack fuel = inv.get(ContainerFirePit.SLOT_FUEL);
				totalBurnTime = ModRecipes.getFirePitBurnTime(fuel);
				burnTime = totalBurnTime;
				fuel.shrink(1);
			} else {
				cookTime = 0;
				totalCookTime = 0;
			}
		}
		
		if (isBurning()) {
			if (!isCookingItem() && ModRecipes.doesItemHaveFirePitRecipe(inv.get(ContainerFirePit.SLOT_ITEM))) {
				totalCookTime = ModRecipes.getFirePitRecipeFromInput(inv.get(ContainerFirePit.SLOT_ITEM)).getCookTime();
				cookTime = totalCookTime;
			}
			
			if (isCookingItem()) {
				if (ModRecipes.doesItemHaveFirePitRecipe(inv.get(ContainerFirePit.SLOT_ITEM))) {
					if (cookTime > 0) {
						cookTime--;
						
						if (cookTime == 0) {
							ItemStack out = ModRecipes.getFirePitRecipeFromInput(inv.get(ContainerFirePit.SLOT_ITEM)).getOutput();
							setInventorySlotContents(ContainerFirePit.SLOT_ITEM, out);
							totalCookTime = 0;
						}
					}
				} else {
					totalCookTime = 0;
					cookTime = 0;
				}
			}
			
			if (burnTime > 0) {
				burnTime--;
				
				if (burnTime == 0) {
					totalBurnTime = 0;
				}
			}
		}
	}
	
	private boolean isCookingItem() {
		return isBurning() && totalCookTime > 0;
	}
	
	public boolean isBurning() {
		return totalBurnTime > 0;
	}
	
	@Override
	public String getName() {
		return "container.fire_pit";
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 1;
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == ContainerFirePit.SLOT_FUEL) {
			return ModRecipes.isItemFirePitFuel(stack);
		} else if (index == ContainerFirePit.SLOT_ITEM) {
			return ModRecipes.doesItemHaveFirePitRecipe(stack);
		}
		
		return false;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound c) {
		super.writeToNBT(c);
		c.setInteger("BurnTime", (short) burnTime);
		c.setInteger("BurnTimeTotal", (short) totalBurnTime);
		c.setInteger("CookTime", (short) cookTime);
		c.setInteger("CookTimeTotal", (short) totalCookTime);
		
		return c;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound c) {
		super.readFromNBT(c);
		burnTime = c.getInteger("BurnTime");
		totalBurnTime = c.getInteger("BurnTimeTotal");
		cookTime = c.getInteger("CookTime");
		totalCookTime = c.getInteger("CookTimeTotal");
	}
	
	@Override
	public void setField(int id, int value) {
		if (id == 0) {
			burnTime = value;
		} else if (id == 1) {
			totalBurnTime = value;
		} else if (id == 2) {
			cookTime = value;
		} else if (id == 3) {
			totalCookTime = value;
		}
	}
	
	public int getCookTime() {
		return cookTime;
	}
	
	public int getTotalCookTime() {
		return totalCookTime;
	}
	
	public int getBurnTime() {
		return burnTime;
	}
	
	public int getTotalBurnTime() {
		return totalBurnTime;
	}
}
