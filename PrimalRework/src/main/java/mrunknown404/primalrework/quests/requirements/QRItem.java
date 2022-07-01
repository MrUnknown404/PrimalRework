package mrunknown404.primalrework.quests.requirements;

import mrunknown404.primalrework.items.raw.StagedItem;
import mrunknown404.primalrework.utils.helpers.WordH;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

public class QRItem extends QuestRequirement<StagedItem, StagedItem> {
	public QRItem(StagedItem obj, int count) {
		super(obj, count);
	}
	
	@Override
	protected boolean check(StagedItem obj) {
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
