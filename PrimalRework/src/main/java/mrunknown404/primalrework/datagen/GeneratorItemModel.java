package mrunknown404.primalrework.datagen;

import java.util.HashMap;
import java.util.Map;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.init.Registry;
import mrunknown404.primalrework.items.utils.StagedItem;
import mrunknown404.primalrework.items.utils.StagedItem.ItemType;
import mrunknown404.primalrework.utils.enums.EnumToolMaterial;
import mrunknown404.primalrework.utils.enums.EnumToolType;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

class GeneratorItemModel extends ModelProvider<TexturelessModelBuilder> {
	private static final Map<ItemType, ModelFile> ITEM_TYPE_MAP = new HashMap<ItemType, ModelFile>();
	
	GeneratorItemModel(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, PrimalRework.MOD_ID, ITEM_FOLDER, TexturelessModelBuilder::new, existingFileHelper);
		
		for (ItemType type : ItemType.values()) {
			if (type != ItemType.none && type != ItemType.block && type != ItemType.itemblock) {
				ITEM_TYPE_MAP.put(type, getExistingFile(mcLoc("item/" + type.name())));
			}
		}
	}
	
	@Override
	protected void registerModels() {
		for (RegistryObject<Item> regItem : Registry.ITEMS.getEntries()) {
			StagedItem i = (StagedItem) regItem.get();
			if (i.getItemType() == ItemType.none) {
				continue;
			}
			
			if (i.getItemType() == ItemType.block) {
				getBuilder(i.getRegName()).parent(new ModelFile.UncheckedModelFile(extendWithFolder(modLoc("block/" + i.getRegName()))));
			} else {
				String id = "";
				String name = i.getRegName();
				if (i.toolType != EnumToolType.none) {
					if (i.toolMat == EnumToolMaterial.wood || i.toolMat == EnumToolMaterial.stone || i.toolMat == EnumToolMaterial.gold || i.toolMat == EnumToolMaterial.iron ||
							i.toolMat == EnumToolMaterial.diamond || i.toolMat == EnumToolMaterial.netherite) {
						if (i.toolType == EnumToolType.sword || i.toolType == EnumToolType.shovel || i.toolType == EnumToolType.pickaxe || i.toolType == EnumToolType.axe ||
								i.toolType == EnumToolType.hoe) {
							id = "minecraft:";
							if (i.toolMat == EnumToolMaterial.wood || i.toolMat == EnumToolMaterial.gold) {
								String[] split = name.split("_");
								name = split[0] + "en_" + split[1];
							}
						}
					}
				}
				
				if (i.getItemType() == ItemType.itemblock) {
					getBuilder(i.getRegName()).parent(ITEM_TYPE_MAP.get(ItemType.generated)).texture("layer0", id + "block/" + name);
				} else {
					getBuilder(i.getRegName()).parent(ITEM_TYPE_MAP.get(i.getItemType())).texture("layer0", id + "item/" + name);
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
