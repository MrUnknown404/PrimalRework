package mrunknown404.primalrework.recipes;

import java.util.Arrays;
import java.util.List;

import com.google.gson.JsonObject;

import mrunknown404.primalrework.init.ModRecipes;
import mrunknown404.primalrework.recipes.util.IStagedFactoryBase;
import mrunknown404.primalrework.recipes.util.IStagedRecipeBase;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.helpers.StageHelper;
import mrunknown404.primalrework.util.jei.JEICompat;
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

public class StagedShapedRecipe extends ShapedRecipes implements IStagedRecipeBase {
	
	protected final EnumStage stage;
	protected final ItemStack output;
	
	public StagedShapedRecipe(EnumStage stage, int width, int height, NonNullList<Ingredient> ingredients, ItemStack result) {
		super("shaped", width, height, ingredients, result);
		this.stage = stage;
		this.output = result;
		
		ModRecipes.addStagedRecipe(this);
	}
	
	@Override
	public int getWidth() {
		return recipeWidth;
	}
	
	@Override
	public int getHeight() {
		return recipeHeight;
	}
	
	@Override
	public List<List<ItemStack>> getListInputs() {
		return JEICompat.helper.getStackHelper().expandRecipeItemStackInputs(getIngredients());
	}
	
	@Override
	public List<ItemStack> getOutputs() {
		return Arrays.asList(output);
	}
	
	@Override
	public boolean isShapeless() {
		return false;
	}
	
	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		return StageHelper.hasAccessToStage(stage) && super.matches(inv, world);
	}
	
	@Override
	public boolean match(InventoryCrafting inv, World world) {
		return false;
	}
	
	@Override
	public IRecipe getRecipe() {
		return this;
	}
	
	@Override
	public EnumStage getStage() {
		return stage;
	}
	
	public static class Factory implements IRecipeFactory, IStagedFactoryBase {
		@Override
		public IRecipe parse(final JsonContext context, final JsonObject json) {
			String[] astring = shrink(patternFromJson(JsonUtils.getJsonArray(json, "pattern")));
			int width = astring[0].length();
			int height = astring.length;
			
			NonNullList<Ingredient> nonnulllist = deserializeIngredients(astring, deserializeKey(JsonUtils.getJsonObject(json, "key")), width, height);
			ItemStack itemstack = deserializeItem(JsonUtils.getJsonObject(json, "result"), true);
			
			return new StagedShapedRecipe(EnumStage.valueOf(JsonUtils.getString(json, "stage")), width, height, nonnulllist, itemstack);
		}
	}
}
