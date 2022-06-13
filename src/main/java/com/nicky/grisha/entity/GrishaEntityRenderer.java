package com.nicky.grisha.entity;

import com.nicky.grisha.Grisha;

import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.util.Identifier;

public class GrishaEntityRenderer extends BipedEntityRenderer<GrishaEntity, PlayerEntityModel<GrishaEntity>> {

	static boolean slim = false;
	
    public GrishaEntityRenderer(Context ctx, PlayerEntityModel<GrishaEntity> model, float shadowRadius) {
		super(ctx, model, shadowRadius);
	}
    
    public GrishaEntityRenderer(Context ctx) {	
		super(ctx, new PlayerEntityModel<GrishaEntity>(ctx.getPart(slim ? EntityModelLayers.PLAYER_SLIM : EntityModelLayers.PLAYER), slim), 0.5F);
	}

	@Override
    public Identifier getTexture(GrishaEntity entity) {
        return new Identifier(Grisha.MOD_ID, "textures/entity/grisha_entity/grisha_entity.png");
    }
}
