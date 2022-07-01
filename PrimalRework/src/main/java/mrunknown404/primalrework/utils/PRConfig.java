package mrunknown404.primalrework.utils;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

public class PRConfig {
	public static class Client {
		public final BooleanValue harvestDisplay_isEnabled;
		public final BooleanValue harvestDisplay_showIcon;
		public final BooleanValue harvestDisplay_showHardnessAndBlast;
		public final BooleanValue harvestDisplay_showRequiredTool;
		
		public final BooleanValue tooltips_showAtomicSymbolsInTooltips;
		public final BooleanValue tooltips_showStackSize;
		public final BooleanValue tooltips_showFoodNutrients;
		public final BooleanValue tooltips_showBlockInfo;
		public final BooleanValue tooltips_showDurability;
		public final BooleanValue tooltips_showToolStats;
		public final BooleanValue tooltips_showStage;
		
		public Client(ForgeConfigSpec.Builder builder) {
			builder.push("Harvest Display");
			harvestDisplay_isEnabled = builder.define("is enabled", true);
			harvestDisplay_showIcon = builder.define("show icon", true);
			harvestDisplay_showHardnessAndBlast = builder.define("show hardness and blast resistance", true);
			harvestDisplay_showRequiredTool = builder.define("show required tool", true);
			builder.pop();
			builder.push("Tooltips");
			tooltips_showAtomicSymbolsInTooltips = builder.define("show atomic symbols", true);
			tooltips_showStackSize = builder.define("show stack size", true);
			tooltips_showFoodNutrients = builder.define("show food nutrients", true);
			tooltips_showBlockInfo = builder.define("show block info", true);
			tooltips_showDurability = builder.define("show durability", true);
			tooltips_showToolStats = builder.define("show tool stats", true);
			tooltips_showStage = builder.define("show stage", true);
			builder.pop();
		}
	}
	
	public static final Client CLIENT;
	public static final ForgeConfigSpec CLIENT_SPEC;
	
	static {
		Pair<Client, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(Client::new);
		CLIENT = clientSpecPair.getLeft();
		CLIENT_SPEC = clientSpecPair.getRight();
	}
}
