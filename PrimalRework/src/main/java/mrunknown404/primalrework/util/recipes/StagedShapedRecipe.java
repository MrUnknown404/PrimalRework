package mrunknown404.primalrework.util.recipes;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import mrunknown404.primalrework.handlers.StageHandler;
import mrunknown404.primalrework.handlers.StageHandler.Stage;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;

public class StagedShapedRecipe extends ShapedRecipes {

	protected final Stage stage;
	
	public StagedShapedRecipe(Stage stage, int width, int height, NonNullList<Ingredient> ingredients, ItemStack result) {
		super("gr", width, height, ingredients, result);
		this.stage = stage;
	}
	
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		return StageHandler.hasAccessToStage(stage) && super.matches(inv, worldIn);
	}
	
	private static String[] shrink(String... strs) {
		int i = Integer.MAX_VALUE;
		int j = 0;
		int k = 0;
		int l = 0;
		
		for (int i1 = 0; i1 < strs.length; ++i1) {
			String s = strs[i1];
			i = Math.min(i, firstNonSpace(s));
			int j1 = lastNonSpace(s);
			j = Math.max(j, j1);
			
			if (j1 < 0) {
				if (k == i1) {
					++k;
				}
				
				++l;
			} else {
				l = 0;
			}
		}
		
		if (strs.length == l) {
			return new String[0];
		} else {
			String[] astring = new String[strs.length - l - k];
			
			for (int k1 = 0; k1 < astring.length; ++k1) {
				astring[k1] = strs[k1 + k].substring(i, j + 1);
			}
			
			return astring;
		}
	}
	
	private static String[] patternFromJson(JsonArray p_192407_0_) {
		String[] astring = new String[p_192407_0_.size()];
		
		if (astring.length > 3) {
			throw new JsonSyntaxException("Invalid pattern: too many rows, 3 is maximum");
		} else if (astring.length == 0) {
			throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
		} else {
			for (int i = 0; i < astring.length; ++i) {
				String s = JsonUtils.getString(p_192407_0_.get(i), "pattern[" + i + "]");
				
				if (s.length() > 3) {
					throw new JsonSyntaxException("Invalid pattern: too many columns, 3 is maximum");
				}
				
				if (i > 0 && astring[0].length() != s.length()) {
					throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
				}
				
				astring[i] = s;
			}
			
			return astring;
		}
	}
	
	private static NonNullList<Ingredient> deserializeIngredients(String[] p_192402_0_, Map<String, Ingredient> p_192402_1_, int p_192402_2_, int p_192402_3_) {
		NonNullList<Ingredient> nonnulllist = NonNullList.<Ingredient>withSize(p_192402_2_ * p_192402_3_, Ingredient.EMPTY);
		Set<String> set = Sets.newHashSet(p_192402_1_.keySet());
		set.remove(" ");
		
		for (int i = 0; i < p_192402_0_.length; ++i) {
			for (int j = 0; j < p_192402_0_[i].length(); ++j) {
				String s = p_192402_0_[i].substring(j, j + 1);
				Ingredient ingredient = p_192402_1_.get(s);
				
				if (ingredient == null) {
					throw new JsonSyntaxException("Pattern references symbol '" + s + "' but it's not defined in the key");
				}
				
				set.remove(s);
				nonnulllist.set(j + p_192402_2_ * i, ingredient);
			}
		}
		
		if (!set.isEmpty()) {
			throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
		} else {
			return nonnulllist;
		}
	}
	
	private static Map<String, Ingredient> deserializeKey(JsonObject json) {
		Map<String, Ingredient> map = Maps.<String, Ingredient>newHashMap();
		
		for (Entry<String, JsonElement> entry : json.entrySet()) {
			if (((String)entry.getKey()).length() != 1) {
				throw new JsonSyntaxException("Invalid key entry: '" + (String)entry.getKey() + "' is an invalid symbol (must be 1 character only).");
			}
			
			if (" ".equals(entry.getKey())) {
				throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
			}
			
			map.put(entry.getKey(), deserializeIngredient(entry.getValue()));
		}
		
		map.put(" ", Ingredient.EMPTY);
		return map;
	}
	
	private static int firstNonSpace(String str) {
		int i;
		
		for (i = 0; i < str.length() && str.charAt(i) == ' '; ++i) {
			;
		}
		
		return i;
	}
	
	private static int lastNonSpace(String str) {
		int i;
		
		for (i = str.length() - 1; i >= 0 && str.charAt(i) == ' '; --i) {
			;
		}
		
		return i;
	}
	
	public static class Factory implements IRecipeFactory {
		@Override
		public IRecipe parse(final JsonContext context, final JsonObject json) {
			String[] astring = shrink(patternFromJson(JsonUtils.getJsonArray(json, "pattern")));
			int width = astring[0].length();
			int height = astring.length;
			
			NonNullList<Ingredient> nonnulllist = deserializeIngredients(astring, deserializeKey(JsonUtils.getJsonObject(json, "key")), width, height);
			ItemStack itemstack = deserializeItem(JsonUtils.getJsonObject(json, "result"), true);
			
			return new StagedShapedRecipe(Stage.valueOf(JsonUtils.getString(json, "stage")), width, height, nonnulllist, itemstack);
		}
	}
}
