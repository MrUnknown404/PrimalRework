package mrunknown404.primalrework.items;

import java.util.List;

import mrunknown404.primalrework.init.ModCreativeTabs;
import mrunknown404.primalrework.util.harvest.EnumToolMaterial;
import mrunknown404.primalrework.util.harvest.EnumToolType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBase extends Item implements IItemBase {

	private final EnumToolMaterial harvestLevel;
	private final boolean isContainer;
	
	public ItemBase(String name, int maxStackSize, boolean isContainer, EnumToolMaterial level) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(ModCreativeTabs.PRIMALREWORK_ITEMS);
		setMaxStackSize(maxStackSize);
		setMaxDamage(level.durability);
		
		this.harvestLevel = level;
		this.isContainer = isContainer;
		
		addToModList(this);
	}
	
	public ItemBase(String name, EnumToolMaterial level) {
		this(name, 1, true, level);
	}
	
	public ItemBase(String name) {
		this(name, 64, false, EnumToolMaterial.hand);
	}
	
	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return isContainer;
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		ItemStack s = stack.copy();
		s.setItemDamage(stack.getItemDamage() + 1);
		return s;
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.addAll(getTooltip(getUnlocalizedName()));
	}
	
	@Override
	public EnumToolType getToolType() {
		return EnumToolType.none;
	}
	
	@Override
	public EnumToolMaterial getHarvestLevel() {
		return harvestLevel;
	}
}
