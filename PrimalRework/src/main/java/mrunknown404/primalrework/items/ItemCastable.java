package mrunknown404.primalrework.items;

import mrunknown404.primalrework.items.util.ItemBase;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemCastable extends ItemBase {
	public ItemCastable(EnumToolType type, EnumStage stage) {
		super((type == EnumToolType.none ? "ingot" : type) + "_cast", 1, stage);
		setHasSubtypes(true);
	}
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		stack.setTagCompound(checkAndFixMissingTag(stack));
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		stack.setTagCompound(checkAndFixMissingTag(stack));
		NBTTagCompound tag = stack.getTagCompound();
		
		return tag.getInteger("units") != 100;
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		stack.setTagCompound(checkAndFixMissingTag(stack));
		NBTTagCompound tag = stack.getTagCompound();
		
		float amount = 100 - tag.getInteger("units");
		return amount == 0 ? 100 : amount / 100f;
	}
	
	private NBTTagCompound checkAndFixMissingTag(ItemStack stack) {
		NBTTagCompound tag = stack.getTagCompound();
		if (tag == null) {
			tag = new NBTTagCompound();
			tag.setInteger("units", 0);
			tag.setString("alloy", "");
		}
		
		return tag;
	}
}
