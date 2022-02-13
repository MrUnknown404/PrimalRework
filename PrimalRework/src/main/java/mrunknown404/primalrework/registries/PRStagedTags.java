package mrunknown404.primalrework.registries;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.stage.StagedTag;
import mrunknown404.primalrework.utils.DoubleCache;
import mrunknown404.primalrework.utils.helpers.StageH;
import net.minecraftforge.fml.RegistryObject;

public class PRStagedTags {
	public static final RegistryObject<StagedTag> LOGS = PRRegistry.stagedTag(
			new StagedTag("logs").add(PRStages.STAGE_1, PRBlocks.OAK_LOG.get().asStagedItem(), PRBlocks.SPRUCE_LOG.get().asStagedItem(), PRBlocks.BIRCH_LOG.get().asStagedItem(),
					PRBlocks.JUNGLE_LOG.get().asStagedItem(), PRBlocks.ACACIA_LOG.get().asStagedItem(), PRBlocks.DARK_OAK_LOG.get().asStagedItem(),
					PRBlocks.STRIPPED_OAK_LOG.get().asStagedItem(), PRBlocks.STRIPPED_SPRUCE_LOG.get().asStagedItem(), PRBlocks.STRIPPED_BIRCH_LOG.get().asStagedItem(),
					PRBlocks.STRIPPED_JUNGLE_LOG.get().asStagedItem(), PRBlocks.STRIPPED_ACACIA_LOG.get().asStagedItem(), PRBlocks.STRIPPED_DARK_OAK_LOG.get().asStagedItem()));
	
	private static final DoubleCache<StagedItem, Stage, List<StagedTag>> tagCache = new DoubleCache<StagedItem, Stage, List<StagedTag>>();
	
	public static List<StagedTag> getItemsTags(StagedItem item) {
		if (tagCache.is(item, StageH.getStage())) {
			return tagCache.get();
		}
		
		List<StagedTag> tags = new ArrayList<StagedTag>();
		for (RegistryObject<StagedTag> tag : PRRegistry.getTags()) {
			if (tag.get().getItemsWithCurrentStage().contains(item)) {
				tags.add(tag.get());
			}
		}
		
		if (!tags.isEmpty()) {
			tagCache.set(item, StageH.getStage(), tags);
		}
		
		return tags;
	}
}
