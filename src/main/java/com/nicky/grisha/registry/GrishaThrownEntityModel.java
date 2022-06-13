package com.nicky.grisha.registry;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class GrishaThrownEntityModel extends EntityModel<GrishaThrownEntity> {

	//private final ModelPart base;
	
    
	
	@Override
	public void setAngles(GrishaThrownEntity entity, float limbAngle, float limbDistance, float animationProgress,
			float headYaw, float headPitch) {

		
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green,
			float blue, float alpha) {
        // translate model down
        matrices.translate(0, 1.125, 0);
 
        // render cube
        //base.render(matrices, vertices, light, overlay);
		
	}

}
