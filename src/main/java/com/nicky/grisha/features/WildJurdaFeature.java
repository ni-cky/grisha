package com.nicky.grisha.features;

import com.mojang.serialization.Codec;
import com.nicky.grisha.Grisha;
import com.nicky.grisha.blocks.JurdaBlock;
import com.nicky.grisha.registry.GrishaBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
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
		StructureWorldAccess world = context.getWorld();
	    BlockPos pos = context.getOrigin();
	    WildJurdaFeatureConfig config = context.getConfig();

		//int number = config.number();
		Identifier blockID = new Identifier(Grisha.MOD_ID,"wild_jurda_block");

		  BlockState blockState = Registries.BLOCK.get(blockID).getDefaultState();
//        ensure the ID is okay
		  if (blockState == null) throw new IllegalStateException(blockID + " could not be parsed to a valid block identifier!");

	    Direction offset = Direction.NORTH;
	    int groupSize = config.groupSize().get(context.getRandom());
	    
	    //context.getWorld().setBlockState(pos, BlockStateProvider.of(Blocks.WATER.getDefaultState()).getBlockState(context.getRandom(), pos), 3);


		  for (int y = 0; y < groupSize; y++) {
	     	offset = offset.rotateYClockwise();
	      	int i = context.getRandom().nextInt(y+2);
	     	BlockPos blockPos = pos.offset(offset,i);

			if(context.getWorld().getBlockState(blockPos.offset(Direction.DOWN)).isSolidBlock(context.getWorld(),blockPos))
	      		context.getWorld().setBlockState(blockPos, blockState,3);

	      	offset = offset.rotateYClockwise();
	      	i = context.getRandom().nextInt(y+2);
	      	blockPos = pos.offset(offset,i);

			  if(context.getWorld().getBlockState(blockPos.offset(Direction.DOWN)).isSolidBlock(context.getWorld(),blockPos))
				  context.getWorld().setBlockState(blockPos, blockState, 3);
		  }

	    return true;
	  }
}
