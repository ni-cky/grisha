package com.nicky.grisha.entity;

import com.google.common.collect.ImmutableSet;

import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;

public class GrishaEntityBrain {

	public static Brain<?> create(GrishaEntity grishaEntity, Brain<?> brain) {
		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
	    brain.setDefaultActivity(Activity.IDLE);
	    brain.resetPossibleActivities();
	    return brain;
	}
   
}

