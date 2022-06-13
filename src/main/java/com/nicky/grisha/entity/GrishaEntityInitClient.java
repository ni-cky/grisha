package com.nicky.grisha.entity;

import net.fabricmc.api.EnvType;

import com.nicky.grisha.Grisha;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@SuppressWarnings("deprecation")
@Environment(EnvType.CLIENT)
public class GrishaEntityInitClient implements ClientModInitializer {
    public static final EntityModelLayer MODEL_GRISHA_ENTITY_LAYER = new EntityModelLayer(new Identifier(Grisha.MOD_ID, "grisha_corporalki_entity"), "main");
    @Override
    public void onInitializeClient() {
        /*
         * Registers our Cube Entity's renderer, which provides a model and texture for the entity.
         *
         * Entity Renderers can also manipulate the model before it renders based on entity context (EndermanEntityRenderer#render).
         */
        EntityRendererRegistry.INSTANCE.register(GrishaEntityInit.GRISHA_CORPORALKI_ENTITY, (context) -> {
            return new GrishaEntityRenderer(context);
        });
        EntityRendererRegistry.INSTANCE.register(GrishaEntityInit.GRISHA_MATERIALKI_ENTITY, (context) -> {
            return new GrishaEntityRenderer(context);
        });
        EntityRendererRegistry.INSTANCE.register(GrishaEntityInit.GRISHA_ETHERIALKI_ENTITY, (context) -> {
            return new GrishaEntityRenderer(context);
        });
        EntityModelLayerRegistry.registerModelLayer(MODEL_GRISHA_ENTITY_LAYER, GrishaEntityModel::getTexturedModelData);
    }
}
