package org.mob.replication_addon_tiers.client.gui.addons;

import com.buuz135.replication.Replication;
import com.hrznstudio.titanium.Titanium;
import com.hrznstudio.titanium.client.screen.addon.WidgetScreenAddon;
import com.hrznstudio.titanium.client.screen.asset.IAssetProvider;
import com.hrznstudio.titanium.network.locator.ILocatable;
import com.hrznstudio.titanium.network.messages.ButtonClickNetworkMessage;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.mob.replication_addon_tiers.block.ReplicatorAdvancedBlock;
import org.mob.replication_addon_tiers.block.custom.ReplicatorAdvancedBlockEntity;

import java.util.Objects;

public class ReplicatorMotorAddon extends WidgetScreenAddon {
    public static ResourceLocation TEXTURE;
    private final ReplicatorAdvancedBlockEntity blockEntity;
    private EditBox editBox;
    private String lastValue;

    public ReplicatorMotorAddon(ReplicatorAdvancedBlockEntity blockEntity, int posX, int posY) {
        super(posX, posY, new EditBox(Minecraft.getInstance().font, 85, 20, 160, 26, Component.translatable("itemGroup.search")));
        this.blockEntity = blockEntity;
        this.lastValue = "";
        this.editBox = (EditBox)this.getWidget();
        this.editBox.setValue("" + blockEntity.getMotorSpeedMultiplier());
        this.editBox.setFilter((s) -> {
            if (s.isEmpty()) {
                return true;
            } else {
                try {
                    int value = Integer.parseInt(s);
                    return value <= 100;
                } catch (NumberFormatException var2) {
                    return false;
                }
            }
        });
        this.editBox.setMaxLength(3);
        this.editBox.setBordered(false);
        this.editBox.setVisible(true);
        this.editBox.setTextColor(7529831);
    }

    public int getXSize() {
        return 0;
    }

    public int getYSize() {
        return 0;
    }

    public void drawBackgroundLayer(GuiGraphics guiGraphics, Screen screen, IAssetProvider iAssetProvider, int guiX, int guiY, int mouseX, int mouseY, float partialTicks) {
        if (this.blockEntity.getBlockState().hasProperty(ReplicatorAdvancedBlock.HAS_MOTOR) && (Boolean)this.blockEntity.getBlockState().getValue(ReplicatorAdvancedBlock.HAS_MOTOR)) {
            this.editBox.setResponder((s) -> {
                if (!s.isEmpty()) {
                    if (!this.lastValue.equals(s) && screen instanceof AbstractContainerScreen) {
                        AbstractContainerScreen<?> containerScreen = (AbstractContainerScreen)screen;
                        AbstractContainerMenu patt0$temp = containerScreen.getMenu();
                        if (patt0$temp instanceof ILocatable) {
                            ILocatable locatable = (ILocatable)patt0$temp;
                            CompoundTag compound = new CompoundTag();
                            compound.putInt("Multiplier", Integer.parseInt(s));
                            Titanium.NETWORK.sendToServer(new ButtonClickNetworkMessage(locatable.getLocatorInstance(), 124578, compound));
                            (new Thread(() -> {
                                try {
                                    Thread.sleep(5000L);
                                    this.editBox.setValue("" + this.blockEntity.getMotorSpeedMultiplier());
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            })).start();
                        }
                    }

                    this.lastValue = s;
                }
            });
            super.drawBackgroundLayer(guiGraphics, screen, iAssetProvider, guiX, guiY, mouseX, mouseY, partialTicks);
            guiGraphics.blit(TEXTURE, guiX + this.getPosX(), guiY + this.getPosY(), 0, 0, 160, 30);
            guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable("tooltip.replication_motor.acceleration"), guiX + this.getPosX() + 7, guiY + this.getPosY() + 8, 7529831, false);
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate((float)(11 + Minecraft.getInstance().font.width(Component.translatable("tooltip.replication_motor.acceleration").getString())), 8.0F, 0.0F);
            this.editBox.render(guiGraphics, mouseX, mouseY, partialTicks);
            guiGraphics.pose().popPose();
            guiGraphics.drawString(Minecraft.getInstance().font, "%", guiX + this.getPosX() + 12 + Minecraft.getInstance().font.width(Component.translatable("tooltip.replication_motor.acceleration").getString()) + Minecraft.getInstance().font.width("000"), guiY + this.getPosY() + 8, 7529831, false);
            Font var10001 = Minecraft.getInstance().font;
            MutableComponent var10002 = Component.translatable("tooltip.replication_motor.failure_chance").append(Component.literal("" + this.blockEntity.getFailureChance()).withStyle(ChatFormatting.RED)).append(" %");
            int var10003 = guiX + this.getPosX() + 7;
            int var10004 = guiY + this.getPosY() + 8;
            Objects.requireNonNull(Minecraft.getInstance().font);
            guiGraphics.drawString(var10001, var10002, var10003, var10004 + 9, 7529831, false);
        }

    }

    public void drawForegroundLayer(GuiGraphics guiGraphics, Screen screen, IAssetProvider iAssetProvider, int guiX, int guiY, int mouseX, int mouseY, float partialTicks) {
        if (this.blockEntity.getBlockState().hasProperty(ReplicatorAdvancedBlock.HAS_MOTOR) && (Boolean)this.blockEntity.getBlockState().getValue(ReplicatorAdvancedBlock.HAS_MOTOR)) {
        }

    }

    static {
        TEXTURE = ResourceLocation.fromNamespaceAndPath(Replication.MOD_ID, "textures/gui/toasts.png");
    }
}
