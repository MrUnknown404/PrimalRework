package mrunknown404.primalrework.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemStackWrapper {
	public final ItemStack item;
	
	public ItemStackWrapper(ItemStack item, int meta) {
		this.item = new ItemStack(item.getItem(), 1, meta);
	}
	
	public ItemStackWrapper(Item item, int meta) {
		this(new ItemStack(item), meta);
	}
	
	public ItemStackWrapper(Block block, int meta) {
		this(new ItemStack(block), meta);
	}
	
	public ItemStackWrapper(ItemStack item) {
		this(item, 0);
	}
	
	public ItemStackWrapper(Item item) {
		this(new ItemStack(item), 0);
	}
	
	public ItemStackWrapper(Block block) {
		this(new ItemStack(block), 0);
	}
	
	public ItemStack getItemStack() {
		return item;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((item == null) ? 0 : item.getItem().getUnlocalizedName().hashCode());
		result = prime * result + ((item == null) ? 0 : item.getMetadata());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		
		ItemStackWrapper other = (ItemStackWrapper) obj;
		if (item.getItem() == null || other.item.getItem() == null) {
			return false;
		} else if (item.getItem() != other.item.getItem() || item.getMetadata() != other.item.getMetadata()) {
			return false;
		}
		
		return true;
	}
}
