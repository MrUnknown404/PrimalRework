package mrunknown404.primalrework.items;

import mrunknown404.primalrework.init.ModCreativeTabs;
import mrunknown404.primalrework.util.harvest.ToolHarvestLevel;
import mrunknown404.primalrework.util.harvest.ToolType;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IItemBase {

	private final ToolType toolType;
	private final ToolHarvestLevel harvestLevel;
	
	public ItemBase(String name, int maxStackSize, ToolType type, ToolHarvestLevel level) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(ModCreativeTabs.PRIMALREWORK_ITEMS);
		setMaxStackSize(maxStackSize);
		
		this.toolType = type;
		this.harvestLevel = level;
		
		addToModList(this);
	}
	
	public ItemBase(String name) {
		this(name, 64, ToolType.none, ToolHarvestLevel.hand);
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
