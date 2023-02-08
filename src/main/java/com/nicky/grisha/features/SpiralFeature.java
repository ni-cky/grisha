package com.nicky.grisha.features;

import com.mojang.serialization.Codec;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class SpiralFeature extends Feature<SpiralFeatureConfig> {
	  public SpiralFeature(Codec<SpiralFeatureConfig> configCodec) {
	    super(configCodec);
	  }
	 
	  @Override
	  public boolean generate(FeatureContext<SpiralFeatureConfig> context) {
	    BlockPos pos = context.getOrigin();
	    SpiralFeatureConfig config = context.getConfig();
	 
	    Direction offset = Direction.NORTH;
	    int height = config.height().get(context.getRandom());
	 
	    for (int y = 0; y < height; y++) {
	      offset = offset.rotateYClockwise();
	      BlockPos blockPos = pos.up(y).offset(offset);
	 
	      context.getWorld().setBlockState(blockPos, config.block().get(context.getRandom(), blockPos), 3);
	    }
	 
	    return true;
	  }
	}