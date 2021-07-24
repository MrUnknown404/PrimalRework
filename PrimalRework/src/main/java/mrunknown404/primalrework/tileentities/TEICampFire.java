package mrunknown404.primalrework.tileentities;

import mrunknown404.primalrework.inventory.container.ContainerCampFire;
import mrunknown404.primalrework.recipes.SRCampFire;
import mrunknown404.primalrework.recipes.input.RISingle;
import mrunknown404.primalrework.registries.PRFuels;
import mrunknown404.primalrework.registries.PRRecipes;
import mrunknown404.primalrework.registries.PRTileEntities;
import mrunknown404.primalrework.utils.enums.EnumFuelType;
import mrunknown404.primalrework.utils.enums.EnumRecipeType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TEICampFire extends TEInventory implements ITickableTileEntity {
	private int burnTimeLeft, maxBurnTime, cookTimeLeft, maxCookTime;
	private final IIntArray dataAccess = new IIntArray() {
		@Override
		public int get(int index) {
			switch (index) {
				case 0:
					return burnTimeLeft;
				case 1:
					return maxBurnTime;
				case 2:
					return cookTimeLeft;
				case 3:
					return maxCookTime;
				default:
					return 0;
			}
		}
		
		@Override
		public void set(int index, int value) {
			switch (index) {
				case 0:
					burnTimeLeft = value;
					break;
				case 1:
					maxBurnTime = value;
					break;
				case 2:
					cookTimeLeft = value;
					break;
				case 3:
					maxCookTime = value;
			}
		}
		
		@Override
		public int getCount() {
			return 4;
		}
	};
	
	public TEICampFire() {
		super(PRTileEntities.CAMPFIRE.get(), 2);
	}
	
	@Override
	public void tick() {
		boolean flag = isBurning(), flag1 = false;
		if (isBurning()) {
			burnTimeLeft--;
			if (burnTimeLeft == 0) {
				maxBurnTime = 0;
			}
		}
		
		if (!level.isClientSide) {
			if (!isBurning()) {
				int burnValue = PRFuels.getBurnTime(EnumFuelType.campfire, getItem(0).getItem());
				if (burnValue > 0) {
					maxBurnTime = burnValue;
					burnTimeLeft = burnValue;
					getItem(0).shrink(1);
				}
			}
			
			if (isBurning()) {
				SRCampFire rec = (SRCampFire) PRRecipes.getRecipeForInput(EnumRecipeType.campfire, new RISingle(getItem(1).getItem()));
				
				if (rec == null) {
					cookTimeLeft = 0;
					maxCookTime = 0;
				} else {
					if (maxCookTime <= 0) {
						cookTimeLeft = rec.time;
						maxCookTime = rec.time;
					} else if (maxCookTime > 0) {
						cookTimeLeft--;
						if (cookTimeLeft == 0) {
							maxCookTime = 0;
							setItem(1, rec.getOutput().copy());
						}
					}
				}
			}
			
			if (flag != isBurning()) {
				flag1 = true;
				level.setBlock(worldPosition, level.getBlockState(worldPosition).setValue(BlockStateProperties.LIT, isBurning()), 3);
			}
		}
		
		if (flag1) {
			setChanged();
		}
	}
	
	@Override
	public CompoundNBT saveNBT(CompoundNBT nbt) {
		nbt.putInt("BurnTimeLeft", burnTimeLeft);
		nbt.putInt("BurnTimeMax", maxBurnTime);
		nbt.putInt("CookTimeLeft", cookTimeLeft);
		nbt.putInt("CookTimeMax", maxCookTime);
		return nbt;
	}
	
	@Override
	protected void loadNBT(BlockState state, CompoundNBT nbt) {
		burnTimeLeft = nbt.getInt("BurnTimeLeft");
		maxBurnTime = nbt.getInt("BurnTimeMax");
		cookTimeLeft = nbt.getInt("CookTimeLeft");
		maxCookTime = nbt.getInt("CookTimeMax");
	}
	
	@Override
	public void setItem(int slot, ItemStack stack) {
		super.setItem(slot, stack);
		if (slot == 1) {
			cookTimeLeft = 0;
			maxCookTime = 0;
			setChanged();
		}
	}
	
	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container.campfire");
	}
	
	@Override
	public Container createMenu(int index, PlayerInventory inv, PlayerEntity player) {
		return new ContainerCampFire(index, inv, this, dataAccess);
	}
	
	public boolean isBurning() {
		return maxBurnTime > 0;
	}
}
