package mrunknown404.primalrework.blocks.util;

import mrunknown404.primalrework.init.InitBlocks;
import mrunknown404.primalrework.init.InitItems;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemSlab;

public interface ISlabStaged<T extends BlockSlab> extends IBlockStaged<T> {
	@Override
	public default void addToModList(T block) {
		InitBlocks.BLOCKS.add(block);
		
		if (!((ISlabStaged<BlockSlab>) block).isDouble()) {
			InitItems.ITEMS.add(new ItemSlab(block, block, ((ISlabStaged<BlockStagedSlab>) block).getDoubleVersion()).setUnlocalizedName(block.getUnlocalizedName().substring(5))
					.setRegistryName(block.getUnlocalizedName().substring(5)));
		}
	}
	
	public abstract boolean isDouble();
	public abstract T getSingleVersion();
	public abstract T getDoubleVersion();
}
