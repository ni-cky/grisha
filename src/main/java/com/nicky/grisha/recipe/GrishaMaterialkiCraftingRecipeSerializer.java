package com.nicky.grisha.recipe;

import java.util.Iterator;
import java.util.Map;

import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;

public class GrishaMaterialkiCraftingRecipeSerializer implements RecipeSerializer<GrishaMaterialkiCraftingRecipe>{

	public GrishaMaterialkiCraftingRecipe read(Identifier identifier, JsonObject jsonObject) {
        String string = JsonHelper.getString(jsonObject, "group", "");
        Map<String, Ingredient> map = GrishaMaterialkiCraftingRecipe.readSymbols(JsonHelper.getObject(jsonObject, "key"));
        String[] strings = GrishaMaterialkiCraftingRecipe.removePadding(GrishaMaterialkiCraftingRecipe.getPattern(JsonHelper.getArray(jsonObject, "pattern")));
        int i = strings[0].length();
        int j = strings.length;
        DefaultedList<Ingredient> defaultedList = GrishaMaterialkiCraftingRecipe.createPatternMatrix(strings, map, i, j);
        ItemStack itemStack = ShapedRecipe.outputFromJson(JsonHelper.getObject(jsonObject, "result"));
        return new GrishaMaterialkiCraftingRecipe(identifier, string, i, j, defaultedList, itemStack);
     }

     public GrishaMaterialkiCraftingRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
        int i = packetByteBuf.readVarInt();
        int j = packetByteBuf.readVarInt();
        String string = packetByteBuf.readString();
        DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i * j, Ingredient.EMPTY);

        for(int k = 0; k < defaultedList.size(); ++k) {
           defaultedList.set(k, Ingredient.fromPacket(packetByteBuf));
        }

        ItemStack itemStack = packetByteBuf.readItemStack();
        return new GrishaMaterialkiCraftingRecipe(identifier, string, i, j, defaultedList, itemStack);
     }

     public void write(PacketByteBuf packetByteBuf, GrishaMaterialkiCraftingRecipe shapedRecipe) {
        packetByteBuf.writeVarInt(shapedRecipe.width);
        packetByteBuf.writeVarInt(shapedRecipe.height);
        packetByteBuf.writeString(shapedRecipe.group);
        Iterator<Ingredient> var3 = shapedRecipe.input.iterator();

        while(var3.hasNext()) {
           Ingredient ingredient = (Ingredient)var3.next();
           ingredient.write(packetByteBuf);
        }

        packetByteBuf.writeItemStack(shapedRecipe.output);
     }

     
     public interface RecipeFactory<T> {
         T create(Identifier id, String group, Ingredient input, ItemStack output);
      }
}
