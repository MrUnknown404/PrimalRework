package mrunknown404.primalrework.blocks;

import mrunknown404.primalrework.blocks.util.BlockRotatedPillarBase;
import mrunknown404.primalrework.util.DoubleValue;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.ITickable;

public class BlockDenseLog extends BlockRotatedPillarBase implements ITickable {
	
	public BlockDenseLog() {
		super("dense_log", Material.WOOD, SoundType.WOOD, 3, 6, EnumStage.stage2, new DoubleValue<EnumToolType, EnumToolMaterial>(EnumToolType.axe, EnumToolMaterial.flint));
	}
	
	@Override
	public void update() {
		//TODO write this
	}
}
