package mrunknown404.primalrework.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import mrunknown404.primalrework.Main;
import net.minecraft.advancements.Advancement.Builder;
import net.minecraft.advancements.AdvancementList;
import net.minecraft.util.ResourceLocation;

public class ModAdvancementList extends AdvancementList {
	@Override
	public void loadAdvancements(Map<ResourceLocation, Builder> advancements) {
		Map<ResourceLocation, Builder> map = new HashMap<ResourceLocation, Builder>();
		Iterator<Entry<ResourceLocation, Builder>> it = advancements.entrySet().iterator();
		while (it.hasNext()) {
			Entry<ResourceLocation, Builder> pair = it.next();
			if (pair.getKey().getResourceDomain().equalsIgnoreCase(Main.MOD_ID)) {
				map.put(pair.getKey(), pair.getValue());
			}
		}
		
		super.loadAdvancements(map);
	}
}
