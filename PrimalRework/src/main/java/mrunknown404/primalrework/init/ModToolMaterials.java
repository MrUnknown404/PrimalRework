package mrunknown404.primalrework.init;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class ModToolMaterials {
	public static final ToolMaterial FLINT_MATERIAL = setupMaterial("FLINT_MATERIAL", 32, 2);
	
	private static ToolMaterial setupMaterial(String name, int durability, int enchantability) {
		return EnumHelper.addToolMaterial(name, 0, durability, 0, 0, enchantability);
	}
}
