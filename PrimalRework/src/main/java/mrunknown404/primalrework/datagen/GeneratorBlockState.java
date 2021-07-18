package mrunknown404.primalrework.datagen;

import com.google.common.base.Preconditions;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.blocks.SBStrippedLog;
import mrunknown404.primalrework.blocks.SBUnlitPrimalWallTorch;
import mrunknown404.primalrework.blocks.utils.StagedBlock;
import mrunknown404.primalrework.blocks.utils.StagedBlock.BlockStateType;
import mrunknown404.primalrework.registries.PRRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

class GeneratorBlockState extends BlockStateProvider {
	GeneratorBlockState(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, PrimalRework.MOD_ID, existingFileHelper);
		
		ObfuscationReflectionHelper.setPrivateValue(BlockStateProvider.class, this, new ModBlockModelProvider(generator, existingFileHelper), "blockModels");
		ObfuscationReflectionHelper.setPrivateValue(BlockStateProvider.class, this, new ModItemModelProvider(generator, existingFileHelper), "itemModels");
	}
	
	@Override
	protected void registerStatesAndModels() {
		for (RegistryObject<Block> regBlock : PRRegistry.BLOCKS.getEntries()) {
			StagedBlock b = (StagedBlock) regBlock.get();
			if (b.getBlockStateType() == BlockStateType.none) {
				continue;
			}
			
			switch (b.getBlockStateType()) {
				case none:
					break;
				case normal:
					simpleBlock(b);
					break;
				case facing:
					int a = 180;
					if (b instanceof SBUnlitPrimalWallTorch) {
						a = 90;
					}
					
					horizontalBlock(b, new UncheckedModelFile(modLoc("block/" + b.getRegName())), a);
					break;
				case facing_pillar:
					facingPillar(b);
					break;
				case lit:
					lit(b);
					break;
				case random_direction:
					randomDirection(b);
					break;
			}
		}
	}
	
	private void randomDirection(StagedBlock b) {
		ConfiguredModel[] models = new ConfiguredModel[4];
		for (int i = 0; i < 4; i++) {
			models[i] = new ConfiguredModel(new UncheckedModelFile(modLoc("block/" + b.getRegName())), 0, 90 * i, false);
		}
		getVariantBuilder(b).partialState().setModels(models);
	}
	
	public void facingPillar(StagedBlock block) {
		ResourceLocation baseName = modLoc(block.getRegName());
		facingPillar(block, extend(baseName, "_side"), extend(baseName, "_end"));
	}
	
	public void facingPillar(StagedBlock block, ResourceLocation side, ResourceLocation end) {
		if (block instanceof SBStrippedLog) {
			facingPillar(block, models().cubeColumn(block.getRegName(), side, end), models().cubeColumnHorizontal(block.getRegName(), side, end));
		} else {
			facingPillar(block, models().cubeColumn(block.getRegName(), side, end), models().cubeColumnHorizontal(block.getRegName() + "_horizontal", side, end));
		}
	}
	
	public void facingPillar(StagedBlock block, ModelFile vertical, ModelFile horizontal) {
		getVariantBuilder(block).partialState().with(BlockStateProperties.AXIS, Axis.Y).modelForState().modelFile(vertical).addModel().partialState()
				.with(RotatedPillarBlock.AXIS, Axis.Z).modelForState().modelFile(horizontal).rotationX(90).addModel().partialState().with(RotatedPillarBlock.AXIS, Axis.X)
				.modelForState().modelFile(horizontal).rotationX(90).rotationY(90).addModel();
	}
	
	public void lit(StagedBlock block) {
		ResourceLocation baseName = modLoc("block/" + block.getRegName());
		getVariantBuilder(block).partialState().with(BlockStateProperties.LIT, false).modelForState().modelFile(new UncheckedModelFile(extend(baseName, "_unlit"))).addModel()
				.partialState().with(BlockStateProperties.LIT, true).modelForState().modelFile(new UncheckedModelFile(extend(baseName, "_lit"))).addModel();
	}
	
	private static ResourceLocation extend(ResourceLocation rl, String suffix) {
		return new ResourceLocation(rl.getNamespace(), rl.getPath() + suffix);
	}
	
	static class ModBlockModelBuilder extends BlockModelBuilder {
		ModBlockModelBuilder(ResourceLocation outputLocation, ExistingFileHelper existingFileHelper) {
			super(outputLocation, existingFileHelper);
		}
		
		ModBlockModelBuilder(BlockModelBuilder builder) {
			this(builder.getLocation(), null);
		}
		
		@Override
		public BlockModelBuilder texture(String key, ResourceLocation texture) {
			Preconditions.checkNotNull(key, "Key must not be null");
			Preconditions.checkNotNull(texture, "Texture must not be null");
			this.textures.put(key, texture.toString());
			return this;
		}
	}
	
	static class ModBlockModelProvider extends BlockModelProvider {
		ModBlockModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
			super(generator, PrimalRework.MOD_ID, existingFileHelper);
		}
		
		@Override
		protected void generateAll(DirectoryCache cache) {
			
		}
		
		@Override
		public BlockModelBuilder singleTexture(String name, ResourceLocation parent, String textureKey, ResourceLocation texture) {
			return withExistingParent(name, parent).texture(textureKey, texture);
		}
		
		@Override
		public BlockModelBuilder withExistingParent(String name, ResourceLocation parent) {
			return new ModBlockModelBuilder(getBuilder(name)).parent(getExistingFile(parent));
		}
		
		@Override
		protected void registerModels() {
			
		}
	}
	
	static class ModItemModelProvider extends ItemModelProvider {
		ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
			super(generator, PrimalRework.MOD_ID, existingFileHelper);
		}
		
		@Override
		protected void generateAll(DirectoryCache cache) {
			
		}
		
		@Override
		protected void registerModels() {
			
		}
	}
}
