package mrunknown404.primalrework.util.jei.category;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableAnimated.StartDirection;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.init.ModRecipes;
import mrunknown404.primalrework.util.jei.wrappers.DryingTableRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class DryingTableRecipeCategory implements IRecipeCategory<DryingTableRecipeWrapper> {
	private static final ResourceLocation TEXTURES = new ResourceLocation(Main.MOD_ID, "textures/gui/jei/drying_table.png");
	public static final int SLOT_INPUT = 0, SLOT_OUTPUT = 1;
	
	private final IDrawableAnimated animatedDryTime;
	private final IDrawable background, icon;
	
	public DryingTableRecipeCategory(IGuiHelper helper) {
		IDrawableStatic staticDryTime = helper.createDrawable(TEXTURES, 115, 0, 22, 16);
		animatedDryTime = helper.createAnimatedDrawable(staticDryTime, 100, StartDirection.LEFT, false);
		
		background = helper.createDrawable(TEXTURES, 0, 0, 115, 45);
		icon = helper.createDrawableIngredient(new ItemStack(ModBlocks.DRYING_TABLE));
	}
	
	@Override
	public IDrawable getBackground() {
		return background;
	}
	
	@Override
	public void drawExtras(Minecraft minecraft) {
		animatedDryTime.draw(minecraft, 46, 5);
	}
	
	@Override
	public String getTitle() {
		return ModBlocks.DRYING_TABLE.getLocalizedName();
	}
	
	@Override
	public String getUid() {
		return ModRecipes.CATEGORY_DRYING_TABLE;
	}
	
	@Override
	public void setRecipe(IRecipeLayout layout, DryingTableRecipeWrapper recipe, IIngredients ingredients) {
		IGuiItemStackGroup stacks = layout.getItemStacks();
		
		stacks.init(SLOT_INPUT, true, 26, 4);
		stacks.init(SLOT_OUTPUT, false, 74, 4);
		
		stacks.set(ingredients);
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
