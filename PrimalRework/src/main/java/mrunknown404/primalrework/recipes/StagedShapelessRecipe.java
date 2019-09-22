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
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;

public class StagedShapelessRecipe extends ShapelessRecipes implements IStagedRecipeBase {

	protected final EnumStage stage;
	protected final ItemStack output;
	
	public StagedShapelessRecipe(EnumStage stage, ItemStack output, NonNullList<Ingredient> ingredients) {
		super("shapeless", output, ingredients);
		this.stage = stage;
		this.output = output;
		
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
		return StageHelper.hasAccessToStage(stage) && super.matches(inv, world);
	}
	
	@Override public boolean match(InventoryCrafting inv, World world) {return false;}
	
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
			NonNullList<Ingredient> ingredients = deserializeIngredients(JsonUtils.getJsonArray(json, "ingredients"));
			ItemStack result = ShapedRecipes.deserializeItem(JsonUtils.getJsonObject(json, "result"), true);
			return new StagedShapelessRecipe(EnumStage.valueOf(JsonUtils.getString(json, "stage")), result, ingredients);
		}
	}
}
