package mrunknown404.primalrework.inventory.container;

import java.util.List;
import java.util.Random;

import mrunknown404.primalrework.util.helpers.EnchantHelper;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;

public class ContainerEnchantmentReplace extends ContainerEnchantment {
	
	private final World world;
	private final BlockPos position;
	private final Random rand;
	
	public ContainerEnchantmentReplace(InventoryPlayer inv, World world, BlockPos pos) {
		super(inv, world, pos);
		this.world = world;
		this.position = pos;
		this.rand = new Random();
	}
	
	@Override
	public void onCraftMatrixChanged(IInventory inventoryIn) {
		if (inventoryIn == this.tableInventory) {
			ItemStack itemstack = inventoryIn.getStackInSlot(0);
			
			if (!itemstack.isEmpty() && EnchantHelper.isEnchantable(itemstack)) {
				if (!world.isRemote) {
					float power = 0;
					
					for (int j = -1; j <= 1; ++j) {
						for (int k = -1; k <= 1; ++k) {
							if ((j != 0 || k != 0) && world.isAirBlock(position.add(k, 0, j)) && world.isAirBlock(position.add(k, 1, j))) {
								power += ForgeHooks.getEnchantPower(world, position.add(k * 2, 0, j * 2));
								power += ForgeHooks.getEnchantPower(world, position.add(k * 2, 1, j * 2));
								
								if (k != 0 && j != 0) {
									power += ForgeHooks.getEnchantPower(world, position.add(k * 2, 0, j));
									power += ForgeHooks.getEnchantPower(world, position.add(k * 2, 1, j));
									power += ForgeHooks.getEnchantPower(world, position.add(k, 0, j * 2));
									power += ForgeHooks.getEnchantPower(world, position.add(k, 1, j * 2));
								}
							}
						}
					}
					
					rand.setSeed(xpSeed);
					
					for (int i1 = 0; i1 < 3; ++i1) {
						enchantLevels[i1] = EnchantHelper.calcItemStackEnchantability(rand, i1, (int) power, itemstack.getItem());
						enchantClue[i1] = -1;
						worldClue[i1] = -1;
						
						if (enchantLevels[i1] < i1 + 1) {
							enchantLevels[i1] = 0;
						}
						
						enchantLevels[i1] = ForgeEventFactory.onEnchantmentLevelSet(world, position, i1, (int) power, itemstack, enchantLevels[i1]);
					}
					
					for (int j1 = 0; j1 < 3; ++j1) {
						if (enchantLevels[j1] > 0) {
							List<EnchantmentData> list = getEnchantmentList(itemstack, j1, enchantLevels[j1]);
							
							if (list != null && !list.isEmpty()) {
								EnchantmentData enchantmentdata = list.get(rand.nextInt(list.size()));
								enchantClue[j1] = Enchantment.getEnchantmentID(enchantmentdata.enchantment);
								worldClue[j1] = enchantmentdata.enchantmentLevel;
							}
						}
					}
					
					detectAndSendChanges();
				}
			} else {
				for (int i = 0; i < 3; ++i) {
					enchantLevels[i] = 0;
					enchantClue[i] = -1;
					worldClue[i] = -1;
				}
			}
		}
	}
	
	private List<EnchantmentData> getEnchantmentList(ItemStack item, int enchantSlot, int level) {
		rand.setSeed(xpSeed + enchantSlot);
		List<EnchantmentData> list = EnchantHelper.buildVanillaEnchantmentList(rand, item, level);
		
		if (item.getItem() == Items.BOOK && list.size() > 1) {
			list.remove(rand.nextInt(list.size()));
		}
		
		return list;
	}
	
	@Override
	public boolean enchantItem(EntityPlayer player, int id) {
		ItemStack itemstack = tableInventory.getStackInSlot(0);
		ItemStack itemstack1 = tableInventory.getStackInSlot(1);
		int i = id + 1;
		
		if ((itemstack1.isEmpty() || itemstack1.getCount() < i) && !player.capabilities.isCreativeMode) {
			return false;
		} else if (enchantLevels[id] > 0 && !itemstack.isEmpty() &&
				(player.experienceLevel >= i && player.experienceLevel >= enchantLevels[id] || player.capabilities.isCreativeMode)) {
			if (!world.isRemote) {
				List<EnchantmentData> list = getEnchantmentList(itemstack, id, enchantLevels[id]);
				
				if (!list.isEmpty()) {
					player.onEnchant(itemstack, i);
					boolean flag = itemstack.getItem() == Items.BOOK;
					
					if (flag) {
						itemstack = new ItemStack(Items.ENCHANTED_BOOK);
						tableInventory.setInventorySlotContents(0, itemstack);
					}
					
					for (int j = 0; j < list.size(); ++j) {
						EnchantmentData enchantmentdata = list.get(j);
						
						if (flag) {
							ItemEnchantedBook.addEnchantment(itemstack, enchantmentdata);
						} else {
							itemstack.addEnchantment(enchantmentdata.enchantment, enchantmentdata.enchantmentLevel);
						}
					}
					
					if (!player.capabilities.isCreativeMode) {
						itemstack1.shrink(i);
						
						if (itemstack1.isEmpty()) {
							tableInventory.setInventorySlotContents(1, ItemStack.EMPTY);
						}
					}
					
					player.addStat(StatList.ITEM_ENCHANTED);
					
					if (player instanceof EntityPlayerMP) {
						CriteriaTriggers.ENCHANTED_ITEM.trigger((EntityPlayerMP) player, itemstack, i);
					}
					
					tableInventory.markDirty();
					xpSeed = player.getXPSeed();
					onCraftMatrixChanged(tableInventory);
					world.playSound(null, position, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, world.rand.nextFloat() * 0.1F + 0.9F);
				}
			}
			
			return true;
		} else {
			return false;
		}
	}
}
