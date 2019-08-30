package mrunknown404.primalrework.init;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

import mrunknown404.primalrework.util.DoubleValue;
import mrunknown404.primalrework.util.DummyRecipe;
import mrunknown404.primalrework.util.FirePitRecipeInfo;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

public class ModRecipes {
	
	private static final List<FirePitRecipeInfo> FIRE_PIT_RECIPES = new ArrayList<FirePitRecipeInfo>();
	private static final List<DoubleValue<ItemStack, Integer>> FIRE_PIT_FUELS = new ArrayList<>();
	
	public static void addRecipes() {
		FIRE_PIT_FUELS.add(new DoubleValue<ItemStack, Integer>(new ItemStack(Items.COAL), 1200));
		FIRE_PIT_FUELS.add(new DoubleValue<ItemStack, Integer>(new ItemStack(Blocks.LOG), 400));
		FIRE_PIT_FUELS.add(new DoubleValue<ItemStack, Integer>(new ItemStack(Blocks.LOG2), 400));
		FIRE_PIT_FUELS.add(new DoubleValue<ItemStack, Integer>(new ItemStack(Blocks.PLANKS), 100));
		FIRE_PIT_FUELS.add(new DoubleValue<ItemStack, Integer>(new ItemStack(Items.STICK), 25));
		
		//TODO write more of these (and remove the test recipe)
		FIRE_PIT_RECIPES.add(new FirePitRecipeInfo(new ItemStack(Blocks.BRICK_BLOCK), new ItemStack(Items.COAL), 100));
	}
	
	public static void removeRecipes() {
		ForgeRegistry<IRecipe> rr = (ForgeRegistry<IRecipe>) ForgeRegistries.RECIPES;
		
		for (IRecipe r : Lists.newArrayList(rr.getValuesCollection())) {
			if (r.getRegistryName().toString().startsWith("minecraft:")) {
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
	
	public static boolean isItemFirePitFuel(ItemStack stack) {
		for (DoubleValue<ItemStack, Integer> dv : FIRE_PIT_FUELS) {
			if (compareItemStacks(dv.getLeft(), stack)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean isItemFirePitResult(ItemStack stack) {
		return getFirePitResult(stack) != null;
	}
	
	public static int getFirePitBurnTime(ItemStack stack) {
		for (DoubleValue<ItemStack, Integer> dv : FIRE_PIT_FUELS) {
			if (compareItemStacks(dv.getLeft(), stack)) {
				return dv.getRight();
			}
		}
		
		return 0;
	}
	
	public static FirePitRecipeInfo getFirePitResult(ItemStack stack) {
		for (FirePitRecipeInfo info : FIRE_PIT_RECIPES) {
			if (compareItemStacks(info.getInput(), stack)) {
				return info;
			}
		}
		
		return null;
	}
	
	public static int getCookTime(ItemStack stack) {
		for (FirePitRecipeInfo info : FIRE_PIT_RECIPES) {
			if (compareItemStacks(info.getInput(), stack)) {
				return info.cookTime;
			}
		}
		
		return 100;
	}
	
	private static boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}
}
