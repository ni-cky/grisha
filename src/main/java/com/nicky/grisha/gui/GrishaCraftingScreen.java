package com.nicky.grisha.gui;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class GrishaCraftingScreen extends HandledScreen<GrishaCraftingController>{
	
	protected GrishaCraftingController description;
	
	public GrishaCraftingScreen(GrishaCraftingController description,PlayerEntity player, Text title) {
		super(description, player.getInventory(), title);
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float partialTicks, int mouseX, int mouseY) {} //This is just an AbstractContainerScreen thing; most Screens don't work this way.
	
}
