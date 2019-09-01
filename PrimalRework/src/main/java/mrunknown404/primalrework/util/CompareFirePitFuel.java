package mrunknown404.primalrework.util;

import java.util.Comparator;

import net.minecraft.item.ItemStack;

public class CompareFirePitFuel implements Comparator<DoubleValue<ItemStack, Integer>> {
	@Override
	public int compare(DoubleValue<ItemStack, Integer> o1, DoubleValue<ItemStack, Integer> o2) {
		return o1.getL().getDisplayName().compareTo(o2.getL().getDisplayName()) - o1.getR()- o2.getR();
	}
}
