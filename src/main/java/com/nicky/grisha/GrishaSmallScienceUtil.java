package com.nicky.grisha;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.nicky.grisha.gui.GrishaCraftingController;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class GrishaSmallScienceUtil {
	
	public static boolean isOnParem(PlayerEntity player) {
		return (player.getStatusEffect(Grisha.JURDA_PAREM_EFFECT) != null);
	}
	
	public static boolean isAddicted(PlayerEntity player) {
		return (player.getStatusEffect(Grisha.JURDA_PAREM_ADDICTION_PHASE0) != null) |
		(player.getStatusEffect(Grisha.JURDA_PAREM_ADDICTION_PHASE1) != null) |
		(player.getStatusEffect(Grisha.JURDA_PAREM_ADDICTION_PHASE2) != null) |
		(player.getStatusEffect(Grisha.JURDA_PAREM_ADDICTION_PHASE3) != null);
	}
	
	public static TypedActionResult<Entity> grishaRaycast(PlayerEntity player, float tickDelta, double distance) {
		MinecraftClient client = MinecraftClient.getInstance();
		Entity e;
		
		tickDelta = 0;
		
		Entity entity = client.getCameraEntity();
		
	    Box box = entity
	            .getBoundingBox()
	            .stretch(entity.getRotationVec(1.0F))
	            .expand(1.0D, 1.0D, 1.0D);
	    
	    Vec3d cameraPos = entity.getCameraPosVec(tickDelta);
	    Vec3d vec3d2 = cameraPos.add(player.getRotationVector().multiply(distance));
		
		EntityHitResult hit = ProjectileUtil.raycast(player, cameraPos, vec3d2, box, (entityx) -> !entityx.isSpectator(), distance);

		if(hit != null)
		switch(hit.getType()) {
	    case ENTITY:
	        EntityHitResult entityHit = (EntityHitResult) hit;
	        e = entityHit.getEntity();
	        System.out.println("o"+e.getEntityWorld().isClient);
	        return TypedActionResult.success(e, true);
	    case BLOCK:
	    	System.out.println("i"+hit.toString());
	    default:
	    	e = player;
	    }
		else
			e = player;
		return TypedActionResult.fail(e);
	}
	
	public static boolean holdsAmplifier(LivingEntity player) {
    	ItemStack x = player.getStackInHand(Hand.OFF_HAND);
    	NbtList y = x.getEnchantments();
    	for (NbtElement i : y) {
    		if(i.asString().equals("{id:\"grisha:amplifier\",lvl:1s}")) {
    			return true;
    		}
    	}
    	return false;
    		 
    }
	
	public static boolean inventoryContainsDoubleAmplifier(PlayerInventory inv) {
		List<DefaultedList<ItemStack>> combinedInventory = ImmutableList.of(inv.main, inv.armor, inv.offHand);
	    DefaultedList<ItemStack> defaultedList;
	    int amplifierCount = 0;
		for(Iterator<DefaultedList<ItemStack>> var3 = combinedInventory.iterator(); var3.hasNext();) {
	         defaultedList = (DefaultedList<ItemStack>)var3.next();
	         for(Iterator<ItemStack> var4 = defaultedList.iterator(); var4.hasNext();) {
	        	 ItemStack current = var4.next();
	        	 NbtList y = current.getEnchantments();
	         	 for (NbtElement i : y) {
	         		 if(i.asString().equals("{id:\"grisha:amplifier\",lvl:1s}")) {
	         			amplifierCount+= current.getCount();
	         		 }
	         	 }
	          }
	      }
		if(amplifierCount > 1)
			return true;
		return false;
	}
	
	public static NamedScreenHandlerFactory createScreenHandlerFactory(World world, BlockPos pos) {
		return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> {
			return new GrishaCraftingController(syncId, inventory, ScreenHandlerContext.create(world, pos));
		}, Text.of("Materialki Crafting"));
	}
	
	public static TypedActionResult<Entity> grishaDistanceRaycast(MinecraftClient client, float tickDelta,Vec3d direction, double distance) {
	    HitResult hit = raycastInDirection(client, tickDelta, direction, distance);
	     
	    switch(hit.getType()) {
	        case MISS:
	            //nothing near enough
	            break; 
	        case BLOCK:
	            BlockHitResult blockHit = (BlockHitResult) hit;
	            BlockPos blockPos = blockHit.getBlockPos();
	            BlockState blockState = client.player.world.getBlockState(blockPos);
	            @SuppressWarnings("unused") Block block = blockState.getBlock();
	            break; 
	        case ENTITY:
	            EntityHitResult entityHit = (EntityHitResult) hit;
	            Entity entity = entityHit.getEntity();
	            return TypedActionResult.success(entity, true);
	    }
	    Entity e = client.player;
		return TypedActionResult.fail(e);
	}
	
    private static HitResult raycastInDirection(MinecraftClient client, float tickDelta, Vec3d direction, double pDistance) {
        Entity entity = client.getCameraEntity();
        if (entity == null || client.world == null) {
            return null;
        }
     
        double reachDistance = pDistance;
        HitResult target = raycast(entity, reachDistance, tickDelta, false, direction);
        boolean tooFar = false;
        double extendedReach = reachDistance;
        
            if (reachDistance > 3.0D) {
                tooFar = true;
            }

     
        Vec3d cameraPos = entity.getCameraPosVec(tickDelta);
     
        extendedReach = extendedReach * extendedReach;
        if (target != null) {
            extendedReach = target.getPos().squaredDistanceTo(cameraPos);
        }
     
        Vec3d vec3d3 = cameraPos.add(direction.multiply(reachDistance));
        Box box = entity
                .getBoundingBox()
                .stretch(entity.getRotationVec(1.0F).multiply(reachDistance))
                .expand(1.0D, 1.0D, 1.0D);
        EntityHitResult entityHitResult = ProjectileUtil.raycast(
                entity,
                cameraPos,
                vec3d3,
                box,
                (entityx) -> !entityx.isSpectator(),
                extendedReach
        );
     
        if (entityHitResult == null) {
            return target;
        }
     
        Entity entity2 = entityHitResult.getEntity();
        Vec3d vec3d4 = entityHitResult.getPos();
        double g = cameraPos.squaredDistanceTo(vec3d4);
        if (tooFar && g > extendedReach) {
            return null;
        } else if (g <= extendedReach || target == null) {
            target = entityHitResult;
            if (entity2 instanceof LivingEntity || entity2 instanceof ItemEntity) {
                client.targetedEntity = entity2;
            }
        }
     
        return target;
    }
     
    private static BlockHitResult raycast(
            Entity entity,
            double maxDistance,
            float tickDelta,
            boolean includeFluids,
            Vec3d direction
    ) {
        Vec3d end = entity.getCameraPosVec(tickDelta).add(direction.multiply(maxDistance));
        return entity.world.raycast(new RaycastContext(
                entity.getCameraPosVec(tickDelta),
                end,
                RaycastContext.ShapeType.OUTLINE,
                includeFluids ? RaycastContext.FluidHandling.ANY : RaycastContext.FluidHandling.NONE,
                entity
        ));
    }
}
