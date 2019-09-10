package mrunknown404.primalrework.items;

import java.util.List;

import mrunknown404.primalrework.init.ModCreativeTabs;
import mrunknown404.primalrework.util.ColorH;
import mrunknown404.primalrework.util.IEasyToolTip;
import mrunknown404.primalrework.util.harvest.ToolHarvestLevel;
import mrunknown404.primalrework.util.harvest.ToolType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class ItemBase extends Item implements IItemBase {

	private final ToolType toolType;
	private final ToolHarvestLevel harvestLevel;
	
	protected TextComponentTranslation tooltip;
	
	public ItemBase(String name, int maxStackSize, ToolType type, ToolHarvestLevel level) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(ModCreativeTabs.PRIMALREWORK_ITEMS);
		setMaxStackSize(maxStackSize);
		
		this.toolType = type;
		this.harvestLevel = level;
		
		if (this instanceof IEasyToolTip) {
			((IEasyToolTip) this).setTooltip();
		}
		
		addToModList(this);
	}
	
	public ItemBase(String name) {
		this(name, 64, ToolType.none, ToolHarvestLevel.hand);
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
	
	@Override
	public ToolType getToolType() {
		return toolType;
	}
	
	@Override
	public ToolHarvestLevel getHarvestLevel() {
		return harvestLevel;
	}
}
