package mrunknown404.primalrework.util.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import mrunknown404.primalrework.util.DoubleValue;
import mrunknown404.primalrework.util.harvest.EnumToolMaterial;
import mrunknown404.primalrework.util.harvest.EnumToolType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.MathHelper;

public class EnchantHelper {
	public static List<EnchantmentData> buildEnchantmentList(Random randomIn, Item item, int level, boolean allowTreasure) {
		List<EnchantmentData> list = Lists.<EnchantmentData>newArrayList();
		int i = HarvestHelper.getHarvestInfo(item).getHighestEnchantability();
		
		if (i <= 0) {
			return list;
		} else {
			level = level + 1 + randomIn.nextInt(i / 4 + 1) + randomIn.nextInt(i / 4 + 1);
			float f = (randomIn.nextFloat() + randomIn.nextFloat() - 1.0F) * 0.15F;
			level = MathHelper.clamp(Math.round((float) level + (float) level * f), 1, Integer.MAX_VALUE);
			List<EnchantmentData> list1 = getEnchantmentDatas(level, item, allowTreasure);
			
			if (!list1.isEmpty()) {
				list.add(WeightedRandom.getRandomItem(randomIn, list1));
				
				while (randomIn.nextInt(50) <= level) {
					if (list1.isEmpty()) {
						break;
					}
					
					list.add(WeightedRandom.getRandomItem(randomIn, list1));
					level /= 2;
				}
			}
			
			return list;
		}
	}
	
	public static List<EnchantmentData> getEnchantmentDatas(int p_185291_0_, Item item, boolean allowTreasure) {
		List<EnchantmentData> list = Lists.<EnchantmentData>newArrayList();
		boolean flag = item == Items.BOOK;
		
		for (Enchantment enchantment : EnchantHelper.getItemsEnchants(item)) {
			if ((!enchantment.isTreasureEnchantment() || allowTreasure) && (isEnchantable(item) || (flag && enchantment.isAllowedOnBooks()))) {
				for (int i = enchantment.getMaxLevel(); i > enchantment.getMinLevel() - 1; --i) {
					if (p_185291_0_ >= enchantment.getMinEnchantability(i) && p_185291_0_ <= enchantment.getMaxEnchantability(i)) {
						list.add(new EnchantmentData(enchantment, i));
						break;
					}
				}
			}
		}
		
		return list;
	}
	
	private static List<Enchantment> getItemsEnchants(Item item) {
		List<Enchantment> enchs = new ArrayList<Enchantment>();
		
		for (DoubleValue<EnumToolType, EnumToolMaterial> t : HarvestHelper.getHarvestInfo(item).getTypesHarvests()) {
			enchs.addAll(t.getL().replaceEnchants);
		}
		
		return enchs;
	}
	
	public static boolean isEnchantable(Item item) {
		if (item == Items.BOOK) {
			return true;
		}
		
		for (DoubleValue<EnumToolType, EnumToolMaterial> t : HarvestHelper.getHarvestInfo(item).getTypesHarvests()) {
			if (t.getL().isEnchantable()) {
				return true;
			}
		}
		
		return false;
	}
	
	public static int calcItemStackEnchantability(Random rand, int enchantNum, int power, Item item) {
		int i = HarvestHelper.getHarvestInfo(item).getHighestEnchantability();
		
		if (i <= 0) {
			return 0;
		} else {
			if (power > 15) {
				power = 15;
			}
			
			int j = rand.nextInt(8) + 1 + (power >> 1) + rand.nextInt(power + 1);
			
			if (enchantNum == 0) {
				return Math.max(j / 3, 1);
			} else {
				return enchantNum == 1 ? j * 2 / 3 + 1 : Math.max(j, power * 2);
			}
		}
	}
}
