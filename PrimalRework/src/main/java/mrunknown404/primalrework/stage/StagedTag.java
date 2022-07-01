package mrunknown404.primalrework.stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

import mrunknown404.primalrework.init.InitStages;
import mrunknown404.primalrework.items.raw.StagedItem;
import mrunknown404.primalrework.utils.helpers.StageH;
import mrunknown404.primalrework.utils.helpers.WordH;
import net.minecraft.util.text.TextComponent;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class StagedTag extends ForgeRegistryEntry<StagedTag> {
	private final Lazy<TextComponent> displayName = Lazy.of(() -> WordH.translate("stagedtag." + getRegistryName().getPath()));
	
	private final Map<Supplier<Stage>, List<StagedItem>> itemMap_sup = new HashMap<Supplier<Stage>, List<StagedItem>>();
	private final Map<Stage, List<StagedItem>> itemMap = new HashMap<Stage, List<StagedItem>>();
	private final Map<StagedItem, Stage> stageMap = new HashMap<StagedItem, Stage>();
	
	public TextComponent getDisplayName() {
		return displayName.get();
	}
	
	public StagedTag add(Supplier<Stage> stage, StagedItem item, StagedItem... items) {
		itemMap_sup.computeIfAbsent(stage, (s) -> new ArrayList<StagedItem>()).add(item);
		
		for (StagedItem i : items) {
			itemMap_sup.get(stage).add(i);
		}
		return this;
	}
	
	public void finish() {
		for (Entry<Supplier<Stage>, List<StagedItem>> pair : itemMap_sup.entrySet()) {
			Stage stage = pair.getKey().get();
			itemMap.computeIfAbsent(stage, (s) -> new ArrayList<StagedItem>());
			
			for (StagedItem i : pair.getValue()) {
				itemMap.get(stage).add(i);
				stageMap.put(i, stage);
			}
		}
	}
	
	public boolean containsAtCurrentStage(StagedItem item) {
		return containsAtAll(item) && StageH.hasAccessToStage(stageMap.get(item));
	}
	
	public boolean containsAtAll(StagedItem item) {
		return stageMap.containsKey(item);
	}
	
	public List<StagedItem> getItemsWithCurrentStage() {
		List<Stage> stages = InitStages.getStagesBeforeCurrent(true);
		List<StagedItem> items = new ArrayList<StagedItem>();
		
		for (Stage s : stages) {
			items.addAll(itemMap.getOrDefault(s, new ArrayList<StagedItem>()));
		}
		
		return items;
	}
	
	@Override
	public String toString() {
		return "Name: " + getRegistryName() + ", Map: " + itemMap.toString();
	}
}
