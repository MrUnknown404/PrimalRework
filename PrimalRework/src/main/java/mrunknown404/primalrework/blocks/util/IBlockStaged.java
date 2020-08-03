package mrunknown404.primalrework.blocks.util;

import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.util.IStaged;
import mrunknown404.primalrework.util.harvest.BlockHarvestInfo;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public interface IBlockStaged<T extends Block> extends IStaged<T> {
	@Override
	public default void addToModList(T block) {
		ModBlocks.BLOCKS.add(block);
		ModItems.ITEMS.add(new ItemBlock(block).setUnlocalizedName(block.getUnlocalizedName().substring(5)).setRegistryName(block.getUnlocalizedName().substring(5)));
	}
	
	public abstract BlockHarvestInfo getHarvestInfo();
}