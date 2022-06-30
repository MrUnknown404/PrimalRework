package mrunknown404.primalrework.datagen;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.blocks.SBMetal;
import mrunknown404.primalrework.blocks.utils.StagedBlock;
import mrunknown404.primalrework.registries.PRRegistry;
import mrunknown404.primalrework.utils.IMetalColored;
import mrunknown404.primalrework.utils.enums.Metal;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
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
			final String rawName = b.getRegistryName().getPath();
			String name = rawName;
			
			if (b instanceof IMetalColored) {
				if (((IMetalColored) b).getMetal() != Metal.UNKNOWN) {
					name = "template_" + name.substring(name.indexOf('_') + 1);
				}
			}
			
			switch (b.getBlockModelType()) {
				case none:
					break;
				case normal:
					if (b instanceof SBMetal) {
						getBuilder(rawName).parent(getExistingFile(new ResourceLocation(id, "block/template_metal_block")));
					} else {
						cubeAll(rawName, new ResourceLocation(id, "block/" + name));
					}
					break;
				case facing_pillar:
					cubeColumnHorizontal(rawName + "_horizontal", new ResourceLocation(id, "block/" + name + "_side"), new ResourceLocation(id, "block/" + name + "_end"));
					cubeColumn(rawName, new ResourceLocation(id, "block/" + name + "_side"), new ResourceLocation(id, "block/" + name + "_end"));
					break;
				case slab:
					ResourceLocation loc = new ResourceLocation(id, "block/" + name.replace("_slab", ""));
					slab(rawName, loc, loc, loc);
					slabTop(rawName + "_top", loc, loc, loc);
					break;
			}
		}
	}
	
	@Override
	public String getName() {
		return "Block Models: " + modid;
	}
}
