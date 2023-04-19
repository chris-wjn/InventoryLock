package net.cwjn.invlock;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.cwjn.invlock.network.Network;
import net.cwjn.invlock.network.UpdateServerPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class LockButton extends AbstractButton {

    private static final ResourceLocation ICONS =
            new ResourceLocation(Invlock.MOD_ID, "textures/gui/icons.png");

    public LockButton(int x, int y) {
        super(x, y, 31, 28, Component.empty());
    }

    @Override
    public void render(@NotNull PoseStack matrix, int mouseX, int mouseY, float pTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        if (!(minecraft.screen instanceof InventoryScreen) || !((InventoryScreen) minecraft.screen).getRecipeBookComponent().isVisible()) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, ICONS);
            blit(matrix, x, y, Invlock.isLocked ? 0 : 8, 16, 8, 8);
        }
    }

    @Override
    public void onPress() {
        Invlock.isLocked = !Invlock.isLocked;
        Network.INSTANCE.sendToServer(new UpdateServerPacket(Invlock.isLocked));
    }

    @Override
    public void updateNarration(@NotNull NarrationElementOutput p_169152_) {

    }

}