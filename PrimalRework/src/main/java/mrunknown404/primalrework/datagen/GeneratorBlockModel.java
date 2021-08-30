package mrunknown404.primalrework.datagen;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.blocks.SBOre;
import mrunknown404.primalrework.blocks.utils.StagedBlock;
import mrunknown404.primalrework.blocks.utils.StagedBlock.BlockModelType;
import mrunknown404.primalrework.registries.PRRegistry;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

class GeneratorBlockModel extends ModelProvider<TexturelessModelBuilder> {
	GeneratorBlockModel(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, PrimalRework.MOD_ID, BLOCK_FOLDER, TexturelessModelBuilder::new, existingFileHelper);
	}
	
	@Override
	protected void registerModels() {
		for (RegistryObject<Block> regBlock : PRRegistry.getBlocks()) {
			StagedBlock b = (StagedBlock) regBlock.get();
			if (b.getBlockModelType() == BlockModelType.none) {
				continue;
			}
			
			switch (b.getBlockModelType()) {
				case none:
					break;
				case normal:
					cubeAll(b.getRegName(), modLoc("block/" + b.getRegName()));
					break;
				case facing_pillar:
					cubeColumnHorizontal(b.getRegName() + "_horizontal", modLoc("block/" + b.getRegName() + "_side"), modLoc("block/" + b.getRegName() + "_end"));
					cubeColumn(b.getRegName(), modLoc("block/" + b.getRegName() + "_side"), modLoc("block/" + b.getRegName() + "_end"));
					break;
				case normal_colored:
					if (b instanceof SBOre) {
						getBuilder(b.getRegName()).parent(new ModelFile.UncheckedModelFile(extendWithFolder(modLoc("block/ore_" + ((SBOre) b).value + "_template"))));
					} else {
						System.err.println("Error!");
					}
					break;
			}
		}
	}
	
	@Override
	public String getName() {
		return "Block Models: " + modid;
	}
	
	private ResourceLocation extendWithFolder(ResourceLocation rl) {
		return rl.getPath().contains("/") ? rl : new ResourceLocation(rl.getNamespace(), folder + "/" + rl.getPath());
	}
}
