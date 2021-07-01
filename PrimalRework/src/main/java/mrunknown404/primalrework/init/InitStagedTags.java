package mrunknown404.primalrework.init;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.stage.StagedTag;
import mrunknown404.primalrework.utils.Cache;
import mrunknown404.primalrework.utils.enums.EnumStage;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class InitStagedTags {
	private static final List<StagedTag> TAGS = new ArrayList<StagedTag>();
	
	private static Cache<Item, List<StagedTag>> tagCache = new Cache<Item, List<StagedTag>>();
	private static StagedTag tagSearchCache;
	
	public static final StagedTag ALL_LOGS = new StagedTag("logs").add(EnumStage.stage0, Items.OAK_LOG, Items.BIRCH_LOG, Items.SPRUCE_LOG, Items.JUNGLE_LOG, Items.DARK_OAK_LOG,
			Items.ACACIA_LOG, InitBlocks.STRIPPED_OAK_LOG.get().asItem(), InitBlocks.STRIPPED_BIRCH_LOG.get().asItem(), InitBlocks.STRIPPED_SPRUCE_LOG.get().asItem(),
			InitBlocks.STRIPPED_JUNGLE_LOG.get().asItem(), InitBlocks.STRIPPED_DARK_OAK_LOG.get().asItem(), InitBlocks.STRIPPED_ACACIA_LOG.get().asItem());
	public static final StagedTag ALL_PLANK_BLOCKS = new StagedTag("plank_blocks").add(EnumStage.stage0, Items.OAK_PLANKS, Items.BIRCH_PLANKS, Items.SPRUCE_PLANKS,
			Items.JUNGLE_PLANKS, Items.DARK_OAK_PLANKS, Items.ACACIA_PLANKS);
	public static final StagedTag ALL_PLANKS = new StagedTag("planks").add(EnumStage.stage2, InitItems.OAK_PLANK.get(), InitItems.BIRCH_PLANK.get(), InitItems.SPRUCE_PLANK.get(),
			InitItems.JUNGLE_PLANK.get(), InitItems.DARK_OAK_PLANK.get(), InitItems.ACACIA_PLANK.get());
	public static final StagedTag OAK_LOGS = new StagedTag("oak_logs").add(EnumStage.stage0, Items.OAK_LOG, InitBlocks.STRIPPED_OAK_LOG.get().asItem());
	public static final StagedTag BIRCH_LOGS = new StagedTag("birch_logs").add(EnumStage.stage0, Items.BIRCH_LOG, InitBlocks.STRIPPED_BIRCH_LOG.get().asItem());
	public static final StagedTag SPRUCE_LOGS = new StagedTag("spruce_logs").add(EnumStage.stage0, Items.SPRUCE_LOG, InitBlocks.STRIPPED_SPRUCE_LOG.get().asItem());
	public static final StagedTag JUNGLE_LOGS = new StagedTag("jungle_logs").add(EnumStage.stage0, Items.JUNGLE_LOG, InitBlocks.STRIPPED_JUNGLE_LOG.get().asItem());
	public static final StagedTag ACACIA_LOGS = new StagedTag("acacia_logs").add(EnumStage.stage0, Items.ACACIA_LOG, InitBlocks.STRIPPED_ACACIA_LOG.get().asItem());
	public static final StagedTag DARK_OAK_LOGS = new StagedTag("dark_oak_logs").add(EnumStage.stage0, Items.DARK_OAK_LOG, InitBlocks.STRIPPED_DARK_OAK_LOG.get().asItem());
	
	//TODO way of viewing tags!
	
	public static List<StagedTag> getItemsTags(Item item) {
		if (tagCache.is(item)) {
			return tagCache.get();
		}
		
		List<StagedTag> tags = new ArrayList<StagedTag>();
		for (StagedTag tag : TAGS) {
			if (tag.getItemsWithCurrentStage().contains(item)) {
				tags.add(tag);
			}
		}
		
		if (!tags.isEmpty()) {
			tagCache.set(item, tags);
		}
		
		return tags;
	}
	
	public static StagedTag getTag(String tag) {
		if (tagSearchCache != null && tagSearchCache.tag.equals(tag)) {
			return tagSearchCache;
		}
		
		for (StagedTag stag : TAGS) {
			if (stag.tag.equals(tag)) {
				tagSearchCache = stag;
				return stag;
			}
		}
		
		return null;
	}
	
	public static void addToList(StagedTag tag) {
		TAGS.add(tag);
	}
	
	public static void load() {
		System.out.println("Loaded " + TAGS.size() + " tags!");
	}
}
