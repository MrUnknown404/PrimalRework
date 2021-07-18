package mrunknown404.primalrework.items.utils;

import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.utils.enums.EnumStage;
import mrunknown404.primalrework.utils.enums.EnumToolMaterial;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import net.minecraft.item.Food;
import net.minecraft.item.Rarity;

public class SIFood extends StagedItem {
	
	public SIFood(String name, EnumStage stage, int maxStackSize, int nutrition, float saturationModifier, boolean isMeat, boolean canAlwaysEat, boolean fastFood) {
		super(name, stage, maxStackSize, EnumToolType.none, EnumToolMaterial.hand, PRItemGroups.FOOD, Rarity.COMMON,
				toFood(nutrition, saturationModifier, isMeat, canAlwaysEat, fastFood), false, false, ItemType.generated);
	}
	
	public SIFood(String name, EnumStage stage, int maxStackSize, int nutrition, float saturationModifier) {
		this(name, stage, maxStackSize, nutrition, saturationModifier, false, false, false);
	}
	
	public SIFood(String name, EnumStage stage, int nutrition, float saturationModifier) {
		this(name, stage, 64, nutrition, saturationModifier, false, false, false);
	}
	
	private static Food toFood(int nutrition, float saturationModifier, boolean isMeat, boolean canAlwaysEat, boolean fastFood) {
		Food.Builder f = new Food.Builder().nutrition(nutrition).saturationMod(saturationModifier);
		
		if (isMeat) {
			f.meat();
		}
		if (canAlwaysEat) {
			f.alwaysEat();
		}
		if (fastFood) {
			f.fast();
		}
		
		return f.build();
	}
}
