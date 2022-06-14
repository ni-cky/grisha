package com.nicky.grisha.entity;

import com.nicky.grisha.Grisha;
import com.nicky.grisha.gui.GrishaSmeltingController;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class MateralkiCraftingTableEntity extends AbstractFurnaceBlockEntity {
    protected MateralkiCraftingTableEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state, RecipeType<? extends AbstractCookingRecipe> recipeType) {
        super(blockEntityType, pos, state, recipeType);
    }

    @Override
    protected Text getContainerName() {
        return null;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return null;
    }
/*
	public MateralkiCraftingTableEntity(BlockPos pos,BlockState cachedState) {
        super(Grisha.MATERIALKI_CRAFTING_TABLE_ENTITY, pos, cachedState, Grisha.MATERIALKI_SMELTING_RECIPE_TYPE);
    }
 
    @Override
    public Text getContainerName() {
        //you should use a translation key instead but this is easier
        return Text.of("test furnace");
    }
 
    @Override
    public ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new GrishaSmeltingController(syncId, playerInventory, this, this.propertyDelegate);
    }
*/
}
