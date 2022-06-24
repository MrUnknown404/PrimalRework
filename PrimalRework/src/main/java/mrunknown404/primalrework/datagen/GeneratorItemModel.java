package mrunknown404.primalrework.datagen;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.items.utils.IColoredItem;
import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.registries.PRRegistry;
import mrunknown404.primalrework.utils.enums.Metal;
import mrunknown404.primalrework.utils.enums.ToolMaterial;
import mrunknown404.primalrework.utils.enums.ToolType;
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
			
			final String id = i.usesVanillaNamespace() ? "minecraft" : PrimalRework.MOD_ID;
			String name = i.getRegName();
			if (i.toolType != ToolType.NONE && i.toolMat == ToolMaterial.WOOD && (i.toolType == ToolType.SHOVEL || i.toolType == ToolType.AXE)) {
				String[] split = name.split("_");
				name = split[0] + "en_" + split[1];
			} else if (name.equals("tall_grass")) {
				name = "grass";
			}
			
			if (i instanceof IColoredItem) {
				if (((IColoredItem) i).getMetal() != Metal.UNKNOWN) {
					name = "template_" + name.substring(name.indexOf('_') + 1);
				}
			}
			
			switch (i.getItemType()) {
				case block:
					getBuilder(i.getRegName()).parent(new UncheckedModelFile(extendWithFolder(new ResourceLocation(id, "block/" + i.getRegName()))));
					break;
				case generated:
					getBuilder(i.getRegName()).parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", new ResourceLocation(id, "item/" + name));
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
