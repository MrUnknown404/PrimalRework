package mrunknown404.primalrework.util.jei.icantgetthistoworkwithoutcopyingeverything;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.gui.IGuiIngredient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

public class StackHelper {
	private final ISubtypeRegistry subtypeRegistry;
	private final Map<UidMode, Map<ItemStack, String>> uidCache = new EnumMap<>(UidMode.class);
	private boolean uidCacheEnabled = true;
	
	public StackHelper(ISubtypeRegistry subtypeRegistry) {
		this.subtypeRegistry = subtypeRegistry;
		
		for (UidMode mode : UidMode.values()) {
			uidCache.put(mode, new IdentityHashMap<>());
		}
	}
	
	MatchingItemsResult getMatchingItems(Map<Integer, ItemStack> availableItemStacks, Map<Integer, ? extends IGuiIngredient<ItemStack>> ingredientsMap) {
		MatchingItemsResult matchingItemResult = new MatchingItemsResult();
		
		int recipeSlotNumber = -1;
		SortedSet<Integer> keys = new TreeSet<>(ingredientsMap.keySet());
		for (Integer key : keys) {
			IGuiIngredient<ItemStack> ingredient = ingredientsMap.get(key);
			if (!ingredient.isInput()) {
				continue;
			}
			
			recipeSlotNumber++;
			List<ItemStack> requiredStacks = ingredient.getAllIngredients();
			if (requiredStacks.isEmpty()) {
				continue;
			}
			
			Integer matching = containsAnyStackIndexed(availableItemStacks, requiredStacks);
			if (matching == null) {
				matchingItemResult.missingItems.add(key);
			} else {
				ItemStack matchingStack = availableItemStacks.get(matching);
				matchingStack.shrink(1);
				
				if (matchingStack.getCount() == 0) {
					availableItemStacks.remove(matching);
				}
				
				matchingItemResult.matchingItems.put(recipeSlotNumber, matching);
			}
		}
		
		return matchingItemResult;
	}
	
	Integer containsAnyStackIndexed(Map<Integer, ItemStack> stacks, Iterable<ItemStack> contains) {
		MatchingIndexed matchingStacks = new MatchingIndexed(stacks);
		MatchingIterable matchingContains = new MatchingIterable(contains);
		return containsStackMatchable(matchingStacks, matchingContains);
	}
	
	<R, T> R containsStackMatchable(Iterable<ItemStackMatchable<R>> stacks, Iterable<ItemStackMatchable<T>> contains) {
		for (ItemStackMatchable<?> containStack : contains) {
			R matchingStack = containsStack(stacks, containStack);
			if (matchingStack != null) {
				return matchingStack;
			}
		}
		
		return null;
	}
	
	<R> R containsStack(Iterable<ItemStackMatchable<R>> stacks, ItemStackMatchable<?> contains) {
		for (ItemStackMatchable<R> stack : stacks) {
			if (isEquivalent(contains.getStack(), stack.getStack())) {
				return stack.getResult();
			}
		}
		
		return null;
	}
	
	boolean isEquivalent(ItemStack lhs, ItemStack rhs) {
		if (lhs == rhs) {
			return true;
		}
		
		if (lhs == null || rhs == null) {
			return false;
		}
		
		if (lhs.getItem() != rhs.getItem()) {
			return false;
		}
		
		String keyLhs = getUniqueIdentifierForStack(lhs, UidMode.NORMAL);
		String keyRhs = getUniqueIdentifierForStack(rhs, UidMode.NORMAL);
		return keyLhs.equals(keyRhs);
	}
	
	String getUniqueIdentifierForStack(ItemStack stack, UidMode mode) {
		if (uidCacheEnabled) {
			String result = uidCache.get(mode).get(stack);
			
			if (result != null) {
				return result;
			}
		}
		
		Item item = stack.getItem();
		ResourceLocation itemName = item.getRegistryName();
		if (itemName == null) {
			throw new IllegalStateException("Item has no registry name: " + stack);
		}
		
		StringBuilder itemKey = new StringBuilder(itemName.toString());
		
		int metadata = stack.getMetadata();
		if (mode != UidMode.WILDCARD && metadata != OreDictionary.WILDCARD_VALUE) {
			String subtypeInfo = subtypeRegistry.getSubtypeInfo(stack);
			if (subtypeInfo != null) {
				if (!subtypeInfo.isEmpty()) {
					itemKey.append(':').append(subtypeInfo);
				}
			} else {
				if (mode == UidMode.FULL) {
					itemKey.append(':').append(metadata);
					
					NBTTagCompound serializedNbt = stack.serializeNBT();
					NBTTagCompound nbtTagCompound = serializedNbt.getCompoundTag("tag").copy();
					
					if (serializedNbt.hasKey("ForgeCaps")) {
						NBTTagCompound forgeCaps = serializedNbt.getCompoundTag("ForgeCaps");
						if (!((Map<UidMode, Map<ItemStack, String>>) forgeCaps).isEmpty()) { // ForgeCaps should never be empty
							nbtTagCompound.setTag("ForgeCaps", forgeCaps);
						}
					}
					
					if (!((Map<UidMode, Map<ItemStack, String>>) nbtTagCompound).isEmpty()) {
						itemKey.append(':').append(nbtTagCompound);
					}
				} else if (stack.getHasSubtypes()) {
					itemKey.append(':').append(metadata);
				}
			}
		}
		
		String result = itemKey.toString();
		if (uidCacheEnabled) {
			uidCache.get(mode).put(stack, result);
		}
		
		return result;
	}
	
	private enum UidMode {
		NORMAL, WILDCARD, FULL
	}
	
	static class MatchingItemsResult {
		public final Map<Integer, Integer> matchingItems = new HashMap<>();
		public final List<Integer> missingItems = new ArrayList<>();
	}
	
	private interface ItemStackMatchable<R> {
		ItemStack getStack();
		R getResult();
	}
	
	private static abstract class DelegateIterator<T, R> implements Iterator<R> {
		protected final Iterator<T> delegate;
		
		DelegateIterator(Iterator<T> delegate) {
			this.delegate = delegate;
		}
		
		@Override
		public boolean hasNext() {
			return delegate.hasNext();
		}
		
		@Override
		public void remove() {
			delegate.remove();
		}
	}
	
	private static class MatchingIterable implements Iterable<ItemStackMatchable<ItemStack>> {
		private final Iterable<ItemStack> list;
		
		MatchingIterable(Iterable<ItemStack> list) {
			this.list = list;
		}
		
		@Override
		public Iterator<ItemStackMatchable<ItemStack>> iterator() {
			Iterator<ItemStack> stacks = list.iterator();
			return new DelegateIterator<ItemStack, ItemStackMatchable<ItemStack>>(stacks) {
				@Override
				public ItemStackMatchable<ItemStack> next() {
					final ItemStack stack = delegate.next();
					
					return new ItemStackMatchable<ItemStack>() {
						@Override
						public ItemStack getStack() {
							return stack;
						}
						
						@Override
						public ItemStack getResult() {
							return stack;
						}
					};
				}
			};
		}
	}
	
	private static class MatchingIndexed implements Iterable<ItemStackMatchable<Integer>> {
		private final Map<Integer, ItemStack> map;
		
		MatchingIndexed(Map<Integer, ItemStack> map) {
			this.map = map;
		}
		
		@Override
		public Iterator<ItemStackMatchable<Integer>> iterator() {
			return new DelegateIterator<Map.Entry<Integer, ItemStack>, ItemStackMatchable<Integer>>(map.entrySet().iterator()) {
				@Override
				public ItemStackMatchable<Integer> next() {
					final Map.Entry<Integer, ItemStack> entry = delegate.next();
					
					return new ItemStackMatchable<Integer>() {
						@Override
						public ItemStack getStack() {
							return entry.getValue();
						}
						
						@Override
						public Integer getResult() {
							return entry.getKey();
						}
					};
				}
			};
		}
	}
}
