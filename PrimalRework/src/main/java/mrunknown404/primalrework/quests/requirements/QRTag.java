package mrunknown404.primalrework.quests.requirements;

import java.util.List;
import java.util.function.Supplier;

import mrunknown404.primalrework.init.InitStagedTags;
import mrunknown404.primalrework.items.StagedItem;
import mrunknown404.primalrework.stage.StagedTag;
import mrunknown404.primalrework.utils.helpers.WordH;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

public class QRTag extends QuestRequirement<StagedTag> {
	public QRTag(Supplier<StagedTag> obj, int count) {
		super(obj.get(), count);
	}
	
	@Override
	public CheckResult checkConditions(List<? extends PlayerEntity> list) {
		for (PlayerEntity pl : list) {
			int count = 0;
			for (ItemStack item : pl.inventory.items) {
				if (item.getItem() instanceof StagedItem) {
					for (StagedTag tag : InitStagedTags.getItemsTags((StagedItem) item.getItem())) {
						if (isObject(tag)) {
							count += item.getCount();
							
							if (checkCount(count)) {
								return new CheckResult(pl);
							}
						}
					}
				}
			}
		}
		
		return new CheckResult();
	}
	
	@Override
	public StagedItem getIcon() {
		return obj.getIcon();
	}
	
	@Override
	public boolean isObject(StagedTag obj) {
		return this.obj == obj;
	}
	
	@Override
	protected ITextComponent setupDescription() {
		return requires.append(WordH.string(" " + count + "x any " + obj.getDisplayName().getString()));
	}
}
