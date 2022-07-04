package mrunknown404.primalrework.api.utils;

import mrunknown404.primalrework.stage.Stage;
import mrunknown404.primalrework.world.savedata.WSDStage;

public interface IStageProvider {
	public abstract Stage getStage();
	
	public default boolean hasAccessToCurrentStage() {
		return hasAccessToStage(getStage());
	}
	
	public default boolean hasAccessToStage(Stage stage) {
		return WSDStage.getStage().id >= stage.id;
	}
}
