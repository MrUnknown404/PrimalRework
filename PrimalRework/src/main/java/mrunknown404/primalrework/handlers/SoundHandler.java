package mrunknown404.primalrework.handlers;

import mrunknown404.primalrework.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SoundHandler {
	public static SoundEvent ENTITY_PIGEON_AMBIENT;
	public static SoundEvent ENTITY_PIGEON_HURT;
	public static SoundEvent ENTITY_PIGEON_DEATH;
	
	public static void registerSounds() {
		ENTITY_PIGEON_AMBIENT = registerSound("entity.pigeon.ambient");
		ENTITY_PIGEON_HURT = registerSound("entity.pigeon.hurt");
		ENTITY_PIGEON_DEATH = registerSound("entity.pigeon.death");
	}
	
	private static SoundEvent registerSound(String name) {
		ResourceLocation loc = new ResourceLocation(Main.MOD_ID, name);
		SoundEvent se = new SoundEvent(loc);
		se.setRegistryName(name);
		ForgeRegistries.SOUND_EVENTS.register(se);
		return se;
	}
}
