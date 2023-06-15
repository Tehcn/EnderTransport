package dev.tehcn.endertransport.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.tehcn.endertransport.util.registry.RegistryHelper;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class AlloyFurnaceScreen extends HandledScreen<AlloyFurnaceScreenHandler> {
    private static final Identifier TEXTURE = RegistryHelper.id("textures/gui/alloy_furnace.png");

    public AlloyFurnaceScreen(AlloyFurnaceScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
        renderProgressArrow(context, x, y);
        renderFuelBar(context, x, y);
    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        if(handler.isCrafting()) {
            context.drawTexture(TEXTURE, x + 133, y + 33, 176, 0, 8, handler.getScaledProgress());
        }
    }

    private void renderFuelBar(DrawContext context, int x, int y) {
        context.drawTexture(TEXTURE, x + 55, y + 14, 184, 8, 19, handler.getScaledFuel());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, x, y);
    }
}
