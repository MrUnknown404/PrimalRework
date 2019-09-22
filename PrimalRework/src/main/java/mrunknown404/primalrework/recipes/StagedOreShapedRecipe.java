package mrunknown404.primalrework.recipes;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

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
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.CraftingHelper.ShapedPrimer;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class StagedOreShapedRecipe extends ShapedOreRecipe implements IStagedRecipeBase {
	
	protected final EnumStage stage;
	protected final ItemStack output;
	
	public StagedOreShapedRecipe(EnumStage stage, ItemStack result, ShapedPrimer primer) {
		super(new ResourceLocation("ore_shaped"), result, primer);
		this.stage = stage;
		this.output = result;
		
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
		return false;
	}
	
	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		return StageHelper.hasAccessToStage(stage) && match(inv, world);
	}
	
	@Override
	public boolean match(InventoryCrafting inv, World world) {
		for (int x = 0; x <= inv.getWidth() - width; x++) {
			for (int y = 0; y <= inv.getHeight() - height; ++y) {
				if (checkMatch(inv, x, y, false)) {
					return true;
				}
				
				if (mirrored && checkMatch(inv, x, y, true)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	@Override
	protected boolean checkMatch(InventoryCrafting inv, int startX, int startY, boolean mirror) {
		for (int x = 0; x < inv.getWidth(); x++) {
			for (int y = 0; y < inv.getHeight(); y++) {
				int subX = x - startX;
				int subY = y - startY;
				Ingredient target = Ingredient.EMPTY;
				
				if (subX >= 0 && subY >= 0 && subX < width && subY < height) {
					if (mirror) {
						target = input.get(width - subX - 1 + subY * width);
					} else {
						target = input.get(subX + subY * width);
					}
				}
				
				if (!target.apply(inv.getStackInRowAndColumn(x, y).copy())) {
					return false;
				}
			}
		}
		
		return true;
	}
	
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
		public IRecipe parse(JsonContext context, JsonObject json) {
			Map<Character, Ingredient> ingMap = Maps.newHashMap();
			for (Entry<String, JsonElement> entry : JsonUtils.getJsonObject(json, "key").entrySet()) {
				if (entry.getKey().length() != 1) {
					throw new JsonSyntaxException("Invalid key entry: '" + entry.getKey() + "' is an invalid symbol (must be 1 character only).");
				}
				if (" ".equals(entry.getKey())) {
					throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
				}
				
				ingMap.put(entry.getKey().toCharArray()[0], CraftingHelper.getIngredient(entry.getValue(), context));
			}
			
			ingMap.put(' ', Ingredient.EMPTY);
			JsonArray patternJ = JsonUtils.getJsonArray(json, "pattern");
			
			if (patternJ.size() == 0) {
				throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
			}
			
			String[] pattern = new String[patternJ.size()];
			for (int x = 0; x < pattern.length; ++x) {
				String line = JsonUtils.getString(patternJ.get(x), "pattern[" + x + "]");
				if (x > 0 && pattern[0].length() != line.length())
					throw new JsonSyntaxException("Invalid pattern: each row must  be the same width");
				pattern[x] = line;
			}
			
			ShapedPrimer primer = new ShapedPrimer();
			primer.width = pattern[0].length();
			primer.height = pattern.length;
			primer.mirrored = JsonUtils.getBoolean(json, "mirrored", true);
			primer.input = NonNullList.withSize(primer.width * primer.height, Ingredient.EMPTY);
			
			Set<Character> keys = Sets.newHashSet(ingMap.keySet());
			keys.remove(' ');
			
			int x = 0;
			for (String line : pattern) {
				for (char chr : line.toCharArray()) {
					Ingredient ing = ingMap.get(chr);
					if (ing == null) {
						throw new JsonSyntaxException("Pattern references symbol '" + chr + "' but it's not defined in the key");
					}
					
					primer.input.set(x++, ing);
					keys.remove(chr);
				}
			}
			
			if (!keys.isEmpty()) {
				throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + keys);
			}
			
			ItemStack result = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context);
			return new StagedOreShapedRecipe(EnumStage.valueOf(JsonUtils.getString(json, "stage")), result, primer);
		}
	}
}
