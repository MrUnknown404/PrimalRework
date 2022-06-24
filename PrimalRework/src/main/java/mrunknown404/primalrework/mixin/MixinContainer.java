package mrunknown404.primalrework.mixin;

import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import mrunknown404.primalrework.inventory.container.slot.SlotInventoryResult;
import mrunknown404.primalrework.inventory.container.slot.SlotOutput;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

@Mixin(Container.class)
public abstract class MixinContainer {
	@Shadow
	public final List<Slot> slots = Lists.newArrayList();
	@Shadow
	private int quickcraftType = -1;
	@Shadow
	private int quickcraftStatus;
	@Shadow
	private final Set<Slot> quickcraftSlots = Sets.newHashSet();
	
	//I didn't know how to fix this without resulting to this hell
	
	@Inject(at = @At("HEAD"), method = "doClick(IILnet/minecraft/inventory/container/ClickType;Lnet/minecraft/entity/player/PlayerEntity;)Lnet/minecraft/item/ItemStack;", cancellable = true)
	private void doClick(int slot0, int mouse, ClickType type, PlayerEntity player, CallbackInfoReturnable<ItemStack> callback) {
		ItemStack itemstack = ItemStack.EMPTY;
		PlayerInventory playerinventory = player.inventory;
		if (type == ClickType.QUICK_CRAFT) {
			int i1 = quickcraftStatus;
			quickcraftStatus = getQuickcraftHeader(mouse);
			if ((i1 != 1 || quickcraftStatus != 2) && i1 != quickcraftStatus) {
				resetQuickCraft();
			} else if (playerinventory.getCarried().isEmpty()) {
				resetQuickCraft();
			} else if (quickcraftStatus == 0) {
				quickcraftType = getQuickcraftType(mouse);
				if (isValidQuickcraftType(quickcraftType, player)) {
					quickcraftStatus = 1;
					quickcraftSlots.clear();
				} else {
					resetQuickCraft();
				}
			} else if (quickcraftStatus == 1) {
				Slot slot7 = slots.get(slot0);
				ItemStack itemstack12 = playerinventory.getCarried();
				if (slot7 != null && canItemQuickReplace(slot7, itemstack12, true) && slot7.mayPlace(itemstack12) &&
						(quickcraftType == 2 || itemstack12.getCount() > quickcraftSlots.size()) && canDragTo(slot7)) {
					quickcraftSlots.add(slot7);
				}
			} else if (quickcraftStatus == 2) {
				if (!quickcraftSlots.isEmpty()) {
					ItemStack itemstack10 = playerinventory.getCarried().copy();
					int k1 = playerinventory.getCarried().getCount();
					
					for (Slot slot8 : quickcraftSlots) {
						ItemStack itemstack13 = playerinventory.getCarried();
						if (slot8 != null && canItemQuickReplace(slot8, itemstack13, true) && slot8.mayPlace(itemstack13) &&
								(quickcraftType == 2 || itemstack13.getCount() >= quickcraftSlots.size()) && canDragTo(slot8)) {
							ItemStack itemstack14 = itemstack10.copy();
							int j3 = slot8.hasItem() ? slot8.getItem().getCount() : 0;
							getQuickCraftSlotCount(quickcraftSlots, quickcraftType, itemstack14, j3);
							int k3 = Math.min(itemstack14.getMaxStackSize(), slot8.getMaxStackSize(itemstack14));
							if (itemstack14.getCount() > k3) {
								itemstack14.setCount(k3);
							}
							
							k1 -= itemstack14.getCount() - j3;
							slot8.set(itemstack14);
						}
					}
					
					itemstack10.setCount(k1);
					playerinventory.setCarried(itemstack10);
				}
				
				resetQuickCraft();
			} else {
				resetQuickCraft();
			}
		} else if (quickcraftStatus != 0) {
			resetQuickCraft();
		} else if ((type == ClickType.PICKUP || type == ClickType.QUICK_MOVE) && (mouse == 0 || mouse == 1)) {
			if (slot0 == -999) {
				if (!playerinventory.getCarried().isEmpty()) {
					if (mouse == 0) {
						player.drop(playerinventory.getCarried(), true);
						playerinventory.setCarried(ItemStack.EMPTY);
					}
					
					if (mouse == 1) {
						player.drop(playerinventory.getCarried().split(1), true);
					}
				}
			} else if (type == ClickType.QUICK_MOVE) {
				if (slot0 < 0) {
					callback.setReturnValue(ItemStack.EMPTY);
					return;
				}
				
				Slot slot5 = slots.get(slot0);
				if (slot5 == null || !slot5.mayPickup(player)) {
					callback.setReturnValue(ItemStack.EMPTY);
					return;
				}
				
				for (ItemStack itemstack8 = quickMoveStack(player, slot0); !itemstack8.isEmpty() &&
						ItemStack.isSame(slot5.getItem(), itemstack8); itemstack8 = quickMoveStack(player, slot0)) {
					itemstack = itemstack8.copy();
				}
			} else {
				if (slot0 < 0) {
					callback.setReturnValue(ItemStack.EMPTY);
					return;
				}
				
				Slot slot6 = slots.get(slot0);
				if (slot6 != null) {
					itemstack = attempt(slot6, player, playerinventory, itemstack, mouse);
					slot6.setChanged();
				}
			}
		} else if (type == ClickType.SWAP) {
			Slot slot = slots.get(slot0);
			ItemStack itemstack1 = playerinventory.getItem(mouse);
			ItemStack itemstack2 = slot.getItem();
			if (!itemstack1.isEmpty() || !itemstack2.isEmpty()) {
				if (itemstack1.isEmpty()) {
					if (slot.mayPickup(player)) {
						playerinventory.setItem(mouse, itemstack2);
						//slot.onSwapCraft(itemstack2.getCount()); //This is unused and private soooooo.... will probably regret this later
						slot.set(ItemStack.EMPTY);
						slot.onTake(player, itemstack2);
					}
				} else if (itemstack2.isEmpty()) {
					if (slot.mayPlace(itemstack1)) {
						int i = slot.getMaxStackSize(itemstack1);
						if (itemstack1.getCount() > i) {
							slot.set(itemstack1.split(i));
						} else {
							slot.set(itemstack1);
							playerinventory.setItem(mouse, ItemStack.EMPTY);
						}
					}
				} else if (slot.mayPickup(player) && slot.mayPlace(itemstack1)) {
					int l1 = slot.getMaxStackSize(itemstack1);
					if (itemstack1.getCount() > l1) {
						slot.set(itemstack1.split(l1));
						slot.onTake(player, itemstack2);
						if (!playerinventory.add(itemstack2)) {
							player.drop(itemstack2, true);
						}
					} else {
						slot.set(itemstack1);
						playerinventory.setItem(mouse, itemstack2);
						slot.onTake(player, itemstack2);
					}
				}
			}
		} else if (type == ClickType.CLONE && player.abilities.instabuild && playerinventory.getCarried().isEmpty() && slot0 >= 0) {
			Slot slot4 = slots.get(slot0);
			if (slot4 != null && slot4.hasItem()) {
				ItemStack itemstack7 = slot4.getItem().copy();
				itemstack7.setCount(itemstack7.getMaxStackSize());
				playerinventory.setCarried(itemstack7);
			}
		} else if (type == ClickType.THROW && playerinventory.getCarried().isEmpty() && slot0 >= 0) {
			Slot slot3 = slots.get(slot0);
			if (slot3 != null && slot3.hasItem() && slot3.mayPickup(player)) {
				ItemStack itemstack6 = slot3.remove(mouse == 0 && !(slot3 instanceof SlotOutput) ? 1 : slot3.getItem().getCount());
				if (slot3 instanceof SlotInventoryResult) { //ugly fix. idc
					((SlotInventoryResult) slot3).drop();
				}
				
				slot3.onTake(player, itemstack6);
				player.drop(itemstack6, true);
			}
		} else if (type == ClickType.PICKUP_ALL && slot0 >= 0) {
			Slot slot2 = slots.get(slot0);
			
			if (slot2 instanceof SlotOutput) {
				itemstack = attempt(slot2, player, playerinventory, itemstack, mouse);
				slot2.setChanged();
			} else {
				ItemStack itemstack5 = playerinventory.getCarried();
				if (!itemstack5.isEmpty() && (slot2 == null || !slot2.hasItem() || !slot2.mayPickup(player))) {
					int j1 = mouse == 0 ? 0 : slots.size() - 1;
					int i2 = mouse == 0 ? 1 : -1;
					
					for (int j = 0; j < 2; ++j) {
						for (int k = j1; k >= 0 && k < slots.size() && itemstack5.getCount() < itemstack5.getMaxStackSize(); k += i2) {
							Slot slot1 = slots.get(k);
							if (slot1 instanceof SlotOutput) {
								continue;
							}
							
							if (slot1.hasItem() && canItemQuickReplace(slot1, itemstack5, true) && slot1.mayPickup(player) && canTakeItemForPickAll(itemstack5, slot1)) {
								ItemStack itemstack3 = slot1.getItem();
								if (j != 0 || itemstack3.getCount() != itemstack3.getMaxStackSize()) {
									int l = Math.min(itemstack5.getMaxStackSize() - itemstack5.getCount(), itemstack3.getCount());
									ItemStack itemstack4 = slot1.remove(l);
									itemstack5.grow(l);
									if (itemstack4.isEmpty()) {
										slot1.set(ItemStack.EMPTY);
									}
									
									slot1.onTake(player, itemstack4);
								}
							}
						}
					}
				}
			}
			
			broadcastChanges();
		}
		
		callback.setReturnValue(itemstack);
		return;
	}
	
	private static ItemStack attempt(Slot slot6, PlayerEntity player, PlayerInventory playerinventory, ItemStack itemstack, int mouse) {
		ItemStack itemstack9 = slot6.getItem();
		ItemStack itemstack11 = playerinventory.getCarried();
		if (!itemstack9.isEmpty()) {
			itemstack = itemstack9.copy();
		}
		
		if (itemstack9.isEmpty()) {
			if (!itemstack11.isEmpty() && slot6.mayPlace(itemstack11)) {
				int j2 = mouse == 0 ? itemstack11.getCount() : 1;
				if (j2 > slot6.getMaxStackSize(itemstack11)) {
					j2 = slot6.getMaxStackSize(itemstack11);
				}
				
				slot6.set(itemstack11.split(j2));
			}
		} else if (slot6.mayPickup(player)) {
			if (itemstack11.isEmpty()) {
				if (itemstack9.isEmpty()) {
					slot6.set(ItemStack.EMPTY);
					playerinventory.setCarried(ItemStack.EMPTY);
				} else {
					int k2 = mouse == 0 || slot6 instanceof SlotOutput ? itemstack9.getCount() : (itemstack9.getCount() + 1) / 2;
					playerinventory.setCarried(slot6.remove(k2));
					if (itemstack9.isEmpty()) {
						slot6.set(ItemStack.EMPTY);
					}
					
					slot6.onTake(player, playerinventory.getCarried());
				}
			} else if (slot6.mayPlace(itemstack11)) {
				if (consideredTheSameItem(itemstack9, itemstack11)) {
					int l2 = mouse == 0 ? itemstack11.getCount() : 1;
					if (l2 > slot6.getMaxStackSize(itemstack11) - itemstack9.getCount()) {
						l2 = slot6.getMaxStackSize(itemstack11) - itemstack9.getCount();
					}
					
					if (l2 > itemstack11.getMaxStackSize() - itemstack9.getCount()) {
						l2 = itemstack11.getMaxStackSize() - itemstack9.getCount();
					}
					
					itemstack11.shrink(l2);
					itemstack9.grow(l2);
				} else if (itemstack11.getCount() <= slot6.getMaxStackSize(itemstack11)) {
					slot6.set(itemstack11);
					playerinventory.setCarried(itemstack9);
				}
			} else if (itemstack11.getMaxStackSize() > 1 && consideredTheSameItem(itemstack9, itemstack11) && !itemstack9.isEmpty()) {
				int i3 = itemstack9.getCount();
				if (i3 + itemstack11.getCount() <= itemstack11.getMaxStackSize()) {
					itemstack11.grow(i3);
					itemstack9 = slot6.remove(i3);
					if (itemstack9.isEmpty()) {
						slot6.set(ItemStack.EMPTY);
					}
					
					slot6.onTake(player, playerinventory.getCarried());
				}
			}
		}
		
		return itemstack;
	}
	
	@SuppressWarnings("unused")
	@Shadow
	public static boolean isValidQuickcraftType(int p_180610_0_, PlayerEntity p_180610_1_) {
		throw new IllegalStateException("Mixin failed to shadow");
	}
	
	@Shadow
	public void broadcastChanges() {
		throw new IllegalStateException("Mixin failed to shadow");
	}
	
	@Shadow
	protected void resetQuickCraft() {
		throw new IllegalStateException("Mixin failed to shadow");
	}
	
	@SuppressWarnings("unused")
	@Shadow
	public static boolean canItemQuickReplace(@Nullable Slot p_94527_0_, ItemStack p_94527_1_, boolean p_94527_2_) {
		throw new IllegalStateException("Mixin failed to shadow");
	}
	
	@SuppressWarnings("unused")
	@Shadow
	public static void getQuickCraftSlotCount(Set<Slot> p_94525_0_, int p_94525_1_, ItemStack p_94525_2_, int p_94525_3_) {
		throw new IllegalStateException("Mixin failed to shadow");
	}
	
	@SuppressWarnings("unused")
	@Shadow
	public boolean canDragTo(Slot p_94531_1_) {
		throw new IllegalStateException("Mixin failed to shadow");
	}
	
	@SuppressWarnings("unused")
	@Shadow
	public static int getQuickcraftType(int p_94529_0_) {
		throw new IllegalStateException("Mixin failed to shadow");
	}
	
	@SuppressWarnings("unused")
	@Shadow
	public static int getQuickcraftHeader(int p_94532_0_) {
		throw new IllegalStateException("Mixin failed to shadow");
	}
	
	@SuppressWarnings("unused")
	@Shadow
	public static boolean consideredTheSameItem(ItemStack p_195929_0_, ItemStack p_195929_1_) {
		throw new IllegalStateException("Mixin failed to shadow");
	}
	
	@SuppressWarnings("unused")
	@Shadow
	public boolean canTakeItemForPickAll(ItemStack p_94530_1_, Slot p_94530_2_) {
		throw new IllegalStateException("Mixin failed to shadow");
	}
	
	@SuppressWarnings("unused")
	@Shadow
	public ItemStack quickMoveStack(PlayerEntity p_82846_1_, int p_82846_2_) {
		throw new IllegalStateException("Mixin failed to shadow");
	}
}
