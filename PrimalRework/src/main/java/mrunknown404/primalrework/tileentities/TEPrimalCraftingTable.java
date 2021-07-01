package mrunknown404.primalrework.tileentities;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.init.InitRecipes;
import mrunknown404.primalrework.init.InitStagedTags;
import mrunknown404.primalrework.init.InitTileEntities;
import mrunknown404.primalrework.inventory.container.ContainerPrimalCraftingTable;
import mrunknown404.primalrework.recipes.Ingredient;
import mrunknown404.primalrework.recipes.SRCrafting3;
import mrunknown404.primalrework.recipes.input.RICrafting3;
import mrunknown404.primalrework.utils.enums.EnumRecipeType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TEPrimalCraftingTable extends TEInventory {
	
	public TEPrimalCraftingTable() {
		super(InitTileEntities.PRIMAL_CRAFTING_TABLE.get(), 10);
	}
	
	@Override
	public void onItemChange(int slot, PlayerEntity player, int windowID) {
		updateRecipes(player, windowID);
	}
	
	private void updateRecipes(PlayerEntity player, int windowID) {
		ItemStack lastStack = getItem(9);
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		for (int i = 0; i < 9; i++) {
			Item item = getItem(i).getItem();
			ingredients.add(new Ingredient(item, InitStagedTags.getItemsTags(item)));
		}
		
		SRCrafting3 rec = (SRCrafting3) InitRecipes.getRecipeForInput(EnumRecipeType.crafting_3, RICrafting3.create().set(ingredients).finish());
		ItemStack stack = rec == null ? ItemStack.EMPTY : rec.output.copy();
		
		if (!lastStack.equals(stack, false)) {
			setItem(9, stack);
			((ServerPlayerEntity) player).connection.send(new SSetSlotPacket(windowID, 9, stack));
		}
	}
	
	@Override
	public Container createMenu(int windowID, PlayerInventory inv, PlayerEntity player) {
		updateRecipes(player, windowID);
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
		return new TranslationTextComponent("container.primal_crafting_table");
	}
}
