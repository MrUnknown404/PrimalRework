package mrunknown404.primalrework.tileentities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.INameable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;

public abstract class TEInventory extends TileEntity implements IInventory, INamedContainerProvider, INameable {
	private NonNullList<ItemStack> items;
	private ITextComponent name;
	
	public TEInventory(TileEntityType<?> type, int size) {
		super(type);
		items = NonNullList.withSize(size, ItemStack.EMPTY);
	}
	
	@Override
	public final void load(BlockState state, CompoundNBT nbt) {
		super.load(state, nbt);
		if (nbt.contains("CustomName", 8)) {
			name = ITextComponent.Serializer.fromJson(nbt.getString("CustomName"));
		}
		items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(nbt, items);
		loadNBT(state, nbt);
	}
	
	@Override
	public final CompoundNBT save(CompoundNBT nbt) {
		super.save(nbt);
		if (name != null) {
			nbt.putString("CustomName", ITextComponent.Serializer.toJson(name));
		}
		ItemStackHelper.saveAllItems(nbt, items);
		saveNBT(nbt);
		
		return nbt;
	}
	
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		return new SUpdateTileEntityPacket(worldPosition, 1, ItemStackHelper.saveAllItems(getUpdateTag(), items));
	}
	
	@Override
	public CompoundNBT getUpdateTag() {
		return saveNBT(super.getUpdateTag());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		load(level.getBlockState(worldPosition), pkt.getTag());
	}
	
	@Override
	public void handleUpdateTag(BlockState state, CompoundNBT nbt) {
		load(state, nbt);
	}
	
	@Override
	public void setChanged() {
		super.setChanged();
		level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
	}
	
	//@formatter:off
	protected abstract CompoundNBT saveNBT(CompoundNBT nbt);
	protected abstract void loadNBT(BlockState state, CompoundNBT nbt);
	protected abstract ITextComponent getDefaultName();
	//@formatter:on
	
	@SuppressWarnings("unused")
	public void onItemChange(int slot, PlayerEntity player, int containerID) {
		
	}
	
	@Override
	public void setItem(int slot, ItemStack stack) {
		items.set(slot, stack);
		if (stack.getCount() > getMaxStackSize()) {
			stack.setCount(getMaxStackSize());
		}
	}
	
	@Override
	public int getContainerSize() {
		return items.size();
	}
	
	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : items) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public ItemStack getItem(int index) {
		return items.get(index);
	}
	
	@Override
	public ItemStack removeItem(int index, int p_70298_2_) {
		return ItemStackHelper.removeItem(items, index, p_70298_2_);
	}
	
	@Override
	public ItemStack removeItemNoUpdate(int index) {
		return ItemStackHelper.takeItem(items, index);
	}
	
	@Override
	public boolean stillValid(PlayerEntity player) {
		return level.getBlockEntity(worldPosition) != this ? false : player.distanceToSqr(worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5) <= 64;
	}
	
	@Override
	public void clearContent() {
		items.clear();
	}
	
	public void setCustomName(ITextComponent name) {
		this.name = name;
	}
	
	@Override
	public ITextComponent getName() {
		return name != null ? name : getDefaultName();
	}
	
	@Override
	public ITextComponent getDisplayName() {
		return getName();
	}
	
	@Override
	public ITextComponent getCustomName() {
		return name;
	}
}
