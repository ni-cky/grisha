package com.nicky.grisha.entity;

import org.jetbrains.annotations.Nullable;

import com.nicky.grisha.GrishaSmallScience;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

public class GrishaCorporalkiEntity extends GrishaEntity {
	
	public GrishaCorporalkiEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world, 2);
    }

    public boolean tryAttack(Entity target) {
         boolean bl = super.tryAttack(target);
         if (bl) {
            float f = this.world.getLocalDifficulty(this.getBlockPos()).getLocalDifficulty();
            if (this.getMainHandStack().isEmpty() && this.isOnFire() && this.random.nextFloat() < f * 0.3F) {
               target.setOnFireFor(2 * (int)f);
            }
            	if(target instanceof LivingEntity) {
            		GrishaSmallScience.corporalkiActive(this,(LivingEntity)target);
            		System.out.println("Entity casts Active Corporalki.");
            	}
         }

         return bl;
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

    	   
    	   
    	   /*static {
    		   SENSOR_TYPES = ImmutableList.of(
    				   SensorType.NEAREST_HURT_GRISHA);
    		   MEMORY_MODULE_TYPES = ImmutableList.of(
    				   MemoryModuleType.HEAL_TARGET);
    	   }*/
}
