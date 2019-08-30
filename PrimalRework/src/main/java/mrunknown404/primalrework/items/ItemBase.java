package mrunknown404.primalrework.items;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.init.ModCreativeTabs;
import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.util.IHasModel;
import mrunknown404.primalrework.util.harvest.ToolHarvestLevel;
import mrunknown404.primalrework.util.harvest.ToolType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel {

	private final ToolType toolType;
	private final ToolHarvestLevel harvestLevel;
	
	public ItemBase(String name, CreativeTabs tab, int maxStackSize, ToolType type, ToolHarvestLevel level) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(tab);
		setMaxStackSize(maxStackSize);
		
		this.toolType = type;
		this.harvestLevel = level;
		
		ModItems.ITEMS.add(this);
	}
	
	public ItemBase(String name) {
		this(name, ModCreativeTabs.PRIMALREWORK_ITEMS, 64, ToolType.none, ToolHarvestLevel.hand);
	}
	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
	public ToolType getToolType() {
		return toolType;
	}
	
	public ToolHarvestLevel getHarvestLevel() {
		return harvestLevel;
	}
}
