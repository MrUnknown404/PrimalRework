package mrunknown404.primalrework.registries;

import java.util.ArrayList;
import java.util.List;

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
import mrunknown404.primalrework.blocks.SBMushroomGrass;
import mrunknown404.primalrework.blocks.SBPrimalCraftingTable;
import mrunknown404.primalrework.blocks.SBRawOre;
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
import net.minecraftforge.fml.RegistryObject;

public class PRBlocks {
	public static final List<RegistryObject<StagedBlock>> ORE_AND_BLOCKS = new ArrayList<RegistryObject<StagedBlock>>();
	@Deprecated
	private static WallFloorWrap lastWrap = null;
	
	//TODO when i add flowers, make them work like pickles
	
	//MISC
	public static final RegistryObject<StagedBlock> THATCH = PRRegistry
			.block(new StagedBlock("thatch", PRStages.STAGE_0, BlockInfo.of(BlockInfo.THATCH), HarvestInfo.HAND, HarvestInfo.KNIFE_MIN));
	public static final RegistryObject<StagedBlock> GROUND_ROCK = PRRegistry.block(new SBGroundItem("rock", BlockInfo.STONE_GROUND_ITEM, PRItems.ROCK));
	public static final RegistryObject<StagedBlock> GROUND_STICK = PRRegistry.block(new SBGroundItem("stick", BlockInfo.WOOD_GROUND_ITEM, PRItems.STICK));
	public static final RegistryObject<StagedBlock> GROUND_FLINT = PRRegistry.block(new SBGroundItem("flint", BlockInfo.STONE_GROUND_ITEM, PRItems.FLINT));
	public static final RegistryObject<StagedBlock> UNLIT_PRIMAL_TORCH = registerWallFloor(new SBUnlitPrimalTorch(), new SBUnlitPrimalWallTorch()).floor;
	public static final RegistryObject<StagedBlock> UNLIT_PRIMAL_WALL_TORCH = getLastWall();
	public static final RegistryObject<StagedBlock> LIT_PRIMAL_TORCH = registerWallFloor(new SBLitPrimalTorch(), new SBLitPrimalWallTorch()).floor;
	public static final RegistryObject<StagedBlock> LIT_PRIMAL_WALL_TORCH = getLastWall();
	public static final RegistryObject<StagedBlock> SALT = PRRegistry.block(
			new StagedBlock("salt_block", PRStages.STAGE_1, BlockInfo.of(BlockInfo.DIRT), new HarvestInfo(ToolType.SHOVEL, ToolMaterial.CLAY, DropInfo.item(PRItems.SALT, 4, 4))));
	public static final RegistryObject<StagedBlock> MUSHROOM_GRASS = PRRegistry.block(new SBMushroomGrass());
	public static final RegistryObject<StagedBlock> DENSE_LOG = PRRegistry.block(new SBDenseLog());
	public static final RegistryObject<StagedBlock> CHARCOAL_BLOCK = PRRegistry
			.block(new StagedBlock("charcoal_block", PRStages.STAGE_2, BlockInfo.with(BlockInfo.COAL, Hardness.SOFT_2), HarvestInfo.HAND, HarvestInfo.KNIFE_MIN));
	public static final RegistryObject<StagedBlock> STRIPPED_OAK_LOG = PRRegistry.block(new SBStrippedLog("oak"));
	public static final RegistryObject<StagedBlock> STRIPPED_SPRUCE_LOG = PRRegistry.block(new SBStrippedLog("spruce"));
	public static final RegistryObject<StagedBlock> STRIPPED_BIRCH_LOG = PRRegistry.block(new SBStrippedLog("birch"));
	public static final RegistryObject<StagedBlock> STRIPPED_JUNGLE_LOG = PRRegistry.block(new SBStrippedLog("jungle"));
	public static final RegistryObject<StagedBlock> STRIPPED_DARK_OAK_LOG = PRRegistry.block(new SBStrippedLog("dark_oak"));
	public static final RegistryObject<StagedBlock> STRIPPED_ACACIA_LOG = PRRegistry.block(new SBStrippedLog("acacia"));
	public static final RegistryObject<StagedBlock> TALL_GRASS = PRRegistry.block(new SBTallGrass());
	
	//SLABS
	public static final RegistryObject<StagedBlock> DIRT_SLAB = PRRegistry
			.block(new SBSlab("dirt_slab", PRStages.STAGE_0, BlockInfo.of(BlockInfo.DIRT), HarvestInfo.SHOVEL_MIN).useVanillaNamespaceBlock());
	public static final RegistryObject<StagedBlock> GRASS_SLAB = PRRegistry.block(new SBGrassSlab());
	
	//MACHINES
	public static final RegistryObject<StagedBlock> CAMPFIRE = PRRegistry.block(new SBCampFire());
	public static final RegistryObject<StagedBlock> PRIMAL_CRAFTING_TABLE = PRRegistry.block(new SBPrimalCraftingTable());
	//@formatter:off
	
	//VANILLA OVERRIDES
	public static final RegistryObject<StagedBlock> DIRT = PRRegistry
			.block(new StagedBlock("dirt", PRStages.STAGE_0, BlockInfo.of(BlockInfo.DIRT), HarvestInfo.SHOVEL_MIN).usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> GRASS_BLOCK = PRRegistry.block(new SBGrassBlock());
	public static final RegistryObject<StagedBlock> STONE = PRRegistry.block(new StagedBlock("stone", PRStages.STAGE_0, BlockInfo.of(BlockInfo.STONE), BlockStateType.none,
			BlockModelType.normal, new HarvestInfo(ToolType.PICKAXE, ToolMaterial.CLAY, DropInfo.item(PRItems.ROCK, 2, 4))).usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> COBBLESTONE = PRRegistry
			.block(new StagedBlock("cobblestone", PRStages.STAGE_0, BlockInfo.of(BlockInfo.STONE), HarvestInfo.PICKAXE_MIN).usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> OAK_LOG = PRRegistry.block(new SBLog("oak").usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> SPRUCE_LOG = PRRegistry.block(new SBLog("spruce").usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> BIRCH_LOG = PRRegistry.block(new SBLog("birch").usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> JUNGLE_LOG = PRRegistry.block(new SBLog("jungle").usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> ACACIA_LOG = PRRegistry.block(new SBLog("acacia").usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> DARK_OAK_LOG = PRRegistry.block(new SBLog("dark_oak").usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> OAK_LEAVES = PRRegistry.block(new SBLeaves("oak").usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> SPRUCE_LEAVES = PRRegistry.block(new SBLeaves("spruce").usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> BIRCH_LEAVES = PRRegistry.block(new SBLeaves("birch").usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> JUNGLE_LEAVES = PRRegistry.block(new SBLeaves("jungle").usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> ACACIA_LEAVES = PRRegistry.block(new SBLeaves("acacia").usesVanillaNamespaceFull());
	public static final RegistryObject<StagedBlock> DARK_OAK_LEAVES = PRRegistry.block(new SBLeaves("dark_oak").usesVanillaNamespaceFull());
	
	private static WallFloorWrap registerWallFloor(StagedBlock floorBlock, StagedBlock wallBlock) {
		RegistryObject<StagedBlock> floor = PRRegistry.block(floorBlock, false);
		RegistryObject<StagedBlock> wall = PRRegistry.block(wallBlock, false);
		PRRegistry.item(new SIWallFloor(floorBlock, wallBlock));
		return lastWrap = new WallFloorWrap(floor, wall);
	}
	
	private static RegistryObject<StagedBlock> getLastWall() {
		RegistryObject<StagedBlock> o = lastWrap.wall;
		lastWrap = null;
		return o;
	}
	
	static {
		for (Metal metal : Metal.values()) {
			ORE_AND_BLOCKS.add(PRRegistry.block(new SBMetal(metal)));
			if (!metal.isAlloy) {
				ORE_AND_BLOCKS.add(PRRegistry.block(new SBRawOre(metal)));
			}
		}
	}
	
	public static SBMetal getMetalBlock(Metal alloy) {
		for (RegistryObject<StagedBlock> b : ORE_AND_BLOCKS) {
			if (b.get() instanceof SBMetal) {
				SBMetal ore = (SBMetal) b.get();
				if (ore.metal == alloy) {
					return ore;
				}
			}
		}
		
		return null;
	}
	
	public static SBRawOre getOreBlock(Metal alloy) {
		for (RegistryObject<StagedBlock> b : ORE_AND_BLOCKS) {
			if (b.get() instanceof SBRawOre) {
				SBRawOre ore = (SBRawOre) b.get();
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
