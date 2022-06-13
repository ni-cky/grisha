package com.nicky.grisha.items.bag;

import com.nicky.grisha.ImplementedInventory;

import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;

public final class BagInventory implements ImplementedInventory {
	private final ItemStack stack;
	private final DefaultedList<ItemStack> items = DefaultedList.ofSize(9, ItemStack.EMPTY);

	public BagInventory(ItemStack stack) {
		this.stack = stack;
		NbtCompound tag = stack.getSubNbt("Items");

		if (tag != null) {
			Inventories.readNbt(tag, items);
		}
	}

	@Override
	public DefaultedList<ItemStack> getItems() {
		return items;
	}

	@Override
	public void markDirty() {
		NbtCompound tag = stack.getOrCreateSubNbt("Items");
		Inventories.writeNbt(tag, items);
	}
}
