package mrunknown404.primalrework.stage;

import java.util.Objects;

import mrunknown404.primalrework.utils.IName;
import mrunknown404.primalrework.utils.helpers.WordH;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class Stage extends ForgeRegistryEntry<Stage> implements IName {
	public final String nameID;
	private final IFormattableTextComponent name;
	public final byte id;
	
	public Stage(byte id) {
		this("stage_" + id, id);
	}
	
	public Stage(String name, byte id) {
		this.nameID = name;
		this.name = WordH.translate("stage." + nameID + ".name");
		this.id = id;
	}
	
	@Override
	public IFormattableTextComponent getFancyName() {
		return name;
	}
	
	@Override
	public String getName() {
		return name.getString();
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
