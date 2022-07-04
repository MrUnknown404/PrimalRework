package mrunknown404.primalrework.init;

import mrunknown404.primalrework.api.registry.PRRegistryObject;
import mrunknown404.primalrework.utils.ToolMaterial;

public class InitToolMaterials {
	public static final PRRegistryObject<ToolMaterial> HAND = InitRegistry.toolMaterial("hand", () -> new ToolMaterial(0, 0, 1, 0));
	public static final PRRegistryObject<ToolMaterial> CLAY = InitRegistry.toolMaterial("clay", () -> new ToolMaterial(1, 1, 1.25f, 0));
	public static final PRRegistryObject<ToolMaterial> WOOD = InitRegistry.toolMaterial("wood", () -> new ToolMaterial(1, 2, 1.75f, 0.25f));
	public static final PRRegistryObject<ToolMaterial> FLINT = InitRegistry.toolMaterial("flint", () -> new ToolMaterial(2, 4, 3, 0.25f));
	public static final PRRegistryObject<ToolMaterial> BONE = InitRegistry.toolMaterial("bone", () -> new ToolMaterial(2, 3, 6, 2));
	public static final PRRegistryObject<ToolMaterial> STONE = InitRegistry.toolMaterial("stone", () -> new ToolMaterial(3, 8, 4.25f, 0.5f));
	public static final PRRegistryObject<ToolMaterial> UNKNOWN = InitRegistry.toolMaterial("unknown", () -> new ToolMaterial(3, 9, 2f, 1.5f));
	public static final PRRegistryObject<ToolMaterial> TIN = InitRegistry.toolMaterial("tin", () -> new ToolMaterial(4, 10, 4.5f, 1f));
	public static final PRRegistryObject<ToolMaterial> COPPER = InitRegistry.toolMaterial("copper", () -> new ToolMaterial(5, 12, 5.25f, 1.25f));
	public static final PRRegistryObject<ToolMaterial> BRONZE = InitRegistry.toolMaterial("bronze", () -> new ToolMaterial(6, 16, 7f, 1.75f));
	public static final PRRegistryObject<ToolMaterial> UNBREAKABLE = InitRegistry.toolMaterial("unbreakable", () -> new ToolMaterial(999, 0, 0, 0));
}
