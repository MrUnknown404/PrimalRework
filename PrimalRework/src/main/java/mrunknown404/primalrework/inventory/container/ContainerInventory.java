package mrunknown404.primalrework.inventory.container;

import com.mojang.datafixers.util.Pair;

import mrunknown404.primalrework.init.InitContainers;
import mrunknown404.primalrework.inventory.InvCraftingGrid;
import mrunknown404.primalrework.inventory.InvCraftingResult;
import mrunknown404.primalrework.inventory.slot.SlotInventoryResult;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ContainerInventory extends Container {
	private static final ResourceLocation[] TEXTURE_EMPTY_SLOTS = new ResourceLocation[] { PlayerContainer.EMPTY_ARMOR_SLOT_BOOTS, PlayerContainer.EMPTY_ARMOR_SLOT_LEGGINGS,
			PlayerContainer.EMPTY_ARMOR_SLOT_CHESTPLATE, PlayerContainer.EMPTY_ARMOR_SLOT_HELMET };
	private static final EquipmentSlotType[] SLOT_IDS = new EquipmentSlotType[] { EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET };
	
	private final InvCraftingResult craftResult = new InvCraftingResult();
	private final InvCraftingGrid craftSlots = new InvCraftingGrid(this, craftResult);
	private final PlayerEntity player;
	
	public ContainerInventory(int windowID, PlayerInventory inv) {
		super(InitContainers.INVENTORY.get(), windowID);
		this.player = inv.player;
		
		addSlot(new SlotInventoryResult(craftResult, 0, 154, 28, inv, craftSlots));
		for (int y = 0; y < 2; y++) {
			for (int x = 0; x < 2; x++) {
				addSlot(new Slot(craftSlots, x + y * 2, 98 + x * 18, 18 + y * 18));
			}
		}
		
		for (int i = 0; i < 4; i++) {
			final EquipmentSlotType equipmentslottype = SLOT_IDS[i];
			addSlot(new Slot(inv, 39 - i, 8, 8 + i * 18) {
				@Override
				public int getMaxStackSize() {
					return 1;
				}
				
				@Override
				public boolean mayPlace(ItemStack stack) {
					return stack.canEquip(equipmentslottype, player);
				}
				
				@Override
				public boolean mayPickup(PlayerEntity player) {
					ItemStack itemstack = getItem();
					return !itemstack.isEmpty() && !player.isCreative() && EnchantmentHelper.hasBindingCurse(itemstack) ? false : super.mayPickup(player);
				}
				
				@Override
				public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
					return Pair.of(PlayerContainer.BLOCK_ATLAS, TEXTURE_EMPTY_SLOTS[equipmentslottype.getIndex()]);
				}
			});
		}
		
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				addSlot(new Slot(inv, x + (y + 1) * 9, 8 + x * 18, 84 + y * 18));
			}
		}
		
		for (int i = 0; i < 9; i++) {
			addSlot(new Slot(inv, i, 8 + i * 18, 142));
		}
		
		addSlot(new Slot(inv, 40, 77, 62) {
			@Override
			public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
				return Pair.of(PlayerContainer.BLOCK_ATLAS, PlayerContainer.EMPTY_ARMOR_SLOT_SHIELD);
			}
		});
	}
	
	@Override
	public boolean stillValid(PlayerEntity player) {
		return true;
	}
	
	@Override
	public void removed(PlayerEntity player) {
		super.removed(player);
		if (!player.level.isClientSide) {
			clearContainer(player, player.level, craftSlots);
		}
	}
	
	@Override
	public ItemStack quickMoveStack(PlayerEntity player, int oslot) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = slots.get(oslot);
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			EquipmentSlotType equipmentslottype = MobEntity.getEquipmentSlotForItem(itemstack);
			if (oslot == 0) {
				if (!moveItemStackTo(itemstack1, 9, 45, true)) {
					return ItemStack.EMPTY;
				}
				
				slot.onQuickCraft(itemstack1, itemstack);
			} else if (oslot >= 1 && oslot < 5) {
				if (!moveItemStackTo(itemstack1, 9, 45, false)) {
					return ItemStack.EMPTY;
				}
			} else if (oslot >= 5 && oslot < 9) {
				if (!moveItemStackTo(itemstack1, 9, 45, false)) {
					return ItemStack.EMPTY;
				}
			} else if (equipmentslottype.getType() == EquipmentSlotType.Group.ARMOR && !slots.get(8 - equipmentslottype.getIndex()).hasItem()) {
				int i = 8 - equipmentslottype.getIndex();
				if (!moveItemStackTo(itemstack1, i, i + 1, false)) {
					return ItemStack.EMPTY;
				}
			} else if (equipmentslottype == EquipmentSlotType.OFFHAND && !slots.get(45).hasItem()) {
				if (!moveItemStackTo(itemstack1, 45, 46, false)) {
					return ItemStack.EMPTY;
				}
			} else if (oslot >= 9 && oslot < 36) {
				if (!moveItemStackTo(itemstack1, 36, 45, false)) {
					return ItemStack.EMPTY;
				}
			} else if (oslot >= 36 && oslot < 45) {
				if (!moveItemStackTo(itemstack1, 9, 36, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!moveItemStackTo(itemstack1, 9, 45, false)) {
				return ItemStack.EMPTY;
			}
			
			if (itemstack1.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
			
			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}
			
			ItemStack itemstack2 = slot.onTake(player, itemstack1);
			if (oslot == 0) {
				player.drop(itemstack2, false);
			}
		}
		
		return itemstack;
	}
}
