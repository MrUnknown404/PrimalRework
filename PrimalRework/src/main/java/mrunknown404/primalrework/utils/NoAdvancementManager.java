package mrunknown404.primalrework.utils;

import java.util.Map;

import com.google.gson.JsonElement;

import net.minecraft.advancements.AdvancementManager;
import net.minecraft.loot.LootPredicateManager;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

public class NoAdvancementManager extends AdvancementManager {
	
	public NoAdvancementManager(LootPredicateManager manager) {
		super(manager);
	}
	
	@Override
	protected void apply(Map<ResourceLocation, JsonElement> map, IResourceManager manager, IProfiler profile) {
		
	}
}
