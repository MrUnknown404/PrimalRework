package mrunknown404.primalrework.quests.requirements;

import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.utils.helpers.WordH;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

public class ItemRequirement extends QuestRequirement<StagedItem, StagedItem> {
	public ItemRequirement(StagedItem obj, int count) {
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
	
	@Override
	protected Class<StagedItem> getClazz() {
		return StagedItem.class;
	}
}
