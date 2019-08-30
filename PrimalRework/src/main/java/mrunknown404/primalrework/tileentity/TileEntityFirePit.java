package mrunknown404.primalrework.tileentity;

import mrunknown404.primalrework.init.ModRecipes;
import mrunknown404.primalrework.inventory.ContainerFirePit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class TileEntityFirePit extends TileEntity implements IInventory, ITickable {

	private NonNullList<ItemStack> inv = NonNullList.<ItemStack>withSize(2, ItemStack.EMPTY);
	private int cookTime, totalCookTime, burnTime, totalBurnTime;
	private String customName;
	
	@Override
	public void update() {
		if (burnTime > 0) {
			burnTime--;
		} else if (burnTime == 0) {
			totalBurnTime = 0;
		}
		
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
		} else {
			if (totalCookTime == 0 && ModRecipes.isItemFirePitResult(inv.get(ContainerFirePit.SLOT_ITEM))) {
				ItemStack item = inv.get(ContainerFirePit.SLOT_ITEM);
				totalCookTime = ModRecipes.getFirePitResult(item).cookTime;
				cookTime = totalCookTime;
			} else if (totalCookTime > 0 && !ModRecipes.isItemFirePitResult(inv.get(ContainerFirePit.SLOT_ITEM))) {
				cookTime = 0;
				totalCookTime = 0;
			}
			
			if (isCookingItem()) {
				if (cookTime > 0) {
					cookTime--;
				} else if (cookTime == 0) {
					totalCookTime = 0;
					ItemStack out = ModRecipes.getFirePitResult(inv.get(ContainerFirePit.SLOT_ITEM)).getOutput();
					setInventorySlotContents(ContainerFirePit.SLOT_ITEM, out);
				}
			}
		}
	}
	
	private boolean isCookingItem() {
		return isBurning() && totalCookTime != 0;
	}
	
	public boolean isBurning() {
		return burnTime != 0;
	}
	
	@Override
	public String getName() {
		return "container.fire_pit";
	}
	
	@Override
	public ITextComponent getDisplayName() {
		return (ITextComponent) (this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName()));
	}
	
	@Override
	public boolean hasCustomName() {
		return customName != null && !customName.isEmpty();
	}
	
	private void setCustomName(String customName) {
		this.customName = customName;
	}
	
	@Override
	public int getSizeInventory() {
		return inv.size();
	}
	
	@Override
	public boolean isEmpty() {
		for (ItemStack item : inv) {
			if (!item.isEmpty()) {
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public ItemStack getStackInSlot(int index) {
		return inv.get(index);
	}
	
	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(inv, index, count);
	}
	
	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(inv, index);
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack item = inv.get(index);
		inv.set(index, stack);
		
		if (item.getCount() > getInventoryStackLimit()) {
			item.setCount(getInventoryStackLimit());
		}
		
		markDirty();
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 1;
	}
	
	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return world.getTileEntity(pos) != this ? false : player.getDistanceSq(new BlockPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5)) <= 64;
	}
	
	@Override public void openInventory(EntityPlayer player) {}
	@Override public void closeInventory(EntityPlayer player) {}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == ContainerFirePit.SLOT_FUEL) {
			return ModRecipes.isItemFirePitFuel(stack);
		} else if (index == ContainerFirePit.SLOT_ITEM) {
			return ModRecipes.isItemFirePitResult(stack);
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
		ItemStackHelper.saveAllItems(c, inv);
		if (hasCustomName()) {
			c.setString("CustomName", customName);
		}
		
		return c;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound c) {
		super.readFromNBT(c);
		inv = NonNullList.<ItemStack>withSize(getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(c, inv);
		burnTime = c.getInteger("BurnTime");
		totalBurnTime = c.getInteger("BurnTimeTotal");
		cookTime = c.getInteger("CookTime");
		totalCookTime = c.getInteger("CookTimeTotal");
		
		if (c.hasKey("CustomName", 8)) {
			setCustomName(c.getString("CustomName"));
		}
	}
	
	@Override
	public int getField(int id) {
		return 0;
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
	
	@Override
	public int getFieldCount() {
		return 0;
	}
	
	@Override
	public void clear() {
		inv.clear();
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
