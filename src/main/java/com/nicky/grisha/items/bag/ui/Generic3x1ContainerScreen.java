package com.nicky.grisha.items.bag.ui;

import com.mojang.blaze3d.systems.RenderSystem;

import com.nicky.grisha.Grisha;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class Generic3x1ContainerScreen extends HandledScreen<Generic3x1ContainerScreenHandler> {
   private static final Identifier TEXTURE = new Identifier(Grisha.MOD_ID,"textures/gui/container/bag.png");
   private int offset = 0;

   public Generic3x1ContainerScreen(Generic3x1ContainerScreenHandler handler, PlayerInventory inventory, Text title) {
      super(handler, inventory, title);
   }

   protected void init() {
      super.init();
      this.titleX = (this.backgroundWidth - this.textRenderer.getWidth((StringVisitable)this.title)) / 2;
      this.titleY = 42+offset;
   }

   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.renderBackground(matrices);
      super.render(matrices, mouseX, mouseY, delta);
      this.drawMouseoverTooltip(matrices, mouseX, mouseY);
   }

   protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.setShaderTexture(0, TEXTURE);
      int i = (this.width - this.backgroundWidth) / 2;
      int j = (this.height - this.backgroundHeight) / 2;
      this.drawTexture(matrices, i, j+36+offset, 0, 0, this.backgroundWidth, this.backgroundHeight);
   }
}
