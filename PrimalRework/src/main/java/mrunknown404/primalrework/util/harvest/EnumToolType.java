package mrunknown404.primalrework.util.harvest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Enchantments;

public enum EnumToolType {
	none   (0.00f, 0.00f, new ArrayList<Enchantment>()),
	shears (1.75f, 1.50f, combineEnchants(Enchs.TOOL_EFF_UN, Enchs.TOOL_FORT_SILK)),
	knife  (1.75f, 2.00f, combineEnchants(Enchs.TOOL_EFF_UN, Enchs.TOOL_FORT_SILK, Enchs.WEAPON_BASE)),
	pickaxe(1.25f, 1.00f, combineEnchants(Enchs.TOOL_EFF_UN, Enchs.TOOL_FORT_SILK)),
	shovel (1.50f, 1.50f, combineEnchants(Enchs.TOOL_EFF_UN, Enchs.TOOL_FORT_SILK)),
	axe    (0.90f, 4.50f, combineEnchants(Enchs.TOOL_EFF_UN, Enchs.TOOL_FORT_SILK, Enchs.WEAPON_BASE)),
	hoe    (1.25f, 0.50f, Arrays.asList(Enchantments.UNBREAKING, Enchantments.KNOCKBACK)),
	sword  (1.50f, 3.00f, combineEnchants(combineEnchants(Enchs.WEAPON_BASE, Enchs.WEAPON_SPECIFIC, Enchs.WEAPON_SPECIAL), Enchantments.UNBREAKING));
	
	public final float swingSpeed, baseDamage;
	public final List<Enchantment> replaceEnchants;
	
	private EnumToolType(float swingSpeed, float baseDamage, List<Enchantment> replaceEnchants) {
		this.swingSpeed = swingSpeed - 4;
		this.baseDamage = baseDamage;
		this.replaceEnchants = replaceEnchants;
	}
	
	public boolean isEnchantable() {
		return this != none;
	}
	
	private static List<Enchantment> combineEnchants(List<Enchantment>... enchs) {
		List<Enchantment> newEnchs = new ArrayList<Enchantment>();
		
		for (List<Enchantment> e : enchs) {
			newEnchs.addAll(e);
		}
		
		return newEnchs;
	}
	
	private static List<Enchantment> combineEnchants(List<Enchantment> tench, Enchantment... enchs) {
		List<Enchantment> ench = new ArrayList<>(tench);
		for (Enchantment e : enchs) {
			ench.add(e);
		}
		
		return ench;
	}
	
	public static class Enchs {
		public static final List<Enchantment> TOOL_EFF_UN = Arrays.asList(Enchantments.EFFICIENCY, Enchantments.UNBREAKING);
		public static final List<Enchantment> TOOL_FORT_SILK = Arrays.asList(Enchantments.FORTUNE, Enchantments.SILK_TOUCH);
		public static final List<Enchantment> TOOL_FISHING = Arrays.asList(Enchantments.LUCK_OF_THE_SEA, Enchantments.LURE);
		
		public static final List<Enchantment> WEAPON_BASE = Arrays.asList(Enchantments.SHARPNESS, Enchantments.KNOCKBACK);
		public static final List<Enchantment> WEAPON_SPECIFIC = Arrays.asList(Enchantments.SMITE, Enchantments.BANE_OF_ARTHROPODS, Enchantments.SWEEPING);
		public static final List<Enchantment> WEAPON_SPECIAL = Arrays.asList(Enchantments.LOOTING, Enchantments.FIRE_ASPECT);
		public static final List<Enchantment> WEAPON_BOW = Arrays.asList(Enchantments.POWER, Enchantments.PUNCH, Enchantments.FLAME, Enchantments.INFINITY);
	}
}
