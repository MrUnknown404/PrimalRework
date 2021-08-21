package mrunknown404.primalrework.stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mrunknown404.primalrework.helpers.StageH;
import mrunknown404.primalrework.helpers.WordH;
import mrunknown404.primalrework.registries.PRStagedTags;
import mrunknown404.primalrework.utils.enums.EnumStage;
import net.minecraft.item.Item;
import net.minecraft.util.text.ITextComponent;

public class StagedTag {
	private final Map<EnumStage, List<Item>> items = new HashMap<EnumStage, List<Item>>();
	private final Map<Item, EnumStage> items_reverse = new HashMap<Item, EnumStage>();
	public final String tag;
	public final ITextComponent displayName;
	
	public StagedTag(String tag) {
		this.tag = tag;
		this.displayName = WordH.translate("staged_tag." + tag + ".name");
		PRStagedTags.addToList(this);
	}
	
	public boolean hasItemWithCurrentStage(Item item) {
		return getItemsWithCurrentStage().contains(item);
	}
	
	/** @param stage The minimum stage required for this item to be in the tag */
	public StagedTag add(EnumStage stage, Item... items) {
		List<Item> its = this.items.get(stage);
		if (its == null) {
			its = new ArrayList<Item>();
		}
		
		for (Item item : items) {
			its.add(item);
			items_reverse.put(item, stage);
		}
		this.items.put(stage, its);
		return this;
	}
	
	public EnumStage getItemsMinStage(Item item) {
		return items_reverse.get(item);
	}
	
	public List<Item> getItemsWithCurrentStage() {
		List<Item> items = new ArrayList<Item>();
		for (EnumStage st : StageH.getAllPrevStages()) {
			List<Item> its = this.items.get(st);
			if (its != null && !its.isEmpty()) {
				items.addAll(its);
			}
		}
		
		return items;
	}
	
	public List<Item> getItemsFromAllStages() {
		List<Item> items = new ArrayList<Item>();
		for (List<Item> list : this.items.values()) {
			for (Item item : list) {
				items.add(item);
			}
		}
		
		return items;
	}
}
