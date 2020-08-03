package mrunknown404.primalrework.blocks.util;

import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.init.ModItems;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemSlab;

public interface ISlabStaged<T extends BlockSlab> extends IBlockStaged<T> {
	@Override
	public default void addToModList(T block) {
		ModBlocks.BLOCKS.add(block);
		
		if (!((ISlabStaged<BlockSlab>) block).isDouble()) {
			ModItems.ITEMS.add(new ItemSlab(block, block, ((ISlabStaged<BlockStagedSlab>) block).getDoubleVersion()).setUnlocalizedName(block.getUnlocalizedName().substring(5))
					.setRegistryName(block.getUnlocalizedName().substring(5)));
		}
	}
	
	public abstract boolean isDouble();
	public abstract T getSingleVersion();
	public abstract T getDoubleVersion();
}
