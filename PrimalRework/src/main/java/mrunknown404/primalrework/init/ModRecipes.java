package mrunknown404.primalrework.init;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.recipes.DryingTableRecipe;
import mrunknown404.primalrework.recipes.FirePitRecipe;
import mrunknown404.primalrework.recipes.util.DummyRecipe;
import mrunknown404.primalrework.recipes.util.IStagedRecipeBase;
import mrunknown404.primalrework.util.DoubleValue;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.helpers.StageHelper;
import mrunknown404.primalrework.util.jei.wrappers.DryingTableRecipeWrapper;
import mrunknown404.primalrework.util.jei.wrappers.FirePitRecipeWrapper;
import mrunknown404.primalrework.util.jei.wrappers.StagedCraftingWrapper;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

public class ModRecipes {
	
	//TODO add recipes for : leather armor, mushroom stew, bread, cook recipes, wooden tools, boat, paper, book, sugar
	
	public static final String CATEGORY_STAGED_CRAFTING = Main.MOD_ID + ".staged_crafting_jei";
	public static final String CATEGORY_FIRE_PIT = Main.MOD_ID + ".fire_pit_jei";
	public static final String CATEGORY_DRYING_TABLE = Main.MOD_ID + ".drying_table_jei";
	
	private static final List<IStagedRecipeBase> STAGED_CRAFTING_RECIPES = new ArrayList<IStagedRecipeBase>();
	
	private static final List<FirePitRecipe> FIRE_PIT_RECIPES = new ArrayList<FirePitRecipe>();
	private static final List<DryingTableRecipe> DRYING_TABLE_RECIPES = new ArrayList<DryingTableRecipe>();
	
	private static final List<DoubleValue<ItemStack, Integer>> FIRE_PIT_FUELS = new ArrayList<>();
	
	public static void addRecipes() {
		FIRE_PIT_FUELS.add(new DoubleValue<ItemStack, Integer>(new ItemStack(Items.COAL), 1200));
		FIRE_PIT_FUELS.add(new DoubleValue<ItemStack, Integer>(new ItemStack(Items.STICK), 25));
		
		for (EnumType t : EnumType.values()) {
			FIRE_PIT_FUELS.add(new DoubleValue<ItemStack, Integer>(new ItemStack(Blocks.PLANKS, 1, t.getMetadata()), 100));
			
			if (t == EnumType.DARK_OAK || t == EnumType.ACACIA) {
				FIRE_PIT_FUELS.add(new DoubleValue<ItemStack, Integer>(new ItemStack(Blocks.LOG2, 1, t.getMetadata() - 4), 400));
			} else {
				FIRE_PIT_FUELS.add(new DoubleValue<ItemStack, Integer>(new ItemStack(Blocks.LOG, 1, t.getMetadata()), 400));
			}
		}
		
		FIRE_PIT_FUELS.sort(new CompareFirePitFuel());
		
		FIRE_PIT_RECIPES.add(new FirePitRecipe(EnumStage.stage0, ModBlocks.UNLIT_PRIMAL_TORCH, ModBlocks.LIT_PRIMAL_TORCH, 10));
		FIRE_PIT_RECIPES.add(new FirePitRecipe(EnumStage.stage1, Blocks.COBBLESTONE, Blocks.STONE, 100));
		FIRE_PIT_RECIPES.add(new FirePitRecipe(EnumStage.stage1, Blocks.STONE, ModBlocks.SMOOTH_STONE, 100));
		
		DRYING_TABLE_RECIPES.add(new DryingTableRecipe(EnumStage.stage1, ModItems.SALTED_HIDE, ModItems.DRIED_HIDE, 1200));
		DRYING_TABLE_RECIPES.add(new DryingTableRecipe(EnumStage.stage1, ModItems.WET_TANNED_HIDE, Items.LEATHER, 1200));
	}
	
	public static void removeRecipes() {
		ForgeRegistry<IRecipe> rr = (ForgeRegistry<IRecipe>) ForgeRegistries.RECIPES;
		
		for (IRecipe r : Lists.newArrayList(rr.getValuesCollection())) {
			if (!r.getRegistryName().toString().startsWith("primalrework:")) {
				rr.remove(r.getRegistryName());
				rr.register(DummyRecipe.from(r));
			}
		}
		
		Iterator<ItemStack> it = FurnaceRecipes.instance().getSmeltingList().keySet().iterator();
		
		while (it.hasNext()) {
			it.next();
			it.remove();
		}
	}
	
	public static boolean doesItemHaveFirePitRecipe(ItemStack stack) {
		return getFirePitRecipeFromInput(stack) != null;
	}
	
	public static boolean doesItemHaveDryingTableRecipe(ItemStack stack) {
		return getDryingTableRecipeFromInput(stack) != null;
	}
	
	public static boolean isItemFirePitFuel(ItemStack stack) {
		for (DoubleValue<ItemStack, Integer> dv : FIRE_PIT_FUELS) {
			if (compareItemStacks(dv.getLeft(), stack)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static int getFirePitBurnTime(ItemStack stack) {
		for (DoubleValue<ItemStack, Integer> dv : FIRE_PIT_FUELS) {
			if (compareItemStacks(dv.getLeft(), stack)) {
				return dv.getRight();
			}
		}
		
		return 0;
	}
	
	public static FirePitRecipe getFirePitRecipeFromInput(ItemStack stack) {
		for (FirePitRecipe info : FIRE_PIT_RECIPES) {
			if (StageHelper.hasAccessToStage(info.getStage()) && compareItemStacks(info.getInput(), stack)) {
				return info;
			}
		}
		
		return null;
	}
	
	public static DryingTableRecipe getDryingTableRecipeFromInput(ItemStack stack) {
		for (DryingTableRecipe info : DRYING_TABLE_RECIPES) {
			if (StageHelper.hasAccessToStage(info.getStage()) && compareItemStacks(info.getInput(), stack)) {
				return info;
			}
		}
		
		return null;
	}
	
	public static int getCookTime(ItemStack stack) {
		for (FirePitRecipe info : FIRE_PIT_RECIPES) {
			if (compareItemStacks(info.getInput(), stack)) {
				return info.getCookTime();
			}
		}
		
		return 100;
	}
	
	private static boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}
	
	public static void addStagedRecipe(IStagedRecipeBase recipe) {
		STAGED_CRAFTING_RECIPES.add(recipe);
	}
	
	public static List<FirePitRecipeWrapper> getWrappedFirePitRecipes() {
		return FirePitRecipeWrapper.createFromList(FIRE_PIT_RECIPES);
	}
	
	public static List<StagedCraftingWrapper> getWrappedStageRecipes() {
		return StagedCraftingWrapper.createFromList(STAGED_CRAFTING_RECIPES);
	}
	
	public static List<DryingTableRecipeWrapper> getWrappedDryingTableRecipes() {
		return DryingTableRecipeWrapper.createFromList(DRYING_TABLE_RECIPES);
	}
	
	public static List<ItemStack> getFirePitFuels() {
		List<ItemStack> fuels = new ArrayList<ItemStack>();
		
		for (DoubleValue<ItemStack, Integer> dv : FIRE_PIT_FUELS) {
			fuels.add(dv.getL());
		}
		
		return fuels;
	}
	
	private static class CompareFirePitFuel implements Comparator<DoubleValue<ItemStack, Integer>> {
		@Override
		public int compare(DoubleValue<ItemStack, Integer> o1, DoubleValue<ItemStack, Integer> o2) {
			return o1.getL().getDisplayName().compareTo(o2.getL().getDisplayName()) - (o1.getR()- o2.getR());
		}
	}
}
