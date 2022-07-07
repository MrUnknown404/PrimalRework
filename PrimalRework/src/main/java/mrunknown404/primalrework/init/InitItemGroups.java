package mrunknown404.primalrework.init;

import java.util.function.Supplier;

import mrunknown404.primalrework.PrimalRework;
import mrunknown404.primalrework.api.registry.ROISIProvider;
import mrunknown404.primalrework.utils.enums.RawPart;
import mrunknown404.primalrework.utils.enums.ToolType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class InitItemGroups {
	public static final ModItemGroup BLOCKS = new ModItemGroup("blocks", () -> InitBlocks.THATCH);
	public static final ModItemGroup MACHINES = new ModItemGroup("machines", () -> InitBlocks.CAMPFIRE);
	public static final ModItemGroup ITEMS = new ModItemGroup("items", () -> InitItems.PLANT_FIBER);
	public static final ModItemGroup RAW_PARTS = new ModItemGroup("raw_parts", () -> InitItems.getRawPart(InitMetals.COPPER.get(), RawPart.PLATE));
	public static final ModItemGroup TOOL_PARTS = new ModItemGroup("tool_parts", () -> InitItems.getToolPart(InitMetals.COPPER.get(), ToolType.PICKAXE));
	public static final ModItemGroup TOOLS = new ModItemGroup("tools", () -> InitItems.CLAY_SHOVEL);
	public static final ModItemGroup FOOD = new ModItemGroup("food", () -> InitItems.DOUGH);
	public static final ModItemGroup ORES = new ModItemGroup("ores", () -> InitItems.PLANT_FIBER);
	
	public static class ModItemGroup extends ItemGroup {
		private Supplier<ItemStack> displayStack;
		
		@SuppressWarnings("deprecation")
		public ModItemGroup(String name, Supplier<ROISIProvider<?>> item) {
			super(PrimalRework.MOD_ID + "_" + name);
			this.displayStack = () -> new ItemStack(item.get().getStagedItem());
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
