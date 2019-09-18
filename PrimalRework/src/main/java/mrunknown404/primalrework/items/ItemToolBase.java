package mrunknown404.primalrework.items;

import java.util.List;

import mrunknown404.primalrework.init.ModCreativeTabs;
import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.util.harvest.EnumToolMaterial;
import mrunknown404.primalrework.util.harvest.EnumToolType;
import mrunknown404.primalrework.util.harvest.HarvestHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class ItemToolBase extends ItemTool implements IItemBase {

	private final EnumToolType toolType;
	private final EnumToolMaterial harvestLevel;
	
	public ItemToolBase(String name, EnumToolType type, EnumToolMaterial level) {
		super(getDamage(type, level), type.swingSpeed, ModItems.MATERIAL, null);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(ModCreativeTabs.PRIMALREWORK_TOOLS);
		setMaxStackSize(1);
		setMaxDamage(level.durability);
		
		this.toolType = type;
		this.harvestLevel = level;
		
		addToModList(this);
	}
	
	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		ItemStack s = stack.copy();
		s.setItemDamage(stack.getItemDamage() + 1);
		return s;
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.addAll(getTooltip(getUnlocalizedName()));
	}
	
	private static float getDamage(EnumToolType type, EnumToolMaterial level) {
		return type == EnumToolType.hoe ? 0 : type.baseDamage + level.extraDamage;
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (toolType == EnumToolType.knife || toolType == EnumToolType.sword || toolType == EnumToolType.axe) {
			stack.damageItem(1, attacker);
		} else {
			stack.damageItem(3, attacker);
		}
		
		return true;
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
		if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0) {
			if (HarvestHelper.canBreak(state.getBlock(), stack.getItem())) {
				stack.damageItem(1, entityLiving);
			} else {
				stack.damageItem(3, entityLiving);
			}
		}
		
		return true;
	}
	
	@SuppressWarnings("incomplete-switch")
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (toolType == EnumToolType.shovel) {
			ItemStack itemstack = player.getHeldItem(hand);
			
			if (!player.canPlayerEdit(pos.offset(facing), facing, itemstack)) {
				return EnumActionResult.FAIL;
			} else {
				IBlockState iblockstate = world.getBlockState(pos);
				Block block = iblockstate.getBlock();
				
				if (facing != EnumFacing.DOWN && world.getBlockState(pos.up()).getMaterial() == Material.AIR && block == Blocks.GRASS) {
					IBlockState iblockstate1 = Blocks.GRASS_PATH.getDefaultState();
					world.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
					
					if (!world.isRemote) {
						world.setBlockState(pos, iblockstate1, 11);
						itemstack.damageItem(1, player);
					}
					
					return EnumActionResult.SUCCESS;
				} else {
					return EnumActionResult.PASS;
				}
			}
		} else if (toolType == EnumToolType.hoe) {
			ItemStack itemstack = player.getHeldItem(hand);
			
			if (!player.canPlayerEdit(pos.offset(facing), facing, itemstack)) {
				return EnumActionResult.FAIL;
			} else {
				int hook = ForgeEventFactory.onHoeUse(itemstack, player, world, pos);
				if (hook != 0) {
					return hook > 0 ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
				}
				
				IBlockState iblockstate = world.getBlockState(pos);
				Block block = iblockstate.getBlock();
				
				if (facing != EnumFacing.DOWN && world.isAirBlock(pos.up())) {
					if (block == Blocks.GRASS || block == Blocks.GRASS_PATH) {
						setBlock(itemstack, player, world, pos, Blocks.FARMLAND.getDefaultState());
						return EnumActionResult.SUCCESS;
					}
					
					if (block == Blocks.DIRT) {
						switch ((BlockDirt.DirtType)iblockstate.getValue(BlockDirt.VARIANT)) {
						case DIRT:
							setBlock(itemstack, player, world, pos, Blocks.FARMLAND.getDefaultState());
							return EnumActionResult.SUCCESS;
						case COARSE_DIRT:
							setBlock(itemstack, player, world, pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
							return EnumActionResult.SUCCESS;
						}
					}
				}
				
				return EnumActionResult.PASS;
			}
		}
		
		return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
	}
	
	private void setBlock(ItemStack stack, EntityPlayer player, World world, BlockPos pos, IBlockState state) {
		world.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
		
		if (!world.isRemote) {
			world.setBlockState(pos, state, 11);
			stack.damageItem(1, player);
		}
	}
	
	@Override
	public boolean canDisableShield(ItemStack stack, ItemStack shield, EntityLivingBase entity, EntityLivingBase attacker) {
		return toolType == EnumToolType.axe;
	}
	
	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		return harvestLevel.speed;
	}
	
	@Override
	public int getItemEnchantability() {
		return harvestLevel.enchantability;
	}
	
	@Override
	public EnumToolType getToolType() {
		return toolType;
	}
	
	@Override
	public EnumToolMaterial getHarvestLevel() {
		return harvestLevel;
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return getItemEnchantability() != 0;
	}
}
