package mrunknown404.primalrework.registries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import mrunknown404.primalrework.items.SICraftingTool;
import mrunknown404.primalrework.items.SIIngot;
import mrunknown404.primalrework.items.SIRawPart;
import mrunknown404.primalrework.items.SISimpleTool;
import mrunknown404.primalrework.items.utils.SIDamageable;
import mrunknown404.primalrework.items.utils.SIFood;
import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.enums.CraftingToolType;
import mrunknown404.primalrework.utils.enums.Metal;
import mrunknown404.primalrework.utils.enums.RawPart;
import mrunknown404.primalrework.utils.enums.ToolMaterial;
import mrunknown404.primalrework.utils.enums.ToolType;
import net.minecraftforge.fml.RegistryObject;

public class PRItems {
	private static final List<RegistryObject<StagedItem>> CASTS = new ArrayList<RegistryObject<StagedItem>>();
	private static final List<RegistryObject<StagedItem>> INGOTS = new ArrayList<RegistryObject<StagedItem>>();
	private static final List<RegistryObject<StagedItem>> RAW_PARTS = new ArrayList<RegistryObject<StagedItem>>();
	private static final List<RegistryObject<StagedItem>> TOOL_PARTS = new ArrayList<RegistryObject<StagedItem>>();
	private static final List<RegistryObject<StagedItem>> CRAFTING_TOOLS = new ArrayList<RegistryObject<StagedItem>>();
	
	//@formatter:off
	
	//MISC
	public static final RegistryObject<StagedItem> PLANT_FIBER = PRRegistry.item("plant_fiber", () -> new StagedItem(PRStages.STAGE_0));
	public static final RegistryObject<StagedItem> PLANT_ROPE = PRRegistry.item("plant_rope", () -> new StagedItem(PRStages.STAGE_0));
	public static final RegistryObject<StagedItem> KNAPPED_FLINT = PRRegistry.item("knapped_flint", () -> new StagedItem(PRStages.STAGE_0));
	public static final RegistryObject<StagedItem> FLINT_POINT = PRRegistry.item("flint_point", () -> new StagedItem(PRStages.STAGE_0));
	public static final RegistryObject<StagedItem> BONE_SHARD = PRRegistry.item("bone_shard", () -> new StagedItem(PRStages.STAGE_0));
	public static final RegistryObject<StagedItem> BARK = PRRegistry.item("bark", () -> new StagedItem(PRStages.STAGE_0));
	public static final RegistryObject<StagedItem> CLOTH = PRRegistry.item("cloth", () -> new StagedItem(PRStages.STAGE_2));
	public static final RegistryObject<StagedItem> OAK_PLANK = PRRegistry.item("oak_plank", () -> new StagedItem(PRStages.STAGE_2));
	public static final RegistryObject<StagedItem> SPRUCE_PLANK = PRRegistry.item("spruce_plank", () -> new StagedItem(PRStages.STAGE_2));
	public static final RegistryObject<StagedItem> BIRCH_PLANK = PRRegistry.item("birch_plank", () -> new StagedItem(PRStages.STAGE_2));
	public static final RegistryObject<StagedItem> JUNGLE_PLANK = PRRegistry.item("jungle_plank", () -> new StagedItem(PRStages.STAGE_2));
	public static final RegistryObject<StagedItem> DARK_OAK_PLANK = PRRegistry.item("dark_oak_plank", () -> new StagedItem(PRStages.STAGE_2));
	public static final RegistryObject<StagedItem> ACACIA_PLANK = PRRegistry.item("acacia_plank", () -> new StagedItem(PRStages.STAGE_2));
	public static final RegistryObject<StagedItem> SALT = PRRegistry.item("salt", () -> new StagedItem(PRStages.STAGE_0));
	public static final RegistryObject<StagedItem> ANIMAL_PELT = PRRegistry.item("animal_pelt", () -> new StagedItem(PRStages.STAGE_0));
	public static final RegistryObject<StagedItem> WET_HIDE = PRRegistry.item("wet_hide", () -> new StagedItem(PRStages.STAGE_1));
	public static final RegistryObject<StagedItem> RAW_HIDE = PRRegistry.item("raw_hide", () -> new StagedItem(PRStages.STAGE_1));
	public static final RegistryObject<StagedItem> CLEANED_HIDE = PRRegistry.item("cleaned_hide", () -> new StagedItem(PRStages.STAGE_1));
	public static final RegistryObject<StagedItem> SALTED_HIDE = PRRegistry.item("salted_hide", () -> new StagedItem(PRStages.STAGE_1));
	public static final RegistryObject<StagedItem> DRIED_HIDE = PRRegistry.item("dried_hide", () -> new StagedItem(PRStages.STAGE_1));
	public static final RegistryObject<StagedItem> WET_TANNED_HIDE = PRRegistry.item("wet_tanned_hide", () -> new StagedItem(PRStages.STAGE_1));
	public static final RegistryObject<StagedItem> DRY_TANNED_HIDE = PRRegistry.item("dry_tanned_hide", () -> new StagedItem(PRStages.STAGE_1));
	public static final RegistryObject<StagedItem> ROPE = PRRegistry.item("rope", () -> new StagedItem(PRStages.STAGE_1));
	public static final RegistryObject<StagedItem> LEATHER_STRIP = PRRegistry.item("leather_strip", () -> new StagedItem(PRStages.STAGE_1));
	public static final RegistryObject<StagedItem> BLANK_CAST = PRRegistry.item("blank_cast", () -> new StagedItem(PRStages.STAGE_3));
	public static final RegistryObject<StagedItem> ROCK = PRRegistry.item("rock", () -> new StagedItem(PRStages.STAGE_0));
	
	//DAMAGEABLE
	public static final RegistryObject<StagedItem> MORTAR_PESTLE = PRRegistry.item("mortar_pestle", () -> new SIDamageable(PRStages.STAGE_0, ToolMaterial.WOOD));
	public static final RegistryObject<StagedItem> PLANT_MESH = PRRegistry.item("plant_mesh", () -> new SIDamageable(PRStages.STAGE_0, ToolMaterial.WOOD));
	public static final RegistryObject<StagedItem> STRING_MESH = PRRegistry.item("string_mesh", () -> new SIDamageable(PRStages.STAGE_0, ToolMaterial.FLINT));
	
	//FOOD
	public static final RegistryObject<StagedItem> DOUGH = PRRegistry.item("dough", () -> new SIFood(PRStages.STAGE_1, 1, 0.5f));
	
	//TOOLS
	public static final RegistryObject<StagedItem> CLAY_SHOVEL = simpleTool(PRStages.STAGE_0, ToolType.SHOVEL, ToolMaterial.CLAY);
	public static final RegistryObject<StagedItem> CLAY_AXE = simpleTool(PRStages.STAGE_0, ToolType.AXE, ToolMaterial.CLAY);
	public static final RegistryObject<StagedItem> WOOD_SHOVEL = simpleTool(PRStages.STAGE_0, ToolType.SHOVEL, ToolMaterial.WOOD);
	public static final RegistryObject<StagedItem> WOOD_AXE = simpleTool(PRStages.STAGE_0, ToolType.AXE, ToolMaterial.WOOD);
	public static final RegistryObject<StagedItem> FLINT_PICKAXE = simpleTool(PRStages.STAGE_1, ToolType.PICKAXE, ToolMaterial.FLINT);
	public static final RegistryObject<StagedItem> FLINT_SHOVEL = simpleTool(PRStages.STAGE_1, ToolType.SHOVEL, ToolMaterial.FLINT);
	public static final RegistryObject<StagedItem> FLINT_AXE = simpleTool(PRStages.STAGE_1, ToolType.AXE, ToolMaterial.FLINT);
	public static final RegistryObject<StagedItem> FLINT_HOE = simpleTool(PRStages.STAGE_1, ToolType.HOE, ToolMaterial.FLINT);
	public static final RegistryObject<StagedItem> FLINT_KNIFE = simpleTool(PRStages.STAGE_1, ToolType.KNIFE, ToolMaterial.FLINT);
	public static final RegistryObject<StagedItem> FLINT_SHEARS = simpleTool(PRStages.STAGE_1, ToolType.SHEARS, ToolMaterial.FLINT);
	public static final RegistryObject<StagedItem> BONE_SWORD = simpleTool(PRStages.STAGE_1, ToolType.SWORD, ToolMaterial.BONE);
	
	//VANILLA OVERRIDES
	public static final RegistryObject<StagedItem> FLINT = PRRegistry.item("flint", () -> new StagedItem(PRStages.STAGE_0).useVanillaNamespace());
	public static final RegistryObject<StagedItem> STICK = PRRegistry.item("stick", () -> new StagedItem(PRStages.STAGE_0).useVanillaNamespace());
	public static final RegistryObject<StagedItem> CLAY_BALL = PRRegistry.item("clay_ball", () -> new StagedItem(PRStages.STAGE_0).useVanillaNamespace());
	
	//@formatter:on
	
	static {
		for (ToolType type : ToolType.values()) {
			if (type != ToolType.NONE) {
				registerCast(type);
			}
		}
		for (CraftingToolType type : CraftingToolType.values()) {
			registerCast(type);
		}
		
		for (Metal metal : Metal.values()) {
			INGOTS.add(PRRegistry.item(metal.toString() + "_ingot", () -> new SIIngot(metal, false)));
			INGOTS.add(PRRegistry.item(metal.toString() + "_nugget", () -> new SIIngot(metal, true)));
			
			for (RawPart type : RawPart.values()) {
				RAW_PARTS.add(PRRegistry.item(metal.toString() + "_" + type.toString(), () -> new SIRawPart<RawPart>(PRItemGroups.RAW_PARTS, metal, type)));
			}
			for (CraftingToolType type : CraftingToolType.values()) {
				CRAFTING_TOOLS.add(PRRegistry.item(metal.toString() + "_" + type.toString(), () -> new SICraftingTool(metal, type)));
				TOOL_PARTS.add(PRRegistry.item(metal.toString() + "_" + type.toString() + "_part", () -> new SIRawPart<CraftingToolType>(PRItemGroups.TOOL_PARTS, metal, type)));
			}
			for (ToolType type : ToolType.values()) {
				if (type != ToolType.NONE) {
					TOOL_PARTS.add(PRRegistry.item(metal.toString() + "_" + type.toString() + "_part", () -> new SIRawPart<ToolType>(PRItemGroups.TOOL_PARTS, metal, type)));
				}
			}
		}
	}
	
	private static RegistryObject<StagedItem> simpleTool(Supplier<Stage> stage, ToolType toolType, ToolMaterial toolMat) {
		return PRRegistry.item(toolMat.toString() + "_" + toolType.toString(),
				() -> toolMat == ToolMaterial.WOOD ? new SISimpleTool(stage, toolType, toolMat).useVanillaNamespace() : new SISimpleTool(stage, toolType, toolMat));
	}
	
	private static RegistryObject<StagedItem> registerCast(Enum<?> type) {
		RegistryObject<StagedItem> reg = PRRegistry.item(type + "_cast", () -> new StagedItem(PRStages.STAGE_3, 8));
		CASTS.add(reg);
		return reg;
	}
	
	@SuppressWarnings("unchecked")
	public static SIRawPart<RawPart> getRawPart(Metal metal, RawPart part) {
		for (RegistryObject<StagedItem> item : RAW_PARTS) {
			SIRawPart<?> itemPart = (SIRawPart<?>) item.get();
			if (itemPart.metal == metal && itemPart.part == part) {
				return (SIRawPart<RawPart>) itemPart;
			}
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static SIRawPart<ToolType> getToolPart(Metal metal, ToolType part) {
		for (RegistryObject<StagedItem> item : TOOL_PARTS) {
			SIRawPart<?> itemPart = (SIRawPart<?>) item.get();
			if (itemPart.metal == metal && itemPart.part == part) {
				return (SIRawPart<ToolType>) itemPart;
			}
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static SIRawPart<CraftingToolType> getToolPart(Metal metal, CraftingToolType part) {
		for (RegistryObject<StagedItem> item : TOOL_PARTS) {
			SIRawPart<?> itemPart = (SIRawPart<?>) item.get();
			if (itemPart.metal == metal && itemPart.part == part) {
				return (SIRawPart<CraftingToolType>) itemPart;
			}
		}
		
		return null;
	}
	
	public static SICraftingTool getCraftingTool(Metal metal, CraftingToolType type) {
		for (RegistryObject<StagedItem> item : TOOL_PARTS) {
			SICraftingTool itemPart = (SICraftingTool) item.get();
			if (itemPart.metal == metal && itemPart.type == type) {
				return itemPart;
			}
		}
		
		return null;
	}
}
