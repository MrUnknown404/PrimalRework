package mrunknown404.primalrework.init;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.items.ISIProvider;
import mrunknown404.primalrework.items.StagedItem;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.stage.StagedTag;
import mrunknown404.primalrework.utils.DoubleCache;
import mrunknown404.primalrework.utils.helpers.StageH;
import net.minecraftforge.fml.RegistryObject;

public class InitStagedTags {
	public static final RegistryObject<StagedTag> LOGS = InitRegistry.stagedTag("logs",
			() -> new StagedTag(InitBlocks.OAK_LOG, InitBlocks.SPRUCE_LOG, InitBlocks.BIRCH_LOG, InitBlocks.JUNGLE_LOG, InitBlocks.ACACIA_LOG, InitBlocks.DARK_OAK_LOG,
					InitBlocks.STRIPPED_OAK_LOG, InitBlocks.STRIPPED_SPRUCE_LOG, InitBlocks.STRIPPED_BIRCH_LOG, InitBlocks.STRIPPED_JUNGLE_LOG, InitBlocks.STRIPPED_ACACIA_LOG,
					InitBlocks.STRIPPED_DARK_OAK_LOG));
	
	private static final DoubleCache<StagedItem, Stage, List<StagedTag>> tagCache = DoubleCache.and();
	
	public static List<StagedTag> getItemsTags(ISIProvider item) {
		if (tagCache.is(item.getStagedItem(), StageH.getStage())) {
			return tagCache.get();
		}
		
		List<StagedTag> tags = new ArrayList<StagedTag>();
		for (RegistryObject<StagedTag> tag : InitRegistry.getTags()) {
			if (tag.get().getItemsWithCurrentStage().contains(item)) {
				tags.add(tag.get());
			}
		}
		
		if (!tags.isEmpty()) {
			tagCache.set(item.getStagedItem(), StageH.getStage(), tags);
		}
		
		return tags;
	}
}
