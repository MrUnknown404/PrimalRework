package mrunknown404.primalrework.registries;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.items.SIClayVessel;
import mrunknown404.primalrework.items.SINugget;
import mrunknown404.primalrework.items.utils.SIDamageable;
import mrunknown404.primalrework.items.utils.SIFood;
import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.utils.enums.EnumAlloy;
import mrunknown404.primalrework.utils.enums.EnumOreValue;
import mrunknown404.primalrework.utils.enums.EnumStage;
import mrunknown404.primalrework.utils.enums.EnumToolMaterial;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class PRItems {
	private static final List<RegistryObject<Item>> CASTS = new ArrayList<RegistryObject<Item>>();
	private static final List<RegistryObject<Item>> INGOTS = new ArrayList<RegistryObject<Item>>();
	
	//MISC
	public static final RegistryObject<Item> PLANT_FIBER = register(new StagedItem("plant_fiber", EnumStage.stage0));
	public static final RegistryObject<Item> PLANT_ROPE = register(new StagedItem("plant_rope", EnumStage.stage0));
	public static final RegistryObject<Item> GRAVEL = register(new StagedItem("gravel", EnumStage.stage0));
	public static final RegistryObject<Item> KNAPPED_FLINT = register(new StagedItem("knapped_flint", EnumStage.stage0));
	public static final RegistryObject<Item> FLINT_POINT = register(new StagedItem("flint_point", EnumStage.stage0));
	public static final RegistryObject<Item> BONE_SHARD = register(new StagedItem("bone_shard", EnumStage.stage0));
	public static final RegistryObject<Item> BARK = register(new StagedItem("bark", EnumStage.stage0));
	public static final RegistryObject<Item> CLOTH = register(new StagedItem("cloth", EnumStage.stage2));
	public static final RegistryObject<Item> OAK_PLANK = register(new StagedItem("oak_plank", EnumStage.stage2));
	public static final RegistryObject<Item> SPRUCE_PLANK = register(new StagedItem("spruce_plank", EnumStage.stage2));
	public static final RegistryObject<Item> BIRCH_PLANK = register(new StagedItem("birch_plank", EnumStage.stage2));
	public static final RegistryObject<Item> JUNGLE_PLANK = register(new StagedItem("jungle_plank", EnumStage.stage2));
	public static final RegistryObject<Item> DARK_OAK_PLANK = register(new StagedItem("dark_oak_plank", EnumStage.stage2));
	public static final RegistryObject<Item> ACACIA_PLANK = register(new StagedItem("acacia_plank", EnumStage.stage2));
	public static final RegistryObject<Item> SALT = register(new StagedItem("salt", EnumStage.stage0));
	public static final RegistryObject<Item> ANIMAL_PELT = register(new StagedItem("animal_pelt", EnumStage.stage0));
	public static final RegistryObject<Item> WET_HIDE = register(new StagedItem("wet_hide", EnumStage.stage1));
	public static final RegistryObject<Item> RAW_HIDE = register(new StagedItem("raw_hide", EnumStage.stage1));
	public static final RegistryObject<Item> CLEANED_HIDE = register(new StagedItem("cleaned_hide", EnumStage.stage1));
	public static final RegistryObject<Item> SALTED_HIDE = register(new StagedItem("salted_hide", EnumStage.stage1));
	public static final RegistryObject<Item> DRIED_HIDE = register(new StagedItem("dried_hide", EnumStage.stage1));
	public static final RegistryObject<Item> WET_TANNED_HIDE = register(new StagedItem("wet_tanned_hide", EnumStage.stage1));
	public static final RegistryObject<Item> DRY_TANNED_HIDE = register(new StagedItem("dry_tanned_hide", EnumStage.stage1));
	public static final RegistryObject<Item> ROPE = register(new StagedItem("rope", EnumStage.stage1));
	public static final RegistryObject<Item> LEATHER_STRIP = register(new StagedItem("leather_strip", EnumStage.stage1));
	public static final RegistryObject<Item> CLAY_VESSEL = register(new SIClayVessel());
	public static final RegistryObject<Item> BLANK_CAST = register(new StagedItem("blank_cast", EnumStage.stage3));
	
	//DAMAGEABLE
	public static final RegistryObject<Item> MORTAR_PESTLE = register(new SIDamageable("mortar_pestle", EnumStage.stage0, EnumToolMaterial.wood));
	public static final RegistryObject<Item> PLANT_MESH = register(new SIDamageable("plant_mesh", EnumStage.stage0, EnumToolMaterial.wood));
	public static final RegistryObject<Item> STRING_MESH = register(new SIDamageable("string_mesh", EnumStage.stage0, EnumToolMaterial.flint));
	
	// TODO edit cooked beetroot texture
	// TODO all these items -> clay buckets, fire starter
	
	//FOOD
	public static final RegistryObject<Item> DOUGH = register(new SIFood("dough", EnumStage.stage1, 1, 0.5f));
	public static final RegistryObject<Item> COOKED_CARROT = register(new SIFood("cooked_carrot", EnumStage.stage0, 5, 0.6f));
	public static final RegistryObject<Item> COOKED_BEETROOT = register(new SIFood("cooked_beetroot", EnumStage.stage0, 5, 0.6f));
	public static final RegistryObject<Item> COOKED_PUFFERFISH = register(new SIFood("cooked_pufferfish", EnumStage.stage0, 5, 0.6f));
	public static final RegistryObject<Item> COOKED_TROPICALFISH = register(new SIFood("cooked_tropicalfish", EnumStage.stage0, 5, 0.6f));
	
	private static RegistryObject<Item> register(StagedItem item) {
		return PRRegistry.ITEMS.register(item.getRegName(), () -> item);
	}
	
	public static void register() {
		for (EnumToolType type : EnumToolType.values()) {
			if (type != EnumToolType.none) {
				registerCast(type);
			}
		}
		for (EnumAlloy alloy : EnumAlloy.values()) {
			if (alloy.hasOre) {
				for (EnumOreValue value : EnumOreValue.values()) {
					if (value == EnumOreValue.block) {
						continue;
					}
					
					INGOTS.add(register(new SINugget(alloy, value)));
				}
			}
			
			if (alloy == EnumAlloy.unknown) {
				INGOTS.add(register(new StagedItem(alloy.toString() + "_ingot", alloy.stage, 16).addTooltip()));
				INGOTS.add(register(new StagedItem(alloy.toString() + "_nugget", alloy.stage, 32).addTooltip()));
			} else {
				INGOTS.add(register(new StagedItem(alloy.toString() + "_ingot", alloy.stage, 16)));
				INGOTS.add(register(new StagedItem(alloy.toString() + "_nugget", alloy.stage, 32)));
			}
		}
	}
	
	private static RegistryObject<Item> registerCast(EnumToolType type) {
		StagedItem item = new StagedItem(type.name() + "_cast", EnumStage.stage3, 8);
		RegistryObject<Item> reg = PRRegistry.ITEMS.register(item.getRegName(), () -> item);
		CASTS.add(reg);
		return reg;
	}
	
	//TODO make a block to clean ore and use this!
	public static SINugget getOre(EnumAlloy alloy, EnumOreValue value) {
		for (RegistryObject<Item> item : INGOTS) {
			if (item.get() instanceof SINugget) {
				SINugget ore = (SINugget) item.get();
				if (ore.alloy == alloy && ore.value == value) {
					return ore;
				}
			}
		}
		
		return null;
	}
}
