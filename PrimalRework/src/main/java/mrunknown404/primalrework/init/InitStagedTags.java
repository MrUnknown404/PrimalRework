package mrunknown404.primalrework.init;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.items.raw.StagedItem;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.stage.StagedTag;
import mrunknown404.primalrework.utils.DoubleCache;
import mrunknown404.primalrework.utils.helpers.StageH;
import net.minecraftforge.fml.RegistryObject;

public class InitStagedTags {
	public static final RegistryObject<StagedTag> LOGS = InitRegistry.stagedTag("logs",
			() -> new StagedTag().add(InitStages.STAGE_1, InitBlocks.OAK_LOG.get().asStagedItem(), InitBlocks.SPRUCE_LOG.get().asStagedItem(), InitBlocks.BIRCH_LOG.get().asStagedItem(),
					InitBlocks.JUNGLE_LOG.get().asStagedItem(), InitBlocks.ACACIA_LOG.get().asStagedItem(), InitBlocks.DARK_OAK_LOG.get().asStagedItem(),
					InitBlocks.STRIPPED_OAK_LOG.get().asStagedItem(), InitBlocks.STRIPPED_SPRUCE_LOG.get().asStagedItem(), InitBlocks.STRIPPED_BIRCH_LOG.get().asStagedItem(),
					InitBlocks.STRIPPED_JUNGLE_LOG.get().asStagedItem(), InitBlocks.STRIPPED_ACACIA_LOG.get().asStagedItem(), InitBlocks.STRIPPED_DARK_OAK_LOG.get().asStagedItem()));
	
	private static final DoubleCache<StagedItem, Stage, List<StagedTag>> tagCache = DoubleCache.and();
	
	public static List<StagedTag> getItemsTags(StagedItem item) {
		if (tagCache.is(item, StageH.getStage())) {
			return tagCache.get();
		}
		
		List<StagedTag> tags = new ArrayList<StagedTag>();
		for (RegistryObject<StagedTag> tag : InitRegistry.getTags()) {
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
