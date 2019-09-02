package mrunknown404.primalrework.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ModCreativeTabs {
	public static final CreativeTabs PRIMALREWORK_ITEMS = (new CreativeTabs("primalrework_items") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ModItems.PLANT_FIBER);
		}
		
		@Override
		public boolean hasSearchBar() {
			return true;
		}
	}).setBackgroundImageName("item_search.png");
	
	public static final CreativeTabs PRIMALREWORK_TOOLS = (new CreativeTabs("primalrework_tools") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ModItems.FLINT_KNIFE);
		}
		
		@Override
		public boolean hasSearchBar() {
			return true;
		}
	}).setBackgroundImageName("item_search.png");
	
	public static final CreativeTabs PRIMALREWORK_BLOCKS = (new CreativeTabs("primalrework_blocks") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ModBlocks.ROCK);
		}
		
		@Override
		public boolean hasSearchBar() {
			return true;
		}
	}).setBackgroundImageName("item_search.png");
}
