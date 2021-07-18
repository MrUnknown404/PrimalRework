package mrunknown404.primalrework.registries;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.blocks.SBCampFire;
import mrunknown404.primalrework.blocks.SBDenseLog;
import mrunknown404.primalrework.blocks.SBGroundItem;
import mrunknown404.primalrework.blocks.SBLitPrimalTorch;
import mrunknown404.primalrework.blocks.SBLitPrimalWallTorch;
import mrunknown404.primalrework.blocks.SBMushroomGrass;
import mrunknown404.primalrework.blocks.SBOre;
import mrunknown404.primalrework.blocks.SBPrimalCraftingTable;
import mrunknown404.primalrework.blocks.SBStrippedLog;
import mrunknown404.primalrework.blocks.SBUnlitPrimalTorch;
import mrunknown404.primalrework.blocks.SBUnlitPrimalWallTorch;
import mrunknown404.primalrework.blocks.utils.SBFlammable;
import mrunknown404.primalrework.blocks.utils.StagedBlock;
import mrunknown404.primalrework.items.utils.SIBlock;
import mrunknown404.primalrework.items.utils.SIWallFloor;
import mrunknown404.primalrework.utils.HarvestInfo;
import mrunknown404.primalrework.utils.HarvestInfo.DropInfo;
import mrunknown404.primalrework.utils.enums.EnumAlloy;
import mrunknown404.primalrework.utils.enums.EnumOreValue;
import mrunknown404.primalrework.utils.enums.EnumStage;
import mrunknown404.primalrework.utils.enums.EnumToolMaterial;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Items;
import net.minecraftforge.fml.RegistryObject;

public class PRBlocks {
	public static final List<RegistryObject<Block>> OREANDBLOCKS = new ArrayList<RegistryObject<Block>>();
	
	//MISC
	public static final RegistryObject<Block> THATCH = register(
			new SBFlammable("thatch", EnumStage.stage0, Material.LEAVES, SoundType.GRASS, 0.5f, 0.5f, 10, 10, HarvestInfo.HAND, HarvestInfo.KNIFE_MIN));
	public static final RegistryObject<Block> GROUND_ROCK = register(new SBGroundItem("rock", SoundType.STONE));
	public static final RegistryObject<Block> GROUND_STICK = register(new SBGroundItem("stick", SoundType.WOOD, Items.STICK));
	public static final RegistryObject<Block> GROUND_FLINT = register(new SBGroundItem("flint", SoundType.STONE, Items.FLINT));
	public static final RegistryObject<Block> UNLIT_PRIMAL_TORCH;
	public static final RegistryObject<Block> UNLIT_PRIMAL_WALL_TORCH;
	public static final RegistryObject<Block> LIT_PRIMAL_TORCH;
	public static final RegistryObject<Block> LIT_PRIMAL_WALL_TORCH;
	public static final RegistryObject<Block> SALT = register(new StagedBlock("salt_block", EnumStage.stage1, Material.CLAY, SoundType.SAND, 1.25f, 1.25f,
			new HarvestInfo(EnumToolType.shovel, EnumToolMaterial.clay, new DropInfo(() -> PRItems.SALT.get(), 4, 4))));
	public static final RegistryObject<Block> MUSHROOM_GRASS = register(new SBMushroomGrass());
	public static final RegistryObject<Block> DENSE_LOG = register(new SBDenseLog());
	public static final RegistryObject<Block> CHARCOAL_BLOCK = register(
			new SBFlammable("charcoal_block", EnumStage.stage2, Material.STONE, SoundType.STONE, 4, 6, 8, 10, HarvestInfo.HAND, HarvestInfo.KNIFE_MIN));
	public static final RegistryObject<Block> STRIPPED_OAK_LOG = register(new SBStrippedLog("oak"));
	public static final RegistryObject<Block> STRIPPED_SPRUCE_LOG = register(new SBStrippedLog("spruce"));
	public static final RegistryObject<Block> STRIPPED_BIRCH_LOG = register(new SBStrippedLog("birch"));
	public static final RegistryObject<Block> STRIPPED_JUNGLE_LOG = register(new SBStrippedLog("jungle"));
	public static final RegistryObject<Block> STRIPPED_DARK_OAK_LOG = register(new SBStrippedLog("dark_oak"));
	public static final RegistryObject<Block> STRIPPED_ACACIA_LOG = register(new SBStrippedLog("acacia"));
	
	//TODO clay pot, all machines, charcoal pit master, slabs
	
	//MACHINES
	public static final RegistryObject<Block> CAMPFIRE = register(new SBCampFire());
	public static final RegistryObject<Block> PRIMAL_CRAFTING_TABLE = register(new SBPrimalCraftingTable());
	
	static {
		WallFloorWrap wrap = registerWallFloor(new SBUnlitPrimalTorch(), new SBUnlitPrimalWallTorch());
		UNLIT_PRIMAL_TORCH = wrap.floor;
		UNLIT_PRIMAL_WALL_TORCH = wrap.wall;
		wrap = registerWallFloor(new SBLitPrimalTorch(), new SBLitPrimalWallTorch());
		LIT_PRIMAL_TORCH = wrap.floor;
		LIT_PRIMAL_WALL_TORCH = wrap.wall;
	}
	
	private static WallFloorWrap registerWallFloor(StagedBlock floorBlock, StagedBlock wallBlock) {
		RegistryObject<Block> floor = PRRegistry.BLOCKS.register(floorBlock.getRegName(), () -> floorBlock);
		RegistryObject<Block> wall = PRRegistry.BLOCKS.register(wallBlock.getRegName(), () -> wallBlock);
		PRRegistry.ITEMS.register(floorBlock.getRegName(), () -> new SIWallFloor(floorBlock, wallBlock));
		return new WallFloorWrap(floor, wall);
	}
	
	private static RegistryObject<Block> register(StagedBlock block) {
		return register(block, true);
	}
	
	private static RegistryObject<Block> register(StagedBlock block, boolean registerItem) {
		RegistryObject<Block> reg = PRRegistry.BLOCKS.register(block.getRegName(), () -> block);
		
		if (registerItem) {
			PRRegistry.ITEMS.register(block.getRegName(), () -> new SIBlock(block));
		}
		return reg;
	}
	
	public static void register() {
		for (EnumAlloy alloy : EnumAlloy.values()) {
			for (EnumOreValue value : EnumOreValue.values()) {
				if (alloy.hasOre || value == EnumOreValue.block) {
					if (alloy == EnumAlloy.unknown) {
						OREANDBLOCKS.add(register(SBOre.create(alloy.stage, alloy.hardness, alloy.blastResist, alloy, value, alloy.toolMat).addTooltip()));
					} else {
						OREANDBLOCKS.add(register(SBOre.create(alloy.stage, alloy.hardness, alloy.blastResist, alloy, value, alloy.toolMat)));
					}
				}
			}
		}
	}
	
	public static SBOre getOreOrBlock(EnumAlloy alloy, EnumOreValue value) {
		for (RegistryObject<Block> b : OREANDBLOCKS) {
			SBOre ore = (SBOre) b.get();
			if (ore.alloy == alloy && ore.value == value) {
				return ore;
			}
		}
		
		return null;
	}
	
	private static class WallFloorWrap {
		private final RegistryObject<Block> wall;
		private final RegistryObject<Block> floor;
		
		private WallFloorWrap(RegistryObject<Block> wall, RegistryObject<Block> floor) {
			this.wall = wall;
			this.floor = floor;
		}
	}
}
