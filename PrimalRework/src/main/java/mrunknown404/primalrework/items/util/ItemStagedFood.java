package mrunknown404.primalrework.items.util;

import java.util.List;

import mrunknown404.primalrework.init.ModCreativeTabs;
import mrunknown404.primalrework.util.enums.EnumStage;
import mrunknown404.primalrework.util.enums.EnumToolMaterial;
import mrunknown404.primalrework.util.enums.EnumToolType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemStagedFood extends ItemFood implements IItemStaged<ItemStagedFood> {
	
	protected final EnumStage stage;
	protected final int eatTime;
	private int amountOfTooltops = 0;
	
	public ItemStagedFood(String name, int maxStackSize, EnumStage stage, int healAmount, float saturationModifier, int eatTime, boolean isWolfFood) {
		super(healAmount, saturationModifier, isWolfFood);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(ModCreativeTabs.PRIMALREWORK_FOOD);
		setMaxStackSize(maxStackSize);
		this.stage = stage;
		this.eatTime = eatTime;
		
		addToModList(this);
	}
	
	public ItemStagedFood(String name, EnumStage stage, int healAmount, float saturationModifier) {
		this(name, 64, stage, healAmount, saturationModifier, 32, false);
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return eatTime;
	}
	
	@Override
	public ItemStagedFood setAmountOfTooltops(int amountOfTooltops) {
		this.amountOfTooltops = amountOfTooltops;
		return this;
	}
	
	@Override
	public int getAmountOfTooltips() {
		return amountOfTooltops;
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.addAll(getTooltips(getUnlocalizedName()));
	}
	
	@Override
	public EnumToolType getToolType() {
		return EnumToolType.none;
	}
	
	@Override
	public EnumToolMaterial getHarvestLevel() {
		return EnumToolMaterial.hand;
	}
	
	@Override
	public EnumStage getStage() {
		return stage;
	}
}
