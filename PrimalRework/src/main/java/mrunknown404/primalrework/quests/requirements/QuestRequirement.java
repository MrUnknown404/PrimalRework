package mrunknown404.primalrework.quests.requirements;

import java.util.List;

import mrunknown404.primalrework.items.StagedItem;
import mrunknown404.primalrework.utils.helpers.WordH;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class QuestRequirement<T> {
	private ITextComponent realDesc;
	protected final TranslationTextComponent requires;
	protected final T obj;
	protected final int count;
	
	public QuestRequirement(T obj, int count) {
		this.obj = obj;
		this.requires = WordH.translate("quest.info.requirement.require");
		this.count = count;
	}
	
	public boolean checkCount(int count) {
		return count >= this.count;
	}
	
	//@formatter:off
	public abstract CheckResult checkConditions(List<? extends PlayerEntity> list);
	public abstract StagedItem getIcon();
	public abstract boolean isObject(T obj);
	protected abstract ITextComponent setupDescription();
	//@formatter:on
	
	public T getObj() {
		return obj;
	}
	
	public ITextComponent getDescription() {
		if (realDesc == null) {
			realDesc = setupDescription();
		}
		
		return realDesc;
	}
	
	public static class CheckResult {
		public final boolean finished;
		public final PlayerEntity player;
		
		protected CheckResult() {
			this.finished = false;
			this.player = null;
		}
		
		protected CheckResult(PlayerEntity player) {
			this.finished = true;
			this.player = player;
		}
	}
}
