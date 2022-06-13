package com.nicky.grisha.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.feature.FeatureConfig;

public record WildJurdaFeatureConfig(IntProvider groupSize) implements FeatureConfig {
	  public static final Codec<WildJurdaFeatureConfig> CODEC = RecordCodecBuilder
			  .create(instance -> instance.group(
					  IntProvider
					  	.VALUE_CODEC
					  	.fieldOf("groupSize")
					  	.forGetter(WildJurdaFeatureConfig::groupSize)
					  ).apply(instance, 
							  instance
							  .stable(WildJurdaFeatureConfig::new)
							  ));
	}
