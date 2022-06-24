package mrunknown404.primalrework.registries;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.items.SICraftingTool;
import mrunknown404.primalrework.items.SIIngot;
import mrunknown404.primalrework.items.SIOre;
import mrunknown404.primalrework.items.SIRawPart;
import mrunknown404.primalrework.items.SISimpleTool;
import mrunknown404.primalrework.items.utils.SIDamageable;
import mrunknown404.primalrework.items.utils.SIFood;
import mrunknown404.primalrework.items.utils.StagedItem;
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
	private static final List<RegistryObject<StagedItem>> ORES = new ArrayList<RegistryObject<StagedItem>>();
	private static final List<RegistryObject<StagedItem>> CRAFTING_TOOLS = new ArrayList<RegistryObject<StagedItem>>();
	
	//MISC
	public static final RegistryObject<StagedItem> PLANT_FIBER = PRRegistry.item(new StagedItem("plant_fiber", PRStages.STAGE_0));
	public static final RegistryObject<StagedItem> PLANT_ROPE = PRRegistry.item(new StagedItem("plant_rope", PRStages.STAGE_0));
	public static final RegistryObject<StagedItem> KNAPPED_FLINT = PRRegistry.item(new StagedItem("knapped_flint", PRStages.STAGE_0));
	public static final RegistryObject<StagedItem> FLINT_POINT = PRRegistry.item(new StagedItem("flint_point", PRStages.STAGE_0));
	public static final RegistryObject<StagedItem> BONE_SHARD = PRRegistry.item(new StagedItem("bone_shard", PRStages.STAGE_0));
	public static final RegistryObject<StagedItem> BARK = PRRegistry.item(new StagedItem("bark", PRStages.STAGE_0));
	public static final RegistryObject<StagedItem> CLOTH = PRRegistry.item(new StagedItem("cloth", PRStages.STAGE_2));
	public static final RegistryObject<StagedItem> OAK_PLANK = PRRegistry.item(new StagedItem("oak_plank", PRStages.STAGE_2));
	public static final RegistryObject<StagedItem> SPRUCE_PLANK = PRRegistry.item(new StagedItem("spruce_plank", PRStages.STAGE_2));
	public static final RegistryObject<StagedItem> BIRCH_PLANK = PRRegistry.item(new StagedItem("birch_plank", PRStages.STAGE_2));
	public static final RegistryObject<StagedItem> JUNGLE_PLANK = PRRegistry.item(new StagedItem("jungle_plank", PRStages.STAGE_2));
	public static final RegistryObject<StagedItem> DARK_OAK_PLANK = PRRegistry.item(new StagedItem("dark_oak_plank", PRStages.STAGE_2));
	public static final RegistryObject<StagedItem> ACACIA_PLANK = PRRegistry.item(new StagedItem("acacia_plank", PRStages.STAGE_2));
	public static final RegistryObject<StagedItem> SALT = PRRegistry.item(new StagedItem("salt", PRStages.STAGE_0));
	public static final RegistryObject<StagedItem> ANIMAL_PELT = PRRegistry.item(new StagedItem("animal_pelt", PRStages.STAGE_0));
	public static final RegistryObject<StagedItem> WET_HIDE = PRRegistry.item(new StagedItem("wet_hide", PRStages.STAGE_1));
	public static final RegistryObject<StagedItem> RAW_HIDE = PRRegistry.item(new StagedItem("raw_hide", PRStages.STAGE_1));
	public static final RegistryObject<StagedItem> CLEANED_HIDE = PRRegistry.item(new StagedItem("cleaned_hide", PRStages.STAGE_1));
	public static final RegistryObject<StagedItem> SALTED_HIDE = PRRegistry.item(new StagedItem("salted_hide", PRStages.STAGE_1));
	public static final RegistryObject<StagedItem> DRIED_HIDE = PRRegistry.item(new StagedItem("dried_hide", PRStages.STAGE_1));
	public static final RegistryObject<StagedItem> WET_TANNED_HIDE = PRRegistry.item(new StagedItem("wet_tanned_hide", PRStages.STAGE_1));
	public static final RegistryObject<StagedItem> DRY_TANNED_HIDE = PRRegistry.item(new StagedItem("dry_tanned_hide", PRStages.STAGE_1));
	public static final RegistryObject<StagedItem> ROPE = PRRegistry.item(new StagedItem("rope", PRStages.STAGE_1));
	public static final RegistryObject<StagedItem> LEATHER_STRIP = PRRegistry.item(new StagedItem("leather_strip", PRStages.STAGE_1));
	public static final RegistryObject<StagedItem> BLANK_CAST = PRRegistry.item(new StagedItem("blank_cast", PRStages.STAGE_3));
	public static final RegistryObject<StagedItem> ROCK = PRRegistry.item(new StagedItem("rock", PRStages.STAGE_0));
	
	//DAMAGEABLE
	public static final RegistryObject<StagedItem> MORTAR_PESTLE = PRRegistry.item(new SIDamageable("mortar_pestle", PRStages.STAGE_0, ToolMaterial.WOOD));
	public static final RegistryObject<StagedItem> PLANT_MESH = PRRegistry.item(new SIDamageable("plant_mesh", PRStages.STAGE_0, ToolMaterial.WOOD));
	public static final RegistryObject<StagedItem> STRING_MESH = PRRegistry.item(new SIDamageable("string_mesh", PRStages.STAGE_0, ToolMaterial.FLINT));
	
	//FOOD
	public static final RegistryObject<StagedItem> DOUGH = PRRegistry.item(new SIFood("dough", PRStages.STAGE_1, 1, 0.5f));
	
	//TOOLS
	public static final RegistryObject<SISimpleTool> CLAY_SHOVEL = PRRegistry.item(new SISimpleTool(PRStages.STAGE_0, ToolType.SHOVEL, ToolMaterial.CLAY));
	public static final RegistryObject<SISimpleTool> CLAY_AXE = PRRegistry.item(new SISimpleTool(PRStages.STAGE_0, ToolType.AXE, ToolMaterial.CLAY));
	public static final RegistryObject<SISimpleTool> WOOD_SHOVEL = PRRegistry
			.item((SISimpleTool) new SISimpleTool(PRStages.STAGE_0, ToolType.SHOVEL, ToolMaterial.WOOD).useVanillaNamespace());
	public static final RegistryObject<SISimpleTool> WOOD_AXE = PRRegistry
			.item((SISimpleTool) new SISimpleTool(PRStages.STAGE_0, ToolType.AXE, ToolMaterial.WOOD).useVanillaNamespace());
	public static final RegistryObject<SISimpleTool> FLINT_PICKAXE = PRRegistry.item(new SISimpleTool(PRStages.STAGE_1, ToolType.PICKAXE, ToolMaterial.FLINT));
	public static final RegistryObject<SISimpleTool> FLINT_SHOVEL = PRRegistry.item(new SISimpleTool(PRStages.STAGE_1, ToolType.SHOVEL, ToolMaterial.FLINT));
	public static final RegistryObject<SISimpleTool> FLINT_AXE = PRRegistry.item(new SISimpleTool(PRStages.STAGE_1, ToolType.AXE, ToolMaterial.FLINT));
	public static final RegistryObject<SISimpleTool> FLINT_HOE = PRRegistry.item(new SISimpleTool(PRStages.STAGE_1, ToolType.HOE, ToolMaterial.FLINT));
	public static final RegistryObject<SISimpleTool> FLINT_KNIFE = PRRegistry.item(new SISimpleTool(PRStages.STAGE_1, ToolType.KNIFE, ToolMaterial.FLINT));
	public static final RegistryObject<SISimpleTool> FLINT_SHEARS = PRRegistry.item(new SISimpleTool(PRStages.STAGE_1, ToolType.SHEARS, ToolMaterial.FLINT));
	public static final RegistryObject<SISimpleTool> BONE_SWORD = PRRegistry.item(new SISimpleTool(PRStages.STAGE_1, ToolType.SWORD, ToolMaterial.BONE));
	
	//VANILLA OVERRIDES
	public static final RegistryObject<StagedItem> FLINT = PRRegistry.item(new StagedItem("flint", PRStages.STAGE_0).useVanillaNamespace());
	public static final RegistryObject<StagedItem> STICK = PRRegistry.item(new StagedItem("stick", PRStages.STAGE_0).useVanillaNamespace());
	public static final RegistryObject<StagedItem> CLAY_BALL = PRRegistry.item(new StagedItem("clay_ball", PRStages.STAGE_0).useVanillaNamespace());
	
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
			INGOTS.add(PRRegistry.item(new SIIngot(metal, false)));
			INGOTS.add(PRRegistry.item(new SIIngot(metal, true)));
			if (!metal.isAlloy) {
				ORES.add(PRRegistry.item(new SIOre(metal)));
			}
			
			for (RawPart type : RawPart.values()) {
				RAW_PARTS.add(PRRegistry.item(SIRawPart.rawPart(metal, type)));
			}
			for (CraftingToolType type : CraftingToolType.values()) {
				CRAFTING_TOOLS.add(PRRegistry.item(new SICraftingTool(metal, type)));
				TOOL_PARTS.add(PRRegistry.item(SIRawPart.toolHead(metal, type)));
			}
			for (ToolType type : ToolType.values()) {
				if (type != ToolType.NONE) {
					TOOL_PARTS.add(PRRegistry.item(SIRawPart.toolHead(metal, type)));
				}
			}
		}
	}
	
	private static RegistryObject<StagedItem> registerCast(Enum<?> type) {
		StagedItem item = new StagedItem(type.name() + "_cast", PRStages.STAGE_3, 8);
		RegistryObject<StagedItem> reg = PRRegistry.item(item);
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
