package mrunknown404.primalrework.util.recipes;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;

import mrunknown404.primalrework.handlers.StageHandler;
import mrunknown404.primalrework.handlers.StageHandler.Stage;
import mrunknown404.primalrework.util.recipes.util.IStagedFactoryBase;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.common.util.RecipeMatcher;

public class StagedShapelessRecipe extends ShapelessRecipes {

	protected final Stage stage;
	
	public StagedShapelessRecipe(Stage stage, ItemStack output, NonNullList<Ingredient> ingredients) {
		super("shapeless", output, ingredients);
		this.stage = stage;
	}
	
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		return StageHandler.hasAccessToStage(stage) && match(inv, worldIn);
	}
	
	private boolean match(InventoryCrafting inv, World worldIn) {
		int ingredientCount = 0;
		List<ItemStack> inputs = Lists.newArrayList();
		
		for (int i = 0; i < inv.getHeight(); ++i) {
			for (int j = 0; j < inv.getWidth(); ++j) {
				ItemStack itemstack = inv.getStackInRowAndColumn(j, i);
				
				if (!itemstack.isEmpty()) {
					++ingredientCount;
					
					ItemStack st = itemstack.copy();
					st.setItemDamage(0);
					inputs.add(st);
				}
			}
		}
		
		if (ingredientCount != this.recipeItems.size()) {
			return false;
		}
		
		return RecipeMatcher.findMatches(inputs, this.recipeItems) != null;
	}
	
	public static class Factory implements IRecipeFactory, IStagedFactoryBase {
		@Override
		public IRecipe parse(final JsonContext context, final JsonObject json) {
			NonNullList<Ingredient> ingredients = deserializeIngredients(JsonUtils.getJsonArray(json, "ingredients"));
			ItemStack result = ShapedRecipes.deserializeItem(JsonUtils.getJsonObject(json, "result"), true);
			return new StagedShapelessRecipe(Stage.valueOf(JsonUtils.getString(json, "stage")), result, ingredients);
		}
	}
}
