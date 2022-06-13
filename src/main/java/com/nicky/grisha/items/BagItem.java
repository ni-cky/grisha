package com.nicky.grisha.items;

import com.nicky.grisha.items.bag.ui.BagScreenHandler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Random;

import com.nicky.grisha.items.bag.BagInventory;

public class BagItem extends Item {
	protected static final Random RANDOM = new Random();
	
	public BagItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		world.playSound((PlayerEntity)null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_CROP_PLANT, SoundCategory.NEUTRAL, 0.5F, 0.4F / (RANDOM.nextFloat() * 0.4F + 0.8F));
		ItemStack stack = user.getStackInHand(hand);
		user.openHandledScreen(createScreenHandlerFactory(stack));
		return TypedActionResult.success(stack);
	}

	private NamedScreenHandlerFactory createScreenHandlerFactory(ItemStack stack) {
		return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> {
			return new BagScreenHandler(syncId, inventory, new BagInventory(stack));
		}, stack.getName());
	}
	
	
}
