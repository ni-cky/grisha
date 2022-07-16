package com.nicky.grisha.entity;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import com.nicky.grisha.entity.ai.goal.GrishaAttackGoal;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
//import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

public class GrishaEntity extends HostileEntity {
	
	protected static final ImmutableList<SensorType<? extends Sensor<? super PiglinEntity>>> SENSOR_TYPES;
	protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULE_TYPES;
	protected int kefta;

	
	public GrishaEntity(EntityType<? extends HostileEntity> entityType, World world, int kefta) {
        super(entityType, world);
        this.experiencePoints = 10;
        this.kefta = kefta;// world.getRandom().nextInt(3);
    }

    protected void initGoals() {
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.initCustomGoals();
    }
    
	protected void initCustomGoals() {
    	this.goalSelector.add(2, new GrishaAttackGoal(this, 1.0D, false));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0D));
        //this.targetSelector.add(2, new FollowTargetGoal<PlayerEntity>(this, PlayerEntity.class, true));
     }
    
    public static DefaultAttributeContainer.Builder createGrishaAttributes(){
    	return HostileEntity.createHostileAttributes()
    			.add(EntityAttributes.GENERIC_MAX_HEALTH,10)
    			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED,0.23000000417232513D)
    			.add(EntityAttributes.GENERIC_FOLLOW_RANGE,10);
    }
    
    protected void initDataTracker() {
        super.initDataTracker();
    }
    
    protected int getXpToDrop(PlayerEntity player) {

        return super.getXpToDrop();
     }
    
    public void onTrackedDataSet(TrackedData<?> data) {

        super.onTrackedDataSet(data);
     }
    
    

     public void tick() {
        super.tick();
     }

     public void tickMovement() {
        if (this.isAlive()) {
           boolean bl = this.burnsInDaylight() && this.isAffectedByDaylight();
           if (bl) {
              ItemStack itemStack = this.getEquippedStack(EquipmentSlot.HEAD);
              if (!itemStack.isEmpty()) {
                 if (itemStack.isDamageable()) {
                    itemStack.setDamage(itemStack.getDamage() + this.random.nextInt(2));
                    if (itemStack.getDamage() >= itemStack.getMaxDamage()) {
                       this.sendEquipmentBreakStatus(EquipmentSlot.HEAD);
                       this.equipStack(EquipmentSlot.HEAD, ItemStack.EMPTY);
                    }
                 }

                 bl = false;
              }

              if (bl) {
                 this.setOnFireFor(8);
              }
           }
        }

        super.tickMovement();
     }
     
     protected boolean burnsInDaylight() {
         return false;
      }
     
     public boolean damage(DamageSource source, float amount) {
         if (!super.damage(source, amount)) {
            return false;
         } else if (!(this.world instanceof ServerWorld)) {
            return false;
         } else {
            LivingEntity livingEntity = this.getTarget();
            if (livingEntity == null && source.getAttacker() instanceof LivingEntity) {
               livingEntity = (LivingEntity)source.getAttacker();
            }
            return true;
         }
      }
     	
      public boolean tryAttack(Entity target) {
         boolean bl = super.tryAttack(target);
         if (bl) {
            float f = this.world.getLocalDifficulty(this.getBlockPos()).getLocalDifficulty();
            if (this.getMainHandStack().isEmpty() && this.isOnFire() && this.random.nextFloat() < f * 0.3F) {
               target.setOnFireFor(2 * (int)f);
            }
         }

         return bl;
      }
      
      protected SoundEvent getAmbientSound() {
          return SoundEvents.ENTITY_SHEEP_AMBIENT;
       }

       protected SoundEvent getHurtSound(DamageSource source) {
          return SoundEvents.ENTITY_PLAYER_HURT;
       }

       protected SoundEvent getDeathSound() {
          return SoundEvents.ENTITY_PLAYER_DEATH;
       }

       protected SoundEvent getStepSound() {
          return SoundEvents.ENTITY_ZOMBIE_STEP;
       }

       protected void playStepSound(BlockPos pos, BlockState state) {
          this.playSound(this.getStepSound(), 0.15F, 1.0F);
       }

       public EntityGroup getGroup() {
          return EntityGroup.DEFAULT;
       }
       
       protected void initEquipment(LocalDifficulty difficulty) {
          super.initEquipment(this.random, difficulty);
       }
       
       public void writeCustomDataToNbt(NbtCompound nbt) {
          super.writeCustomDataToNbt(nbt);
       }

       public void readCustomDataFromNbt(NbtCompound nbt) {
          super.readCustomDataFromNbt(nbt);
       }

       public boolean onKilledOther(ServerWorld world, LivingEntity other) {
           return super.onKilledOther(world, other);
       }
       
       protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
          return 1.74F;
       }

       public boolean canPickupItem(ItemStack stack) {
          return stack.isOf(Items.EGG) && this.hasVehicle() ? false : super.canPickupItem(stack);
       }

       public boolean canGather(ItemStack stack) {
          return stack.isOf(Items.GLOW_INK_SAC) ? false : super.canGather(stack);
       }
       
       @Nullable
       public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
          entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
          float f = difficulty.getClampedLocalDifficulty();
          this.setCanPickUpLoot(this.random.nextFloat() < 0.55F * f);
          if (entityData == null) {
             entityData = new GrishaData();
          }

          if (entityData instanceof GrishaData) {
        	 GrishaData grishaData = (GrishaData)entityData;
             this.initEquipment(difficulty);
             this.updateEnchantments(this.random,difficulty);
          }

          if (this.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
             LocalDate localDate = LocalDate.now();
             int i = localDate.get(ChronoField.DAY_OF_MONTH);
             int j = localDate.get(ChronoField.MONTH_OF_YEAR);
             if (j == 10 && i == 31 && this.random.nextFloat() < 0.25F) {
                this.equipStack(EquipmentSlot.HEAD, new ItemStack(this.random.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));
                this.armorDropChances[EquipmentSlot.HEAD.getEntitySlotId()] = 0.0F;
             }
          }

          this.applyAttributeModifiers(f);
          return (EntityData)entityData;
       }
       
       protected void applyAttributeModifiers(float chanceMultiplier) {
    	      this.initAttributes();
    	      double d = this.random.nextDouble() * 1.5D * (double)chanceMultiplier;
    	      if (d > 1.0D) {
    	         this.getAttributeInstance(EntityAttributes.GENERIC_FOLLOW_RANGE).addPersistentModifier(new EntityAttributeModifier("Random zombie-spawn bonus", d, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
    	      }

    	      if (this.random.nextFloat() < chanceMultiplier * 0.05F) {
    	         this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).addPersistentModifier(new EntityAttributeModifier("Leader grisha bonus", this.random.nextDouble() * 3.0D + 1.0D, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
    	      }

    	   }

    	   protected void initAttributes() {
    	      
    	   }

    	   public double getHeightOffset() {
    	      return this.isBaby() ? 0.0D : -0.45D;
    	   }

    	   protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
    	      super.dropEquipment(source, lootingMultiplier, allowDrops);
    	      Entity entity = source.getAttacker();
    	      if (entity instanceof CreeperEntity) {
    	         CreeperEntity creeperEntity = (CreeperEntity)entity;
    	         if (creeperEntity.shouldDropHead()) {
    	            ItemStack itemStack = this.getSkull();
    	            if (!itemStack.isEmpty()) {
    	               creeperEntity.onHeadDropped();
    	               this.dropStack(itemStack);
    	            }
    	         }
    	      }

    	   }
    	   
    	   protected boolean isDisallowedInPeaceful() {
    		      return false;
    		   }
    	   
    	   public boolean canImmediatelyDespawn(double distanceSquared) {
    		      return !this.isPersistent();
    		   }
    	   
    	   protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
    		      return GrishaEntityBrain.create(this, this.createBrainProfile().deserialize(dynamic));
    		   }
    	   
    	   public Brain<?> getBrain() {
    		      return super.getBrain();
    		   }
    	   
    	   /**
    	    * Returns the item stack this entity will drop when killed by a charged creeper.
    	    */
    	   protected ItemStack getSkull() {
    	      return new ItemStack(Items.PLAYER_HEAD);
    	   }
    	   
    	   public static class GrishaData implements EntityData {

    		      public GrishaData() {
    		    	  
    		      }
    		   }
    	   
    	   static {
    		   SENSOR_TYPES = ImmutableList.of(
    				   SensorType.NEAREST_LIVING_ENTITIES, 
    				   SensorType.NEAREST_PLAYERS, 
    				   SensorType.NEAREST_ITEMS, 
    				   SensorType.HURT_BY);
    		   MEMORY_MODULE_TYPES = ImmutableList.of(
    				   MemoryModuleType.LOOK_TARGET, 
    				   MemoryModuleType.MOBS, 
    				   MemoryModuleType.VISIBLE_MOBS, 
    				   MemoryModuleType.NEAREST_VISIBLE_PLAYER, 
    				   MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER, 
    				   MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, 
    				   MemoryModuleType.HURT_BY, 
    				   MemoryModuleType.HURT_BY_ENTITY, 
    				   MemoryModuleType.WALK_TARGET, 
    				   MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, 
    				   MemoryModuleType.ATTACK_TARGET, 
    				   MemoryModuleType.ATTACK_COOLING_DOWN, 
    				   MemoryModuleType.INTERACTION_TARGET, 
    				   MemoryModuleType.PATH, 
    				   MemoryModuleType.AVOID_TARGET, 
    				   MemoryModuleType.ADMIRING_ITEM, 
    				   MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM, 
    				   MemoryModuleType.ADMIRING_DISABLED, 
    				   MemoryModuleType.DISABLE_WALK_TO_ADMIRE_ITEM, 
    				   MemoryModuleType.CELEBRATE_LOCATION, 
    				   MemoryModuleType.DANCING, 
    				   MemoryModuleType.HUNTED_RECENTLY, 
    				   MemoryModuleType.NEAREST_VISIBLE_BABY_HOGLIN, 
    				   MemoryModuleType.NEAREST_VISIBLE_NEMESIS, 
    				   MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED, 
    				   MemoryModuleType.RIDE_TARGET, 
    				   MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT, 
    				   MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT, 
    				   MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN, 
    				   MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD, 
    				   MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM, 
    				   MemoryModuleType.ATE_RECENTLY, 
    				   MemoryModuleType.NEAREST_REPELLENT);
    	   }
}

