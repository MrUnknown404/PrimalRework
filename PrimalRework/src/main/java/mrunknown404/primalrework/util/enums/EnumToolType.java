package mrunknown404.primalrework.util.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Enchantments;

public enum EnumToolType {
	none   (0.00f, 0.00f, new ArrayList<EnchantmentData>(), new ArrayList<EnchantmentData>()),
	shears (1.75f, 1.50f,
			combineEnchants(Enchs.getToolBase(5, 3), Enchs.getToolSpecials(3, 1)),
			Enchs.getToolBase(2, 1)),
	knife  (1.75f, 2.00f,
			combineEnchants(Enchs.getToolBase(5, 3), Enchs.getToolSpecials(3, 1), Enchs.getWeaponBase(5, 2)),
			combineEnchants(Enchs.getToolBase(2, 1), Enchs.getWeaponBase(2, 1))),
	pickaxe(1.25f, 1.00f,
			combineEnchants(Enchs.getToolBase(5, 3), Enchs.getToolSpecials(3, 1)),
			Enchs.getToolBase(2, 1)),
	shovel (1.50f, 1.50f,
			combineEnchants(Enchs.getToolBase(5, 3), Enchs.getToolSpecials(3, 1)),
			Enchs.getToolBase(2, 1)),
	axe    (0.90f, 4.50f,
			combineEnchants(Enchs.getToolBase(5, 3), Enchs.getToolSpecials(3, 1), Enchs.getWeaponBase(5, 2)),
			combineEnchants(Enchs.getToolBase(2, 1), Enchs.getWeaponBase(2, 1))),
	hoe    (1.25f, 0.50f,
			Arrays.asList(new EnchantmentData(Enchantments.UNBREAKING, 3), new EnchantmentData(Enchantments.KNOCKBACK, 2)),
			Arrays.asList(new EnchantmentData(Enchantments.UNBREAKING, 1), new EnchantmentData(Enchantments.KNOCKBACK, 1))),
	sword  (1.50f, 3.00f,
			combineEnchants(combineEnchants(Enchs.getWeaponBase(5, 2), Enchs.getWeaponSword(5, 5), Enchs.getWeaponSpecial(3, 2, 3)), new EnchantmentData(Enchantments.UNBREAKING, 3)),
			combineEnchants(Enchs.getWeaponBase(2, 1), new EnchantmentData(Enchantments.UNBREAKING, 1)));
	
	public final float swingSpeed, baseDamage;
	public final List<EnchantmentData> replaceEnchants, primalEnchants;
	
	private EnumToolType(float swingSpeed, float baseDamage, List<EnchantmentData> replaceEnchants, List<EnchantmentData> primalEnchants) {
		this.swingSpeed = swingSpeed - 4;
		this.baseDamage = baseDamage;
		this.replaceEnchants = replaceEnchants;
		this.primalEnchants = primalEnchants;
	}
	
	public boolean isEnchantable() {
		return this != none;
	}
	
	private static List<EnchantmentData> combineEnchants(List<EnchantmentData>... enchs) {
		List<EnchantmentData> newEnchs = new ArrayList<EnchantmentData>();
		
		for (List<EnchantmentData> e : enchs) {
			newEnchs.addAll(e);
		}
		
		return newEnchs;
	}
	
	private static List<EnchantmentData> combineEnchants(List<EnchantmentData> tench, EnchantmentData... enchs) {
		List<EnchantmentData> ench = new ArrayList<>(tench);
		for (EnchantmentData e : enchs) {
			ench.add(e);
		}
		
		return ench;
	}
	
	private static class Enchs {
		//private static final List<Enchantment> TOOL_FISHING = Arrays.asList(Enchantments.LUCK_OF_THE_SEA, Enchantments.LURE);
		//private static final List<Enchantment> WEAPON_BOW = Arrays.asList(Enchantments.POWER, Enchantments.PUNCH, Enchantments.FLAME, Enchantments.INFINITY);
		
		/**@param i1 EFFICIENCY
		 * @param i2 UNBREAKING
		 */
		private static List<EnchantmentData> getToolBase(int i1, int i2) {
			return Arrays.asList(new EnchantmentData(Enchantments.EFFICIENCY, i1), new EnchantmentData(Enchantments.UNBREAKING, i2));
		}
		
		/**@param i1 FORTUNE
		 * @param i2 SILK_TOUCH
		 */
		private static List<EnchantmentData> getToolSpecials(int i1, int i2) {
			return Arrays.asList(new EnchantmentData(Enchantments.FORTUNE, i1), new EnchantmentData(Enchantments.SILK_TOUCH, i2));
		}
		
		/**@param i1 SHARPNESS
		 * @param i2 KNOCKBACK
		 */
		private static List<EnchantmentData> getWeaponBase(int i1, int i2) {
			return Arrays.asList(new EnchantmentData(Enchantments.SHARPNESS, i1), new EnchantmentData(Enchantments.KNOCKBACK, i2));
		}
		
		/**@param i1 SMITE
		 * @param i2 BANE_OF_ARTHROPODS
		 */
		private static List<EnchantmentData> getWeaponSword(int i1, int i2) {
			return Arrays.asList(new EnchantmentData(Enchantments.SMITE, i1), new EnchantmentData(Enchantments.BANE_OF_ARTHROPODS, i2));
		}
		
		/**@param i1 LOOTING
		 * @param i2 FIRE_ASPECT
		 * @param i3 SWEEPING
		 */
		private static List<EnchantmentData> getWeaponSpecial(int i1, int i2, int i3) {
			return Arrays.asList(new EnchantmentData(Enchantments.LOOTING, i1), new EnchantmentData(Enchantments.FIRE_ASPECT, i2),
					new EnchantmentData(Enchantments.SWEEPING, i3));
		}
	}
}