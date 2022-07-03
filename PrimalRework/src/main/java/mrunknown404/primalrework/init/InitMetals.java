package mrunknown404.primalrework.init;

import java.awt.Color;

import mrunknown404.primalrework.blocks.BlockInfo.Hardness;
import mrunknown404.primalrework.registry.Metal;
import mrunknown404.primalrework.registry.PRRegistryObject;
import mrunknown404.primalrework.utils.enums.Element;
import mrunknown404.primalrework.utils.enums.ToolMaterial;

public class InitMetals {
	public static final PRRegistryObject<Metal> UNKNOWN = InitRegistry.metal("unknown",
			() -> Metal.metal(Hardness.MEDIUM_0, InitStages.STAGE_3, ToolMaterial.STONE, null, 500, 1000));
	public static final PRRegistryObject<Metal> COPPER = InitRegistry.metal("copper",
			() -> Metal.elemental(Hardness.MEDIUM_0, InitStages.STAGE_3, ToolMaterial.STONE, new Color(255, 145, 0), Element.COPPER));
	public static final PRRegistryObject<Metal> TIN = InitRegistry.metal("tin",
			() -> Metal.elemental(Hardness.SOFT_2, InitStages.STAGE_3, ToolMaterial.STONE, new Color(255, 237, 251), Element.TIN));
	public static final PRRegistryObject<Metal> BRONZE = InitRegistry.metal("bronze",
			() -> Metal.elementalAlloy(Hardness.MEDIUM_1, InitStages.STAGE_4, ToolMaterial.COPPER, new Color(208, 101, 0), 1183, 2573, Element.COPPER, Element.TIN));
}