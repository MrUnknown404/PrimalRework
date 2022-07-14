package mrunknown404.primalrework.recipes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import mrunknown404.primalrework.init.InitStagedTags;
import mrunknown404.primalrework.items.StagedItem;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.stage.StagedTag;
import mrunknown404.primalrework.utils.Cache;
import mrunknown404.primalrework.world.savedata.WSDStage;

public class Ingredient implements IIngredientProvider {
	public static final Ingredient EMPTY = createUsingItem(null);
	
	private final StagedItem item;
	private final List<StagedTag> tags;
	private final boolean isEmpty;
	
	private Cache<Stage, List<StagedItem>> itemsCache = Cache.create();
	
	private Ingredient(StagedItem item, List<StagedTag> tags) {
		this.item = item;
		this.tags = tags;
		this.isEmpty = item == null && tags.isEmpty();
	}
	
	public static Ingredient createUsingItem(StagedItem item) {
		return new Ingredient(item, new ArrayList<StagedTag>());
	}
	
	public static Ingredient createUsingTags(StagedItem item) {
		return new Ingredient(item, InitStagedTags.getItemsTags(item));
	}
	
	public static Ingredient createUsingTag(StagedTag tag) {
		return new Ingredient(null, Arrays.asList(tag));
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
	
	public List<StagedItem> getStagedItems() {
		return itemsCache.computeIfAbsent(WSDStage.getStage(), () -> {
			List<StagedItem> items = new ArrayList<StagedItem>();
			if (item != null) {
				items.add(item);
			}
			
			if (!tags.isEmpty()) {
				items.addAll(tags.stream().flatMap(tag -> tag.getItemsWithCurrentStage().stream()).collect(Collectors.toList()));
			}
			
			return items;
		});
	}
	
	@Override
	public Ingredient getIngredient() {
		return this;
	}
	
	@Override
	public String toString() {
		if (isEmpty()) {
			return "Empty";
		} else if (!tags.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < tags.size(); i++) {
				StagedTag tag = tags.get(i);
				sb.append("Any '" + tag.getDisplayName().getString() + "'");
				
				if (i != tags.size() - 1) {
					sb.append(" or ");
				}
			}
			return sb.toString();
		} else {
			return "Item: " + item.toString();
		}
	}
}
