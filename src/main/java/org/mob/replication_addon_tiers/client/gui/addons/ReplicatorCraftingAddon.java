package org.mob.replication_addon_tiers.client.gui.addons;

import com.buuz135.replication.Replication;
import com.hrznstudio.titanium.client.screen.addon.BasicScreenAddon;
import com.hrznstudio.titanium.client.screen.asset.IAssetProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.mob.replication_addon_tiers.block.custom.ReplicatorAdvancedBlockEntity;

public class ReplicatorCraftingAddon extends BasicScreenAddon {
    private final ReplicatorAdvancedBlockEntity blockEntity;

    public ReplicatorCraftingAddon(int posX, int posY, ReplicatorAdvancedBlockEntity blockEntity) {
        super(posX, posY);
        this.blockEntity = blockEntity;
    }

    public int getXSize() {
        return 0;
    }

    public int getYSize() {
        return 0;
    }

    public void drawBackgroundLayer(GuiGraphics guiGraphics, Screen screen, IAssetProvider iAssetProvider, int guiX, int guiY, int mouseX, int mouseY, float partialTicks) {
        guiGraphics.blit(ResourceLocation.fromNamespaceAndPath(Replication.MOD_ID, "textures/gui/replication_terminal_extras.png"), guiX + 41, guiY + 26, 211, 125, 45, 36);
        guiGraphics.blit(ResourceLocation.fromNamespaceAndPath(Replication.MOD_ID, "textures/gui/replication_terminal_extras.png"), guiX + 100, guiY + 58, 250, 161, 6, 3);
        if (!this.blockEntity.getCraftingStack().isEmpty()) {
            guiGraphics.renderItem(this.blockEntity.getCraftingStack(), guiX + 67, guiY + 29);
        }

        float scale = 0.6F;
        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(scale, scale, scale);
        guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable("replication.infinite_mode").append(this.blockEntity.isInfinite() ? Component.translatable("replication.true") : Component.translatable("replication.false")).getString(), (float)((guiX + 41) * 1) / scale, (float)((guiY + 20) * 1) / scale, 7529831, false);
        guiGraphics.pose().popPose();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, 0.0F, 100.0F);
        guiGraphics.blit(ResourceLocation.fromNamespaceAndPath(Replication.MOD_ID, "textures/gui/replication_terminal_extras.png"), guiX + 46, guiY + 32, 232, 164, 12, 9);
        guiGraphics.pose().popPose();
        if (!this.blockEntity.getCraftingStack().isEmpty() && mouseX > guiX + 67 && mouseX < guiX + 67 + 16 && mouseY > guiY + 29 && mouseY < guiY + 29 + 16) {
            guiGraphics.renderTooltip(Minecraft.getInstance().font, this.blockEntity.getCraftingStack(), mouseX, mouseY);
        }

    }

    public void drawForegroundLayer(GuiGraphics guiGraphics, Screen screen, IAssetProvider iAssetProvider, int guiX, int guiY, int mouseX, int mouseY, float partialTicks) {
    }
}
