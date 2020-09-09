package mrunknown404.primalrework.util.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import mrunknown404.primalrework.init.InitItems;
import mrunknown404.primalrework.util.enums.EnumToolType;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;

// TODO rewrite all enchanting (maybe write equation that gives % chance for half/etc of the possible enchants)

public class EnchantHelper {
	public static List<EnchantmentData> buildPrimalEnchantmentList(Random rand, ItemStack item, int level) {
		List<EnchantmentData> list = Lists.<EnchantmentData>newArrayList();
		List<EnchantmentData> list1 = getPrimalEnchantmentDatas(level, item);
		
		if (!list1.isEmpty()) {
			EnchantmentData re1 = WeightedRandom.getRandomItem(rand, list1);
			list.add(re1);
			list1.remove(re1);
			
			while (rand.nextInt(30) <= level) {
				if (list1.isEmpty()) {
					break;
				}
				
				EnchantmentData re2 = WeightedRandom.getRandomItem(rand, list1);
				list.add(re2);
				list1.remove(re2);
				level /= 2;
			}
		}
		
		return list;
	}
	
	private static List<EnchantmentData> getPrimalEnchantmentDatas(int level, ItemStack item) {
		List<EnchantmentData> list = Lists.<EnchantmentData>newArrayList();
		
		if (isEnchantable(item)) {
			for (EnchantmentData enchantment : EnchantHelper.getPrimalItemsEnchants(item.getItem())) {
				for (int i = enchantment.enchantmentLevel; i > 0; --i) {
					if (level >= enchantment.enchantment.getMinEnchantability(i) && level <= enchantment.enchantment.getMaxEnchantability(i)) {
						list.add(new EnchantmentData(enchantment.enchantment, i));
						break;
					}
				}
			}
		}
		
		return list;
	}
	
	private static List<EnchantmentData> getPrimalItemsEnchants(Item item) {
		List<EnchantmentData> enchs = new ArrayList<EnchantmentData>();
		
		for (EnumToolType t : HarvestHelper.getHarvestInfo(item).getToolTypes()) {
			enchs.addAll(t.primalEnchants);
		}
		
		return enchs;
	}
	
	public static boolean isEnchantable(ItemStack item) {
		if (item.isItemEnchanted()) {
			return false;
		}
		
		for (EnumToolType t : HarvestHelper.getHarvestInfo(item.getItem()).getToolTypes()) {
			if (t.isEnchantable()) {
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean isMagicDust(Item item) {
		return item == InitItems.MAGIC_DUST_RED || item == InitItems.MAGIC_DUST_GREEN || item == InitItems.MAGIC_DUST_BLUE ? true : false;
	}
}
