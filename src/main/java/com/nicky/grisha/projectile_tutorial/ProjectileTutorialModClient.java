package com.nicky.grisha.projectile_tutorial;

import java.util.UUID;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.MinecraftClient;


@SuppressWarnings("deprecation")
public class ProjectileTutorialModClient implements ClientModInitializer{
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.INSTANCE.register(ProjectileTutorialMod.PackedSnowballEntityType, (context) ->
				 new FlyingItemEntityRenderer(context));
		receiveEntityPacket();
		
	}
	
	@SuppressWarnings("resource")
	public void receiveEntityPacket() {
		ClientSidePacketRegistry.INSTANCE.register(ProjectileTutorialMod.PacketID, (ctx, byteBuf) -> {
			EntityType<?> et = Registry.ENTITY_TYPE.get(byteBuf.readVarInt());
			UUID uuid = byteBuf.readUuid();
			int entityId = byteBuf.readVarInt();
			Vec3d pos = EntitySpawnPacket.PacketBufUtil.readVec3d(byteBuf);
			float pitch = EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
			float yaw = EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
			ctx.getTaskQueue().execute(() -> {
				if (MinecraftClient.getInstance().world == null)
					throw new IllegalStateException("Tried to spawn entity in a null world!");
				Entity e = et.create(MinecraftClient.getInstance().world);
				if (e == null)
					throw new IllegalStateException("Failed to create instance of entity \"" + Registry.ENTITY_TYPE.getId(et) + "\"!");
				e.updateTrackedPosition(pos);
				e.setPos(pos.x, pos.y, pos.z);
				e.setPitch(pitch);
				e.setYaw(yaw);
				e.setId(entityId);
				e.setUuid(uuid);
				MinecraftClient.getInstance().world.addEntity(entityId, e);
			});
		});
	}
}
