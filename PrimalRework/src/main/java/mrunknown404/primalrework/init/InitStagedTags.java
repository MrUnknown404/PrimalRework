package mrunknown404.primalrework.init;

import java.util.List;
import java.util.stream.Collectors;

import mrunknown404.primalrework.api.registry.PRRegistries;
import mrunknown404.primalrework.api.utils.ISIProvider;
import mrunknown404.primalrework.items.StagedItem;
import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.stage.StagedTag;
import mrunknown404.primalrework.utils.DoubleCache;
import mrunknown404.primalrework.world.savedata.WSDStage;
import net.minecraftforge.fml.RegistryObject;

public class InitStagedTags {
	public static final RegistryObject<StagedTag> LOGS = InitRegistry.stagedTag("logs",
			() -> new StagedTag(InitBlocks.OAK_LOG, InitBlocks.SPRUCE_LOG, InitBlocks.BIRCH_LOG, InitBlocks.JUNGLE_LOG, InitBlocks.ACACIA_LOG, InitBlocks.DARK_OAK_LOG,
					InitBlocks.STRIPPED_OAK_LOG, InitBlocks.STRIPPED_SPRUCE_LOG, InitBlocks.STRIPPED_BIRCH_LOG, InitBlocks.STRIPPED_JUNGLE_LOG, InitBlocks.STRIPPED_ACACIA_LOG,
					InitBlocks.STRIPPED_DARK_OAK_LOG));
	
	private static final DoubleCache<StagedItem, Stage, List<StagedTag>> TAG_CACHE = DoubleCache.and();
	
	public static List<StagedTag> getItemsTags(ISIProvider item) {
		return TAG_CACHE.computeIfAbsent(item.getStagedItem(), WSDStage.getStage(),
				() -> PRRegistries.STAGED_TAGS.getValues().stream().filter(tag -> tag.getItemsWithCurrentStage().contains(item)).collect(Collectors.toList()));
	}
}
