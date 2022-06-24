package mrunknown404.primalrework.datagen;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.blocks.SBMetal;
import mrunknown404.primalrework.blocks.utils.StagedBlock;
import mrunknown404.primalrework.registries.PRRegistry;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
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
			
			if (b.usesVanillaNamespaceItem()) {
				continue;
			}
			
			final String id = b.usesVanillaNamespaceBlock() ? "minecraft" : PrimalRework.MOD_ID;
			
			switch (b.getBlockModelType()) {
				case none:
					break;
				case normal:
					cubeAll(b.getRegName(), new ResourceLocation(id, "block/" + b.getRegName()));
					break;
				case facing_pillar:
					cubeColumnHorizontal(b.getRegName() + "_horizontal", new ResourceLocation(id, "block/" + b.getRegName() + "_side"),
							new ResourceLocation(id, "block/" + b.getRegName() + "_end"));
					cubeColumn(b.getRegName(), new ResourceLocation(id, "block/" + b.getRegName() + "_side"), new ResourceLocation(id, "block/" + b.getRegName() + "_end"));
					break;
				case normal_colored:
					if (b instanceof SBMetal) {
						getBuilder(b.getRegName()).parent(new UncheckedModelFile(new ResourceLocation(id, ("block/template_metal_block"))));
					} else {
						cubeAll(b.getRegName(), new ResourceLocation(id, "block/" + b.getRegName()));
					}
					break;
				case slab:
					ResourceLocation loc = new ResourceLocation(id, "block/" + b.getRegName().replace("_slab", ""));
					slab(b.getRegName(), loc, loc, loc);
					slabTop(b.getRegName() + "_top", loc, loc, loc);
					break;
			}
		}
	}
	
	@Override
	public String getName() {
		return "Block Models: " + modid;
	}
}
