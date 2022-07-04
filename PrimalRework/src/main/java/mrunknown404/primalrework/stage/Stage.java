package mrunknown404.primalrework.stage;

import java.util.Objects;

import mrunknown404.primalrework.api.utils.IStageProvider;
import mrunknown404.primalrework.utils.helpers.WordH;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class Stage extends ForgeRegistryEntry<Stage> implements IStageProvider {
	private final Lazy<IFormattableTextComponent> name = Lazy.of(() -> WordH.translate("stage." + getNameID() + ".name"));
	public final byte id;
	
	public Stage(byte id) {
		this.id = id;
	}
	
	public IFormattableTextComponent getFancyName() {
		return name.get();
	}
	
	public String getName() {
		return name.get().getString();
	}
	
	public String getNameID() {
		return getRegistryName().getPath();
	}
	
	@Override
	public Stage getStage() {
		return this;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this != obj || obj == null || getClass() != obj.getClass()) {
			return false;
		}
		
		Stage other = (Stage) obj;
		return id == other.id;
	}
}
