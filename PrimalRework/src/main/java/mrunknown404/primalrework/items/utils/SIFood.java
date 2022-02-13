package mrunknown404.primalrework.items.utils;

import java.util.function.Supplier;

import mrunknown404.primalrework.registries.PRItemGroups;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.enums.EnumToolMaterial;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import net.minecraft.item.Food;
import net.minecraft.item.Rarity;

public class SIFood extends StagedItem {
	public final int nutrition;
	public final float saturation;
	
	public SIFood(String name, Supplier<Stage> stage, int maxStackSize, int nutrition, float saturation, boolean isMeat, boolean canAlwaysEat, boolean fastFood) {
		super(name, stage, maxStackSize, EnumToolType.none, EnumToolMaterial.hand, PRItemGroups.FOOD, Rarity.COMMON,
				toFood(nutrition, saturation, isMeat, canAlwaysEat, fastFood), false, false, ItemType.generated);
		this.nutrition = nutrition;
		this.saturation = saturation;
	}
	
	public SIFood(String name, Supplier<Stage> stage, int maxStackSize, int nutrition, float saturation) {
		this(name, stage, maxStackSize, nutrition, saturation, false, false, false);
	}
	
	public SIFood(String name, Supplier<Stage> stage, int nutrition, float saturation) {
		this(name, stage, 64, nutrition, saturation, false, false, false);
	}
	
	private static Food toFood(int nutrition, float saturation, boolean isMeat, boolean canAlwaysEat, boolean fastFood) {
		Food.Builder f = new Food.Builder().nutrition(nutrition).saturationMod(saturation);
		
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
