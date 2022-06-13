package com.nicky.grisha.status_effects;

import com.nicky.grisha.damage_source.JurdaParemAddictionDamageSource;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

public class JurdaParemAddictionPhase3 extends StatusEffect{
	
	//Length of phase 3
	static int LENGTH = 200;
	
	public JurdaParemAddictionPhase3() {
	    super(
				StatusEffectCategory.HARMFUL, // whether beneficial or harmful for entities
	      0xbe5050); // color in RGB
	  }
	
	// This method is called every tick to check whether it should apply the status effect or not
	  @Override
	  public boolean canApplyUpdateEffect(int duration, int amplifier) {
	    // In our case, we just make it return true so that it applies the status effect every tick.
	    return duration == 1;
	  }
	  
	  @Override
	  public void applyUpdateEffect(LivingEntity entity, int amplifier) {
	    if (entity instanceof PlayerEntity) {
	      ((PlayerEntity) entity).damage(new JurdaParemAddictionDamageSource(), 200f);
	    }
	  }

}
