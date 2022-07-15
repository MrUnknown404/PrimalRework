package mrunknown404.primalrework.stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import mrunknown404.primalrework.api.utils.ISIProvider;
import mrunknown404.primalrework.items.StagedItem;
import mrunknown404.primalrework.recipes.IIngredientProvider;
import mrunknown404.primalrework.recipes.Ingredient;
import mrunknown404.primalrework.utils.helpers.WordH;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class StagedTag extends ForgeRegistryEntry<StagedTag> implements IIngredientProvider {
	private final Lazy<ITextComponent> displayName = Lazy.of(() -> WordH.translate("stagedtag." + getRegistryName().getPath()));
	
	private final List<StagedItem> items = new ArrayList<StagedItem>();
	private final Map<Stage, List<StagedItem>> stageMap = new HashMap<Stage, List<StagedItem>>();
	
	public StagedTag(ISIProvider icon, ISIProvider... items) {
		add(icon.getStagedItem());
		for (ISIProvider i : items) {
			add(i.getStagedItem());
		}
	}
	
	public ITextComponent getDisplayName() {
		return displayName.get();
	}
	
	public StagedItem getIcon() {
		return items.get(0);
	}
	
	private void add(StagedItem item) {
		if (item == null) { //Data-gen fix.
			return;
		}
		
		stageMap.computeIfAbsent(item.stage, s -> new ArrayList<StagedItem>()).add(item);
		items.add(item);
	}
	
	public boolean containsAtCurrentStage(StagedItem item) {
		return containsAtAll(item) && stageMap.entrySet().stream().anyMatch(e -> e.getKey().hasAccessToCurrentStage() && e.getValue().contains(item));
	}
	
	public boolean containsAtAll(StagedItem item) {
		return items.contains(item);
	}
	
	public List<StagedItem> getItemsWithCurrentStage() {
		return stageMap.entrySet().stream().filter(e -> e.getKey().hasAccessToCurrentStage()).map(e -> e.getValue()).flatMap(List::stream).collect(Collectors.toList());
	}
	
	@Override
	public Ingredient getIngredient() {
		return Ingredient.createUsingTag(this);
	}
}
