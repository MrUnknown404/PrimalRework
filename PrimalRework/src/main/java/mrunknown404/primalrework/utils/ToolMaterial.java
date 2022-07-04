package mrunknown404.primalrework.utils;

import net.minecraftforge.registries.ForgeRegistryEntry;

public class ToolMaterial extends ForgeRegistryEntry<ToolMaterial> {
	public final int level, durability;
	public final float speed, extraDamage;
	
	public ToolMaterial(int level, int durability, float speed, float extraDamage) {
		this.level = level;
		this.durability = durability * 8;
		this.speed = speed;
		this.extraDamage = extraDamage;
	}
	
	@Override
	public String toString() {
		return getRegistryName().getPath();
	}
}
