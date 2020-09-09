package mrunknown404.primalrework.items.util;

import java.util.List;

import mrunknown404.primalrework.init.InitCreativeTabs;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemStaged extends Item implements IItemStaged<ItemStaged> {
	
	protected final EnumStage stage;
	private int amountOfTooltops = 0;
	
	protected ItemStaged(String name, CreativeTabs tab, int maxStackSize, EnumStage stage) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(tab);
		setMaxStackSize(maxStackSize);
		this.stage = stage;
		
		addToModList(this);
	}
	
	public ItemStaged(String name, int maxStackSize, EnumStage stage) {
		this(name, InitCreativeTabs.PRIMALREWORK_ITEMS, maxStackSize, stage);
	}
	
	public ItemStaged(String name, EnumStage stage) {
		this(name, InitCreativeTabs.PRIMALREWORK_ITEMS, 64, stage);
	}
	
	@Override
	public ItemStaged setAmountOfTooltops(int amountOfTooltops) {
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
	
	@Override
	public EnumStage getStage() {
		return stage;
	}
}
