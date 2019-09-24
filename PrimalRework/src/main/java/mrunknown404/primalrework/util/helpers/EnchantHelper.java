package mrunknown404.primalrework.util.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.util.DoubleValue;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.MathHelper;

public class EnchantHelper {
	public static List<EnchantmentData> buildVanillaEnchantmentList(Random randomIn, ItemStack item, int level) {
		List<EnchantmentData> list = Lists.<EnchantmentData>newArrayList();
		int i = HarvestHelper.getHarvestInfo(item.getItem()).getHighestEnchantability();
		
		if (i <= 0) {
			return list;
		} else {
			level = level + 1 + randomIn.nextInt(i / 4 + 1) + randomIn.nextInt(i / 4 + 1);
			float f = (randomIn.nextFloat() + randomIn.nextFloat() - 1.0F) * 0.15F;
			level = MathHelper.clamp(Math.round((float) level + (float) level * f), 1, Integer.MAX_VALUE);
			List<EnchantmentData> list1 = getVanillaEnchantmentDatas(level, item);
			
			if (!list1.isEmpty()) {
				EnchantmentData re1 = WeightedRandom.getRandomItem(randomIn, list1);
				list.add(re1);
				list1.remove(re1);
				
				while (randomIn.nextInt(50) <= level) {
					if (list1.isEmpty()) {
						break;
					}
					
					EnchantmentData re2 = WeightedRandom.getRandomItem(randomIn, list1);
					list.add(re2);
					list1.remove(re2);
					level /= 2;
				}
			}
			
			return list;
		}
	}
	
	public static List<EnchantmentData> buildPrimalEnchantmentList(Random rand, ItemStack item, int level) {
		List<EnchantmentData> list = Lists.<EnchantmentData>newArrayList();
		int enchantability = HarvestHelper.getHarvestInfo(item.getItem()).getHighestEnchantability();
		
		if (enchantability <= 0) {
			return list;
		} else {
			if (enchantability < 5) {
				level *= (enchantability * 4);
			} else {
				level *= enchantability;
			}
			
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
	}
	
	private static List<EnchantmentData> getVanillaEnchantmentDatas(int enchantability, ItemStack item) {
		List<EnchantmentData> list = Lists.<EnchantmentData>newArrayList();
		
		for (EnchantmentData enchantment : EnchantHelper.getVanillaItemsEnchants(item.getItem())) {
			if (isEnchantable(item) || (item.getItem() == Items.BOOK && enchantment.enchantment.isAllowedOnBooks())) {
				for (int i = enchantment.enchantmentLevel; i > 0; --i) {
					if (enchantability >= enchantment.enchantment.getMinEnchantability(i) && enchantability <= enchantment.enchantment.getMaxEnchantability(i)) {
						list.add(new EnchantmentData(enchantment.enchantment, i));
						break;
					}
				}
			}
		}
		
		return list;
	}
	
	private static List<EnchantmentData> getPrimalEnchantmentDatas(int enchantability, ItemStack item) {
		List<EnchantmentData> list = Lists.<EnchantmentData>newArrayList();
		
		for (EnchantmentData enchantment : EnchantHelper.getPrimalItemsEnchants(item.getItem())) {
			if (isEnchantable(item) || (item.getItem() == Items.BOOK && enchantment.enchantment.isAllowedOnBooks())) {
				for (int i = enchantment.enchantmentLevel; i > 0; --i) {
					if (enchantability >= enchantment.enchantment.getMinEnchantability(i) && enchantability <= enchantment.enchantment.getMaxEnchantability(i)) {
						list.add(new EnchantmentData(enchantment.enchantment, i));
						break;
					}
				}
			}
		}
		
		return list;
	}
	
	private static List<EnchantmentData> getVanillaItemsEnchants(Item item) {
		List<EnchantmentData> enchs = new ArrayList<EnchantmentData>();
		
		for (DoubleValue<EnumToolType, EnumToolMaterial> t : HarvestHelper.getHarvestInfo(item).getTypesHarvests()) {
			enchs.addAll(t.getL().replaceEnchants);
		}
		
		return enchs;
	}
	
	private static List<EnchantmentData> getPrimalItemsEnchants(Item item) {
		List<EnchantmentData> enchs = new ArrayList<EnchantmentData>();
		
		for (DoubleValue<EnumToolType, EnumToolMaterial> t : HarvestHelper.getHarvestInfo(item).getTypesHarvests()) {
			enchs.addAll(t.getL().primalEnchants);
		}
		
		return enchs;
	}
	
	public static boolean isEnchantable(ItemStack item) {
		if (item.isItemEnchanted()) {
			return false;
		}
		
		for (DoubleValue<EnumToolType, EnumToolMaterial> t : HarvestHelper.getHarvestInfo(item.getItem()).getTypesHarvests()) {
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
	
	public static boolean isMagicDust(Item item) {
		return item == ModItems.MAGIC_DUST_RED || item == ModItems.MAGIC_DUST_GREEN || item == ModItems.MAGIC_DUST_BLUE ? true : false;
	}
}