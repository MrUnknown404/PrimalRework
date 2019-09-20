package mrunknown404.primalrework.recipes.util;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public interface IStagedFactoryBase {
	public default String[] shrink(String... strs) {
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
	
	public default String[] patternFromJson(JsonArray p_192407_0_) {
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
	
	public default NonNullList<Ingredient> deserializeIngredients(String[] p_192402_0_, Map<String, Ingredient> p_192402_1_, int p_192402_2_, int p_192402_3_) {
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
	
	public default Map<String, Ingredient> deserializeKey(JsonObject json) {
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
	
	public default Ingredient deserializeIngredient(@Nullable JsonElement json) {
		if (json != null && !json.isJsonNull()) {
			if (json.isJsonObject()) {
				return Ingredient.fromStacks(deserializeItem(json.getAsJsonObject(), false));
			} else if (!json.isJsonArray()) {
				throw new JsonSyntaxException("Expected item to be object or array of objects");
			} else {
				JsonArray jsonarray = json.getAsJsonArray();
				
				if (jsonarray.size() == 0) {
					throw new JsonSyntaxException("Item array cannot be empty, at least one item must be defined");
				} else {
					ItemStack[] aitemstack = new ItemStack[jsonarray.size()];
					
					for (int i = 0; i < jsonarray.size(); ++i) {
						aitemstack[i] = deserializeItem(JsonUtils.getJsonObject(jsonarray.get(i), "item"), false);
					}
					
					return Ingredient.fromStacks(aitemstack);
				}
			}
		} else {
			throw new JsonSyntaxException("Item cannot be null");
		}
	}
	
	public default NonNullList<Ingredient> deserializeIngredients(JsonArray array) {
		NonNullList<Ingredient> nonnulllist = NonNullList.<Ingredient>create();
		
		for (int i = 0; i < array.size(); ++i) {
			Ingredient ingredient = deserializeIngredient(array.get(i));
			
			if (ingredient != Ingredient.EMPTY) {
				nonnulllist.add(ingredient);
			}
		}
		
		return nonnulllist;
	}
	
	public default ItemStack deserializeItem(JsonObject obj, boolean useCount) {
		String s = JsonUtils.getString(obj, "item");
		Item item = Item.REGISTRY.getObject(new ResourceLocation(s));
		
		if (item == null) {
			throw new JsonSyntaxException("Unknown item '" + s + "'");
		} else if (item.getHasSubtypes() && !obj.has("data")) {
			throw new JsonParseException("Missing data for item '" + s + "'");
		} else {
			int i = JsonUtils.getInt(obj, "data", 0);
			int j = useCount ? JsonUtils.getInt(obj, "count", 1) : 1;
			return new ItemStack(item, j, i);
		}
	}
	
	public default int firstNonSpace(String str) {
		int i;
		
		for (i = 0; i < str.length() && str.charAt(i) == ' '; ++i) {
			;
		}
		
		return i;
	}
	
	public default int lastNonSpace(String str) {
		int i;
		
		for (i = str.length() - 1; i >= 0 && str.charAt(i) == ' '; --i) {
			;
		}
		
		return i;
	}
}
