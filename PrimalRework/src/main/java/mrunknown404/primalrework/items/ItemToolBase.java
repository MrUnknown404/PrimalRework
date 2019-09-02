package mrunknown404.primalrework.items;

import mrunknown404.primalrework.init.ModCreativeTabs;
import mrunknown404.primalrework.util.harvest.ToolHarvestLevel;
import mrunknown404.primalrework.util.harvest.ToolType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

public class ItemToolBase extends ItemTool implements IItemBase {

	private final ToolType toolType;
	private final ToolHarvestLevel harvestLevel;
	
	public ItemToolBase(String name, ToolType type, ToolHarvestLevel level, ToolMaterial materialIn) {
		super(getDamage(type, level), type.swingSpeed, materialIn, null);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(ModCreativeTabs.PRIMALREWORK_TOOLS);
		setMaxStackSize(1);
		
		this.toolType = type;
		this.harvestLevel = level;
		
		addToModList(this);
	}
	
	private static float getDamage(ToolType type, ToolHarvestLevel level) {
		return type == ToolType.hoe ? 0 : type.baseDamage + level.extraDamage;
	}
	
	@Override
	public boolean canDisableShield(ItemStack stack, ItemStack shield, EntityLivingBase entity, EntityLivingBase attacker) {
		return toolType == ToolType.axe;
	}
	
	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		return harvestLevel.speed;
	}
	
	@Override
	public ToolType getToolType() {
		return toolType;
	}
	
	@Override
	public ToolHarvestLevel getHarvestLevel() {
		return harvestLevel;
	}
}
