package com.nicky.grisha.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.nicky.grisha.GrishaSmallScience;
import com.nicky.grisha.GrishaSmallScienceUtil;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(MinecraftClient.class)
public class GrishaMinecraftClientGlowingMixin {
	
	
	//Injection for rendering outlines for creatures while on Parem
	@Inject(method = "hasOutline", at = @At("HEAD"),cancellable = true)
	public void hasOutline(Entity entity, CallbackInfoReturnable<Boolean> cir) {
		@SuppressWarnings("resource")
		MinecraftClient minecraftClient = (MinecraftClient) (Object) this;
		PlayerEntity player = minecraftClient.player;
		if((player.getEyePos().distanceTo(entity.getPos()) < 15.) &
				GrishaSmallScienceUtil.isOnParem(player) &
				GrishaSmallScience.isWearingCorporalki(player) &
				entity instanceof LivingEntity &
				entity.getType() != EntityType.ZOMBIE &
				entity.getType() != EntityType.SKELETON) cir.setReturnValue(true);
	}
}
