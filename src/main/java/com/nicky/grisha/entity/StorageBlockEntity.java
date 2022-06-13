package com.nicky.grisha.entity;

import com.nicky.grisha.ImplementedInventory;
import com.nicky.grisha.gui.GrishaCraftingController;
import com.nicky.grisha.registry.GrishaBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class StorageBlockEntity extends BlockEntity implements NamedScreenHandlerFactory,ImplementedInventory{

	DefaultedList<ItemStack> items = DefaultedList.ofSize(1,ItemStack.EMPTY);
	
	public StorageBlockEntity(BlockPos pos, BlockState state) {
		super(GrishaBlocks.STORAGE_BLOCK_ENTITY, pos, state);
	}

	@Override
	public DefaultedList<ItemStack> getItems() {
		return items;
	}
	
	public boolean canPlayerUseInv(PlayerEntity player) {
		return true;
	}
	
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        //We provide *this* to the screenHandler as our class Implements Inventory
        //Only the Server has the Inventory at the start, this will be synced to the client in the ScreenHandler
        return new GrishaCraftingController(syncId, playerInventory);
    }
	
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		Inventories.readNbt(nbt, items);
	}

	public void writeNbt(NbtCompound nbt) {
		Inventories.writeNbt(nbt, items);
		super.writeNbt(nbt);
	}

	@Override
	public Text getDisplayName() {
		return new TranslatableText(getCachedState().getBlock().getTranslationKey());
	}

}
