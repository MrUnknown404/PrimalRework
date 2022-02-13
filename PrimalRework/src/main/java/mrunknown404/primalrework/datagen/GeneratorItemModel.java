package mrunknown404.primalrework.datagen;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.items.SIIngot;
import mrunknown404.primalrework.items.SIMetalPart;
import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.registries.PRRegistry;
import mrunknown404.primalrework.utils.enums.EnumToolMaterial;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

class GeneratorItemModel extends ModelProvider<TexturelessModelBuilder> {
	GeneratorItemModel(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, PrimalRework.MOD_ID, ITEM_FOLDER, TexturelessModelBuilder::new, existingFileHelper);
	}
	
	@Override
	protected void registerModels() {
		for (RegistryObject<Item> regItem : PRRegistry.getItems()) {
			StagedItem i = (StagedItem) regItem.get();
			
			final String id = i.overridesVanilla() ? "minecraft" : PrimalRework.MOD_ID;
			String name = i.getRegName();
			if (i.toolType != EnumToolType.none && i.toolMat == EnumToolMaterial.wood && (i.toolType == EnumToolType.shovel || i.toolType == EnumToolType.axe)) {
				String[] split = name.split("_");
				name = split[0] + "en_" + split[1];
			}
			
			switch (i.getItemType()) {
				case block:
					getBuilder(i.getRegName()).parent(new UncheckedModelFile(extendWithFolder(new ResourceLocation(id, "block/" + i.getRegName()))));
					break;
				case generated:
					getBuilder(i.getRegName()).parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", new ResourceLocation(id, "item/" + name));
					break;
				case generated_colored:
					if (i instanceof SIIngot) {
						getBuilder(i.getRegName()).parent(getExistingFile(mcLoc("item/generated"))).texture("layer0",
								new ResourceLocation(id, "item/template_" + (((SIIngot) i).isNugget ? "nugget" : "ingot")));
					} else if (i instanceof SIMetalPart) {
						getBuilder(i.getRegName()).parent(getExistingFile(mcLoc("item/generated"))).texture("layer0",
								new ResourceLocation(id, "item/template_" + ((SIMetalPart) i).part));
					} else {
						System.err.println("Error! Class not supported!");
					}
					break;
				case handheld:
					getBuilder(i.getRegName()).parent(getExistingFile(mcLoc("item/handheld"))).texture("layer0", new ResourceLocation(id, "item/" + name));
					break;
				case handheld_rod:
					getBuilder(i.getRegName()).parent(getExistingFile(mcLoc("item/handheld_rod"))).texture("layer0", new ResourceLocation(id, "item/" + name));
					break;
				case itemblock:
					getBuilder(i.getRegName()).parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", new ResourceLocation(id, "block/" + name));
					break;
				case none: {
					break;
				}
			}
		}
	}
	
	@Override
	public String getName() {
		return "Item Models: " + modid;
	}
	
	@Override
	public ModelFile.ExistingModelFile getExistingFile(ResourceLocation path) {
		return new ModelFile.ExistingModelFile(extendWithFolder(path), existingFileHelper);
	}
	
	private ResourceLocation extendWithFolder(ResourceLocation rl) {
		return rl.getPath().contains("/") ? rl : new ResourceLocation(rl.getNamespace(), folder + "/" + rl.getPath());
	}
}
