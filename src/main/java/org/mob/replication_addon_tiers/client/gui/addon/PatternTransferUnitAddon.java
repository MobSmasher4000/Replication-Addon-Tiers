package org.mob.replication_addon_tiers.client.gui.addon;

import java.util.List;
import java.util.function.BooleanSupplier;

import com.hrznstudio.titanium.api.client.AssetTypes;
import com.hrznstudio.titanium.client.screen.addon.BasicButtonAddon;
import com.hrznstudio.titanium.client.screen.asset.IAssetProvider;
import com.hrznstudio.titanium.component.button.ButtonComponent;
import com.hrznstudio.titanium.util.AssetUtil;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class PatternTransferUnitAddon extends BasicButtonAddon {

    private final BooleanSupplier isMoveMode;

    public PatternTransferUnitAddon(ButtonComponent buttonComponent, BooleanSupplier isMoveMode) {
        super(buttonComponent);
        this.isMoveMode = isMoveMode;
    }

    @Override
    public int getXSize() {
        return 14;
    }

    @Override
    public int getYSize() {
        return 14;
    }

    @Override
    public void drawForegroundLayer(GuiGraphics guiGraphics, Screen screen, IAssetProvider assets, int guiX, int guiY, int mouseX, int mouseY, float partialTicks) {
        // no-op
    }

    @Override
    public void drawBackgroundLayer(GuiGraphics guiGraphics, Screen screen, IAssetProvider assets, int guiX, int guiY, int mouseX, int mouseY, float partialTicks) {
        int x = guiX + getPosX();
        int y = guiY + getPosY();
        var assetType = this.isMoveMode.getAsBoolean()
                ? AssetTypes.BUTTON_SIDENESS_PUSH
                : AssetTypes.BUTTON_SIDENESS_PULL;
        AssetUtil.drawAsset(guiGraphics, screen, assets.getAsset(assetType), x, y);
    }

    @Override
    public List<Component> getTooltipLines() {
        if (this.isMoveMode.getAsBoolean()) {
            return List.of(
                    Component.translatable("gui.replication_addon_tiers.pattern_transfer_unit.mode.move")
                            .withStyle(ChatFormatting.GOLD),
                    Component.translatable("gui.replication_addon_tiers.pattern_transfer_unit.mode.move.tooltip")
                            .withStyle(ChatFormatting.GRAY)
            );
        }
        return List.of(
                Component.translatable("gui.replication_addon_tiers.pattern_transfer_unit.mode.copy")
                        .withStyle(ChatFormatting.GREEN),
                Component.translatable("gui.replication_addon_tiers.pattern_transfer_unit.mode.copy.tooltip")
                        .withStyle(ChatFormatting.GRAY)
        );
    }
}