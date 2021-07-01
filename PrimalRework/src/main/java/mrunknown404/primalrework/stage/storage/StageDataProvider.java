package mrunknown404.primalrework.stage.storage;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class StageDataProvider implements ICapabilitySerializable<INBT> {
	@CapabilityInject(IStageData.class)
	public static Capability<IStageData> capability = null;
	private LazyOptional<IStageData> instance = LazyOptional.of(capability::getDefaultInstance);
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return cap == capability ? instance.cast() : LazyOptional.empty();
	}
	
	@Override
	public INBT serializeNBT() {
		return capability.getStorage().writeNBT(capability, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null);
	}
	
	@Override
	public void deserializeNBT(INBT nbt) {
		capability.getStorage().readNBT(capability, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null, nbt);
	}
}
