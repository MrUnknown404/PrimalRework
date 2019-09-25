package mrunknown404.primalrework.blocks.util;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.util.IThingBase;
import mrunknown404.primalrework.util.harvest.BlockHarvestInfo;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSlab;

public interface ISlabBase<R extends BlockSlabBase> extends IThingBase<Block, R> {
	@Override
	public default void registerModels(Block block) {
		if (!((ISlabBase<BlockSlabBase>) block).isDouble()) {
			Main.proxy.registerItemRenderer(Item.getItemFromBlock(block), 0, "inventory");
		}
	}
	
	@Override
	public default void addToModList(Block block) {
		ModBlocks.BLOCKS.add(block);
		
		if (!((ISlabBase<BlockSlabBase>) block).isDouble()) {
			ModItems.ITEMS.add(new ItemSlab(block, (BlockSlab) block, ((ISlabBase<BlockSlabBase>) block).getDoubleVersion()).setUnlocalizedName(block.getUnlocalizedName().substring(5)).setRegistryName(block.getUnlocalizedName().substring(5)));
		}
	}
	
	public abstract boolean isDouble();
	public abstract R getSingleVersion();
	public abstract R getDoubleVersion();
	public abstract void setHarvestInfo(BlockHarvestInfo info);
	public abstract BlockHarvestInfo getHarvestInfo();
}
