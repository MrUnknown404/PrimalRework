package mrunknown404.primalrework.recipes.input;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mrunknown404.primalrework.recipes.Ingredient;
import mrunknown404.primalrework.stage.StagedTag;
import net.minecraft.item.Item;

public class RICrafting3 extends RecipeInput<RICrafting3> {
	private final List<Ingredient> ingredients;
	private final boolean isShapeless, isEmpty;
	public final int width, height;
	
	private RICrafting3(List<Ingredient> ingredients, boolean isShapeless) {
		this.ingredients = ingredients;
		this.isShapeless = isShapeless;
		
		if (!isShapeless) {
			int width = 0, empty = 0;
			for (int x = 0; x < 3; x++) {
				boolean found = false;
				for (int y = 0; y < 3; y++) {
					Ingredient i = ingredients.get(x + y * 3);
					if (!i.isEmpty()) {
						width += 1 + empty;
						empty = 0;
						found = true;
						break;
					}
				}
				
				if (!found && width > 0) {
					empty++;
				}
			}
			
			int height = 0;
			empty = 0;
			for (int y = 0; y < 3; y++) {
				boolean found = false;
				for (int x = 0; x < 3; x++) {
					Ingredient i = ingredients.get(x + y * 3);
					if (!i.isEmpty()) {
						height += 1 + empty;
						empty = 0;
						found = true;
						break;
					}
				}
				
				if (!found && height > 0) {
					empty++;
				}
			}
			
			this.width = width;
			this.height = height;
		} else {
			this.width = 3;
			this.height = 3;
		}
		
		boolean found = false;
		for (Ingredient i : ingredients) {
			if (!i.isEmpty()) {
				found = true;
				break;
			}
		}
		
		isEmpty = !found;
	}
	
	@Override
	protected boolean match(RICrafting3 input) {
		if (isShapeless) {
			int matches = 0;
			
			List<Ingredient> inputIngs = new ArrayList<Ingredient>(input.ingredients);
			List<Ingredient> recipeIngs = new ArrayList<Ingredient>(ingredients);
			
			Collections.sort(inputIngs, new IngredientCompare());
			Collections.sort(recipeIngs, new IngredientCompare());
			
			for (int i = 0; i < 9; i++) {
				if (inputIngs.get(i).matches(recipeIngs.get(i))) {
					matches++;
				}
			}
			
			return matches == 9;
		}
		
		if (width != input.width || height != input.height) {
			return false;
		}
		
		for (int i = 0; i <= 3 - width; ++i) {
			for (int j = 0; j <= 3 - height; ++j) {
				if (matches(input, i, j, true) || matches(input, i, j, false)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean matches(RICrafting3 recipe, int x, int y, boolean flag) {
		for (int i = 0; i < width; ++i) {
			for (int j = 0; j < height; ++j) {
				int k = x + i, l = j + y;
				
				if (!ingredients.get(i + j * 3).matches(recipe.ingredients.get(flag ? k + l * 3 : 3 - k - 1 + l * 3))) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public Ingredient get(int x, int y) {
		return ingredients.get(x + y * 3);
	}
	
	public boolean has(Ingredient input) {
		for (Ingredient ing : ingredients) {
			if (ing.matches(input)) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean isEmpty() {
		return isEmpty;
	}
	
	public static Builder create(boolean isShapeless) {
		return new Builder(isShapeless);
	}
	
	public static Builder create() {
		return new Builder(false);
	}
	
	public static class Builder {
		private final List<Ingredient> ingredients = new ArrayList<Ingredient>(9);
		private final boolean isShapeless;
		
		private Builder(boolean isShapeless) {
			this.isShapeless = isShapeless;
			for (int i = 0; i < 9; i++) {
				ingredients.add(Ingredient.EMPTY);
			}
		}
		
		public RICrafting3 finish() {
			return new RICrafting3(ingredients, isShapeless);
		}
		
		public Builder set(List<Ingredient> i) {
			if (i.size() > 9) {
				System.out.println("Tried to feed in too much ingredients");
				return this;
			} else if (i.size() < 9) {
				System.out.println("Tried to feed in too little ingredients");
				return this;
			}
			
			ingredients.clear();
			ingredients.addAll(i);
			return this;
		}
		
		private Builder set(Ingredient... i) {
			if (!isShapeless) {
				System.out.println("Tried to use a shapeless method in a non shapeless recipe!");
				return this;
			} else if (i.length > 9) {
				System.out.println("Tried to feed in too much ingredients");
				return this;
			}
			
			for (int j = 0; j < i.length; j++) {
				ingredients.set(j, i[j]);
			}
			
			return this;
		}
		
		public Builder set(Item... i) {
			Ingredient[] items = new Ingredient[i.length];
			for (int j = 0; j < i.length; j++) {
				items[j] = new Ingredient(i[j]);
			}
			return set(items);
		}
		
		public Builder set(StagedTag... i) {
			Ingredient[] items = new Ingredient[i.length];
			for (int j = 0; j < i.length; j++) {
				items[j] = new Ingredient(i[j]);
			}
			return set(items);
		}
		
		private Builder setRing(Ingredient i) {
			ingredients.set(0, i);
			ingredients.set(1, i);
			ingredients.set(2, i);
			ingredients.set(3, i);
			ingredients.set(5, i);
			ingredients.set(6, i);
			ingredients.set(7, i);
			ingredients.set(8, i);
			return this;
		}
		
		public Builder setRing(Item i) {
			return setRing(new Ingredient(i));
		}
		
		public Builder setRing(StagedTag i) {
			return setRing(new Ingredient(i));
		}
		
		private Builder set2x2(Ingredient i0, Ingredient i1, Ingredient i2, Ingredient i3) {
			ingredients.set(0, i0);
			ingredients.set(1, i1);
			ingredients.set(3, i2);
			ingredients.set(4, i3);
			return this;
		}
		
		public Builder set2x2(Item i0, Item i1, Item i2, Item i3) {
			return set2x2(new Ingredient(i0), new Ingredient(i1), new Ingredient(i2), new Ingredient(i3));
		}
		
		public Builder set2x2(StagedTag i0, StagedTag i1, StagedTag i2, StagedTag i3) {
			return set2x2(new Ingredient(i0), new Ingredient(i1), new Ingredient(i2), new Ingredient(i3));
		}
		
		public Builder set2x2(Ingredient i) {
			return set2x2(i, i, i, i);
		}
		
		public Builder set2x2(Item i) {
			Ingredient ig = new Ingredient(i);
			return set2x2(ig, ig, ig, ig);
		}
		
		public Builder set2x2(StagedTag i) {
			Ingredient ig = new Ingredient(i);
			return set2x2(ig, ig, ig, ig);
		}
		
		private Builder set1x2(Ingredient i0, Ingredient i1) {
			ingredients.set(0, i0);
			ingredients.set(3, i1);
			return this;
		}
		
		public Builder set1x2(Item i0, Item i1) {
			return set1x2(new Ingredient(i0), new Ingredient(i1));
		}
		
		public Builder set1x2(StagedTag i0, StagedTag i1) {
			return set1x2(new Ingredient(i0), new Ingredient(i1));
		}
		
		public Builder set1x2(Ingredient i) {
			return set1x2(i, i);
		}
		
		public Builder set1x2(Item i) {
			Ingredient ig = new Ingredient(i);
			return set1x2(ig, ig);
		}
		
		public Builder set1x2(StagedTag i) {
			Ingredient ig = new Ingredient(i);
			return set1x2(ig, ig);
		}
	}
	
	private static class IngredientCompare implements Comparator<Ingredient> {
		@Override
		public int compare(Ingredient o1, Ingredient o2) {
			return o1.isEmpty() ? Integer.MIN_VALUE : o1.toString().compareTo(o2.toString());
		}
	}
}
