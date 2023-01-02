package com.nicky.grisha;

import java.util.UUID;

import org.lwjgl.glfw.GLFW;

import com.nicky.grisha.gui.GrishaSmeltingScreen;
import com.nicky.grisha.items.bag.ui.Generic3x1ContainerScreen;
import com.nicky.grisha.registry.GrishaBlocks;
import com.nicky.grisha.registry.GrishaThrownEntity;
import com.nicky.grisha.registry.GrishaThrownEntityRenderer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.registry.Registry;

public class GrishaClient implements ClientModInitializer{
    // The KeyBinding declaration and registration are commonly executed here statically
	
	public static KeyBinding keyBinding;
	public static final Identifier PacketID = new Identifier(Grisha.MOD_ID, "spawn_packet");
	
	public static final Identifier ACTIVATE_CORPORALKI_PARTICLES_PACKET_ID = new Identifier(Grisha.MOD_ID, "corporalki_particles");
	
    @Override
    public void onInitializeClient() {
    	
    	GrishaBlocks.registerBlocksClient();
    	
    	System.out.println("------------ Was here first ----------");
    	
    	ScreenRegistry.register(Grisha.BAG_SCREEN_HANDLER, Generic3x1ContainerScreen::new);
    	
        // Event registration will be executed inside this method
		keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
	            "key.grisha.cast", // The translation key of the keybinding's name
	            InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
	            GLFW.GLFW_KEY_R, // The keycode of the key
	            "category.grisha.small_science" // The translation key of the keybinding's category.
	        ));
		
		System.out.println("------------ Was here ----------");
		
		EntityRendererRegistry.register(GrishaThrownEntity.GrishaThrownEntityType, GrishaThrownEntityRenderer::new);
		receiveEntityPacket();
		
		/*ScreenRegistry.register(Grisha.MATERIALKI_SCREEN_HANDLER, new ScreenRegistry.Factory<GrishaCraftingController, GrishaCraftingScreen>()  {
			@Override
			public GrishaCraftingScreen create(GrishaCraftingController handler, PlayerInventory inventory, Text title) {
				return new GrishaCraftingScreen(new GrishaCraftingController(handler.syncId,inventory), MinecraftClient.getInstance().player);
			}
			});*/
		
		ScreenRegistry.register(Grisha.MATERIALKI_SMELTING_SCREEN_HANDLER, GrishaSmeltingScreen::new);
        
		ClientPlayNetworking.registerGlobalReceiver(ACTIVATE_CORPORALKI_PARTICLES_PACKET_ID, (MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) -> {
				LivingEntity entity = (LivingEntity) client.player.world.getEntityById(buf.readInt());
				client.execute(()->GrishaSmallScience.corporalkiParticles(client.player,entity));
		});
		
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
            	if(GrishaSmallScience.isWearingMaterialki(client.player)) {
            		//ClientSide ausfuehren
            		System.out.println("Materialki Cast! - (PlayerSide)");
            		GrishaSmallScience.ACTIVATE_MATERIALKI(client.player);
            		
            		//ServerSide Packet verschicken
            		PacketByteBuf buf = PacketByteBufs.create();
            		ClientPlayNetworking.send(Grisha.ACTIVATE_MATERIALKI_PACKET_ID, buf);
            	}else
            	if(GrishaSmallScience.isWearingEtherealki(client.player)) {
            		//ClientSide ausfuehren
            		System.out.println("Etherealki Cast! - (PlayerSide)");
            		GrishaSmallScience.ACTIVATE_ETHEREALKI(client.player);
            		
            		//ServerSide Packet verschicken
            		PacketByteBuf buf = PacketByteBufs.create();
            		ClientPlayNetworking.send(Grisha.ACTIVATE_ETHEREALKI_PACKET_ID, buf);
            	}else
            	if(GrishaSmallScience.isWearingCorporalki(client.player)) {
            		//ClientSide ausfuehren
            		System.out.println("Corporalki Cast! - (PlayerSide)");
            		GrishaSmallScience.ACTIVATE_CORPORALKI(client.player);
            		
            		//ServerSide Packet verschicken
            		PacketByteBuf buf = PacketByteBufs.create();
            		ClientPlayNetworking.send(Grisha.ACTIVATE_CORPORALKI_PACKET_ID, buf);
            	}
            }
        });
        
        ClientTickEvents.END_CLIENT_TICK.register(client->{
        	if(client.player!=null) {
        		if(client.player.hasNoGravity() && client.player.isOnGround()) {
					client.player.setNoGravity(false);
				}
        		if(GrishaSmallScienceUtil.inventoryContainsDoubleAmplifier(client.player.getInventory())) {
        			System.out.println("Two Amplifiers");
        			PacketByteBuf buf = PacketByteBufs.create();
            		ClientPlayNetworking.send(Grisha.AMPLIFIER_SICKNESS_PACKET_ID, buf);
            	}
        		PacketByteBuf buf = PacketByteBufs.create();
        		ClientPlayNetworking.send(Grisha.START_PAREM_ADDICTION_PACKET_ID, buf);

        	}
        });
        

    }
    
	@SuppressWarnings("resource")
	public void receiveEntityPacket() {
		ClientPlayNetworking.registerGlobalReceiver(PacketID, (ctx, handler, byteBuf, responseSender) -> {
			System.out.println("Packet Sent");
			EntityType<?> et = Registry.ENTITY_TYPE.get(byteBuf.readVarInt());
			UUID uuid = byteBuf.readUuid();
			int entityId = byteBuf.readVarInt();
			Vec3d pos = EntitySpawnPacket.PacketBufUtil.readVec3d(byteBuf);
			float pitch = EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
			float yaw = EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
			ctx.execute(() -> {
				System.out.println("Spawn");
				if (MinecraftClient.getInstance().world == null)
					throw new IllegalStateException("Tried to spawn entity in a null world!");
				Entity e = et.create(MinecraftClient.getInstance().world);
				if (e == null)
					throw new IllegalStateException("Failed to create instance of entity \"" + Registry.ENTITY_TYPE.getId(et) + "\"!");
				e.updateTrackedPosition(pos.x, pos.y, pos.z);
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
