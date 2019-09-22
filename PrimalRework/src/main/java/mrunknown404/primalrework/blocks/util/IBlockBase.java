package mrunknown404.primalrework.blocks.util;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.util.IThingBase;
import mrunknown404.primalrework.util.harvest.BlockHarvestInfo;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public interface IBlockBase<R extends BlockBase> extends IThingBase<Block, R> {
	@Override
	public default void registerModels(Block block) {
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(block), 0, "inventory");
	}
	
	@Override
	public default void addToModList(Block block) {
		ModBlocks.BLOCKS.add(block);
		ModItems.ITEMS.add(new ItemBlock(block).setUnlocalizedName(block.getUnlocalizedName().substring(5)).setRegistryName(block.getUnlocalizedName().substring(5)));
	}
	
	public abstract void setHarvestInfo(BlockHarvestInfo info);
	public abstract BlockHarvestInfo getHarvestInfo();
}
