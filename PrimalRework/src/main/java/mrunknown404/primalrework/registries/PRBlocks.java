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
import mrunknown404.primalrework.blocks.SBUnlitPrimalTorch;
import mrunknown404.primalrework.blocks.SBUnlitPrimalWallTorch;
import mrunknown404.primalrework.blocks.utils.SBFlammable;
import mrunknown404.primalrework.blocks.utils.SBSlab;
import mrunknown404.primalrework.blocks.utils.StagedBlock;
import mrunknown404.primalrework.blocks.utils.StagedBlock.BlockStateType;
import mrunknown404.primalrework.blocks.utils.StagedBlock.Builder;
import mrunknown404.primalrework.items.utils.SIWallFloor;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.HarvestInfo.DropInfo;
import mrunknown404.primalrework.utils.enums.EnumBlockInfo;
import mrunknown404.primalrework.utils.enums.EnumMetal;
import mrunknown404.primalrework.utils.enums.EnumToolMaterial;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;

public class PRBlocks {
	public static final List<RegistryObject<StagedBlock>> OREANDBLOCKS = new ArrayList<RegistryObject<StagedBlock>>();
	private static WallFloorWrap lastWrap = null;
	
	//MISC
	public static final RegistryObject<StagedBlock> THATCH = PRRegistry
			.block(new SBFlammable("thatch", PRStages.STAGE_0, Material.LEAVES, SoundType.GRASS, EnumBlockInfo.thatch, 10, 10, HarvestInfo.HAND, HarvestInfo.KNIFE_MIN));
	public static final RegistryObject<StagedBlock> GROUND_ROCK = PRRegistry.block(new SBGroundItem("rock", SoundType.STONE, PRItems.ROCK));
	public static final RegistryObject<StagedBlock> GROUND_STICK = PRRegistry.block(new SBGroundItem("stick", SoundType.WOOD, PRItems.STICK));
	public static final RegistryObject<StagedBlock> GROUND_FLINT = PRRegistry.block(new SBGroundItem("flint", SoundType.STONE, PRItems.FLINT));
	public static final RegistryObject<StagedBlock> UNLIT_PRIMAL_TORCH = registerWallFloor(new SBUnlitPrimalTorch(), new SBUnlitPrimalWallTorch()).floor;
	public static final RegistryObject<StagedBlock> UNLIT_PRIMAL_WALL_TORCH = lastWrap.wall;
	public static final RegistryObject<StagedBlock> LIT_PRIMAL_TORCH = registerWallFloor(new SBLitPrimalTorch(), new SBLitPrimalWallTorch()).floor;
	public static final RegistryObject<StagedBlock> LIT_PRIMAL_WALL_TORCH = lastWrap.wall;
	public static final RegistryObject<StagedBlock> SALT = PRRegistry.block(new Builder("salt_block", PRStages.STAGE_1, Material.CLAY, SoundType.SAND, EnumBlockInfo.dirt,
			new HarvestInfo(EnumToolType.shovel, EnumToolMaterial.clay, DropInfo.item(PRItems.SALT, 4, 4))).create());
	public static final RegistryObject<StagedBlock> MUSHROOM_GRASS = PRRegistry.block(new SBMushroomGrass());
	public static final RegistryObject<StagedBlock> DENSE_LOG = PRRegistry.block(new SBDenseLog());
	public static final RegistryObject<StagedBlock> CHARCOAL_BLOCK = PRRegistry.block(
			new SBFlammable("charcoal_block", PRStages.STAGE_2, Material.STONE, SoundType.STONE, EnumBlockInfo.very_hard_wood, 8, 10, HarvestInfo.HAND, HarvestInfo.KNIFE_MIN));
	public static final RegistryObject<StagedBlock> STRIPPED_OAK_LOG = PRRegistry.block(new SBStrippedLog("oak"));
	public static final RegistryObject<StagedBlock> STRIPPED_SPRUCE_LOG = PRRegistry.block(new SBStrippedLog("spruce"));
	public static final RegistryObject<StagedBlock> STRIPPED_BIRCH_LOG = PRRegistry.block(new SBStrippedLog("birch"));
	public static final RegistryObject<StagedBlock> STRIPPED_JUNGLE_LOG = PRRegistry.block(new SBStrippedLog("jungle"));
	public static final RegistryObject<StagedBlock> STRIPPED_DARK_OAK_LOG = PRRegistry.block(new SBStrippedLog("dark_oak"));
	public static final RegistryObject<StagedBlock> STRIPPED_ACACIA_LOG = PRRegistry.block(new SBStrippedLog("acacia"));
	
	//SLABS
	public static final RegistryObject<StagedBlock> DIRT_SLAB = PRRegistry
			.block(new SBSlab("dirt_slab", PRStages.STAGE_0, Material.DIRT, SoundType.GRAVEL, EnumBlockInfo.dirt, false, HarvestInfo.SHOVEL_MIN).overrideVanilla());
	public static final RegistryObject<StagedBlock> GRASS_SLAB = PRRegistry.block(new SBGrassSlab());
	
	//MACHINES
	public static final RegistryObject<StagedBlock> CAMPFIRE = PRRegistry.block(new SBCampFire());
	public static final RegistryObject<StagedBlock> PRIMAL_CRAFTING_TABLE = PRRegistry.block(new SBPrimalCraftingTable());
	
	//VANILLA OVERRIDES
	public static final RegistryObject<StagedBlock> DIRT = PRRegistry
			.block(new Builder("dirt", PRStages.STAGE_0, Material.DIRT, SoundType.GRASS, EnumBlockInfo.dirt, HarvestInfo.SHOVEL_MIN).create().overrideVanilla(true));
	public static final RegistryObject<StagedBlock> GRASS_BLOCK = PRRegistry.block(new SBGrassBlock());
	public static final RegistryObject<StagedBlock> STONE = PRRegistry.block(new Builder("stone", PRStages.STAGE_0, Material.STONE, SoundType.STONE, EnumBlockInfo.stone,
			new HarvestInfo(EnumToolType.pickaxe, EnumToolMaterial.clay, DropInfo.item(PRItems.ROCK, 2, 4))).setBlockStateType(BlockStateType.none).create().overrideVanilla(true));
	public static final RegistryObject<StagedBlock> COBBLESTONE = PRRegistry
			.block(new Builder("cobblestone", PRStages.STAGE_0, Material.STONE, SoundType.STONE, EnumBlockInfo.stone, HarvestInfo.PICKAXE_MIN).create().overrideVanilla(true));
	public static final RegistryObject<StagedBlock> OAK_LOG = PRRegistry.block(new SBLog("oak").overrideVanilla(true));
	public static final RegistryObject<StagedBlock> SPRUCE_LOG = PRRegistry.block(new SBLog("spruce").overrideVanilla(true));
	public static final RegistryObject<StagedBlock> BIRCH_LOG = PRRegistry.block(new SBLog("birch").overrideVanilla(true));
	public static final RegistryObject<StagedBlock> JUNGLE_LOG = PRRegistry.block(new SBLog("jungle").overrideVanilla(true));
	public static final RegistryObject<StagedBlock> ACACIA_LOG = PRRegistry.block(new SBLog("acacia").overrideVanilla(true));
	public static final RegistryObject<StagedBlock> DARK_OAK_LOG = PRRegistry.block(new SBLog("dark_oak").overrideVanilla(true));
	public static final RegistryObject<StagedBlock> OAK_LEAVES = PRRegistry.block(new SBLeaves("oak").overrideVanilla(true));
	public static final RegistryObject<StagedBlock> SPRUCE_LEAVES = PRRegistry.block(new SBLeaves("spruce").overrideVanilla(true));
	public static final RegistryObject<StagedBlock> BIRCH_LEAVES = PRRegistry.block(new SBLeaves("birch").overrideVanilla(true));
	public static final RegistryObject<StagedBlock> JUNGLE_LEAVES = PRRegistry.block(new SBLeaves("jungle").overrideVanilla(true));
	public static final RegistryObject<StagedBlock> ACACIA_LEAVES = PRRegistry.block(new SBLeaves("acacia").overrideVanilla(true));
	public static final RegistryObject<StagedBlock> DARK_OAK_LEAVES = PRRegistry.block(new SBLeaves("dark_oak").overrideVanilla(true));
	
	private static WallFloorWrap registerWallFloor(StagedBlock floorBlock, StagedBlock wallBlock) {
		RegistryObject<StagedBlock> floor = PRRegistry.block(floorBlock, false);
		RegistryObject<StagedBlock> wall = PRRegistry.block(wallBlock, false);
		PRRegistry.item(new SIWallFloor(floorBlock, wallBlock));
		return lastWrap = new WallFloorWrap(floor, wall);
	}
	
	static {
		for (EnumMetal metal : EnumMetal.values()) {
			OREANDBLOCKS.add(PRRegistry.block(new SBMetal(metal)));
			if (!metal.isAlloy) {
				OREANDBLOCKS.add(PRRegistry.block(new SBRawOre(metal)));
			}
		}
	}
	
	public static SBMetal getMetalBlock(EnumMetal alloy) {
		for (RegistryObject<StagedBlock> b : OREANDBLOCKS) {
			if (b.get() instanceof SBMetal) {
				SBMetal ore = (SBMetal) b.get();
				if (ore.metal == alloy) {
					return ore;
				}
			}
		}
		
		return null;
	}
	
	public static SBRawOre getOreBlock(EnumMetal alloy) {
		for (RegistryObject<StagedBlock> b : OREANDBLOCKS) {
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
