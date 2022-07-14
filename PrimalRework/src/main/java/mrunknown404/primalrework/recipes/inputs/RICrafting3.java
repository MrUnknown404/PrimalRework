package mrunknown404.primalrework.recipes.inputs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mrunknown404.primalrework.recipes.IIngredientProvider;
import mrunknown404.primalrework.recipes.Ingredient;
import mrunknown404.primalrework.utils.Logger;

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
			
			Collections.sort(inputIngs, COMPARE);
			Collections.sort(recipeIngs, COMPARE);
			
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
		return ingredients.stream().anyMatch((i) -> i.matches(input));
	}
	
	@Override
	public boolean isEmpty() {
		return isEmpty;
	}
	
	public static SBuilder shapeless() {
		return new SBuilder();
	}
	
	public static Builder shaped() {
		return new Builder();
	}
	
	public static RICrafting3 fromInventory(List<Ingredient> i) {
		if (i.size() > 9) {
			Logger.warn("Tried to feed in too much ingredients");
			return null;
		} else if (i.size() < 9) {
			Logger.warn("Tried to feed in too little ingredients");
			return null;
		}
		
		return new RICrafting3(i, false);
	}
	
	@Override
	public String toString() {
		return ingredients.toString();
	}
	
	public static final class Builder {
		private final List<Ingredient> ingredients = new ArrayList<Ingredient>(9);
		
		private Builder() {
			for (int i = 0; i < 9; i++) {
				ingredients.add(Ingredient.EMPTY);
			}
		}
		
		public RICrafting3 setRing(IIngredientProvider i) {
			Ingredient ig = create(i);
			ingredients.set(0, ig);
			ingredients.set(1, ig);
			ingredients.set(2, ig);
			ingredients.set(3, ig);
			ingredients.set(5, ig);
			ingredients.set(6, ig);
			ingredients.set(7, ig);
			ingredients.set(8, ig);
			return new RICrafting3(ingredients, false);
		}
		
		public RICrafting3 set2x2(IIngredientProvider i0, IIngredientProvider i1, IIngredientProvider i2, IIngredientProvider i3) {
			ingredients.set(0, create(i0));
			ingredients.set(1, create(i1));
			ingredients.set(3, create(i2));
			ingredients.set(4, create(i3));
			return new RICrafting3(ingredients, false);
		}
		
		public RICrafting3 set2x2(IIngredientProvider i) {
			Ingredient ig = create(i);
			return set2x2(ig, ig, ig, ig);
		}
		
		public RICrafting3 set1x2(IIngredientProvider i0, IIngredientProvider i1) {
			ingredients.set(0, create(i0));
			ingredients.set(3, create(i1));
			return new RICrafting3(ingredients, false);
		}
		
		public RICrafting3 set1x2(IIngredientProvider i) {
			Ingredient ig = create(i);
			return set1x2(ig, ig);
		}
		
		public RICrafting3 set3x3(IIngredientProvider i0, IIngredientProvider i1, IIngredientProvider i2, IIngredientProvider i3, IIngredientProvider i4, IIngredientProvider i5,
				IIngredientProvider i6, IIngredientProvider i7, IIngredientProvider i8) {
			ingredients.set(0, create(i0));
			ingredients.set(1, create(i1));
			ingredients.set(2, create(i2));
			ingredients.set(3, create(i3));
			ingredients.set(4, create(i4));
			ingredients.set(5, create(i5));
			ingredients.set(6, create(i6));
			ingredients.set(7, create(i7));
			ingredients.set(8, create(i8));
			return new RICrafting3(ingredients, false);
		}
		
		public RICrafting3 set3x3(IIngredientProvider i) {
			Ingredient ig = create(i);
			return set3x3(ig, ig, ig, ig, ig, ig, ig, ig, ig);
		}
		
		public RICrafting3 set2x3(IIngredientProvider i0, IIngredientProvider i1, IIngredientProvider i2, IIngredientProvider i3, IIngredientProvider i4, IIngredientProvider i5) {
			ingredients.set(0, create(i0));
			ingredients.set(1, create(i1));
			ingredients.set(3, create(i2));
			ingredients.set(4, create(i3));
			ingredients.set(6, create(i4));
			ingredients.set(7, create(i5));
			return new RICrafting3(ingredients, false);
		}
		
		public RICrafting3 set2x3(IIngredientProvider i) {
			Ingredient ig = create(i);
			return set2x3(ig, ig, ig, ig, ig, ig);
		}
		
		public RICrafting3 set1x3(IIngredientProvider i0, IIngredientProvider i1, IIngredientProvider i2) {
			ingredients.set(0, create(i0));
			ingredients.set(3, create(i1));
			ingredients.set(6, create(i2));
			return new RICrafting3(ingredients, false);
		}
		
		public RICrafting3 set1x3(IIngredientProvider i) {
			Ingredient ig = create(i);
			return set1x3(ig, ig, ig);
		}
		
		public RICrafting3 set3x2(IIngredientProvider i0, IIngredientProvider i1, IIngredientProvider i2, IIngredientProvider i3, IIngredientProvider i4, IIngredientProvider i5) {
			ingredients.set(0, create(i0));
			ingredients.set(1, create(i1));
			ingredients.set(2, create(i2));
			ingredients.set(3, create(i3));
			ingredients.set(4, create(i4));
			ingredients.set(5, create(i5));
			return new RICrafting3(ingredients, false);
		}
		
		public RICrafting3 set3x2(IIngredientProvider i) {
			Ingredient ig = create(i);
			return set3x2(ig, ig, ig, ig, ig, ig);
		}
		
		public RICrafting3 set3x1(IIngredientProvider i0, IIngredientProvider i1, IIngredientProvider i2) {
			ingredients.set(0, create(i0));
			ingredients.set(1, create(i1));
			ingredients.set(2, create(i2));
			return new RICrafting3(ingredients, false);
		}
		
		public RICrafting3 set3x1(IIngredientProvider i) {
			Ingredient ig = create(i);
			return set1x3(ig, ig, ig);
		}
	}
	
	public static final class SBuilder {
		private final List<Ingredient> ingredients = new ArrayList<Ingredient>(9);
		
		private SBuilder() {
			for (int i = 0; i < 9; i++) {
				ingredients.add(Ingredient.EMPTY);
			}
		}
		
		public RICrafting3 set(IIngredientProvider... i) {
			if (i.length > 9) {
				Logger.warn("Tried to feed in too much ingredients");
				return null;
			} else if (i.length == 0) {
				Logger.warn("Tried to feed in too little ingredients");
				return null;
			}
			
			for (int j = 0; j < i.length; j++) {
				ingredients.set(j, create(i[j]));
			}
			return new RICrafting3(ingredients, true);
		}
		
		public RICrafting3 set(IIngredientProvider i, int amount) {
			if (amount > 10) {
				Logger.warn("Tried to feed in too much ingredients");
				return null;
			} else if (amount <= 0) {
				Logger.warn("Tried to feed in too little ingredients");
				return null;
			}
			
			for (int j = 0; j < amount; j++) {
				ingredients.set(j, create(i));
			}
			return new RICrafting3(ingredients, true);
		}
	}
	
	private static Ingredient create(IIngredientProvider obj) {
		return obj == null ? Ingredient.EMPTY : obj.getIngredient();
	}
	
	private static final IngredientCompare COMPARE = new IngredientCompare();
	
	private static class IngredientCompare implements Comparator<Ingredient> {
		@Override
		public int compare(Ingredient o1, Ingredient o2) {
			return o1.isEmpty() ? Integer.MIN_VALUE : o1.toString().compareTo(o2.toString());
		}
	}
}
