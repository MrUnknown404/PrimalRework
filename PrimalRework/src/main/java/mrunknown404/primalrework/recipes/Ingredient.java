package mrunknown404.primalrework.recipes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mrunknown404.primalrework.helpers.StageH;
import mrunknown404.primalrework.stage.StagedTag;
import mrunknown404.primalrework.utils.Cache;
import mrunknown404.primalrework.utils.enums.EnumStage;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class Ingredient {
	public static final Ingredient EMPTY = new Ingredient(null, null);
	
	private final Item item;
	private final List<StagedTag> tags;
	private final boolean isEmpty;
	
	private Cache<EnumStage, List<Item>> itemsCache = new Cache<EnumStage, List<Item>>();
	
	public Ingredient(Item item, List<StagedTag> tags) {
		this.item = item;
		this.tags = tags;
		
		if ((item == null && tags == null) || item == Items.AIR || (item == null && tags.isEmpty())) {
			isEmpty = true;
		} else {
			isEmpty = false;
		}
	}
	
	public Ingredient(Item item) {
		this(item, new ArrayList<StagedTag>());
	}
	
	public Ingredient(StagedTag tag) {
		this(null, tag == null ? new ArrayList<StagedTag>() :Arrays.asList(tag));
	}
	
	public boolean isEmpty() {
		return isEmpty;
	}
	
	public boolean matches(Ingredient ingredient) {
		if (isEmpty() || ingredient.isEmpty()) {
			return isEmpty() && ingredient.isEmpty();
		} else if (!tags.isEmpty() && !ingredient.tags.isEmpty()) {
			for (StagedTag tag : ingredient.tags) {
				if (tags.contains(tag)) {
					return true;
				}
			}
		} else if (ingredient.item == item) {
			return true;
		}
		
		return false;
	}
	
	public List<Item> getItems() {
		if (itemsCache.is(StageH.getStage())) {
			return itemsCache.get();
		}
		
		List<Item> items = new ArrayList<Item>();
		if (item != null) {
			items.add(item);
		}
		if (!tags.isEmpty()) {
			for (StagedTag tag : tags) {
				for (Item item : tag.getItemsWithCurrentStage()) {
					items.add(item);
				}
			}
		}
		
		itemsCache.set(StageH.getStage(), items);
		return itemsCache.get();
	}
	
	@Override
	public String toString() {
		if (isEmpty()) {
			return "Empty";
		} else if (item != null) {
			return item.toString();
		} else {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < tags.size(); i++) {
				StagedTag tag = tags.get(i);
				sb.append("Any '" + tag.displayName.getString() + "'");
				
				if (i != tags.size() - 1) {
					sb.append(" ");
				}
			}
			return sb.toString();
		}
	}
}
