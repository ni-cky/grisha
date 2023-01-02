package com.nicky.grisha.recipe;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.nicky.grisha.Grisha;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.registry.Registry;
import net.minecraft.world.World;

public class GrishaMaterialkiCraftingRecipe implements CraftingRecipe {
	   private final Identifier id;
	   final String group;
	   final ItemStack output;
	   final DefaultedList<Ingredient> input;
	final int width;
	final int height;

	   public GrishaMaterialkiCraftingRecipe(Identifier id, String group, int width, int height, DefaultedList<Ingredient> input, ItemStack output) {
		   this.id = id;
		   this.group = group;
		   this.width = width;
		   this.height = height;
		   this.input = input;
		   this.output = output;
	   }

	   public Identifier getId() {
		      return this.id;
		   }

		   public RecipeSerializer<?> getSerializer() {
		      return Grisha.MATERIALKI_CRAFTING_RECIPE_SERIALIZER;
		   }

		   public String getGroup() {
		      return this.group;
		   }

		   public ItemStack getOutput() {
		      return this.output;
		   }

		   public DefaultedList<Ingredient> getIngredients() {
		      return this.input;
		   }

		   public boolean fits(int width, int height) {
		      return width >= this.width && height >= this.height;
		   }

		   public boolean matches(CraftingInventory craftingInventory, World world) {
		      for(int i = 0; i <= craftingInventory.getWidth() - this.width; ++i) {
		         for(int j = 0; j <= craftingInventory.getHeight() - this.height; ++j) {
		            if (this.matchesPattern(craftingInventory, i, j, true)) {
		               return true;
		            }

		            if (this.matchesPattern(craftingInventory, i, j, false)) {
		               return true;
		            }
		         }
		      }

		      return false;
		   }

		   private boolean matchesPattern(CraftingInventory inv, int offsetX, int offsetY, boolean flipped) {
		      for(int i = 0; i < inv.getWidth(); ++i) {
		         for(int j = 0; j < inv.getHeight(); ++j) {
		            int k = i - offsetX;
		            int l = j - offsetY;
		            Ingredient ingredient = Ingredient.EMPTY;
		            if (k >= 0 && l >= 0 && k < this.width && l < this.height) {
		               if (flipped) {
		                  ingredient = this.input.get(this.width - k - 1 + l * this.width);
		               } else {
		                  ingredient = this.input.get(k + l * this.width);
		               }
		            }

		            if (!ingredient.test(inv.getStack(i + j * inv.getWidth()))) {
		               return false;
		            }
		         }
		      }

		      return true;
		   }

		   public ItemStack craft(CraftingInventory craftingInventory) {
		      return this.getOutput().copy();
		   }

		   public int getWidth() {
		      return this.width;
		   }

		   public int getHeight() {
		      return this.height;
		   }

		   /**
		    * Compiles a pattern and series of symbols into a list of ingredients (the matrix) suitable for matching
		    * against a crafting grid.
		    */
		   static DefaultedList<Ingredient> createPatternMatrix(String[] pattern, Map<String, Ingredient> symbols, int width, int height) {
		      DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(width * height, Ingredient.EMPTY);
		      Set<String> set = Sets.newHashSet((Iterable<String>)symbols.keySet());
		      set.remove(" ");

		      for(int i = 0; i < pattern.length; ++i) {
		         for(int j = 0; j < pattern[i].length(); ++j) {
		            String string = pattern[i].substring(j, j + 1);
		            Ingredient ingredient = symbols.get(string);
		            if (ingredient == null) {
		               throw new JsonSyntaxException("Pattern references symbol '" + string + "' but it's not defined in the key");
		            }

		            set.remove(string);
		            defaultedList.set(j + width * i, ingredient);
		         }
		      }

		      if (!set.isEmpty()) {
		         throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
		      } else {
		         return defaultedList;
		      }
		   }

		   /**
		    * Removes empty space from around the recipe pattern.
		    * 
		    * <p>Turns patterns such as:</p>
		    * <pre>
		    * {@code
		    * "   o"
		    * "   a"
		    * "    "
		    * }
		    * </pre>
		    * Into:
		    * <pre>
		    * {@code
		    * "o"
		    * "a"
		    * }
		    * </pre>
		    * 
		    * @return a new recipe pattern with all leading and trailing empty rows/columns removed
		    */
		   @VisibleForTesting
		   static String[] removePadding(String... pattern) {
		      int i = Integer.MAX_VALUE;
		      int j = 0;
		      int k = 0;
		      int l = 0;

		      for(int m = 0; m < pattern.length; ++m) {
		         String string = pattern[m];
		         i = Math.min(i, findFirstSymbol(string));
		         int n = findLastSymbol(string);
		         j = Math.max(j, n);
		         if (n < 0) {
		            if (k == m) {
		               ++k;
		            }

		            ++l;
		         } else {
		            l = 0;
		         }
		      }

		      if (pattern.length == l) {
		         return new String[0];
		      } else {
		         String[] strings = new String[pattern.length - l - k];

		         for(int o = 0; o < strings.length; ++o) {
		            strings[o] = pattern[o + k].substring(i, j + 1);
		         }

		         return strings;
		      }
		   }

		   public boolean isEmpty() {
		      DefaultedList<Ingredient> defaultedList = this.getIngredients();
		      return defaultedList.isEmpty() || defaultedList.stream().filter((ingredient) -> {
		         return !ingredient.isEmpty();
		      }).anyMatch((ingredient) -> {
		         return ingredient.getMatchingStacks().length == 0;
		      });
		   }

		   private static int findFirstSymbol(String line) {
		      int i;
		      for(i = 0; i < line.length() && line.charAt(i) == ' '; ++i) {
		      }

		      return i;
		   }

		   private static int findLastSymbol(String pattern) {
		      int i;
		      for(i = pattern.length() - 1; i >= 0 && pattern.charAt(i) == ' '; --i) {
		      }

		      return i;
		   }

		   static String[] getPattern(JsonArray json) {
		      String[] strings = new String[json.size()];
		      if (strings.length > 3) {
		         throw new JsonSyntaxException("Invalid pattern: too many rows, 3 is maximum");
		      } else if (strings.length == 0) {
		         throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
		      } else {
		         for(int i = 0; i < strings.length; ++i) {
		            String string = JsonHelper.asString(json.get(i), "pattern[" + i + "]");
		            if (string.length() > 3) {
		               throw new JsonSyntaxException("Invalid pattern: too many columns, 3 is maximum");
		            }

		            if (i > 0 && strings[0].length() != string.length()) {
		               throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
		            }

		            strings[i] = string;
		         }

		         return strings;
		      }
		   }

		   /**
		    * Reads the pattern symbols.
		    * 
		    * @return a mapping from a symbol to the ingredient it represents
		    */
		   static Map<String, Ingredient> readSymbols(JsonObject json) {
		      Map<String, Ingredient> map = Maps.newHashMap();
		      Iterator<Entry<String, JsonElement>> var2 = json.entrySet().iterator();

		      while(var2.hasNext()) {
		         Entry<String, JsonElement> entry = (Entry<String, JsonElement>)var2.next();
		         if (entry.getKey().length() != 1) {
		            throw new JsonSyntaxException("Invalid key entry: '" + entry.getKey() + "' is an invalid symbol (must be 1 character only).");
		         }

		         if (" ".equals(entry.getKey())) {
		            throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
		         }

		         map.put(entry.getKey(), Ingredient.fromJson(entry.getValue()));
		      }

		      map.put(" ", Ingredient.EMPTY);
		      return map;
		   }

		   public static ItemStack outputFromJson(JsonObject json) {
		      Item item = getItem(json);
		      if (json.has("data")) {
		         throw new JsonParseException("Disallowed data tag found");
		      } else {
		         int i = JsonHelper.getInt(json, "count", 1);
		         if (i < 1) {
		            throw new JsonSyntaxException("Invalid output count: " + i);
		         } else {
		            return new ItemStack(item, i);
		         }
		      }
		   }

		   public static Item getItem(JsonObject json) {
		      String string = JsonHelper.getString(json, "item");
		      Item item = (Item)Registry.ITEM.getOrEmpty(new Identifier(string)).orElseThrow(() -> {
		         return new JsonSyntaxException("Unknown item '" + string + "'");
		      });
		      if (item == Items.AIR) {
		         throw new JsonSyntaxException("Invalid item: " + string);
		      } else {
		         return item;
		      }
		   }
	}

/*public class GrishaMaterialkiCraftingRecipe extends ShapedRecipe {

	
	
	//You can add as much inputs as you want here.
		//It is important to always use Ingredient, so you can support tags.
	 
		public GrishaMaterialkiCraftingRecipe(Identifier id, String group, int width, int height, DefaultedList<Ingredient> input, ItemStack output) {
			super(id, group, width, height, input, output);
		}

		
	    public RecipeSerializer<?> getSerializer() {
	        return RecipeSerializer.SHAPELESS;
	        //return Grisha.MATERIALKI_CRAFTING_RECIPE_SERIALIZER;
	    }
		
		public static class GrishaType implements  RecipeType<GrishaMaterialkiCraftingRecipe> {
			private GrishaType() {}
			public static final GrishaType INSTANCE = new GrishaType();
			public static final String ID = "materialki_recipe";
		}
		
		/*@Override
		public RecipeType<?> getType() {
			return GrishaType.INSTANCE;
		}


}
*/