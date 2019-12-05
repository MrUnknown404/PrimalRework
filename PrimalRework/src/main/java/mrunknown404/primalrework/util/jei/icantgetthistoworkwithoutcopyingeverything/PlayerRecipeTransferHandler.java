package mrunknown404.primalrework.util.jei.icantgetthistoworkwithoutcopyingeverything;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableSet;

import mezz.jei.api.gui.IGuiIngredient;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import mrunknown404.primalrework.Main;
import mrunknown404.primalrework.init.ModRecipes;
import mrunknown404.primalrework.network.RecipeTransferMessage;
import mrunknown404.primalrework.util.jei.JEICompat;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class PlayerRecipeTransferHandler implements IRecipeTransferHandler<ContainerPlayer> {
	private final StackHelper stackHelper;
	private final IRecipeTransferHandlerHelper handlerHelper;
	private final IRecipeTransferInfo<ContainerPlayer> transferHelper;
	
	public PlayerRecipeTransferHandler(IRecipeTransferHandlerHelper handlerHelper) {
		this.stackHelper = JEICompat.stackHelper;
		this.handlerHelper = handlerHelper;
		this.transferHelper = new BasicRecipeTransferInfo<>(ContainerPlayer.class, ModRecipes.CATEGORY_STAGED_CRAFTING, 1, 4, 9, 36);
	}
	
	@Override
	public Class<ContainerPlayer> getContainerClass() {
		return transferHelper.getContainerClass();
	}
	
	@Nullable
	@Override
	public IRecipeTransferError transferRecipe(ContainerPlayer container, IRecipeLayout recipeLayout, EntityPlayer player, boolean maxTransfer, boolean doTransfer) {
		if (!transferHelper.canHandle(container)) {
			return handlerHelper.createInternalError();
		}
		
		Map<Integer, Slot> inventorySlots = new HashMap<>();
		for (Slot slot : transferHelper.getInventorySlots(container)) {
			inventorySlots.put(slot.slotNumber, slot);
		}
		
		Map<Integer, Slot> craftingSlots = new HashMap<>();
		for (Slot slot : transferHelper.getRecipeSlots(container)) {
			craftingSlots.put(slot.slotNumber, slot);
		}
		
		IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
		int inputCount = 0;
		
		Set<Integer> badIndexes = ImmutableSet.of(2, 5, 6, 7, 8);
		
		int inputIndex = 0;
		for (IGuiIngredient<ItemStack> ingredient : itemStackGroup.getGuiIngredients().values()) {
			if (ingredient.isInput()) {
				if (!ingredient.getAllIngredients().isEmpty()) {
					inputCount++;
					if (badIndexes.contains(inputIndex)) {
						return handlerHelper.createUserErrorWithTooltip(I18n.format("jei.tooltip.error.recipe.transfer.too.large.player.inventory"));
					}
				}
				
				inputIndex++;
			}
		}
		
		List<IGuiIngredient<ItemStack>> guiIngredients = new ArrayList<>();
		for (IGuiIngredient<ItemStack> guiIngredient : itemStackGroup.getGuiIngredients().values()) {
			if (guiIngredient.isInput()) {
				guiIngredients.add(guiIngredient);
			}
		}
		
		IGuiItemStackGroup playerInvItemStackGroup = new GuiItemStackGroup(null, 0);
		int[] playerGridIndexes = { 0, 1, 3, 4 };
		for (int i = 0; i < 4; i++) {
			int index = playerGridIndexes[i];
			
			if (index < guiIngredients.size()) {
				IGuiIngredient<ItemStack> ingredient = guiIngredients.get(index);
				playerInvItemStackGroup.init(i, true, 0, 0);
				playerInvItemStackGroup.set(i, ingredient.getAllIngredients());
			}
		}
		
		Map<Integer, ItemStack> availableItemStacks = new HashMap<>();
		int filledCraftSlotCount = 0;
		int emptySlotCount = 0;
		
		for (Slot slot : craftingSlots.values()) {
			final ItemStack stack = slot.getStack();
			
			if (!stack.isEmpty()) {
				if (!slot.canTakeStack(player)) {
					return handlerHelper.createInternalError();
				}
				
				filledCraftSlotCount++;
				availableItemStacks.put(slot.slotNumber, stack.copy());
			}
		}
		
		for (Slot slot : inventorySlots.values()) {
			final ItemStack stack = slot.getStack();
			
			if (!stack.isEmpty()) {
				availableItemStacks.put(slot.slotNumber, stack.copy());
			} else {
				emptySlotCount++;
			}
		}
		
		if (filledCraftSlotCount - inputCount > emptySlotCount) {
			return handlerHelper.createUserErrorWithTooltip(I18n.format("jei.tooltip.error.recipe.transfer.inventory.full"));
		}
		
		StackHelper.MatchingItemsResult matchingItemsResult = stackHelper.getMatchingItems(availableItemStacks, playerInvItemStackGroup.getGuiIngredients());
		
		if (matchingItemsResult.missingItems.size() > 0) {
			matchingItemsResult = stackHelper.getMatchingItems(availableItemStacks, itemStackGroup.getGuiIngredients());
			return handlerHelper.createUserErrorForSlots(I18n.format("jei.tooltip.error.recipe.transfer.missing"), matchingItemsResult.missingItems);
		}
		
		List<Integer> craftingSlotIndexes = new ArrayList<>(craftingSlots.keySet());
		Collections.sort(craftingSlotIndexes);
		
		List<Integer> inventorySlotIndexes = new ArrayList<>(inventorySlots.keySet());
		Collections.sort(inventorySlotIndexes);
		
		for (Map.Entry<Integer, Integer> entry : matchingItemsResult.matchingItems.entrySet()) {
			int craftNumber = entry.getKey();
			int slotNumber = craftingSlotIndexes.get(craftNumber);
			
			if (slotNumber < 0 || slotNumber >= container.inventorySlots.size()) {
				return handlerHelper.createInternalError();
			}
		}
		
		if (doTransfer) {
			Main.networkWrapper.sendToServer(new RecipeTransferMessage(player, matchingItemsResult.matchingItems, craftingSlotIndexes, inventorySlotIndexes, maxTransfer, false));
		}
		
		return null;
	}
}
