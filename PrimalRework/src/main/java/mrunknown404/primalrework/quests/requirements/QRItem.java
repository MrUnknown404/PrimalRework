package mrunknown404.primalrework.quests.requirements;

import java.util.List;

import mrunknown404.primalrework.items.ISIProvider;
import mrunknown404.primalrework.items.StagedItem;
import mrunknown404.primalrework.utils.helpers.WordH;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

public class QRItem extends QuestRequirement<StagedItem> {
	public QRItem(ISIProvider obj, int count) {
		super(obj.getStagedItem(), count);
	}
	
	@Override
	public CheckResult checkConditions(List<? extends PlayerEntity> list) {
		for (PlayerEntity pl : list) {
			int count = 0;
			for (ItemStack item : pl.inventory.items) {
				if (item.getItem() instanceof StagedItem && isObject((StagedItem) item.getItem())) {
					count += item.getCount();
					
					if (checkCount(count)) {
						return new CheckResult(pl);
					}
				}
			}
		}
		
		return new CheckResult();
	}
	
	@Override
	public boolean isObject(StagedItem obj) {
		return this.obj == obj;
	}
	
	@Override
	public StagedItem getIcon() {
		return obj;
	}
	
	@Override
	protected ITextComponent setupDescription() {
		return requires.append(WordH.string(" " + count + "x " + obj.getName(new ItemStack(obj)).getString()));
	}
}
