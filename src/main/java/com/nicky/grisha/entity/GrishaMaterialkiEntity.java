package com.nicky.grisha.entity;

import org.jetbrains.annotations.Nullable;

import com.nicky.grisha.entity.ai.goal.GrishaThrownAttackGoal;
import com.nicky.grisha.registry.GrishaItems;
import com.nicky.grisha.registry.GrishaThrownEntity;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
//import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

public class GrishaMaterialkiEntity extends GrishaEntity implements RangedAttackMob{
	private final GrishaThrownAttackGoal<GrishaMaterialkiEntity> throwAttackGoal = new GrishaThrownAttackGoal<GrishaMaterialkiEntity>(this, 1.0D, 20, 15.0F);
	private final MeleeAttackGoal meleeAttackGoal = new MeleeAttackGoal(this, 1.2D, false) {
	      public void stop() {
	         super.stop();
	         GrishaMaterialkiEntity.this.setAttacking(false);
	      }

	      public void start() {
	         super.start();
	         GrishaMaterialkiEntity.this.setAttacking(true);
	      }
	   };
	
	public GrishaMaterialkiEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world, 0);
        this.updateAttackType();
    }
	
	protected void initGoals() {
		this.goalSelector.add(3, new FleeEntityGoal<WolfEntity>(this, WolfEntity.class, 6.0F, 1.0D, 1.2D));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
		this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(6, new LookAroundGoal(this));
		this.targetSelector.add(1, new RevengeGoal(this, new Class[0]));
		//this.targetSelector.add(2, new FollowTargetGoal<PlayerEntity>(this, PlayerEntity.class, true));
	}
	
	public void updateAttackType() {
		if (this.world != null && !this.world.isClient) {
	         this.goalSelector.remove(this.meleeAttackGoal);
	         this.goalSelector.remove(this.throwAttackGoal);
	         int i = 20;
	         if (this.world.getDifficulty() != Difficulty.HARD) {
	            i = 40;
	         }

	         this.throwAttackGoal.setAttackInterval(i);
	         this.goalSelector.add(4, this.throwAttackGoal);

	      }
	   }
	
	public void attack(LivingEntity target, float pullProgress) {
	      ItemStack itemStack = this.getStackInHand(Hand.MAIN_HAND);
	      GrishaThrownEntity persistentProjectileEntity = this.createArrowProjectile(itemStack, 4);
	      persistentProjectileEntity.setThrownByMob();
	      double d = target.getX() - this.getX();
	      double e = target.getBodyY(0.3333333333333333D) - persistentProjectileEntity.getY();
	      double f = target.getZ() - this.getZ();
	      double g = Math.sqrt(d * d + f * f);
	      persistentProjectileEntity.setVelocity(d, e + g * 0.20000000298023224D, f, 1.6F, (float)(14 - this.world.getDifficulty().getId() * 4));
	      this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
	      this.world.spawnEntity(persistentProjectileEntity);
	   }

	   protected GrishaThrownEntity createArrowProjectile(ItemStack arrow, int damageModifier) {
	      return new GrishaThrownEntity(this.world, this, arrow,damageModifier);
	   }
	
	public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
     }

     public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
     }
     
     protected void initEquipment(LocalDifficulty difficulty) {
         super.initEquipment(difficulty);
         this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Registry.ITEM.getRandom(random)));
         this.equipStack(EquipmentSlot.HEAD, new ItemStack(GrishaItems.KEFTA_PURPLE_HOOD));
     }

     @Nullable
     public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        float f = difficulty.getClampedLocalDifficulty();
        this.initEquipment(difficulty);
        this.updateAttackType();

        this.applyAttributeModifiers(f);
        return (EntityData)entityData;
     }
     
     public boolean canUseRangedWeapon(RangedWeaponItem weapon) {
         return true;
      }
     
     public void equipStack(EquipmentSlot slot, ItemStack stack) {
         super.equipStack(slot, stack);
         if (!this.world.isClient) {
            this.updateAttackType();
         }

      }
     
     protected void applyAttributeModifiers(float chanceMultiplier) {
  	      this.initAttributes();
  	   }
}
