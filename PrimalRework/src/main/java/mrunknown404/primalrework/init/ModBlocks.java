package mrunknown404.primalrework.init;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.blocks.BlockFirePit;
import mrunknown404.primalrework.blocks.util.BlockGroundItem;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.init.Items;

public class ModBlocks {
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block ROCK = new BlockGroundItem("rock", SoundType.STONE);
	public static final Block STICK = new BlockGroundItem("stick", SoundType.WOOD, Items.STICK);
	public static final Block FIRE_PIT = new BlockFirePit();
}
