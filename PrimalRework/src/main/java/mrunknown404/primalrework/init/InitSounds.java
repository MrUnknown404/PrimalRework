package mrunknown404.primalrework.init;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class InitSounds {
	public static final List<SoundEvent> SOUNDS = new ArrayList<SoundEvent>();
	
	public static final SoundEvent ENTITY_PIGEON_AMBIENT = createSound("entity.pigeon.ambient");
	public static final SoundEvent ENTITY_PIGEON_HURT = createSound("entity.pigeon.hurt");
	public static final SoundEvent ENTITY_PIGEON_DEATH = createSound("entity.pigeon.death");
	
	private static SoundEvent createSound(String name) {
		return new SoundEvent(new ResourceLocation(Main.MOD_ID, name)).setRegistryName(Main.MOD_ID, name);
	}
}
