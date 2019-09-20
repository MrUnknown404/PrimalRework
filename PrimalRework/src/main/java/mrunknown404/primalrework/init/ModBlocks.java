package mrunknown404.primalrework.init;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.blocks.BlockCraftingStump;
import mrunknown404.primalrework.blocks.BlockFirePit;
import mrunknown404.primalrework.blocks.BlockPrimalEnchantingTable;
import mrunknown404.primalrework.blocks.BlockPrimalTorchLit;
import mrunknown404.primalrework.blocks.BlockPrimalTorchUnlit;
import mrunknown404.primalrework.blocks.BlockStrippedLog;
import mrunknown404.primalrework.blocks.util.BlockGroundItem;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.init.Items;

public class ModBlocks {
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block ROCK = new BlockGroundItem("rock", SoundType.STONE);
	public static final Block STICK = new BlockGroundItem("stick", SoundType.WOOD, Items.STICK);
	public static final Block FLINT = new BlockGroundItem("flint", SoundType.STONE, Items.FLINT);
	
	public static final Block FIRE_PIT = new BlockFirePit();
	public static final Block UNLIT_PRIMAL_TORCH = new BlockPrimalTorchUnlit().setAmountOfTooltops(2);
	public static final Block LIT_PRIMAL_TORCH = new BlockPrimalTorchLit();
	public static final Block CRAFTING_STUMP = new BlockCraftingStump();
	public static final Block BlockPrimalEnchantingTable = new BlockPrimalEnchantingTable();
	
	public static final Block STRIPPED_OAK_LOG = new BlockStrippedLog("oak");
	public static final Block STRIPPED_SPRUCE_LOG = new BlockStrippedLog("spruce");
	public static final Block STRIPPED_BIRCH_LOG = new BlockStrippedLog("birch");
	public static final Block STRIPPED_JUNGLE_LOG = new BlockStrippedLog("jungle");
	public static final Block STRIPPED_DARK_OAK_LOG = new BlockStrippedLog("dark_oak");
	public static final Block STRIPPED_ACACIA_LOG = new BlockStrippedLog("acacia");
}
