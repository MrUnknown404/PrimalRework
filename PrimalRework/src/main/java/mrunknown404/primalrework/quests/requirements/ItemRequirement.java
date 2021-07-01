package mrunknown404.primalrework.quests.requirements;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class ItemRequirement extends QuestRequirement<Item, Item> {
	
	public ItemRequirement(Item obj, int count) {
		super(obj, count);
	}
	
	@Override
	protected boolean check(Item obj) {
		return this.obj == obj;
	}
	
	@Override
	public Item getIcon() {
		return obj;
	}
	
	@Override
	protected ITextComponent setupDescription() {
		return requires.append(new StringTextComponent(" " + count + "x " + obj.getName(new ItemStack(obj)).getString()));
	}
	
	@Override
	protected Class<Item> getClazz() {
		return Item.class;
	}
}
