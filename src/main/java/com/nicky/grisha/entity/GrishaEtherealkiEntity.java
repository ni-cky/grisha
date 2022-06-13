package com.nicky.grisha.entity;

import org.jetbrains.annotations.Nullable;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

public class GrishaEtherealkiEntity extends GrishaEntity{
	public GrishaEtherealkiEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world, 1);
    }
	
	public static DefaultAttributeContainer.Builder createGrishaAttributes(){
    	return HostileEntity.createHostileAttributes()
    			.add(EntityAttributes.GENERIC_MAX_HEALTH,10)
    			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED,0.23000000417232513D)
    			.add(EntityAttributes.GENERIC_FOLLOW_RANGE,10);
    }
	
	public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
     }

     public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
     }

     @Nullable
     public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        float f = difficulty.getClampedLocalDifficulty();
        

        this.applyAttributeModifiers(f);
        return (EntityData)entityData;
     }
     
     protected void applyAttributeModifiers(float chanceMultiplier) {
  	      this.initAttributes();
  	   }
}
