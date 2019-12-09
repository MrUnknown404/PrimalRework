package mrunknown404.primalrework.init;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.blocks.BlockCharcoalKiln;
import mrunknown404.primalrework.blocks.BlockCraftingStump;
import mrunknown404.primalrework.blocks.BlockDirtSlab;
import mrunknown404.primalrework.blocks.BlockDryingTable;
import mrunknown404.primalrework.blocks.BlockFirePit;
import mrunknown404.primalrework.blocks.BlockGrassSlab;
import mrunknown404.primalrework.blocks.BlockGroundItem;
import mrunknown404.primalrework.blocks.BlockLoom;
import mrunknown404.primalrework.blocks.BlockMushroomGrassSlab;
import mrunknown404.primalrework.blocks.BlockOre;
import mrunknown404.primalrework.blocks.BlockPrimalEnchantingTable;
import mrunknown404.primalrework.blocks.BlockPrimalTorchLit;
import mrunknown404.primalrework.blocks.BlockPrimalTorchUnlit;
import mrunknown404.primalrework.blocks.BlockSalt;
import mrunknown404.primalrework.blocks.BlockStoneSlab;
import mrunknown404.primalrework.blocks.BlockStrippedLog;
import mrunknown404.primalrework.blocks.BlockThatchSlab;
import mrunknown404.primalrework.blocks.util.BlockBase;
import mrunknown404.primalrework.blocks.util.BlockMushroomGrass;
import mrunknown404.primalrework.util.DoubleValue;
import mrunknown404.primalrework.util.enums.EnumAlloy;
import mrunknown404.primalrework.util.enums.EnumOreValue;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;

public class ModBlocks {
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	// TODO add new wood types, cotton crop
	
	public static final Block ROCK = new BlockGroundItem("rock", SoundType.STONE);
	public static final Block STICK = new BlockGroundItem("stick", SoundType.WOOD, Items.STICK);
	public static final Block FLINT = new BlockGroundItem("flint", SoundType.STONE, Items.FLINT);
	
	public static final Block FIRE_PIT = new BlockFirePit();
	public static final Block UNLIT_PRIMAL_TORCH = new BlockPrimalTorchUnlit().setAmountOfTooltops(2);
	public static final Block LIT_PRIMAL_TORCH = new BlockPrimalTorchLit();
	public static final Block CRAFTING_STUMP = new BlockCraftingStump();
	public static final Block PRIMAL_ENCHANTING_TABLE = new BlockPrimalEnchantingTable();
	public static final Block THATCH = new BlockBase("thatch", Material.LEAVES, SoundType.PLANT, 0.2f, 0.2f, EnumStage.stage0,
			new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.none, EnumToolMaterial.hand),
			new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.knife, EnumToolMaterial.flint));
	public static final Block THATCH_DOUBLE_SLAB = new BlockThatchSlab("thatch_double_slab", true);
	public static final Block THATCH_SLAB = new BlockThatchSlab("thatch_slab", false);
	public static final Block SALT = new BlockSalt();
	public static final Block SMOOTH_STONE = new BlockBase("smooth_stone", Material.ROCK, SoundType.STONE, 2, 2, EnumStage.stage1,
			new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.pickaxe, EnumToolMaterial.flint));
	public static final Block STONE_DOUBLE_SLAB = new BlockStoneSlab("pr_stone_double_slab", true);
	public static final Block STONE_SLAB = new BlockStoneSlab("pr_stone_slab", false);
	public static final Block DIRT_DOUBLE_SLAB = new BlockDirtSlab("dirt_double_slab", true);
	public static final Block DIRT_SLAB = new BlockDirtSlab("dirt_slab", false);
	public static final Block GRASS_DOUBLE_SLAB = new BlockGrassSlab("grass_double_slab", true);
	public static final Block GRASS_SLAB = new BlockGrassSlab("grass_slab", false);
	public static final Block DRYING_TABLE = new BlockDryingTable();
	public static final Block LOOM = new BlockLoom();
	public static final Block COPPER_ORE_POOR = new BlockOre(2, 2, EnumStage.stage2, EnumAlloy.copper, EnumOreValue.poor, BlockOre.COPPER_TYPES);
	public static final Block COPPER_ORE_LOW = new BlockOre(2, 2, EnumStage.stage2, EnumAlloy.copper, EnumOreValue.low, BlockOre.COPPER_TYPES);
	public static final Block COPPER_ORE_MEDIUM = new BlockOre(2, 2, EnumStage.stage2, EnumAlloy.copper, EnumOreValue.medium, BlockOre.COPPER_TYPES);
	public static final Block COPPER_ORE_GOOD = new BlockOre(2, 2, EnumStage.stage2, EnumAlloy.copper, EnumOreValue.good, BlockOre.COPPER_TYPES);
	public static final Block COPPER_ORE_HIGH = new BlockOre(2, 2, EnumStage.stage2, EnumAlloy.copper, EnumOreValue.high, BlockOre.COPPER_TYPES);
	public static final Block COPPER_BLOCK = new BlockOre(3, 3, EnumStage.stage2, EnumAlloy.copper, BlockOre.COPPER_TYPES);
	public static final Block MUSHROOM_GRASS = new BlockMushroomGrass();
	public static final Block MUSHROOM_GRASS_DOUBLE_SLAB = new BlockMushroomGrassSlab("mushroom_grass_double_slab", true);
	public static final Block MUSHROOM_GRASS_SLAB = new BlockMushroomGrassSlab("mushroom_grass_slab", false);
	public static final Block CHARCOAL_KILN = new BlockCharcoalKiln();
	
	public static final Block STRIPPED_OAK_LOG = new BlockStrippedLog("oak");
	public static final Block STRIPPED_SPRUCE_LOG = new BlockStrippedLog("spruce");
	public static final Block STRIPPED_BIRCH_LOG = new BlockStrippedLog("birch");
	public static final Block STRIPPED_JUNGLE_LOG = new BlockStrippedLog("jungle");
	public static final Block STRIPPED_DARK_OAK_LOG = new BlockStrippedLog("dark_oak");
	public static final Block STRIPPED_ACACIA_LOG = new BlockStrippedLog("acacia");
}
