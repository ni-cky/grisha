package com.nicky.grisha.entity.ai.goal;

import com.nicky.grisha.entity.GrishaEntity;

import net.minecraft.entity.ai.goal.MeleeAttackGoal;

public class GrishaAttackGoal extends MeleeAttackGoal {
   private final GrishaEntity grisha;
   private int ticks;

   public GrishaAttackGoal(GrishaEntity grisha, double speed, boolean pauseWhenMobIdle) {
      super(grisha, speed, pauseWhenMobIdle);
      this.grisha = grisha;
   }

   public void start() {
      super.start();
      this.ticks = 0;
   }

   public void stop() {
      super.stop();
      this.grisha.setAttacking(false);
   }

   public void tick() {
      super.tick();
      ++this.ticks;
      if (this.ticks >= 5 && this.getCooldown() < this.getMaxCooldown() / 2) {
         this.grisha.setAttacking(true);
      } else {
         this.grisha.setAttacking(false);
      }

   }
}

