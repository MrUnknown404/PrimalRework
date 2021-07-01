package mrunknown404.primalrework.stage.storage;

import mrunknown404.primalrework.utils.enums.EnumStage;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class StageDataStorage implements IStorage<IStageData> {
	
	@Override
	public INBT writeNBT(Capability<IStageData> capability, IStageData instance, Direction side) {
		CompoundNBT tag = new CompoundNBT();
		tag.putString("Stage", instance.getStage().name());
		return tag;
	}
	
	@Override
	public void readNBT(Capability<IStageData> capability, IStageData instance, Direction side, INBT nbt) {
		instance.setStage(EnumStage.fromString(((CompoundNBT) nbt).getString("Stage")));
	}
}
