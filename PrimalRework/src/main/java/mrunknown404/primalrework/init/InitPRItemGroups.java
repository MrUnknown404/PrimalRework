package mrunknown404.primalrework.init;

import java.util.function.Supplier;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.utils.enums.RawPart;
import mrunknown404.primalrework.utils.enums.ToolType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class InitPRItemGroups {
	public static final ModItemGroup BLOCKS = new ModItemGroup("blocks", () -> new ItemStack(InitBlocks.THATCH.get()));
	public static final ModItemGroup MACHINES = new ModItemGroup("machines", () -> new ItemStack(InitBlocks.CAMPFIRE.get()));
	public static final ModItemGroup ITEMS = new ModItemGroup("items", () -> new ItemStack(InitItems.PLANT_FIBER.get()));
	public static final ModItemGroup RAW_PARTS = new ModItemGroup("raw_parts", () -> new ItemStack(InitItems.getRawPart(InitMetals.COPPER.get(), RawPart.PLATE)));
	public static final ModItemGroup TOOL_PARTS = new ModItemGroup("tool_parts", () -> new ItemStack(InitItems.getToolPart(InitMetals.COPPER.get(), ToolType.PICKAXE)));
	public static final ModItemGroup TOOLS = new ModItemGroup("tools", () -> new ItemStack(InitItems.CLAY_SHOVEL.get()));
	public static final ModItemGroup FOOD = new ModItemGroup("food", () -> new ItemStack(InitItems.DOUGH.get()));
	public static final ModItemGroup ORES = new ModItemGroup("ores", () -> new ItemStack(InitItems.PLANT_FIBER.get()));
	
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
