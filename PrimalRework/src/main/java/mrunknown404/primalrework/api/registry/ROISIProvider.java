package mrunknown404.primalrework.api.registry;

import java.util.function.Supplier;

import mrunknown404.primalrework.api.utils.ISIProvider;
import mrunknown404.primalrework.items.StagedItem;
import mrunknown404.primalrework.recipes.IIngredientProvider;
import mrunknown404.primalrework.recipes.Ingredient;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * aka, {@code Registry Object Interface Staged Item Provider} <br>
 * Basically just a wrapper
 */
public class ROISIProvider<T extends IForgeRegistryEntry<? super T> & ISIProvider> implements ISIProvider, IIngredientProvider, Supplier<T> {
	private final RegistryObject<T> ro;
	
	private ROISIProvider(RegistryObject<T> ro) {
		this.ro = ro;
	}
	
	public static <T extends IForgeRegistryEntry<? super T> & ISIProvider> ROISIProvider<T> of(RegistryObject<T> ro) {
		return new ROISIProvider<T>(ro);
	}
	
	@Override
	public StagedItem getStagedItem() {
		return ro.get().getStagedItem();
	}
	
	@Override
	public Ingredient getIngredient() {
		return Ingredient.createUsingItem(getStagedItem());
	}
	
	@Override
	public T get() {
		return ro.get();
	}
}
