package mrunknown404.primalrework.items.util;

import java.util.List;

import mrunknown404.primalrework.init.ModCreativeTabs;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBase extends Item implements IItemBase<ItemBase> {

	private int amountOfTooltops = 0;
	
	protected ItemBase(String name, CreativeTabs tab, int maxStackSize) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(tab);
		setMaxStackSize(maxStackSize);
		
		addToModList(this);
	}
	
	public ItemBase(String name, int maxStackSize) {
		this(name, ModCreativeTabs.PRIMALREWORK_ITEMS, maxStackSize);
	}
	
	public ItemBase(String name) {
		this(name, ModCreativeTabs.PRIMALREWORK_ITEMS, 64);
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
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.addAll(getTooltips(getUnlocalizedName()));
	}
	
	@Override
	public EnumToolType getToolType() {
		return EnumToolType.none;
	}
	
	@Override
	public EnumToolMaterial getHarvestLevel() {
		return EnumToolMaterial.hand;
	}
}
