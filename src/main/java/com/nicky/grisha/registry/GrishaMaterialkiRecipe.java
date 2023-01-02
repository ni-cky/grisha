package com.nicky.grisha.registry;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.CookingRecipeCategory;
import net.minecraft.util.Identifier;

public class GrishaMaterialkiRecipe extends AbstractCookingRecipe {

	public GrishaMaterialkiRecipe(RecipeType<?> type, Identifier id, String group, CookingRecipeCategory category, Ingredient input, ItemStack output,
								  float experience, int cookTime) {
		super(type, id, group,category, input, output, experience, cookTime);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		// TODO Auto-generated method stub
		return null;
	}

}
