package com.nicky.grisha.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;

/*public class GrishaCraftingController extends AbstractRecipeScreenHandler<CraftingInventory> {
	   public static final int field_30781 = 0;
	   private static final int field_30782 = 1;
	   private static final int field_30783 = 10;
	   private static final int field_30784 = 10;
	   private static final int field_30785 = 37;
	   private static final int field_30786 = 37;
	   private static final int field_30787 = 46;
	   private final CraftingInventory input;
	   private final CraftingResultInventory result;
	   private final ScreenHandlerContext context;
	   private final PlayerEntity player;

	   public GrishaCraftingController(int syncId, PlayerInventory playerInventory) {
	      this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
	   }

	   public GrishaCraftingController(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
	      super(Grisha.MATERIALKI_SCREEN_HANDLER, syncId);
	      this.input = new CraftingInventory(this, 3, 3);
	      this.result = new CraftingResultInventory();
	      this.context = context;
	      this.player = playerInventory.player;
	      this.addSlot(new CraftingResultSlot(playerInventory.player, this.input, this.result, 0, 124, 35));

	      int m;
	      int l;
	      for(m = 0; m < 3; ++m) {
	         for(l = 0; l < 3; ++l) {
	            this.addSlot(new Slot(this.input, l + m * 3, 30 + l * 18, 17 + m * 18));
	         }
	      }

	      for(m = 0; m < 3; ++m) {
	         for(l = 0; l < 9; ++l) {
	            this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
	         }
	      }

	      for(m = 0; m < 9; ++m) {
	         this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
	      }

	   }

	   protected static void updateResult(ScreenHandler handler, World world, PlayerEntity player, CraftingInventory craftingInventory, CraftingResultInventory resultInventory) {
	      if (!world.isClient) {
	         ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)player;
	         ItemStack itemStack = ItemStack.EMPTY;
	         Optional<GrishaMaterialkiCraftingRecipe> optional = world.getServer().getRecipeManager().getFirstMatch(Grisha.MATERIALKI_CRAFTING_RECIPE_TYPE, craftingInventory, world);
	         if (optional.isPresent()) {
	            CraftingRecipe craftingRecipe = (CraftingRecipe)optional.get();
	            if (resultInventory.shouldCraftRecipe(world, serverPlayerEntity, craftingRecipe)) {
	               itemStack = craftingRecipe.craft(craftingInventory);
	            }
	         }

	         resultInventory.setStack(0, itemStack);
	         handler.setPreviousTrackedSlot(0, itemStack);
	         serverPlayerEntity.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(handler.syncId, handler.nextRevision(), 0, itemStack));
	      }
	   }

	   public void onContentChanged(Inventory inventory) {
	      this.context.run((world, pos) -> {
	         updateResult(this, world, this.player, this.input, this.result);
	      });
	   }

	   public void populateRecipeFinder(RecipeMatcher finder) {
	      this.input.provideRecipeInputs(finder);
	   }

	   public void clearCraftingSlots() {
	      this.input.clear();
	      this.result.clear();
	   }

	   public boolean matches(Recipe<? super CraftingInventory> recipe) {
	      return recipe.matches(this.input, this.player.world);
	   }

	   public void close(PlayerEntity player) {
	      super.close(player);
	      this.context.run((world, pos) -> {
	         this.dropInventory(player, this.input);
	      });
	   }

	   public boolean canUse(PlayerEntity player) {
	      return true;
	   }

	   public ItemStack transferSlot(PlayerEntity player, int index) {
	      ItemStack itemStack = ItemStack.EMPTY;
	      Slot slot = (Slot)this.slots.get(index);
	      if (slot != null && slot.hasStack()) {
	         ItemStack itemStack2 = slot.getStack();
	         itemStack = itemStack2.copy();
	         if (index == 0) {
	            this.context.run((world, pos) -> {
	               itemStack2.getItem().onCraft(itemStack2, world, player);
	            });
	            if (!this.insertItem(itemStack2, 10, 46, true)) {
	               return ItemStack.EMPTY;
	            }

	            slot.onQuickTransfer(itemStack2, itemStack);
	         } else if (index >= 10 && index < 46) {
	            if (!this.insertItem(itemStack2, 1, 10, false)) {
	               if (index < 37) {
	                  if (!this.insertItem(itemStack2, 37, 46, false)) {
	                     return ItemStack.EMPTY;
	                  }
	               } else if (!this.insertItem(itemStack2, 10, 37, false)) {
	                  return ItemStack.EMPTY;
	               }
	            }
	         } else if (!this.insertItem(itemStack2, 10, 46, false)) {
	            return ItemStack.EMPTY;
	         }

	         if (itemStack2.isEmpty()) {
	            slot.setStack(ItemStack.EMPTY);
	         } else {
	            slot.markDirty();
	         }

	         if (itemStack2.getCount() == itemStack.getCount()) {
	            return ItemStack.EMPTY;
	         }

	         slot.onTakeItem(player, itemStack2);
	         if (index == 0) {
	            player.dropItem(itemStack2, false);
	         }
	      }

	      return itemStack;
	   }

	   public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
	      return slot.inventory != this.result && super.canInsertIntoSlot(stack, slot);
	   }

	   public int getCraftingResultSlotIndex() {
	      return 0;
	   }

	   public int getCraftingWidth() {
	      return this.input.getWidth();
	   }

	   public int getCraftingHeight() {
	      return this.input.getHeight();
	   }

	   public int getCraftingSlotCount() {
	      return 10;
	   }

	   public RecipeBookCategory getCategory() {
	      return RecipeBookCategory.CRAFTING;
	   }

	   public boolean canInsertIntoSlot(int index) {
	      return index != this.getCraftingResultSlotIndex();
	   }
	}*/


public class GrishaCraftingController extends CraftingScreenHandler{

	public GrishaCraftingController(int syncId, PlayerInventory playerInventory) {
	      this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
	   }
	
	public GrishaCraftingController(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
		super(syncId, playerInventory, context);
	}
	
	public boolean canUse(PlayerEntity player) {
	      return true;
	}
	
}