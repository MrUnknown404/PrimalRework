package mrunknown404.primalrework.stage.storage;

import mrunknown404.primalrework.utils.enums.EnumStage;

public interface IStageData {
	public abstract void setStage(EnumStage stage);
	public abstract EnumStage getStage();
}
