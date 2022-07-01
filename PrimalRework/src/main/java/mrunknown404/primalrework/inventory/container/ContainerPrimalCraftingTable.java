package mrunknown404.primalrework.inventory.container;

import java.util.List;

import mrunknown404.primalrework.init.InitContainers;
import mrunknown404.primalrework.inventory.slot.SlotOutput;
import mrunknown404.primalrework.tileentities.TEIPrimalCraftingTable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPrimalCraftingTable extends Container implements IEasyQuickMoveStack {
	private final IInventory container;
	private final PlayerEntity player;
	
	public ContainerPrimalCraftingTable(int windowID, PlayerInventory inv) {
		this(windowID, inv, new Inventory(10));
	}
	
	public ContainerPrimalCraftingTable(int windowID, PlayerInventory inv, IInventory container) {
		super(InitContainers.PRIMAL_CRAFTING_TABLE.get(), windowID);
		this.container = container;
		this.player = inv.player;
		
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				addSlot(new Slot(container, y + x * 3, 30 + y * 18, 17 + x * 18) {
					@Override
					public void set(ItemStack stack) {
						super.set(stack);
						if (container instanceof TEIPrimalCraftingTable) {
							((TEIPrimalCraftingTable) container).onItemChange(getSlotIndex(), player, windowID);
						}
					}
				});
			}
		}
		
		addSlot(new SlotOutput(container, 9, 124, 35) {
			@Override
			public ItemStack onTake(PlayerEntity player, ItemStack stack) {
				for (int i = 0; i < 9; i++) {
					ItemStack item = container.getItem(i);
					
					if (item.isDamageableItem()) {
						if (item.getDamageValue() + 1 >= item.getMaxDamage()) {
							container.setItem(i, ItemStack.EMPTY);
						} else {
							item.setDamageValue(item.getDamageValue() + 1);
							container.setItem(i, item);
						}
					} else {
						container.removeItem(i, 1);
					}
				}
				
				if (container instanceof TEIPrimalCraftingTable) {
					((TEIPrimalCraftingTable) container).onItemChange(9, player, windowID);
				}
				
				return super.onTake(player, stack);
			}
		});
		
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				addSlot(new Slot(inv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
			}
		}
		
		for (int i = 0; i < 9; i++) {
			addSlot(new Slot(inv, i, 8 + i * 18, 142));
		}
	}
	
	@Override
	public ItemStack quickMoveStack(PlayerEntity player, int slotID) {
		return IQuickMoveStack(player, slotID);
	}
	
	@Override
	public int getAmountOfSlots() {
		return 10;
	}
	
	@Override
	public List<Slot> getSlots() {
		return slots;
	}
	
	@Override
	public boolean IMoveItemStackTo(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
		return moveItemStackTo(stack, startIndex, endIndex, reverseDirection);
	}
	
	@Override
	public boolean stillValid(PlayerEntity player) {
		return container.stillValid(player);
	}
}
