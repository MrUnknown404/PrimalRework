package mrunknown404.primalrework.stage.storage;

import mrunknown404.primalrework.utils.enums.EnumStage;

public class StageDataFactory implements IStageData {
	private EnumStage stage = EnumStage.stage0;
	
	@Override
	public void setStage(EnumStage stage) {
		this.stage = stage;
	}
	
	@Override
	public EnumStage getStage() {
		return stage;
	}
}
