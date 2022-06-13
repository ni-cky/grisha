package com.nicky.grisha.features;

import com.mojang.serialization.Codec;
import com.nicky.grisha.blocks.JurdaBlock;
import com.nicky.grisha.registry.GrishaBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;

public class WildJurdaFeature  extends Feature<WildJurdaFeatureConfig>{
	public WildJurdaFeature(Codec<WildJurdaFeatureConfig> configCodec) {
	    super(configCodec);
	  }
	 
	  @Override
	  public boolean generate(FeatureContext<WildJurdaFeatureConfig> context) {
	    BlockPos pos = context.getOrigin();
	    WildJurdaFeatureConfig config = context.getConfig();
	 
	    Direction offset = Direction.NORTH;
	    int groupSize = config.groupSize().get(context.getRandom());
	    
	    //context.getWorld().setBlockState(pos, BlockStateProvider.of(Blocks.WATER.getDefaultState()).getBlockState(context.getRandom(), pos), 3);


		  for (int y = 0; y < groupSize; y++) {
	     	offset = offset.rotateYClockwise();
	      	int i = context.getRandom().nextInt(y+2);
	     	BlockPos blockPos = pos.offset(offset,i);

			if(context.getWorld().getBlockState(blockPos.offset(Direction.DOWN)).isSolidBlock(context.getWorld(),blockPos))
	      		context.getWorld().setBlockState(blockPos, BlockStateProvider.of(GrishaBlocks.WILD_JURDA_BLOCK).getBlockState(context.getRandom(), blockPos), 3);

	      	offset = offset.rotateYClockwise();
	      	i = context.getRandom().nextInt(y+2);
	      	blockPos = pos.offset(offset,i);

			  if(context.getWorld().getBlockState(blockPos.offset(Direction.DOWN)).isSolidBlock(context.getWorld(),blockPos))
				  context.getWorld().setBlockState(blockPos, BlockStateProvider.of(GrishaBlocks.WILD_JURDA_BLOCK).getBlockState(context.getRandom(), blockPos), 3);
		  }

	    return true;
	  }
}
