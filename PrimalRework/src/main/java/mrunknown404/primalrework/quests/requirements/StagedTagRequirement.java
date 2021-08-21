package mrunknown404.primalrework.quests.requirements;

import java.util.List;

import mrunknown404.primalrework.helpers.WordH;
import mrunknown404.primalrework.stage.StagedTag;
import net.minecraft.item.Item;
import net.minecraft.util.text.ITextComponent;

public class StagedTagRequirement extends QuestRequirement<StagedTag, Item> {
	private final Item icon;
	
	public StagedTagRequirement(StagedTag obj, Item icon, int count) {
		super(obj, count);
		this.icon = icon;
	}
	
	@Override
	public Item getIcon() {
		return icon;
	}
	
	@Override
	protected boolean check(Item obj) {
		List<Item> list = this.obj.getItemsWithCurrentStage();
		if (list.isEmpty()) {
			return false;
		}
		
		return list.contains(obj);
	}
	
	@Override
	protected ITextComponent setupDescription() {
		return requires.append(WordH.string(" " + count + "x " + obj.displayName));
	}
	
	@Override
	protected Class<Item> getClazz() {
		return Item.class;
	}
}
