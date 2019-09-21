package mrunknown404.primalrework.recipes;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import mrunknown404.primalrework.handlers.StageHandler;
import mrunknown404.primalrework.init.ModRecipes;
import mrunknown404.primalrework.recipes.util.IStagedFactoryBase;
import mrunknown404.primalrework.recipes.util.IStagedRecipeBase;
import mrunknown404.primalrework.util.EnumStage;
import mrunknown404.primalrework.util.jei.JEICompat;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.common.util.RecipeMatcher;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class StagedOreShapelessRecipe extends ShapelessOreRecipe implements IStagedRecipeBase {

	protected final EnumStage stage;
	protected final ItemStack output;
	
	public StagedOreShapelessRecipe(EnumStage stage, NonNullList<Ingredient> input, ItemStack result) {
		super(new ResourceLocation("ore_shapeless"), input, result);
		this.stage = stage;
		this.output = result;
		
		ModRecipes.addStagedRecipe(this);
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
		return true;
	}
	
	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		return StageHandler.hasAccessToStage(stage) && match(inv, world);
	}
	
	@Override
	public boolean match(InventoryCrafting inv, World world) {
		int ingredientCount = 0;
		List<ItemStack> inputs = Lists.newArrayList();
		
		for (int i = 0; i < inv.getHeight(); ++i) {
			for (int j = 0; j < inv.getWidth(); ++j) {
				ItemStack itemstack = inv.getStackInRowAndColumn(j, i);
				
				if (!itemstack.isEmpty()) {
					++ingredientCount;
					
					inputs.add(itemstack.copy());
				}
			}
		}
		
		if (ingredientCount != input.size()) {
			return false;
		}
		
		return RecipeMatcher.findMatches(inputs, input) != null;
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
		public IRecipe parse(JsonContext context, JsonObject json) {
			NonNullList<Ingredient> ings = NonNullList.create();
			for (JsonElement ele : JsonUtils.getJsonArray(json, "ingredients")) {
				ings.add(CraftingHelper.getIngredient(ele, context));
			}
			
			if (ings.isEmpty()) {
				throw new JsonParseException("No ingredients for shapeless recipe");
			}
			
			ItemStack itemstack = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context);
			return new StagedOreShapelessRecipe(EnumStage.valueOf(JsonUtils.getString(json, "stage")), ings, itemstack);
		}
	}
}
