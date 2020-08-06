package mrunknown404.primalrework.inventory.container;

import java.util.List;
import java.util.Random;

import mrunknown404.primalrework.init.ModItems;
import mrunknown404.primalrework.inventory.slot.SlotMagicDust;
import mrunknown404.primalrework.inventory.slot.SlotPrimalEnchantable;
import mrunknown404.primalrework.tileentity.TileEntityPrimalEnchanting;
import mrunknown404.primalrework.util.helpers.EnchantHelper;
import mrunknown404.unknownlibs.inventory.container.IEasyTransferStack;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ContainerPrimalEnchanting extends Container implements IEasyTransferStack {
	
	private final TileEntityPrimalEnchanting te;
	private final World world;
	private final Random rand;
	
	public int xpSeed;
	
	public ContainerPrimalEnchanting(InventoryPlayer player, TileEntityPrimalEnchanting te) {
		this.te = te.setContainer(this);
		this.world = te.getWorld();
		this.rand = new Random();
		this.xpSeed = player.player.getXPSeed();
		
		addSlotToContainer(new SlotPrimalEnchantable(te, 0, 80, 31));
		addSlotToContainer(new SlotMagicDust(te, 1, 80, 52));
		
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				addSlotToContainer(new Slot(player, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
			}
		}
		
		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(player, i, 8 + i * 18, 142));
		}
	}
	
	@Override
	public void onCraftMatrixChanged(IInventory inv) {
		if (inv == te) {
			ItemStack itemstack = inv.getStackInSlot(0);
			
			if (!itemstack.isEmpty() && EnchantHelper.isEnchantable(itemstack)) {
				if (!world.isRemote) {
					rand.setSeed(xpSeed);
					detectAndSendChanges();
				}
			}
		}
	}
	
	private List<EnchantmentData> getEnchantmentList(ItemStack item, int level) {
		rand.setSeed(xpSeed + level);
		return EnchantHelper.buildPrimalEnchantmentList(rand, item, level);
	}
	
	public int enchantItem(EntityPlayer player) {
		if (te.getStackInSlot(0).isEmpty() || te.getStackInSlot(1).isEmpty()) {
			return -1;
		}
		
		ItemStack itemEnchanted = te.getStackInSlot(0);
		ItemStack dust = te.getStackInSlot(1);
		int level = 0;
		
		if (dust.getItem() == ModItems.MAGIC_DUST_RED) {
			level = 1;
		} else if (dust.getItem() == ModItems.MAGIC_DUST_GREEN) {
			level = 2;
		} else if (dust.getItem() == ModItems.MAGIC_DUST_BLUE) {
			level = 3;
		}
		
		ItemStack item = itemEnchanted.copy();
		
		List<EnchantmentData> datas = getEnchantmentList(item, level);
		
		for (EnchantmentData d : datas) {
			item.addEnchantment(d.enchantment, d.enchantmentLevel);
		}
		
		player.onEnchant(itemEnchanted, 0);
		te.setInventorySlotContents(0, item);
		dust.shrink(1);
		
		te.markDirty();
		xpSeed = player.getXPSeed();
		onCraftMatrixChanged(te);
		
		if (world.isRemote) {
			world.playSound(player, te.getPos(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1, world.rand.nextFloat() * 0.1f + 0.9f);
		}
		
		return level;
	}
	
	@Override
	public List<Slot> getSlots() {
		return inventorySlots;
	}
	
	@Override
	public int getAmountOfInputSlots() {
		return 2;
	}
	
	@Override
	public int getAmountOfOutputSlots() {
		return 0;
	}
	
	@Override
	public boolean IMergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
		return mergeItemStack(stack, startIndex, endIndex, reverseDirection);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		return transferStack(player, index);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return te.isUsableByPlayer(playerIn);
	}
}
