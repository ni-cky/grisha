package com.nicky.grisha.items;

import java.util.Random;

import com.nicky.grisha.Grisha;
import com.nicky.grisha.GrishaSmallScienceUtil;
import com.nicky.grisha.mixin_util.PlayerAbilitiesExt;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.world.World;

public class JurdaParemItem extends Item{

	public JurdaParemItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public ItemStack finishUsing(ItemStack itemStack, World world, LivingEntity livingEntity) 
	{
	  itemStack = super.finishUsing(itemStack, world, livingEntity);
	  if(livingEntity instanceof PlayerEntity) {
		  PacketByteBuf buf = PacketByteBufs.create();
		  ClientPlayNetworking.send(Grisha.INCREASE_JURDA_PAREM_STAT_PACKET_ID, buf);
	  }
	  return itemStack;
	}
	
	public static void addiction(PlayerEntity player) {
		if(player instanceof PlayerEntity) {
			boolean stat =((PlayerAbilitiesExt)player.getAbilities()).getConsumedParem();
			if(stat)
				if(!GrishaSmallScienceUtil.isAddicted(player) && !GrishaSmallScienceUtil.isOnParem(player))
					if(0.00003 > new Random().nextDouble()) {
						PacketByteBuf buf = PacketByteBufs.create();
						ClientPlayNetworking.send(Grisha.SHOW_PAREM_ADDICTION_PACKET_ID, buf);
					}
		}
	}

}
