package mrunknown404.primalrework.util.jei.icantgetthistoworkwithoutcopyingeverything;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;

public class CycleTimer {
	private static final int CYCLE_TIME = 1000;
	private long startTime;
	private long drawTime;
	private long pausedDuration = 0;
	
	CycleTimer(int offset) {
		long time = System.currentTimeMillis();
		this.startTime = time - (offset * CYCLE_TIME);
		this.drawTime = time;
	}
	
	<T> T getCycledItem(List<T> list) {
		if (list.isEmpty()) {
			return null;
		}
		
		Long index = ((drawTime - startTime) / CYCLE_TIME) % list.size();
		return list.get(index.intValue());
	}
	
	void onDraw() {
		if (!GuiScreen.isShiftKeyDown()) {
			if (pausedDuration > 0) {
				startTime += pausedDuration;
				pausedDuration = 0;
			}
			drawTime = System.currentTimeMillis();
		} else {
			pausedDuration = System.currentTimeMillis() - drawTime;
		}
	}
}
