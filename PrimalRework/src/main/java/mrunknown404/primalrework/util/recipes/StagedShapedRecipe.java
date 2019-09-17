package mrunknown404.primalrework.util.recipes;

import com.google.gson.JsonObject;

import mrunknown404.primalrework.handlers.StageHandler;
import mrunknown404.primalrework.handlers.StageHandler.Stage;
import mrunknown404.primalrework.util.recipes.util.IStagedFactoryBase;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;

public class StagedShapedRecipe extends ShapedRecipes {

	protected final Stage stage;
	
	public StagedShapedRecipe(Stage stage, int width, int height, NonNullList<Ingredient> ingredients, ItemStack result) {
		super("gr", width, height, ingredients, result);
		this.stage = stage;
	}
	
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		return StageHandler.hasAccessToStage(stage) && match(inv, worldIn);
	}
	
	public boolean match(InventoryCrafting inv, World worldIn) {
		for (int i = 0; i <= inv.getWidth() - this.recipeWidth; ++i) {
			for (int j = 0; j <= inv.getHeight() - this.recipeHeight; ++j) {
				if (this.checkMatch(inv, i, j, true)) {
					return true;
				}
				
				if (this.checkMatch(inv, i, j, false)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean checkMatch(InventoryCrafting inv, int w, int h, boolean bool) {
		for (int i = 0; i < inv.getWidth(); ++i) {
			for (int j = 0; j < inv.getHeight(); ++j) {
				int k = i - w;
				int l = j - h;
				Ingredient ingredient = Ingredient.EMPTY;
				
				if (k >= 0 && l >= 0 && k < this.recipeWidth && l < this.recipeHeight) {
					if (bool) {
						ingredient = this.recipeItems.get(this.recipeWidth - k - 1 + l * this.recipeWidth);
					} else {
						ingredient = this.recipeItems.get(k + l * this.recipeWidth);
					}
				}
				
				ItemStack is = inv.getStackInRowAndColumn(i, j).copy();
				is.setItemDamage(0);
				
				if (!ingredient.apply(is)) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public static class Factory implements IRecipeFactory, IStagedFactoryBase{
		@Override
		public IRecipe parse(final JsonContext context, final JsonObject json) {
			String[] astring = shrink(patternFromJson(JsonUtils.getJsonArray(json, "pattern")));
			int width = astring[0].length();
			int height = astring.length;
			
			NonNullList<Ingredient> nonnulllist = deserializeIngredients(astring, deserializeKey(JsonUtils.getJsonObject(json, "key")), width, height);
			ItemStack itemstack = deserializeItem(JsonUtils.getJsonObject(json, "result"), true);
			
			return new StagedShapedRecipe(Stage.valueOf(JsonUtils.getString(json, "stage")), width, height, nonnulllist, itemstack);
		}
	}
}
