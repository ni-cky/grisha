package com.nicky.grisha.gui;

import com.nicky.grisha.Grisha;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.screen.PropertyDelegate;

public class GrishaSmeltingController extends AbstractFurnaceScreenHandler{
	public GrishaSmeltingController(int i, PlayerInventory playerInventory) {
        super(Grisha.MATERIALKI_SMELTING_SCREEN_HANDLER, Grisha.MATERIALKI_SMELTING_RECIPE_TYPE, RecipeBookCategory.FURNACE, i, playerInventory);
    }
 
    public GrishaSmeltingController(int i, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(Grisha.MATERIALKI_SMELTING_SCREEN_HANDLER, Grisha.MATERIALKI_SMELTING_RECIPE_TYPE, RecipeBookCategory.FURNACE, i, playerInventory, inventory, propertyDelegate);
    }
}


