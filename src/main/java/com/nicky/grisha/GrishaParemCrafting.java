package com.nicky.grisha;

import java.util.LinkedList;

import com.nicky.grisha.registry.GrishaItems;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.TypedActionResult;

public class GrishaParemCrafting {
	
	private static ItemStack[] core_cloth = {new ItemStack(Items.WHITE_WOOL, 4),new ItemStack(Items.IRON_INGOT, 8),new ItemStack(GrishaItems.CORE_CLOTH, 1)};
	private static ItemStack[] iron_to_gold = {new ItemStack(Items.IRON_INGOT, 2),new ItemStack(Items.IRON_INGOT, 2),new ItemStack(Items.GOLD_INGOT, 4)};
	private static ItemStack[] iron_block_to_gold = {new ItemStack(Items.IRON_BLOCK, 1),new ItemStack(Items.IRON_BLOCK, 1),new ItemStack(Items.GOLD_BLOCK, 2)};
	private static ItemStack[] gold_to_iron = {new ItemStack(Items.GOLD_INGOT, 2),new ItemStack(Items.GOLD_INGOT, 2),new ItemStack(Items.IRON_INGOT, 4)};
	private static ItemStack[] gold_block_to_iron = {new ItemStack(Items.GOLD_BLOCK, 1),new ItemStack(Items.GOLD_BLOCK, 1),new ItemStack(Items.IRON_BLOCK, 2)};
	private static ItemStack[] gold_and_iron_to_diamond = {new ItemStack(Items.GOLD_BLOCK, 2),new ItemStack(Items.IRON_BLOCK, 2),new ItemStack(Items.DIAMOND_BLOCK, 1)};
	private static LinkedList<ItemStack[]> recipe_List = new LinkedList<ItemStack[]>();
	
	static {
		recipe_List.add(core_cloth);
		recipe_List.add(iron_to_gold);
		recipe_List.add(iron_block_to_gold);
		recipe_List.add(gold_to_iron);
		recipe_List.add(gold_block_to_iron);
		recipe_List.add(gold_and_iron_to_diamond);
	}
	
	public static boolean canCraft(PlayerEntity player) {
		return GrishaSmallScienceUtil.isOnParem(player) && GrishaSmallScience.isWearingMaterialki(player);
	}
	
	public static TypedActionResult<ItemStack> craft(PlayerEntity player){
		if(canCraft(player)) {
			LinkedList<TypedActionResult<ItemStack>> success = new LinkedList<TypedActionResult<ItemStack>>();
			
			Iterable<ItemStack> stacks = player.getHandItems();
			recipe_List.forEach((recipe)->{
				boolean[] applies = {false,false};
				stacks.forEach((stack)->{
						if(recipe[0].isItemEqual(stack) && stack.getCount() >= recipe[0].getCount() && !applies[0])
							applies[0] = true;
						else if(recipe[1].isItemEqual(stack) && stack.getCount() >= recipe[1].getCount())
							applies[1] = true;
				});
				if(applies[0] && applies[1]){
					stacks.forEach((stack)->{
							if(recipe[0].isItemEqual(stack))
								stack.decrement(recipe[0].getCount());
							else if(recipe[1].isItemEqual(stack))
								stack.decrement(recipe[1].getCount());
						
					});
					for(int i = 0;i < recipe[2].getCount(); i++)	
						player.dropItem(recipe[2].getItem(),0);
						success.add(TypedActionResult.success(recipe[2]));	
				}
			});
			if(success.isEmpty())
				success.add(TypedActionResult.fail(null));
			return success.getFirst();
		}
		return TypedActionResult.fail(null);
	}
}
