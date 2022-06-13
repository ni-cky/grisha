package com.nicky.grisha.status_effects;

import java.util.ArrayList;
import java.util.Collection;

import com.nicky.grisha.Grisha;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

public class JurdaParem extends StatusEffect{

	//Length of the trip
		public static int LENGTH = 3600;
	
	public JurdaParem() {
	    super(
				StatusEffectCategory.BENEFICIAL, // whether beneficial or harmful for entities
	      0xff8800); // color in RGB
	  }

	public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
		super.onApplied(entity,attributes,amplifier);
		Collection<StatusEffectInstance> list = entity.getStatusEffects();
		ArrayList<StatusEffect> y = new ArrayList<StatusEffect>();
		list.forEach((effect)->{
			StatusEffect effectType = effect.getEffectType();
			if(effectType.getCategory() == StatusEffectCategory.HARMFUL)
				y.add(effectType);
		});
		y.forEach((e)->entity.removeStatusEffect(e));
		/*entity.removeStatusEffect(Grisha.JURDA_PAREM_ADDICTION_PHASE1);
		entity.removeStatusEffect(Grisha.JURDA_PAREM_ADDICTION_PHASE2);
		entity.removeStatusEffect(Grisha.JURDA_PAREM_ADDICTION_PHASE3);*/
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
		      ((PlayerEntity) entity).addStatusEffect(new StatusEffectInstance(Grisha.JURDA_PAREM_ADDICTION_PHASE0,JurdaParemAddictionPhase1.LENGTH,0,false,false,true));
		    }
		  }

}
