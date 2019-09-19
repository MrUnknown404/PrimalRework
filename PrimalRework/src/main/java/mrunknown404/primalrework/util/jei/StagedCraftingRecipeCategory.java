package mrunknown404.primalrework.util.jei;

import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.init.ModRecipes;
import mrunknown404.primalrework.util.recipes.StagedCraftingWrapper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class StagedCraftingRecipeCategory implements IRecipeCategory<StagedCraftingWrapper> {
	
	private static final ResourceLocation TEXTURES = new ResourceLocation(Main.MOD_ID, "textures/gui/crafting.png");
	private static final int craftOutputSlot = 0;
	private static final int craftInputSlot1 = 1;
	
	private final IDrawable background, icon;
	private final ICraftingGridHelper craftingGridHelper;
	
	public StagedCraftingRecipeCategory(IGuiHelper helper) {
		background = helper.createDrawable(TEXTURES, 0, 0, 116, 64);
		icon = helper.createDrawableIngredient(new ItemStack(Blocks.CRAFTING_TABLE));
		craftingGridHelper = helper.createCraftingGridHelper(craftInputSlot1, craftOutputSlot);
	}
	
	@Override
	public IDrawable getBackground() {
		return background;
	}
	
	@Override
	public String getTitle() {
		return Blocks.CRAFTING_TABLE.getLocalizedName();
	}
	
	@Override
	public String getUid() {
		return ModRecipes.CATEGORY_STAGED_CRAFTING;
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, StagedCraftingWrapper recipe, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		
		guiItemStacks.init(craftOutputSlot, false, 94, 18);
		
		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 3; ++x) {
				int index = craftInputSlot1 + x + (y * 3);
				guiItemStacks.init(index, true, x * 18, y * 18);
			}
		}
		
		List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
		List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);
		
		if (recipe.isShapeless()) {
			craftingGridHelper.setInputs(guiItemStacks, inputs);
			recipeLayout.setShapeless();
		} else {
			craftingGridHelper.setInputs(guiItemStacks, inputs, recipe.getRecipe().getWidth(), recipe.getRecipe().getHeight());
		}
		
		guiItemStacks.set(craftOutputSlot, outputs.get(0));
	}
	
	@Override
	public String getModName() {
		return "Primal Rework";
	}
	
	@Override
	public IDrawable getIcon() {
		return icon;
	}
}
