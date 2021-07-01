package mrunknown404.primalrework.utils;

import java.util.List;

import net.minecraft.util.text.IFormattableTextComponent;

public interface IDescription {
	public abstract List<IFormattableTextComponent> getFancyDescription();
	public abstract List<String> getDescription();
}
