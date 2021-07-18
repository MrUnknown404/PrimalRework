package mrunknown404.primalrework.registries;

import java.util.function.Supplier;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.utils.enums.EnumAlloy;
import mrunknown404.primalrework.utils.enums.EnumOreValue;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class PRItemGroups {
	public static final ModItemGroup BLOCKS = new ModItemGroup("blocks", () -> new ItemStack(PRBlocks.THATCH.get()));
	public static final ModItemGroup MACHINES = new ModItemGroup("machines", () -> new ItemStack(PRBlocks.CAMPFIRE.get()));
	public static final ModItemGroup ORES = new ModItemGroup("ores", () -> new ItemStack(PRBlocks.getOreOrBlock(EnumAlloy.copper, EnumOreValue.perfect)));
	public static final ModItemGroup ITEMS = new ModItemGroup("items", () -> new ItemStack(PRItems.PLANT_FIBER.get()));
	public static final ModItemGroup TOOLS = new ModItemGroup("tools", () -> new ItemStack(PRTools.CLAY_SHOVEL.get()));
	public static final ModItemGroup NUGGETS = new ModItemGroup("nuggets", () -> new ItemStack(PRItems.getOre(EnumAlloy.copper, EnumOreValue.perfect)));
	public static final ModItemGroup FOOD = new ModItemGroup("food", () -> new ItemStack(PRItems.DOUGH.get()));
	
	public static class ModItemGroup extends ItemGroup {
		private Supplier<ItemStack> displayStack;
		
		@SuppressWarnings("deprecation")
		public ModItemGroup(String name, Supplier<ItemStack> displayStack) {
			super(PrimalRework.MOD_ID + "_" + name);
			this.displayStack = displayStack;
			setBackgroundSuffix("item_search.png");
		}
		
		@Override
		public ItemStack makeIcon() {
			return displayStack.get();
		}
		
		@Override
		public boolean hasSearchBar() {
			return true;
		}
	}
}
