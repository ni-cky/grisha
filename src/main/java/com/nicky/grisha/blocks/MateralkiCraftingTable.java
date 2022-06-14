package com.nicky.grisha.blocks;

import com.nicky.grisha.entity.MateralkiCraftingTableEntity;

import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MateralkiCraftingTable extends AbstractFurnaceBlock {

	public MateralkiCraftingTable(Settings settings) {
        super(settings);
    }
	
	@Override
    public BlockEntity createBlockEntity(BlockPos arg0,BlockState world) {
        //return new MateralkiCraftingTableEntity(arg0,world);
        return null;
    }
 
    @Override
    public void openScreen(World world, BlockPos pos, PlayerEntity player) {
        //This is called by the onUse method inside AbstractFurnaceBlock so
        //it is a little bit different of how you open the screen for normal container
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof MateralkiCraftingTableEntity) {
            player.openHandledScreen((NamedScreenHandlerFactory)blockEntity);
            // Optional: increment player's stat
            player.incrementStat(Stats.INTERACT_WITH_FURNACE);
        }
    }
}
