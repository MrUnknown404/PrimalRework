package mrunknown404.primalrework.init;

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
import mrunknown404.primalrework.blocks.raw.SBSlab;
import mrunknown404.primalrework.blocks.raw.StagedBlock;
import mrunknown404.primalrework.blocks.raw.StagedBlock.BlockModelType;
import mrunknown404.primalrework.blocks.raw.StagedBlock.BlockStateType;
import mrunknown404.primalrework.items.raw.SIWallFloor;
import mrunknown404.primalrework.utils.BlockInfo;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.BlockInfo.Hardness;
import mrunknown404.primalrework.utils.HarvestInfo.DropInfo;
import mrunknown404.primalrework.utils.enums.Metal;
import mrunknown404.primalrework.utils.enums.ToolMaterial;
import mrunknown404.primalrework.utils.enums.ToolType;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;

public class InitBlocks {
	public static final List<RegistryObject<StagedBlock>> INGOT_BLOCKS = new ArrayList<RegistryObject<StagedBlock>>();
	@Deprecated
	private static WallFloorWrap lastWrap = null;
	
	//TODO when i add flowers, make them work like pickles
	
	//@formatter:off
	
	//MISC
	public static final RegistryObject<StagedBlock> THATCH = InitRegistry.block("thatch", () -> new StagedBlock(InitStages.STAGE_0, BlockInfo.of(BlockInfo.THATCH), HarvestInfo.HAND, HarvestInfo.KNIFE_MIN));
	public static final RegistryObject<StagedBlock> GROUND_ROCK = InitRegistry.block("ground_rock", () -> new SBGroundItem(BlockInfo.STONE_GROUND_ITEM, InitItems.ROCK));
	public static final RegistryObject<StagedBlock> GROUND_STICK = InitRegistry.block("ground_stick", () -> new SBGroundItem(BlockInfo.WOOD_GROUND_ITEM, InitItems.STICK));
	public static final RegistryObject<StagedBlock> GROUND_FLINT = InitRegistry.block("ground_flint", () -> new SBGroundItem(BlockInfo.STONE_GROUND_ITEM, InitItems.FLINT));
	public static final RegistryObject<StagedBlock> GROUND_CLAY = InitRegistry.block("ground_clay", () -> new SBGroundItem(BlockInfo.CLAY_GROUND_ITEM, InitItems.CLAY_BALL));
	public static final RegistryObject<StagedBlock> UNLIT_PRIMAL_TORCH = registerWallFloor("unlit_primal_torch", "unlit_primal_wall_torch", () -> new SBUnlitPrimalTorch(), () -> new SBUnlitPrimalWallTorch()).floor;
	public static final RegistryObject<StagedBlock> UNLIT_PRIMAL_WALL_TORCH = getLastWall();
	public static final RegistryObject<StagedBlock> LIT_PRIMAL_TORCH = registerWallFloor("lit_primal_torch", "lit_primal_wall_torch", () -> new SBLitPrimalTorch(), () -> new SBLitPrimalWallTorch()).floor;
	public static final RegistryObject<StagedBlock> LIT_PRIMAL_WALL_TORCH = getLastWall();
	public static final RegistryObject<StagedBlock> SALT = InitRegistry.block("salt_block", () -> new StagedBlock(InitStages.STAGE_1, BlockInfo.of(BlockInfo.DIRT), new HarvestInfo(ToolType.SHOVEL, ToolMaterial.CLAY, DropInfo.item(InitItems.SALT, 4, 4))));
	public static final RegistryObject<StagedBlock> DENSE_LOG = InitRegistry.block("dense_log", () -> new SBDenseLog());
	public static final RegistryObject<StagedBlock> CHARCOAL_BLOCK = InitRegistry.block("charcoal_block", () -> new StagedBlock(InitStages.STAGE_2, BlockInfo.with(BlockInfo.COAL, Hardness.SOFT_2), HarvestInfo.HAND, HarvestInfo.KNIFE_MIN));
	public static final RegistryObject<StagedBlock> STRIPPED_OAK_LOG = InitRegistry.block("stripped_oak_log", () -> new SBStrippedLog());
	public static final RegistryObject<StagedBlock> STRIPPED_SPRUCE_LOG = InitRegistry.block("stripped_spruce_log", () -> new SBStrippedLog());
	public static final RegistryObject<StagedBlock> STRIPPED_BIRCH_LOG = InitRegistry.block("stripped_birch_log", () -> new SBStrippedLog());
	public static final RegistryObject<StagedBlock> STRIPPED_JUNGLE_LOG = InitRegistry.block("stripped_jungle_log", () -> new SBStrippedLog());
	public static final RegistryObject<StagedBlock> STRIPPED_DARK_OAK_LOG = InitRegistry.block("stripped_dark_oak_log", () -> new SBStrippedLog());
	public static final RegistryObject<StagedBlock> STRIPPED_ACACIA_LOG = InitRegistry.block("stripped_acacia_log", () -> new SBStrippedLog());
	public static final RegistryObject<StagedBlock> SHORT_GRASS = InitRegistry.block("short_grass", () -> new SBTallGrass(() -> InitBlocks.SHORT_GRASS, Hardness.SOFT_1, Block.box(2, 0, 2, 14, 6, 14)));
	public static final RegistryObject<StagedBlock> MEDIUM_GRASS = InitRegistry.block("medium_grass", () -> new SBTallGrass(() -> InitBlocks.MEDIUM_GRASS, Hardness.SOFT_1, Block.box(2, 0, 2, 14, 9, 14)));
	public static final RegistryObject<StagedBlock> TALL_GRASS = InitRegistry.block("tall_grass", () -> new SBTallGrass(() -> InitBlocks.TALL_GRASS, Hardness.MEDIUM_0, Block.box(2, 0, 2, 14, 13, 14)).useVanillaNamespaceItem());
	
	//SLABS
	public static final RegistryObject<StagedBlock> DIRT_SLAB = InitRegistry.block("dirt_slab", () -> new SBSlab(InitStages.STAGE_0, BlockInfo.of(BlockInfo.DIRT), HarvestInfo.SHOVEL_MIN).useVanillaNamespaceBlock());
	public static final RegistryObject<StagedBlock> GRASS_SLAB = InitRegistry.block("grass_slab", () -> new SBGrassSlab());
	
	//MACHINES
	public static final RegistryObject<StagedBlock> CAMPFIRE = InitRegistry.block("campfire", () -> new SBCampFire());
	public static final RegistryObject<StagedBlock> PRIMAL_CRAFTING_TABLE = InitRegistry.block("primal_crafting_table", () -> new SBPrimalCraftingTable());
	
	//VANILLA OVERRIDES
	public static final RegistryObject<StagedBlock> DIRT = InitRegistry.block("dirt", () -> new StagedBlock(InitStages.STAGE_0, BlockInfo.of(BlockInfo.DIRT), HarvestInfo.SHOVEL_MIN).usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> GRASS_BLOCK = InitRegistry.block("grass_block", () -> new SBGrassBlock());
	public static final RegistryObject<StagedBlock> STONE = InitRegistry.block("stone", () -> new StagedBlock(InitStages.STAGE_0, BlockInfo.of(BlockInfo.STONE), BlockStateType.none, BlockModelType.normal, new HarvestInfo(ToolType.PICKAXE, ToolMaterial.CLAY, DropInfo.item(InitItems.ROCK, 2, 4))).usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> COBBLESTONE = InitRegistry.block("cobblestone", () -> new StagedBlock(InitStages.STAGE_0, BlockInfo.of(BlockInfo.STONE), HarvestInfo.PICKAXE_MIN).usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> OAK_LOG = InitRegistry.block("oak_log", () -> new SBLog().usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> SPRUCE_LOG = InitRegistry.block("spruce_log", () -> new SBLog().usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> BIRCH_LOG = InitRegistry.block("birch_log", () -> new SBLog().usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> JUNGLE_LOG = InitRegistry.block("jungle_log", () -> new SBLog().usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> ACACIA_LOG = InitRegistry.block("acacia_log", () -> new SBLog().usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> DARK_OAK_LOG = InitRegistry.block("dark_oak_log", () -> new SBLog().usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> OAK_LEAVES = InitRegistry.block("oak_leaves", () -> new SBLeaves().usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> SPRUCE_LEAVES = InitRegistry.block("spruce_leaves", () -> new SBLeaves().usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> BIRCH_LEAVES = InitRegistry.block("birch_leaves", () -> new SBLeaves().usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> JUNGLE_LEAVES = InitRegistry.block("jungle_leaves", () -> new SBLeaves().usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> ACACIA_LEAVES = InitRegistry.block("acacia_leaves", () -> new SBLeaves().usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> DARK_OAK_LEAVES = InitRegistry.block("dark_oak_leaves", () -> new SBLeaves().usesVanillaNamespaceFull());
	
	//@formatter:off
	
	private static WallFloorWrap registerWallFloor(String floorName, String wallname, Supplier<StagedBlock> floorBlock, Supplier<StagedBlock> wallBlock) {
		RegistryObject<StagedBlock> floor = InitRegistry.blockNoItem(floorName, floorBlock);
		RegistryObject<StagedBlock> wall = InitRegistry.blockNoItem(wallname, wallBlock);
		InitRegistry.item(floorName, ()-> new SIWallFloor(floor.get(), wall.get()));
		return lastWrap = new WallFloorWrap(floor, wall);
	}
	
	private static RegistryObject<StagedBlock> getLastWall() {
		RegistryObject<StagedBlock> o = lastWrap.wall;
		lastWrap = null;
		return o;
	}
	
	static {
		for (Metal metal : Metal.values()) {
			INGOT_BLOCKS.add(InitRegistry.block(metal + "_block",()->new SBMetal(metal)));
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
