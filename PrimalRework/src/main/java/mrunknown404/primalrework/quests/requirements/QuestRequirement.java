package mrunknown404.primalrework.quests.requirements;

import mrunknown404.primalrework.helpers.WordH;
import net.minecraft.item.Item;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class QuestRequirement<T, I> {
	private ITextComponent realDesc;
	protected final TranslationTextComponent requires;
	protected final T obj;
	protected final int count;
	
	public QuestRequirement(T obj, int count) {
		this.obj = obj;
		this.requires = WordH.translate("quest.requirement.require");
		this.count = count;
	}
	
	public boolean check(I obj, int count) {
		return isType(obj) && check(obj) && count >= this.count;
	}
	
	//@formatter:off
	public abstract Item getIcon();
	protected abstract boolean check(I obj);
	protected abstract ITextComponent setupDescription();
	protected abstract Class<I> getClazz();
	//@formatter:on
	
	protected boolean isType(I obj) {
		return getClazz().isAssignableFrom(obj.getClass());
	}
	
	public T getObj() {
		return obj;
	}
	
	public ITextComponent getDescription() {
		if (realDesc == null) {
			realDesc = setupDescription();
		}
		
		return realDesc;
	}
}
