package mrunknown404.primalrework.registries;

import mrunknown404.primalrework.items.SITool;
import mrunknown404.primalrework.utils.enums.EnumStage;
import mrunknown404.primalrework.utils.enums.EnumToolMaterial;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import net.minecraftforge.fml.RegistryObject;

public class PRTools {
	public static final RegistryObject<SITool> CLAY_SHOVEL = register(new SITool(EnumStage.stage0, EnumToolType.shovel, EnumToolMaterial.clay));
	public static final RegistryObject<SITool> CLAY_AXE = register(new SITool(EnumStage.stage0, EnumToolType.axe, EnumToolMaterial.clay));
	public static final RegistryObject<SITool> WOOD_SHOVEL = register(new SITool(EnumStage.stage0, EnumToolType.shovel, EnumToolMaterial.wood));
	public static final RegistryObject<SITool> WOOD_AXE = register(new SITool(EnumStage.stage0, EnumToolType.axe, EnumToolMaterial.wood));
	public static final RegistryObject<SITool> FLINT_PICKAXE = register(new SITool(EnumStage.stage1, EnumToolType.pickaxe, EnumToolMaterial.flint));
	public static final RegistryObject<SITool> FLINT_SHOVEL = register(new SITool(EnumStage.stage1, EnumToolType.shovel, EnumToolMaterial.flint));
	public static final RegistryObject<SITool> FLINT_AXE = register(new SITool(EnumStage.stage1, EnumToolType.axe, EnumToolMaterial.flint));
	public static final RegistryObject<SITool> FLINT_HOE = register(new SITool(EnumStage.stage1, EnumToolType.hoe, EnumToolMaterial.flint));
	public static final RegistryObject<SITool> FLINT_KNIFE = register(new SITool(EnumStage.stage1, EnumToolType.knife, EnumToolMaterial.flint));
	public static final RegistryObject<SITool> FLINT_SAW = register(new SITool(EnumStage.stage1, EnumToolType.saw, EnumToolMaterial.flint));
	public static final RegistryObject<SITool> FLINT_SHEARS = register(new SITool(EnumStage.stage1, EnumToolType.shears, EnumToolMaterial.flint));
	public static final RegistryObject<SITool> BONE_SWORD = register(new SITool(EnumStage.stage1, EnumToolType.sword, EnumToolMaterial.bone));
	
	private static RegistryObject<SITool> register(SITool item) {
		return PRRegistry.ITEMS.register(item.getRegName(), () -> item);
	}
	
	//@formatter:off
	static void register() { }
	//@formatter:on
}
