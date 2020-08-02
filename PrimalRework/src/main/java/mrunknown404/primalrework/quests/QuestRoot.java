package mrunknown404.primalrework.quests;

import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.helpers.StageHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class QuestRoot extends Quest {
	public QuestRoot(EnumStage stage) {
		super(stage, "root", null, null, null, null, null, null);
	}
	
	@Override
	public void checkRequirements(World world, EntityPlayer player, int amount) {
		if (StageHelper.hasAccessToStage(stage)) {
			finishQuest(world, player);
		}
	}
}
