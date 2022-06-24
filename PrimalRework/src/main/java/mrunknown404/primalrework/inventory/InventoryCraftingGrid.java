package mrunknown404.primalrework.inventory;

import java.util.Arrays;
import java.util.List;

import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.recipes.IStagedRecipe;
import mrunknown404.primalrework.recipes.Ingredient;
import mrunknown404.primalrework.recipes.input.RICrafting3;
import mrunknown404.primalrework.registries.PRRecipes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;

public class InventoryCraftingGrid implements IInventory {
	private final NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
	private final Container menu;
	private final InventoryCraftingResult result;
	private IStagedRecipe<?, ?> recipe;
	
	public InventoryCraftingGrid(Container container, InventoryCraftingResult result) {
		this.menu = container;
		this.result = result;
	}
	
	@Override
	public int getContainerSize() {
		return items.size();
	}
	
	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.items) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public ItemStack getItem(int slot) {
		return slot >= getContainerSize() ? ItemStack.EMPTY : items.get(slot);
	}
	
	@Override
	public ItemStack removeItemNoUpdate(int slot) {
		return ItemStackHelper.takeItem(items, slot);
	}
	
	@Override
	public ItemStack removeItem(int slot, int amount) {
		ItemStack itemstack = ItemStackHelper.removeItem(items, slot, amount);
		if (!itemstack.isEmpty()) {
			menu.slotsChanged(this);
		}
		
		return itemstack;
	}
	
	@Override
	public void setItem(int slot, ItemStack stack) {
		items.set(slot, stack);
		menu.slotsChanged(this);
	}
	
	@Override
	public void setChanged() {
		Item item0 = getItem(0).getItem(), item1 = getItem(1).getItem(), item2 = getItem(2).getItem(), item3 = getItem(3).getItem();
		if ((item0 != Items.AIR && !(item0 instanceof StagedItem)) || (item1 != Items.AIR && !(item1 instanceof StagedItem)) ||
				(item2 != Items.AIR && !(item2 instanceof StagedItem)) || (item3 != Items.AIR && !(item3 instanceof StagedItem))) {
			return;
		}
		
		List<Ingredient> ings = Arrays.asList(item0 == Items.AIR ? Ingredient.EMPTY : Ingredient.createUsingTags((StagedItem) item0),
				item1 == Items.AIR ? Ingredient.EMPTY : Ingredient.createUsingTags((StagedItem) item1), Ingredient.EMPTY,
				item2 == Items.AIR ? Ingredient.EMPTY : Ingredient.createUsingTags((StagedItem) item2),
				item3 == Items.AIR ? Ingredient.EMPTY : Ingredient.createUsingTags((StagedItem) item3), Ingredient.EMPTY, Ingredient.EMPTY, Ingredient.EMPTY, Ingredient.EMPTY);
		
		recipe = PRRecipes.getRecipeForInput(RICrafting3.fromInventory(ings));
		ItemStack stack = recipe == null ? ItemStack.EMPTY : recipe.getOutput().copy();
		result.setItem(0, stack);
	}
	
	@Override
	public boolean stillValid(PlayerEntity player) {
		return true;
	}
	
	@Override
	public void clearContent() {
		items.clear();
	}
	
	public IStagedRecipe<?, ?> getRecipe() {
		return recipe;
	}
}
