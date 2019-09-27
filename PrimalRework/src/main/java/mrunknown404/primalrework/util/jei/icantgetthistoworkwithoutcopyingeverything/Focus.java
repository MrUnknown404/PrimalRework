package mrunknown404.primalrework.util.jei.icantgetthistoworkwithoutcopyingeverything;

import mezz.jei.api.recipe.IFocus;
import mrunknown404.primalrework.util.jei.JEICompat;

public class Focus<V> implements IFocus<V> {
	private final Mode mode;
	private final V value;
	
	Focus(Mode mode, V value) {
		this.mode = mode;
		this.value = JEICompat.ingReg.getIngredientHelper(value).copyIngredient(value);
	}
	
	@Override
	public V getValue() {
		return value;
	}
	
	@Override
	public Mode getMode() {
		return mode;
	}
	
	static <V> Focus<V> check(IFocus<V> focus) {
		if (focus instanceof Focus) {
			return (Focus<V>) focus;
		}
		
		return new Focus<>(focus.getMode(), focus.getValue());
	}
}
