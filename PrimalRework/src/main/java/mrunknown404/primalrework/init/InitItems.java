package mrunknown404.primalrework.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import mrunknown404.primalrework.api.registry.PRRegistries;
import mrunknown404.primalrework.api.registry.PRRegistryObject;
import mrunknown404.primalrework.api.registry.ROISIProvider;
import mrunknown404.primalrework.items.SICraftingTool;
import mrunknown404.primalrework.items.SIDamageable;
import mrunknown404.primalrework.items.SIFood;
import mrunknown404.primalrework.items.SIIngot;
import mrunknown404.primalrework.items.SIRawPart;
import mrunknown404.primalrework.items.SISimpleTool;
import mrunknown404.primalrework.items.StagedItem;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.Metal;
import mrunknown404.primalrework.utils.ToolMaterial;
import mrunknown404.primalrework.utils.enums.CraftingToolType;
import mrunknown404.primalrework.utils.enums.RawPart;
import mrunknown404.primalrework.utils.enums.ToolType;

public class InitItems {
	private static final List<ROISIProvider<StagedItem>> CASTS = new ArrayList<ROISIProvider<StagedItem>>();
	private static final List<ROISIProvider<StagedItem>> INGOTS = new ArrayList<ROISIProvider<StagedItem>>();
	private static final List<ROISIProvider<StagedItem>> RAW_PARTS = new ArrayList<ROISIProvider<StagedItem>>();
	private static final List<ROISIProvider<StagedItem>> TOOL_PARTS = new ArrayList<ROISIProvider<StagedItem>>();
	private static final List<ROISIProvider<StagedItem>> CRAFTING_TOOLS = new ArrayList<ROISIProvider<StagedItem>>();
	
	//@formatter:off
	
	//MISC
	public static final ROISIProvider<StagedItem> PLANT_FIBER = InitRegistry.item("plant_fiber", () -> new StagedItem(InitStages.STAGE_BEFORE));
	public static final ROISIProvider<StagedItem> PLANT_ROPE = InitRegistry.item("plant_rope", () -> new StagedItem(InitStages.STAGE_BEFORE));
	public static final ROISIProvider<StagedItem> KNAPPED_FLINT = InitRegistry.item("knapped_flint", () -> new StagedItem(InitStages.STAGE_BEFORE));
	public static final ROISIProvider<StagedItem> FLINT_POINT = InitRegistry.item("flint_point", () -> new StagedItem(InitStages.STAGE_BEFORE));
	public static final ROISIProvider<StagedItem> BONE_SHARD = InitRegistry.item("bone_shard", () -> new StagedItem(InitStages.STAGE_BEFORE));
	public static final ROISIProvider<StagedItem> BARK = InitRegistry.item("bark", () -> new StagedItem(InitStages.STAGE_BEFORE));
	public static final ROISIProvider<StagedItem> CLOTH = InitRegistry.item("cloth", () -> new StagedItem(InitStages.STAGE_STONE));
	public static final ROISIProvider<StagedItem> OAK_PLANK = InitRegistry.item("oak_plank", () -> new StagedItem(InitStages.STAGE_STONE));
	public static final ROISIProvider<StagedItem> SPRUCE_PLANK = InitRegistry.item("spruce_plank", () -> new StagedItem(InitStages.STAGE_STONE));
	public static final ROISIProvider<StagedItem> BIRCH_PLANK = InitRegistry.item("birch_plank", () -> new StagedItem(InitStages.STAGE_STONE));
	public static final ROISIProvider<StagedItem> JUNGLE_PLANK = InitRegistry.item("jungle_plank", () -> new StagedItem(InitStages.STAGE_STONE));
	public static final ROISIProvider<StagedItem> DARK_OAK_PLANK = InitRegistry.item("dark_oak_plank", () -> new StagedItem(InitStages.STAGE_STONE));
	public static final ROISIProvider<StagedItem> ACACIA_PLANK = InitRegistry.item("acacia_plank", () -> new StagedItem(InitStages.STAGE_STONE));
	public static final ROISIProvider<StagedItem> SALT = InitRegistry.item("salt", () -> new StagedItem(InitStages.STAGE_BEFORE));
	public static final ROISIProvider<StagedItem> ANIMAL_PELT = InitRegistry.item("animal_pelt", () -> new StagedItem(InitStages.STAGE_BEFORE));
	public static final ROISIProvider<StagedItem> WET_HIDE = InitRegistry.item("wet_hide", () -> new StagedItem(InitStages.STAGE_STONE));
	public static final ROISIProvider<StagedItem> RAW_HIDE = InitRegistry.item("raw_hide", () -> new StagedItem(InitStages.STAGE_STONE));
	public static final ROISIProvider<StagedItem> CLEANED_HIDE = InitRegistry.item("cleaned_hide", () -> new StagedItem(InitStages.STAGE_STONE));
	public static final ROISIProvider<StagedItem> SALTED_HIDE = InitRegistry.item("salted_hide", () -> new StagedItem(InitStages.STAGE_STONE));
	public static final ROISIProvider<StagedItem> DRIED_HIDE = InitRegistry.item("dried_hide", () -> new StagedItem(InitStages.STAGE_STONE));
	public static final ROISIProvider<StagedItem> WET_TANNED_HIDE = InitRegistry.item("wet_tanned_hide", () -> new StagedItem(InitStages.STAGE_STONE));
	public static final ROISIProvider<StagedItem> DRY_TANNED_HIDE = InitRegistry.item("dry_tanned_hide", () -> new StagedItem(InitStages.STAGE_STONE));
	public static final ROISIProvider<StagedItem> ROPE = InitRegistry.item("rope", () -> new StagedItem(InitStages.STAGE_STONE));
	public static final ROISIProvider<StagedItem> LEATHER_STRIP = InitRegistry.item("leather_strip", () -> new StagedItem(InitStages.STAGE_STONE));
	public static final ROISIProvider<StagedItem> BLANK_CAST = InitRegistry.item("blank_cast", () -> new StagedItem(InitStages.STAGE_COPPER));
	public static final ROISIProvider<StagedItem> ROCK = InitRegistry.item("rock", () -> new StagedItem(InitStages.STAGE_BEFORE));
	public static final ROISIProvider<StagedItem> FLINT = InitRegistry.item("flint", () -> new StagedItem(InitStages.STAGE_BEFORE).useVanillaNamespace());
	public static final ROISIProvider<StagedItem> STICK = InitRegistry.item("stick", () -> new StagedItem(InitStages.STAGE_BEFORE).useVanillaNamespace());
	public static final ROISIProvider<StagedItem> CLAY_BALL = InitRegistry.item("clay_ball", () -> new StagedItem(InitStages.STAGE_BEFORE).useVanillaNamespace());
	
	//DAMAGEABLE
	public static final ROISIProvider<StagedItem> MORTAR_PESTLE = InitRegistry.item("mortar_pestle", () -> new SIDamageable(InitStages.STAGE_BEFORE, InitToolMaterials.WOOD.get()));
	public static final ROISIProvider<StagedItem> PLANT_MESH = InitRegistry.item("plant_mesh", () -> new SIDamageable(InitStages.STAGE_BEFORE, InitToolMaterials.WOOD.get()));
	public static final ROISIProvider<StagedItem> STRING_MESH = InitRegistry.item("string_mesh", () -> new SIDamageable(InitStages.STAGE_STONE, InitToolMaterials.FLINT.get()));
	
	//FOOD
	public static final ROISIProvider<StagedItem> DOUGH = InitRegistry.item("dough", () -> new SIFood(InitStages.STAGE_STONE, 1, 0.5f));
	
	//TOOLS
	public static final ROISIProvider<StagedItem> CLAY_SHOVEL = simpleTool(InitStages.STAGE_BEFORE, ToolType.SHOVEL, InitToolMaterials.CLAY);
	public static final ROISIProvider<StagedItem> CLAY_AXE = simpleTool(InitStages.STAGE_BEFORE, ToolType.AXE, InitToolMaterials.CLAY);
	public static final ROISIProvider<StagedItem> FLINT_PICKAXE = simpleTool(InitStages.STAGE_STONE, ToolType.PICKAXE, InitToolMaterials.FLINT);
	public static final ROISIProvider<StagedItem> FLINT_SHOVEL = simpleTool(InitStages.STAGE_STONE, ToolType.SHOVEL, InitToolMaterials.FLINT);
	public static final ROISIProvider<StagedItem> FLINT_AXE = simpleTool(InitStages.STAGE_STONE, ToolType.AXE, InitToolMaterials.FLINT);
	public static final ROISIProvider<StagedItem> FLINT_HOE = simpleTool(InitStages.STAGE_STONE, ToolType.HOE, InitToolMaterials.FLINT);
	public static final ROISIProvider<StagedItem> FLINT_KNIFE = simpleTool(InitStages.STAGE_STONE, ToolType.KNIFE, InitToolMaterials.FLINT);
	public static final ROISIProvider<StagedItem> FLINT_SHEARS = simpleTool(InitStages.STAGE_STONE, ToolType.SHEARS, InitToolMaterials.FLINT);
	public static final ROISIProvider<StagedItem> BONE_SWORD = simpleTool(InitStages.STAGE_STONE, ToolType.SWORD, InitToolMaterials.BONE);
	
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
		for (RawPart type : RawPart.values()) {
			registerCast(type);
		}
		
		for (PRRegistryObject<Metal> metal : PRRegistries.METALS) {
			INGOTS.add(InitRegistry.item(metal.get() + "_ingot", () -> new SIIngot(metal.get())));
			INGOTS.add(InitRegistry.item(metal.get() + "_nugget", () -> new SIIngot(metal.get())));
			
			for (RawPart type : RawPart.values()) {
				RAW_PARTS.add(InitRegistry.item(metal.get() + "_" + type, () -> new SIRawPart<RawPart>(InitItemGroups.RAW_PARTS, metal.get(), type)));
			}
			for (CraftingToolType type : CraftingToolType.values()) {
				CRAFTING_TOOLS.add(InitRegistry.item(metal.get() + "_" + type, () -> new SICraftingTool(metal.get(), type)));
				TOOL_PARTS.add(InitRegistry.item(metal.get() + "_" + type + "_part", () -> new SIRawPart<CraftingToolType>(InitItemGroups.TOOL_PARTS, metal.get(), type)));
			}
			for (ToolType type : ToolType.values()) {
				if (type != ToolType.NONE) {
					TOOL_PARTS.add(InitRegistry.item(metal.get() + "_" + type + "_part", () -> new SIRawPart<ToolType>(InitItemGroups.TOOL_PARTS, metal.get(), type)));
				}
			}
		}
	}
	
	private static ROISIProvider<StagedItem> simpleTool(PRRegistryObject<Stage> stage, ToolType toolType, PRRegistryObject<ToolMaterial> toolMat) {
		return InitRegistry.item(toolMat.get() + "_" + toolType,
				() -> toolMat.get() == InitToolMaterials.WOOD.get() ? new SISimpleTool(stage, toolType, toolMat.get()).useVanillaNamespace() :
						new SISimpleTool(stage, toolType, toolMat.get()));
	}
	
	private static ROISIProvider<StagedItem> registerCast(Enum<?> type) {
		ROISIProvider<StagedItem> reg = InitRegistry.item("cast_" + type, () -> new StagedItem(InitStages.STAGE_COPPER, 8));
		CASTS.add(reg);
		return reg;
	}
	
	public static ROISIProvider<StagedItem> getRawPart(Metal metal, RawPart part) {
		Optional<ROISIProvider<StagedItem>> item = RAW_PARTS.stream().filter(i -> {
			SIRawPart<?> itemPart = ((SIRawPart<?>) i.get());
			return itemPart.metal == metal && itemPart.part == part;
		}).findFirst();
		return item.isPresent() ? item.get() : null;
	}
	
	public static ROISIProvider<StagedItem> getToolPart(Metal metal, ToolType part) {
		return I_getToolPart(metal, part);
	}
	
	public static ROISIProvider<StagedItem> getToolPart(Metal metal, CraftingToolType part) {
		return I_getToolPart(metal, part);
	}
	
	private static <T extends Enum<T>> ROISIProvider<StagedItem> I_getToolPart(Metal metal, T part) {
		Optional<ROISIProvider<StagedItem>> item = TOOL_PARTS.stream().filter(i -> {
			SIRawPart<?> itemPart = ((SIRawPart<?>) i.get());
			return itemPart.metal == metal && itemPart.part == part;
		}).findFirst();
		return item.isPresent() ? item.get() : null;
	}
	
	public static ROISIProvider<StagedItem> getCraftingTool(Metal metal, CraftingToolType type) {
		Optional<ROISIProvider<StagedItem>> item = CRAFTING_TOOLS.stream().filter(i -> {
			SICraftingTool tool = ((SICraftingTool) i.get());
			return tool.metal == metal && tool.type == type;
		}).findFirst();
		return item.isPresent() ? item.get() : null;
	}
	
	public static ROISIProvider<StagedItem> getIngot(PRRegistryObject<Metal> metal) {
		Optional<ROISIProvider<StagedItem>> item = INGOTS.stream().filter(i -> ((SIIngot) i.get()).metal == metal.get()).findFirst();
		return item.isPresent() ? item.get() : null;
	}
}
