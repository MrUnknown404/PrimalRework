package mrunknown404.primalrework.items;

import java.util.function.Supplier;

import mrunknown404.primalrework.init.InitPRItemGroups;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.utils.enums.ToolMaterial;
import mrunknown404.primalrework.utils.enums.ToolType;
import mrunknown404.primalrework.utils.helpers.MathH;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;

public class SIFood extends StagedItem {
	public final int nutrition;
	public final float saturation;
	
	public SIFood(Supplier<Stage> stage, int maxStackSize, int nutrition, float saturation, boolean isMeat, boolean canAlwaysEat, boolean fastFood) {
		super(stage, maxStackSize, ToolType.NONE, ToolMaterial.HAND, InitPRItemGroups.FOOD, Rarity.COMMON, toFood(nutrition, saturation, isMeat, canAlwaysEat, fastFood), false, false,
				ItemType.generated);
		this.nutrition = nutrition;
		this.saturation = saturation;
	}
	
	public SIFood(Supplier<Stage> stage, int maxStackSize, int nutrition, float saturation) {
		this(stage, maxStackSize, nutrition, saturation, false, false, false);
	}
	
	public SIFood(Supplier<Stage> stage, int nutrition, float saturation) {
		this(stage, 64, nutrition, saturation, false, false, false);
	}
	
	@Override
	public int getUseDuration(ItemStack stack) {
		return MathH.ceil((Math.max(getFoodProperties().getNutrition(), 3) / 20f) * 96);
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
