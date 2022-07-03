package mrunknown404.primalrework.datagen;

import com.google.common.base.Preconditions;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

class TexturelessModelBuilder extends ModelBuilder<TexturelessModelBuilder> {
	TexturelessModelBuilder(ResourceLocation outputLocation, ExistingFileHelper existingFileHelper) {
		super(outputLocation, existingFileHelper);
	}
	
	@Override
	public TexturelessModelBuilder texture(String key, ResourceLocation texture) {
		Preconditions.checkNotNull(key, "Key must not be null");
		Preconditions.checkNotNull(texture, "Texture must not be null");
		textures.put(key, texture.toString());
		return this;
	}
	
	@Override
	public TexturelessModelBuilder parent(ModelFile parent) {
		Preconditions.checkNotNull(parent, "Parent must not be null");
		this.parent = parent;
		return this;
	}
}
