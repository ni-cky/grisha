package com.nicky.grisha.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.nicky.grisha.Grisha;
import com.nicky.grisha.GrishaSmallScience;
import com.nicky.grisha.GrishaSmallScienceUtil;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class GrishaMinecraftClientDoAttackMixin {
	@Inject(method = "doAttack", at = @At("HEAD"),cancellable = true)
	public void doAttack(CallbackInfoReturnable<Boolean> cir) {
		MinecraftClient minecraftClient = (MinecraftClient) (Object) this;
		PlayerEntity player = minecraftClient.player;
		System.out.println("Attack!");
		if(player != null)
		if(GrishaSmallScience.isWearingCorporalki(player))
		if(GrishaSmallScienceUtil.isOnParem(player))
		{
			System.out.println("On Parem!");
			Vec3d direction = lookVector(player);
			TypedActionResult<Entity> x = GrishaSmallScienceUtil.grishaDistanceRaycast(minecraftClient, 15f, direction, 15.);
			if(x.getResult().isAccepted()) {
				if(x.getValue().isLiving()) {
					LivingEntity y = (LivingEntity) x.getValue();
					System.out.println(y.toString());
					PacketByteBuf buf = PacketByteBufs.create();
					buf.writeInt(y.getId());
            		ClientPlayNetworking.send(Grisha.ACTIVATE_CORPORALKI_PAREM_PACKET_ID, buf);
            		if(y.world.isClient())
            			for(int i1 = 0; i1 < 8; ++i1) {
            				int i2 = 3840;
            				double d = 0;
            				double e = 0;
            				double f = 0;
            				y.world.addParticle(ParticleTypes.EFFECT,y.getParticleX(0.5D), y.getRandomBodyY(), y.getParticleZ(0.5D), d, e, f);
            			}
				}
			}
		}
	}
	
	float rad(float angle) { 
		return angle * (float) Math.PI / 180; 
		}

		Vec3d lookVector(Entity player) {
		float rotationYaw = player.getYaw(), rotationPitch = player.getPitch();
		float vx = -MathHelper.sin(rad(rotationYaw)) * MathHelper.cos(rad(rotationPitch));
		float vz = MathHelper.cos(rad(rotationYaw)) * MathHelper.cos(rad(rotationPitch));
		float vy = -MathHelper.sin(rad(rotationPitch));
		return new Vec3d(vx, vy, vz);
		}
}
