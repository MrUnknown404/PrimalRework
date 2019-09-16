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

	private final EnumToolType toolType;
	private final EnumToolMaterial harvestLevel;
	
	public ItemBase(String name, int maxStackSize, EnumToolType type, EnumToolMaterial level) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(ModCreativeTabs.PRIMALREWORK_ITEMS);
		setMaxStackSize(maxStackSize);
		
		this.toolType = type;
		this.harvestLevel = level;
		
		addToModList(this);
	}
	
	public ItemBase(String name) {
		this(name, 64, EnumToolType.none, EnumToolMaterial.hand);
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.addAll(getTooltip(getUnlocalizedName()));
	}
	
	@Override
	public EnumToolType getToolType() {
		return toolType;
	}
	
	@Override
	public EnumToolMaterial getHarvestLevel() {
		return harvestLevel;
	}
}
