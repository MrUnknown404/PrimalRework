package mrunknown404.primalrework.items;

import java.util.List;

import mrunknown404.primalrework.init.ModCreativeTabs;
import mrunknown404.primalrework.util.ColorH;
import mrunknown404.primalrework.util.IEasyToolTip;
import mrunknown404.primalrework.util.harvest.ToolHarvestLevel;
import mrunknown404.primalrework.util.harvest.ToolType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class ItemToolBase extends ItemTool implements IItemBase {

	private final ToolType toolType;
	private final ToolHarvestLevel harvestLevel;
	
	protected TextComponentTranslation tooltip;
	
	public ItemToolBase(String name, ToolType type, ToolHarvestLevel level, ToolMaterial materialIn) {
		super(getDamage(type, level), type.swingSpeed, materialIn, null);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(ModCreativeTabs.PRIMALREWORK_TOOLS);
		setMaxStackSize(1);
		
		this.toolType = type;
		this.harvestLevel = level;
		
		if (this instanceof IEasyToolTip) {
			((IEasyToolTip) this).setTooltip();
		}
		
		addToModList(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		if (this.tooltip == null) {
			super.addInformation(stack, world, tooltip, advanced);
			return;
		}
		
		String[] tips = this.tooltip.getUnformattedText().trim().split("\\\\n");
		
		for (String t : tips) {
			tooltip.add(ColorH.addColor(t));
		}
	}
	
	private static float getDamage(ToolType type, ToolHarvestLevel level) {
		return type == ToolType.hoe ? 0 : type.baseDamage + level.extraDamage;
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (toolType == ToolType.knife || toolType == ToolType.sword) {
			stack.damageItem(1, attacker);
		} else {
			stack.damageItem(2, attacker);
		}
		
		return true;
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
