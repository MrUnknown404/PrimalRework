package mrunknown404.primalrework.items.util;

import java.util.List;

import mrunknown404.primalrework.init.ModCreativeTabs;
import mrunknown404.primalrework.util.harvest.EnumToolMaterial;
import mrunknown404.primalrework.util.harvest.EnumToolType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBase extends Item implements IItemBase<ItemBase> {

	private final EnumToolMaterial harvestLevel;
	private final boolean isContainer;
	private int amountOfTooltops = 0;
	
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
	
	public ItemBase(String name, int maxStackSize) {
		this(name, maxStackSize, false, EnumToolMaterial.hand);
	}
	
	public ItemBase(String name) {
		this(name, 64, false, EnumToolMaterial.hand);
	}
	
	@Override
	public ItemBase setAmountOfTooltops(int amountOfTooltops) {
		this.amountOfTooltops = amountOfTooltops;
		return this;
	}
	
	@Override
	public int getAmountOfTooltips() {
		return amountOfTooltops;
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
		tooltip.addAll(getTooltips(getUnlocalizedName()));
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
