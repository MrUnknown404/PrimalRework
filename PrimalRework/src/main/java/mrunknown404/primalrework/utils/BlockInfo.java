package mrunknown404.primalrework.utils;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;

public class BlockInfo {
	public static final RawBlockInfo PLANT = new RawBlockInfo(MaterialColor.PLANT, SoundType.GRASS, 0.2f, 0.1f, false, false, false, 100, 60, false, false, 0,
			PushReaction.DESTROY);
	public static final RawBlockInfo LEAVES = new RawBlockInfo(MaterialColor.PLANT, SoundType.GRASS, 0.8f, 0.2f, true, true, false, 60, 30, false, true, 0, PushReaction.DESTROY);
	public static final RawBlockInfo DIRT = new RawBlockInfo(MaterialColor.DIRT, SoundType.GRAVEL, 0.5f, 0.5f);
	public static final RawBlockInfo DRY_GRASS = new RawBlockInfo(MaterialColor.GRASS, SoundType.GRASS, 0.7f, 0.5f);
	public static final RawBlockInfo WET_GRASS = new RawBlockInfo(MaterialColor.GRASS, SoundType.WET_GRASS, 0.7f, 0.5f);
	public static final RawBlockInfo STONE = new RawBlockInfo(MaterialColor.STONE, SoundType.STONE, 2f, 6f);
	public static final RawBlockInfo METAL = new RawBlockInfo(MaterialColor.METAL, SoundType.METAL, 3f, 10f);
	public static final RawBlockInfo COAL = new RawBlockInfo(MaterialColor.STONE, SoundType.STONE, 3f, 10f, 8, 10);
	public static final RawBlockInfo WOOD = new RawBlockInfo(MaterialColor.WOOD, SoundType.WOOD, 1f, 2f, 20, 5);
	
	public static final UniqueRawBlockInfo WOOD_GROUND_ITEM = new UniqueRawBlockInfo(MaterialColor.NONE, SoundType.WOOD, 0, 0, false, false, false, 0, 0, false, false, 0,
			PushReaction.DESTROY);
	public static final UniqueRawBlockInfo STONE_GROUND_ITEM = new UniqueRawBlockInfo(MaterialColor.NONE, SoundType.STONE, 0, 0, false, false, false, 0, 0, false, false, 0,
			PushReaction.DESTROY);
	public static final UniqueRawBlockInfo CAMPFIRE = new UniqueRawBlockInfo(MaterialColor.WOOD, SoundType.WOOD, 1f, 0.5f, true, true, true, 0, 0, false, false, 0,
			PushReaction.DESTROY);
	public static final UniqueRawBlockInfo PRIMAL_CRAFTING_TABLE = new UniqueRawBlockInfo(MaterialColor.WOOD, SoundType.WOOD, 2f, 4f);
	public static final UniqueRawBlockInfo UNLIT_PRIMAL_TORCH = new UniqueRawBlockInfo(MaterialColor.WOOD, SoundType.WOOD, 0, 0, false, false, false, 0, 0, false, false, 0,
			PushReaction.DESTROY);
	public static final UniqueRawBlockInfo LIT_PRIMAL_TORCH = new UniqueRawBlockInfo(MaterialColor.WOOD, SoundType.WOOD, 0, 0, false, false, false, 0, 0, false, false, 8,
			PushReaction.DESTROY);
	public static final UniqueRawBlockInfo THATCH = new UniqueRawBlockInfo(MaterialColor.PLANT, SoundType.GRASS, 0.8f, 0.2f, 60, 30);
	
	private final I_RawBlockInfo info;
	private final Hardness hardness;
	
	private BlockInfo(I_RawBlockInfo info, Hardness hardness) {
		this.info = info;
		this.hardness = hardness;
	}
	
	public static BlockInfo of(I_RawBlockInfo info) {
		return new BlockInfo(info, Hardness.MEDIUM_0);
	}
	
	public static BlockInfo with(RawBlockInfo info, Hardness hardness) {
		return new BlockInfo(info, hardness);
	}
	
	public float getHardness() {
		return info.getHardness() * hardness.mod;
	}
	
	public float getBlast() {
		return info.getBlast() * hardness.mod;
	}
	
	public int getLight() {
		return info.getLight();
	}
	
	public int getFlammability() {
		return info.getFlammability();
	}
	
	public int getFireSpreadSpeed() {
		return info.getFireSpreadSpeed();
	}
	
	public boolean isRandomTick() {
		return info.isRandomTick();
	}
	
	public SoundType getSound() {
		return info.getSound();
	}
	
	public Material getMaterial() {
		return info.getMaterial();
	}
	
	private static class I_RawBlockInfo {
		private final float rawHardness, rawBlast;
		private final int light, flammability, fireSpreadSpeed;
		private final boolean isRandomTick;
		private final SoundType sound;
		private final Material material;
		
		protected I_RawBlockInfo(MaterialColor color, SoundType sound, float rawHardness, float rawBlast, boolean isSolid, boolean blocksMotion, boolean solidBlocking,
				int flammability, int fireSpreadSpeed, boolean replaceable, boolean isRandomTick, int light, PushReaction pushReaction) {
			this.sound = sound;
			this.rawHardness = rawHardness;
			this.rawBlast = rawBlast;
			this.flammability = flammability;
			this.fireSpreadSpeed = fireSpreadSpeed;
			this.isRandomTick = isRandomTick;
			this.light = light;
			
			this.material = new Material(color, false, isSolid, blocksMotion, solidBlocking, flammability != 0, replaceable, pushReaction);
		}
		
		public float getHardness() {
			return rawHardness;
		}
		
		public float getBlast() {
			return rawBlast;
		}
		
		public int getLight() {
			return light;
		}
		
		public int getFlammability() {
			return flammability;
		}
		
		public int getFireSpreadSpeed() {
			return fireSpreadSpeed;
		}
		
		public boolean isRandomTick() {
			return isRandomTick;
		}
		
		public SoundType getSound() {
			return sound;
		}
		
		public Material getMaterial() {
			return material;
		}
	}
	
	public static class UniqueRawBlockInfo extends I_RawBlockInfo {
		public UniqueRawBlockInfo(MaterialColor color, SoundType sound, float rawHardness, float rawBlast, boolean isSolid, boolean blocksMotion, boolean solidBlocking,
				int flammability, int fireSpreadSpeed, boolean replaceable, boolean isRandomTick, int light, PushReaction pushReaction) {
			super(color, sound, rawHardness, rawBlast, isSolid, blocksMotion, solidBlocking, flammability, fireSpreadSpeed, replaceable, isRandomTick, light, pushReaction);
		}
		
		public UniqueRawBlockInfo(MaterialColor color, SoundType sound, float rawHardness, float rawBlast, int flammability, int fireSpreadSpeed) {
			super(color, sound, rawHardness, rawBlast, true, true, true, flammability, fireSpreadSpeed, false, false, 0, PushReaction.NORMAL);
		}
		
		public UniqueRawBlockInfo(MaterialColor color, SoundType sound, float rawHardness, float rawBlast) {
			super(color, sound, rawHardness, rawBlast, true, true, true, 0, 0, false, false, 0, PushReaction.NORMAL);
		}
	}
	
	public static class RawBlockInfo extends I_RawBlockInfo {
		public RawBlockInfo(MaterialColor color, SoundType sound, float rawHardness, float rawBlast, boolean isSolid, boolean blocksMotion, boolean solidBlocking, int flammability,
				int fireSpreadSpeed, boolean replaceable, boolean isRandomTick, int light, PushReaction pushReaction) {
			super(color, sound, rawHardness, rawBlast, isSolid, blocksMotion, solidBlocking, flammability, fireSpreadSpeed, replaceable, isRandomTick, light, pushReaction);
		}
		
		public RawBlockInfo(MaterialColor color, SoundType sound, float rawHardness, float rawBlast, int flammability, int fireSpreadSpeed) {
			super(color, sound, rawHardness, rawBlast, true, true, true, flammability, fireSpreadSpeed, false, false, 0, PushReaction.NORMAL);
		}
		
		public RawBlockInfo(MaterialColor color, SoundType sound, float rawHardness, float rawBlast) {
			super(color, sound, rawHardness, rawBlast, true, true, true, 0, 0, false, false, 0, PushReaction.NORMAL);
		}
	}
	
	public enum Hardness {
		INSTANT(0),
		UNBREAKABLE(-1),
		
		//@formatter:off
		/** 0.2f */ SOFT_0     (0.2f),
		/** 0.5f */ SOFT_1     (0.5f),
		/** 0.7f */ SOFT_2     (0.7f),
		/** 1.0f */ MEDIUM_0   (1.0f),
		/** 1.2f */ MEDIUM_1   (1.2f),
		/** 1.5f */ MEDIUM_2   (1.5f),
		/** 2.0f */ HARD_0     (2.0f),
		/** 2.5f */ HARD_1     (2.5f),
		/** 3.0f */ HARD_2     (3.0f),
		/** 4.0f */ VERY_HARD_0(4.0f),
		/** 5.0f */ VERY_HARD_1(5.0f),
		/** 6.0f */ VERY_HARD_2(6.0f);
		//@formatter:on
		
		private final float mod;
		
		private Hardness(float mod) {
			this.mod = mod;
		}
	}
}
