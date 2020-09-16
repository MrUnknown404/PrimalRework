package mrunknown404.primalrework.quests;

import mrunknown404.primalrework.util.helpers.StageHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class QuestEnd extends Quest {
	
	public QuestEnd(String name_key, Quest parent, QuestRequirement req) {
		super(name_key, parent, req);
	}
	
	@Override
	public void finishQuest(World world, EntityPlayer player) {
		super.finishQuest(world, player);
		StageHelper.setStage(StageHelper.getNextStage());
		System.out.println("The world has advanced to the next stage!");
	}
}
