package mrunknown404.primalrework.registries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import mrunknown404.primalrework.blocks.SBCampFire;
import mrunknown404.primalrework.blocks.SBDenseLog;
import mrunknown404.primalrework.blocks.SBGrassBlock;
import mrunknown404.primalrework.blocks.SBGrassSlab;
import mrunknown404.primalrework.blocks.SBGroundItem;
import mrunknown404.primalrework.blocks.SBLeaves;
import mrunknown404.primalrework.blocks.SBLitPrimalTorch;
import mrunknown404.primalrework.blocks.SBLitPrimalWallTorch;
import mrunknown404.primalrework.blocks.SBLog;
import mrunknown404.primalrework.blocks.SBMetal;
import mrunknown404.primalrework.blocks.SBPrimalCraftingTable;
import mrunknown404.primalrework.blocks.SBStrippedLog;
import mrunknown404.primalrework.blocks.SBTallGrass;
import mrunknown404.primalrework.blocks.SBUnlitPrimalTorch;
import mrunknown404.primalrework.blocks.SBUnlitPrimalWallTorch;
import mrunknown404.primalrework.blocks.utils.SBSlab;
import mrunknown404.primalrework.blocks.utils.StagedBlock;
import mrunknown404.primalrework.blocks.utils.StagedBlock.BlockModelType;
import mrunknown404.primalrework.blocks.utils.StagedBlock.BlockStateType;
import mrunknown404.primalrework.items.utils.SIWallFloor;
import mrunknown404.primalrework.utils.BlockInfo;
import mrunknown404.primalrework.utils.BlockInfo.Hardness;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.HarvestInfo.DropInfo;
import mrunknown404.primalrework.utils.enums.Metal;
import mrunknown404.primalrework.utils.enums.ToolMaterial;
import mrunknown404.primalrework.utils.enums.ToolType;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;

public class PRBlocks {
	public static final List<RegistryObject<StagedBlock>> INGOT_BLOCKS = new ArrayList<RegistryObject<StagedBlock>>();
	@Deprecated
	private static WallFloorWrap lastWrap = null;
	
	//TODO when i add flowers, make them work like pickles
	
	//@formatter:off
	
	//MISC
	public static final RegistryObject<StagedBlock> THATCH = PRRegistry.block("thatch", () -> new StagedBlock(PRStages.STAGE_0, BlockInfo.of(BlockInfo.THATCH), HarvestInfo.HAND, HarvestInfo.KNIFE_MIN));
	public static final RegistryObject<StagedBlock> GROUND_ROCK = PRRegistry.block("ground_rock", () -> new SBGroundItem(BlockInfo.STONE_GROUND_ITEM, PRItems.ROCK));
	public static final RegistryObject<StagedBlock> GROUND_STICK = PRRegistry.block("ground_stick", () -> new SBGroundItem(BlockInfo.WOOD_GROUND_ITEM, PRItems.STICK));
	public static final RegistryObject<StagedBlock> GROUND_FLINT = PRRegistry.block("ground_flint", () -> new SBGroundItem(BlockInfo.STONE_GROUND_ITEM, PRItems.FLINT));
	public static final RegistryObject<StagedBlock> GROUND_CLAY = PRRegistry.block("ground_clay", () -> new SBGroundItem(BlockInfo.CLAY_GROUND_ITEM, PRItems.CLAY_BALL));
	public static final RegistryObject<StagedBlock> UNLIT_PRIMAL_TORCH = registerWallFloor("unlit_primal_torch", "unlit_primal_wall_torch", () -> new SBUnlitPrimalTorch(), () -> new SBUnlitPrimalWallTorch()).floor;
	public static final RegistryObject<StagedBlock> UNLIT_PRIMAL_WALL_TORCH = getLastWall();
	public static final RegistryObject<StagedBlock> LIT_PRIMAL_TORCH = registerWallFloor("lit_primal_torch", "lit_primal_wall_torch", () -> new SBLitPrimalTorch(), () -> new SBLitPrimalWallTorch()).floor;
	public static final RegistryObject<StagedBlock> LIT_PRIMAL_WALL_TORCH = getLastWall();
	public static final RegistryObject<StagedBlock> SALT = PRRegistry.block("salt_block", () -> new StagedBlock(PRStages.STAGE_1, BlockInfo.of(BlockInfo.DIRT), new HarvestInfo(ToolType.SHOVEL, ToolMaterial.CLAY, DropInfo.item(PRItems.SALT, 4, 4))));
	public static final RegistryObject<StagedBlock> DENSE_LOG = PRRegistry.block("dense_log", () -> new SBDenseLog());
	public static final RegistryObject<StagedBlock> CHARCOAL_BLOCK = PRRegistry.block("charcoal_block", () -> new StagedBlock(PRStages.STAGE_2, BlockInfo.with(BlockInfo.COAL, Hardness.SOFT_2), HarvestInfo.HAND, HarvestInfo.KNIFE_MIN));
	public static final RegistryObject<StagedBlock> STRIPPED_OAK_LOG = PRRegistry.block("stripped_oak_log", () -> new SBStrippedLog());
	public static final RegistryObject<StagedBlock> STRIPPED_SPRUCE_LOG = PRRegistry.block("stripped_spruce_log", () -> new SBStrippedLog());
	public static final RegistryObject<StagedBlock> STRIPPED_BIRCH_LOG = PRRegistry.block("stripped_birch_log", () -> new SBStrippedLog());
	public static final RegistryObject<StagedBlock> STRIPPED_JUNGLE_LOG = PRRegistry.block("stripped_jungle_log", () -> new SBStrippedLog());
	public static final RegistryObject<StagedBlock> STRIPPED_DARK_OAK_LOG = PRRegistry.block("stripped_dark_oak_log", () -> new SBStrippedLog());
	public static final RegistryObject<StagedBlock> STRIPPED_ACACIA_LOG = PRRegistry.block("stripped_acacia_log", () -> new SBStrippedLog());
	public static final RegistryObject<StagedBlock> SHORT_GRASS = PRRegistry.block("short_grass", () -> new SBTallGrass(() -> PRBlocks.SHORT_GRASS, Hardness.SOFT_1, Block.box(2, 0, 2, 14, 6, 14)));
	public static final RegistryObject<StagedBlock> MEDIUM_GRASS = PRRegistry.block("medium_grass", () -> new SBTallGrass(() -> PRBlocks.MEDIUM_GRASS, Hardness.SOFT_1, Block.box(2, 0, 2, 14, 9, 14)));
	public static final RegistryObject<StagedBlock> TALL_GRASS = PRRegistry.block("tall_grass", () -> new SBTallGrass(() -> PRBlocks.TALL_GRASS, Hardness.MEDIUM_0, Block.box(2, 0, 2, 14, 13, 14)).useVanillaNamespaceItem());
	
	//SLABS
	public static final RegistryObject<StagedBlock> DIRT_SLAB = PRRegistry.block("dirt_slab", () -> new SBSlab(PRStages.STAGE_0, BlockInfo.of(BlockInfo.DIRT), HarvestInfo.SHOVEL_MIN).useVanillaNamespaceBlock());
	public static final RegistryObject<StagedBlock> GRASS_SLAB = PRRegistry.block("grass_slab", () -> new SBGrassSlab());
	
	//MACHINES
	public static final RegistryObject<StagedBlock> CAMPFIRE = PRRegistry.block("campfire", () -> new SBCampFire());
	public static final RegistryObject<StagedBlock> PRIMAL_CRAFTING_TABLE = PRRegistry.block("primal_crafting_table", () -> new SBPrimalCraftingTable());
	
	//VANILLA OVERRIDES
	public static final RegistryObject<StagedBlock> DIRT = PRRegistry.block("dirt", () -> new StagedBlock(PRStages.STAGE_0, BlockInfo.of(BlockInfo.DIRT), HarvestInfo.SHOVEL_MIN).usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> GRASS_BLOCK = PRRegistry.block("grass_block", () -> new SBGrassBlock());
	public static final RegistryObject<StagedBlock> STONE = PRRegistry.block("stone", () -> new StagedBlock(PRStages.STAGE_0, BlockInfo.of(BlockInfo.STONE), BlockStateType.none, BlockModelType.normal, new HarvestInfo(ToolType.PICKAXE, ToolMaterial.CLAY, DropInfo.item(PRItems.ROCK, 2, 4))).usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> COBBLESTONE = PRRegistry.block("cobblestone", () -> new StagedBlock(PRStages.STAGE_0, BlockInfo.of(BlockInfo.STONE), HarvestInfo.PICKAXE_MIN).usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> OAK_LOG = PRRegistry.block("oak_log", () -> new SBLog().usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> SPRUCE_LOG = PRRegistry.block("spruce_log", () -> new SBLog().usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> BIRCH_LOG = PRRegistry.block("birch_log", () -> new SBLog().usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> JUNGLE_LOG = PRRegistry.block("jungle_log", () -> new SBLog().usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> ACACIA_LOG = PRRegistry.block("acacia_log", () -> new SBLog().usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> DARK_OAK_LOG = PRRegistry.block("dark_oak_log", () -> new SBLog().usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> OAK_LEAVES = PRRegistry.block("oak_leaves", () -> new SBLeaves().usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> SPRUCE_LEAVES = PRRegistry.block("spruce_leaves", () -> new SBLeaves().usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> BIRCH_LEAVES = PRRegistry.block("birch_leaves", () -> new SBLeaves().usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> JUNGLE_LEAVES = PRRegistry.block("jungle_leaves", () -> new SBLeaves().usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> ACACIA_LEAVES = PRRegistry.block("acacia_leaves", () -> new SBLeaves().usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> DARK_OAK_LEAVES = PRRegistry.block("dark_oak_leaves", () -> new SBLeaves().usesVanillaNamespaceFull());
	
	//@formatter:off
	
	private static WallFloorWrap registerWallFloor(String floorName, String wallname, Supplier<StagedBlock> floorBlock, Supplier<StagedBlock> wallBlock) {
		RegistryObject<StagedBlock> floor = PRRegistry.blockNoItem(floorName, floorBlock);
		RegistryObject<StagedBlock> wall = PRRegistry.blockNoItem(wallname, wallBlock);
		PRRegistry.item(floorName, ()-> new SIWallFloor(floor.get(), wall.get()));
		return lastWrap = new WallFloorWrap(floor, wall);
	}
	
	private static RegistryObject<StagedBlock> getLastWall() {
		RegistryObject<StagedBlock> o = lastWrap.wall;
		lastWrap = null;
		return o;
	}
	
	static {
		for (Metal metal : Metal.values()) {
			INGOT_BLOCKS.add(PRRegistry.block(metal + "_block",()->new SBMetal(metal)));
		}
	}
	
	public static SBMetal getMetalBlock(Metal alloy) {
		for (RegistryObject<StagedBlock> b : INGOT_BLOCKS) {
			if (b.get() instanceof SBMetal) {
				SBMetal ore = (SBMetal) b.get();
				if (ore.metal == alloy) {
					return ore;
				}
			}
		}
		
		return null;
	}
	
	private static class WallFloorWrap {
		private final RegistryObject<StagedBlock> wall;
		private final RegistryObject<StagedBlock> floor;
		
		private WallFloorWrap(RegistryObject<StagedBlock> wall, RegistryObject<StagedBlock> floor) {
			this.wall = wall;
			this.floor = floor;
		}
	}
}
