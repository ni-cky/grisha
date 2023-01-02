package com.nicky.grisha.items.bag.ui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

public class Generic3x1ContainerScreenHandler extends ScreenHandler {
	   private final Inventory inventory;

	   public Generic3x1ContainerScreenHandler(int syncId, PlayerInventory playerInventory) {
	      this(syncId, playerInventory, new SimpleInventory(3));
	   }

	   public Generic3x1ContainerScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
	      super(ScreenHandlerType.GENERIC_9X2, syncId);
	      checkSize(inventory, 3);
	      this.inventory = inventory;
	      inventory.onOpen(playerInventory.player);

	      int m;
	      int l;
	      for(m = 0; m < 1; ++m) {
	         for(l = 0; l < 3; ++l) {
	            this.addSlot(new Slot(inventory, l + m * 3, 62 + l * 18, 53));
	         }
	      }

	      for(m = 0; m < 9; ++m) {
	          this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 84));
	       }

	   }

	   public boolean canUse(PlayerEntity player) {
	      return this.inventory.canPlayerUse(player);
	   }

	   public ItemStack transferSlot(PlayerEntity player, int index) {
	      ItemStack itemStack = ItemStack.EMPTY;
	      Slot slot = (Slot)this.slots.get(index);
	      if (slot != null && slot.hasStack()) {
	         ItemStack itemStack2 = slot.getStack();
	         itemStack = itemStack2.copy();
	         if (index < 3) {
	            if (!this.insertItem(itemStack2, 3, 10, true)) {
	               return ItemStack.EMPTY;
	            }
	         } else if (!this.insertItem(itemStack2, 0, 3, false)) {
	            return ItemStack.EMPTY;
	         }

	         if (itemStack2.isEmpty()) {
	            slot.setStack(ItemStack.EMPTY);
	         } else {
	            slot.markDirty();
	         }

	         if (itemStack2.getCount() == itemStack.getCount()) {
	            return ItemStack.EMPTY;
	         }

	         slot.onTakeItem(player, itemStack2);
	      }

	      return itemStack;
	   }

	@Override
	public ItemStack quickMove(PlayerEntity player, int slot) {
		if (slot >= this.slots.size() - 9 && slot < this.slots.size()) {
			Slot slot2 = (Slot)this.slots.get(slot);
			if (slot2 != null && slot2.hasStack()) {
				slot2.setStack(ItemStack.EMPTY);
			}
		}

		return ItemStack.EMPTY;
	}

	public void close(PlayerEntity player) {
	      super.close(player);
	      this.inventory.onClose(player);
	   }
	}

