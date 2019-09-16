package mrunknown404.primalrework.items;

import java.util.List;

import mrunknown404.primalrework.init.ModCreativeTabs;
import mrunknown404.primalrework.util.harvest.EnumToolMaterial;
import mrunknown404.primalrework.util.harvest.EnumToolType;
import mrunknown404.primalrework.util.harvest.HarvestHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemToolBase extends ItemTool implements IItemBase {

	private final EnumToolType toolType;
	private final EnumToolMaterial harvestLevel;
	
	public ItemToolBase(String name, EnumToolType type, EnumToolMaterial level, ToolMaterial materialIn) {
		super(getDamage(type, level), type.swingSpeed, materialIn, null);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(ModCreativeTabs.PRIMALREWORK_TOOLS);
		setMaxStackSize(1);
		setMaxDamage(level.durability);
		
		this.toolType = type;
		this.harvestLevel = level;
		
		addToModList(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.addAll(getTooltip(getUnlocalizedName()));
	}
	
	private static float getDamage(EnumToolType type, EnumToolMaterial level) {
		return type == EnumToolType.hoe ? 0 : type.baseDamage + level.extraDamage;
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (toolType == EnumToolType.knife || toolType == EnumToolType.sword || toolType == EnumToolType.axe) {
			stack.damageItem(1, attacker);
		} else {
			stack.damageItem(3, attacker);
		}
		
		return true;
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
		if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0) {
			if (HarvestHelper.canBreak(state.getBlock(), stack.getItem())) {
				stack.damageItem(1, entityLiving);
			} else {
				stack.damageItem(3, entityLiving);
			}
		}
		
		return true;
	}
	
	@Override
	public boolean canDisableShield(ItemStack stack, ItemStack shield, EntityLivingBase entity, EntityLivingBase attacker) {
		return toolType == EnumToolType.axe;
	}
	
	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		return harvestLevel.speed;
	}
	
	@Override
	public int getItemEnchantability() {
		return harvestLevel.enchantability;
	}
	
	@Override
	public EnumToolType getToolType() {
		return toolType;
	}
	
	@Override
	public EnumToolMaterial getHarvestLevel() {
		return harvestLevel;
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return getItemEnchantability() != 0;
	}
}
