package mrunknown404.primalrework.tileentities;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.init.InitRecipes;
import mrunknown404.primalrework.init.InitTileEntities;
import mrunknown404.primalrework.inventory.container.ContainerPrimalCraftingTable;
import mrunknown404.primalrework.items.StagedItem;
import mrunknown404.primalrework.network.packets.toclient.PSyncPrimalCraftingTableOutput;
import mrunknown404.primalrework.recipes.Ingredient;
import mrunknown404.primalrework.recipes.StagedRecipe;
import mrunknown404.primalrework.recipes.inputs.RICrafting3;
import mrunknown404.primalrework.utils.enums.RecipeType;
import mrunknown404.primalrework.utils.helpers.WordH;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;

public class TEICraftingTable extends TEInventory {
	public TEICraftingTable() {
		super(InitTileEntities.PRIMAL_CRAFTING_TABLE.get(), 10);
	}
	
	@Override
	public void setChanged() {
		super.setChanged();
		
		ItemStack lastStack = getItem(9);
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		for (int i = 0; i < 9; i++) {
			Item item = getItem(i).getItem();
			ingredients.add(item instanceof StagedItem ? Ingredient.createUsingTags((StagedItem) item) : Ingredient.EMPTY);
		}
		
		StagedRecipe<?, ?> rec = InitRecipes.getRecipeForInput(RecipeType.CRAFTING_3, RICrafting3.fromInventory(ingredients));
		ItemStack stack = rec == null ? ItemStack.EMPTY : rec.getOutputCopy();
		
		if (!lastStack.equals(stack, false)) {
			setItem(9, stack);
			PrimalRework.NETWORK.sendPacketToAll(new PSyncPrimalCraftingTableOutput(stack));
		}
	}
	
	@Override
	public Container createMenu(int windowID, PlayerInventory inv, PlayerEntity player) {
		setChanged();
		return new ContainerPrimalCraftingTable(windowID, inv, this);
	}
	
	@Override
	protected CompoundNBT saveNBT(CompoundNBT nbt) {
		return nbt;
	}
	
	@Override
	protected void loadNBT(BlockState state, CompoundNBT nbt) {
	}
	
	@Override
	protected ITextComponent getDefaultName() {
		return WordH.translate("container.primal_crafting_table");
	}
}
