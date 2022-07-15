package mrunknown404.primalrework.utils.enums;

import mrunknown404.primalrework.api.utils.ISIProvider;
import mrunknown404.primalrework.init.InitBlocks;
import mrunknown404.primalrework.recipes.SRCampFire;
import mrunknown404.primalrework.recipes.SRCrafting3;
import mrunknown404.primalrework.recipes.StagedRecipe;
import mrunknown404.primalrework.utils.helpers.WordH;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;

public enum RecipeType implements ICraftingInput {
	CRAFTING_3(SRCrafting3.class, InitBlocks.CRAFTING_TABLE),
	CAMPFIRE(SRCampFire.class, InitBlocks.CAMPFIRE);
	
	public final Class<? extends StagedRecipe<?, ?>> supportsClass;
	private final ItemStack icon;
	private final IFormattableTextComponent name;
	
	private RecipeType(Class<? extends StagedRecipe<?, ?>> supportsClass, ISIProvider icon) {
		this.supportsClass = supportsClass;
		this.icon = new ItemStack(icon.getStagedItem());
		this.name = WordH.translate("recipetype." + toString() + ".name");
	}
	
	@Override
	public IFormattableTextComponent getFancyName() {
		return name;
	}
	
	@Override
	public String getName() {
		return name.getString();
	}
	
	@Override
	public ItemStack getIcon() {
		return icon;
	}
	
	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
}
