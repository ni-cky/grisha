package com.nicky.grisha.registry;

import com.nicky.grisha.EntitySpawnPacket;
import com.nicky.grisha.Grisha;
import com.nicky.grisha.GrishaClient;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;
import net.minecraft.world.World;

public class GrishaThrownEntity extends ThrownItemEntity {
	
	private ItemStack contains = new ItemStack(Items.SLIME_BALL);
	
	private int damage;
	
	private boolean thrownByMob;

	public static final EntityType<GrishaThrownEntity> GrishaThrownEntityType = 
				FabricEntityTypeBuilder.<GrishaThrownEntity>create(SpawnGroup.MISC, GrishaThrownEntity:: new)
					.dimensions(EntityDimensions.fixed(0.25F, 0.25F)) // dimensions in Minecraft units of the projectile
					.trackRangeBlocks(4).trackedUpdateRate(10) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
					.build(); // VERY IMPORTANT DONT DELETE FOR THE LOVE OF GOD PSLSSSSSS
	
	public static void registerThrownEntities() {
		Registry.register(Registries.ENTITY_TYPE,new Identifier(Grisha.MOD_ID, "grisha_thrown_entity"),GrishaThrownEntityType);
	}
	
	public GrishaThrownEntity(EntityType<? extends GrishaThrownEntity> entityType, World world) {
	      super(entityType, world);
	      this.contains = new ItemStack(Items.DIRT);
	      this.damage = 3;
	   }

	   public GrishaThrownEntity(World world, LivingEntity owner, ItemStack item, int damage) {
	      super(GrishaThrownEntityType, owner, world);
	      this.contains = owner.getStackInHand(Hand.MAIN_HAND).copy();
	      this.damage = damage;
	   }

	   /*public GrishaThrownEntity(World world, double x, double y, double z, ItemStack item, int damage) {
	      super(GrishaThrownEntityType, x, y, z, world);
	      this.contains = item;
	      this.contains = MinecraftClient.getInstance().player.getStackInHand(Hand.MAIN_HAND).copy();
	      this.damage = damage;
	   }*/

	   protected Item getDefaultItem() {
		   if(contains != null)
			   return contains.getItem();
		   else
			   return Items.SLIME_BALL;
	   }

	   @Environment(EnvType.CLIENT)
	   private ParticleEffect getParticleParameters() {
	      ItemStack itemStack = this.getItem();
	      return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.ASH : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
	   }

	   @Environment(EnvType.CLIENT)
	   public void handleStatus(byte status) {
	      if (status == 3) {
	         ParticleEffect particleEffect = this.getParticleParameters();

	         for(int i = 0; i < 8; ++i) {
	            this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
	         }
	      }

	   }

	   protected void onEntityHit(EntityHitResult entityHitResult) {
	      super.onEntityHit(entityHitResult);
	      Entity entity = entityHitResult.getEntity();
	      entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), (float)damage);
	   }

	   protected void onCollision(HitResult hitResult) {
	      super.onCollision(hitResult);
	      if (!this.world.isClient & !thrownByMob) {
	         this.world.sendEntityStatus(this, (byte)3);
	         ItemStack containingStack = contains.copy();
	         containingStack.setCount(1);
	         ItemEntity itemEntity = new ItemEntity(world, this.getX(), this.getY(), this.getZ(), containingStack);
	         world.spawnEntity(itemEntity);
	         this.remove(RemovalReason.DISCARDED);
	      }

	   }
	   
	   public void setThrownByMob() {
		   thrownByMob = true;
	   }
	   
       /*@Override
       public Packet<ClientPlayPacketListener> createSpawnPacket() {
    	   return EntitySpawnPacket.create(this, GrishaClient.PacketID);
       }*/

}
