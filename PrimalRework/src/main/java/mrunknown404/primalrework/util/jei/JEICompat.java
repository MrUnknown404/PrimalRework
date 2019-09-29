package mrunknown404.primalrework.util.jei;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import mrunknown404.primalrework.client.gui.GuiFirePit;
import mrunknown404.primalrework.init.ModBlocks;
import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.init.ModRecipes;
import mrunknown404.primalrework.inventory.ContainerFirePit;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.helpers.StageHelper;
import mrunknown404.primalrework.util.jei.category.DryingTableRecipeCategory;
import mrunknown404.primalrework.util.jei.category.FirePitRecipeCategory;
import mrunknown404.primalrework.util.jei.category.StagedCraftingRecipeCategory;
import mrunknown404.primalrework.util.jei.icantgetthistoworkwithoutcopyingeverything.PlayerRecipeTransferHandler;
import mrunknown404.primalrework.util.jei.icantgetthistoworkwithoutcopyingeverything.StackHelper;
import mrunknown404.primalrework.util.jei.wrappers.IRecipeWrapperBase;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;

@JEIPlugin
public class JEICompat implements IModPlugin {

	//TODO replace vanilla crafting GUI with custom GUI
	//TODO add vanilla, primal enchanting & loom category
	
	public static final Map<String, List<IRecipeWrapperBase<?>>> RECIPE_MAP = new HashMap<String, List<IRecipeWrapperBase<?>>>();
	
	public static IRecipeRegistry rr;
	public static IJeiHelpers helper;
	public static IIngredientRegistry ingReg;
	public static IIngredientBlacklist ib;
	public static StackHelper stackHelper;
	
	@Override
	public void registerItemSubtypes(ISubtypeRegistry reg) {
		stackHelper = new StackHelper(reg);
	}
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration reg) {
		IGuiHelper gui = reg.getJeiHelpers().getGuiHelper();
		
		reg.addRecipeCategories(new StagedCraftingRecipeCategory(gui));
		reg.addRecipeCategories(new FirePitRecipeCategory(gui));
		reg.addRecipeCategories(new DryingTableRecipeCategory(gui));
	}
	
	@Override
	public void register(IModRegistry reg) {
		IRecipeTransferRegistry recipeTransfer = reg.getRecipeTransferRegistry();
		helper = reg.getJeiHelpers();
		ingReg = reg.getIngredientRegistry();
		ib = helper.getIngredientBlacklist();
		ib.addIngredientToBlacklist(new ItemStack(Items.SPAWN_EGG));
		ib.addIngredientToBlacklist(new ItemStack(Items.POTIONITEM));
		ib.addIngredientToBlacklist(new ItemStack(Items.LINGERING_POTION));
		ib.addIngredientToBlacklist(new ItemStack(Items.SPLASH_POTION));
		ib.addIngredientToBlacklist(new ItemStack(Items.TIPPED_ARROW));
		
		reg.addIngredientInfo(new ItemStack(ModItems.FLINT_KNAPPED), VanillaTypes.ITEM, new TextComponentTranslation("jei.info.flint_knapped").getUnformattedText());
		reg.addIngredientInfo(new ItemStack(ModItems.FLINT_POINT), VanillaTypes.ITEM, new TextComponentTranslation("jei.info.flint_point").getUnformattedText());
		reg.addIngredientInfo(new ItemStack(ModItems.BONE_SHARD), VanillaTypes.ITEM, new TextComponentTranslation("jei.info.bone_shard").getUnformattedText());
		reg.addIngredientInfo(new ItemStack(ModItems.BARK), VanillaTypes.ITEM, new TextComponentTranslation("jei.info.bark").getUnformattedText());
		reg.addIngredientInfo(new ItemStack(ModItems.WET_HIDE), VanillaTypes.ITEM, new TextComponentTranslation("jei.info.wet_hide").getUnformattedText());
		reg.addIngredientInfo(new ItemStack(ModItems.WET_TANNED_HIDE), VanillaTypes.ITEM, new TextComponentTranslation("jei.info.wet_tanned_hide").getUnformattedText());
		
		reg.addRecipes(ModRecipes.getWrappedStageRecipes(), ModRecipes.CATEGORY_STAGED_CRAFTING);
		reg.addRecipes(ModRecipes.getWrappedFirePitRecipes(), ModRecipes.CATEGORY_FIRE_PIT);
		reg.addRecipes(ModRecipes.getWrappedDryingTableRecipes(), ModRecipes.CATEGORY_DRYING_TABLE);
		
		reg.addRecipeClickArea(GuiFirePit.class, 102, 9, 3, 25, ModRecipes.CATEGORY_FIRE_PIT);
		//reg.addRecipeClickArea(GuiInventory.class, 137, 29, 10, 13, ModRecipes.CATEGORY_STAGED_CRAFTING);
		//reg.addRecipeClickArea(GuiCrafting.class, 88, 32, 28, 23, ModRecipes.CATEGORY_STAGED_CRAFTING);
		
		reg.addRecipeCatalyst(new ItemStack(Blocks.CRAFTING_TABLE), ModRecipes.CATEGORY_STAGED_CRAFTING);
		reg.addRecipeCatalyst(new ItemStack(ModBlocks.CRAFTING_STUMP), ModRecipes.CATEGORY_STAGED_CRAFTING);
		reg.addRecipeCatalyst(new ItemStack(ModBlocks.FIRE_PIT), ModRecipes.CATEGORY_FIRE_PIT);
		reg.addRecipeCatalyst(new ItemStack(ModBlocks.DRYING_TABLE), ModRecipes.CATEGORY_DRYING_TABLE);
		
		recipeTransfer.addRecipeTransferHandler(ContainerWorkbench.class, ModRecipes.CATEGORY_STAGED_CRAFTING, 1, 9, 10, 36);
		recipeTransfer.addRecipeTransferHandler(new PlayerRecipeTransferHandler(helper.recipeTransferHandlerHelper()), ModRecipes.CATEGORY_STAGED_CRAFTING);
		recipeTransfer.addRecipeTransferHandler(ContainerFirePit.class, ModRecipes.CATEGORY_FIRE_PIT, FirePitRecipeCategory.SLOT_INPUT, 1, FirePitRecipeCategory.SLOT_INPUT, 36);
	}
	
	@Override
	public void onRuntimeAvailable(IJeiRuntime run) {
		rr = run.getRecipeRegistry();
		
		RECIPE_MAP.put(ModRecipes.CATEGORY_FIRE_PIT, rr.getRecipeWrappers(rr.getRecipeCategory(ModRecipes.CATEGORY_FIRE_PIT)));
		RECIPE_MAP.put(ModRecipes.CATEGORY_STAGED_CRAFTING, rr.getRecipeWrappers(rr.getRecipeCategory(ModRecipes.CATEGORY_STAGED_CRAFTING)));
		
		rr.hideRecipeCategory(VanillaRecipeCategoryUid.CRAFTING);
		rr.hideRecipeCategory(VanillaRecipeCategoryUid.FUEL);
		rr.hideRecipeCategory(VanillaRecipeCategoryUid.ANVIL);
		
		setupRecipesAndItems();
	}
	
	public static void setupRecipesAndItems() {
		Minecraft.getMinecraft().addScheduledTask(new Runnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				Iterator<Entry<String, List<IRecipeWrapperBase<?>>>> it = RECIPE_MAP.entrySet().iterator();
				
				while (it.hasNext()) {
					Entry<String, List<IRecipeWrapperBase<?>>> pair = it.next();
					
					for (IRecipeWrapperBase<?> wrap : pair.getValue()) {
						if (StageHelper.hasAccessToStage(wrap.getRecipe().getStage())) {
							rr.unhideRecipe(wrap, pair.getKey());
						} else {
							rr.hideRecipe(wrap, pair.getKey());
						}
					}
				}
				
				Iterator<Entry<EnumStage, List<ItemStack>>> it2 = StageHelper.ITEM_STAGE_MAP.entrySet().iterator();
				
				while (it2.hasNext()) {
					Entry<EnumStage, List<ItemStack>> pair = it2.next();
					List<ItemStack> hidden = new ArrayList<ItemStack>();
					List<ItemStack> visible = new ArrayList<ItemStack>();
					
					for (ItemStack item : pair.getValue()) {
						if (StageHelper.hasAccessToStage(pair.getKey())) {
							visible.add(item);
						} else {
							hidden.add(item);
						}
					}
					
					if (!hidden.isEmpty()) {
						ingReg.removeIngredientsAtRuntime(VanillaTypes.ITEM, hidden);
					}
					if (!visible.isEmpty()) {
						ingReg.addIngredientsAtRuntime(VanillaTypes.ITEM, visible);
					}
					
					List<EnchantmentData> enchs = new ArrayList<EnchantmentData>();
					for (Enchantment en : Enchantment.REGISTRY) {
						for (int i = 1; i < en.getMaxLevel() + 1; i++) {
							enchs.add(new EnchantmentData(en, i));
						}
					}
					
					if (StageHelper.hasAccessToStage(EnumStage.do_later)) { //TODO change to enchant later
						ingReg.addIngredientsAtRuntime(VanillaTypes.ENCHANT, enchs);
					} else {
						ingReg.removeIngredientsAtRuntime(VanillaTypes.ENCHANT, enchs);
					}
				}
			}
		});
	}
}
