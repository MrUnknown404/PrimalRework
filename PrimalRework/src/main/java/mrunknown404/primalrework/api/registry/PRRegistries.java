package mrunknown404.primalrework.api.registry;

import java.util.HashMap;
import java.util.Map;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.stage.StagedTag;
import mrunknown404.primalrework.utils.Metal;
import mrunknown404.primalrework.utils.ToolMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

public final class PRRegistries {
	private static final Map<Class<? extends ForgeRegistryEntry<?>>, PRRegistry<?>> REGISTRIES = new HashMap<Class<? extends ForgeRegistryEntry<?>>, PRRegistry<?>>();
	
	public static final PRRegistry<Metal> METALS = addMasterRegistry("metal", Metal.class);
	public static final PRRegistry<ToolMaterial> TOOL_MATERIALS = addMasterRegistry("tool_materials", ToolMaterial.class);
	
	public static final IForgeRegistry<Stage> STAGES = new RegistryBuilder<Stage>().setName(new ResourceLocation(PrimalRework.MOD_ID, "stages")).setType(Stage.class).create();
	public static final IForgeRegistry<StagedTag> STAGED_TAGS = new RegistryBuilder<StagedTag>().setName(new ResourceLocation(PrimalRework.MOD_ID, "staged_tags"))
			.setType(StagedTag.class).create();
	
	private PRRegistries() {
		
	}
	
	/** Don't call this. It's automatic! */
	@SuppressWarnings("unchecked")
	@Deprecated
	public static <T extends ForgeRegistryEntry<T>> void addToMasterRegistery(PRRegistry<T> reg) {
		if (!REGISTRIES.containsKey(reg.getType())) {
			throw new UnsupportedOperationException("Unknown registry type '" + reg.getType().getSimpleName() + "'");
		}
		
		reg.finish();
		reg.forEach(((PRRegistry<T>) REGISTRIES.get(reg.getType()))::register);
	}
	
	private static <T extends ForgeRegistryEntry<T>> PRRegistry<T> addMasterRegistry(String name, Class<T> clazz) {
		PRRegistry<T> reg = new PRRegistry<T>("master_" + name, clazz, null);
		REGISTRIES.put(clazz, reg);
		return reg;
	}
}
