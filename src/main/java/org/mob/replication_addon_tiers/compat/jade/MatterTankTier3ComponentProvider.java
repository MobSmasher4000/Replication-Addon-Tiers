package org.mob.replication_addon_tiers.compat.jade;

import com.buuz135.replication.ReplicationConfig;
import com.buuz135.replication.api.matter_fluid.IMatterTank;
import com.buuz135.replication.api.matter_fluid.MatterStack;
import com.buuz135.replication.util.NumberUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.mob.replication_addon_tiers.Config;
import org.mob.replication_addon_tiers.ReplicationAddonTiers;
import org.mob.replication_addon_tiers.block.custom.matterTank.MatterTankTier3BlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.BoxStyle;
import snownee.jade.api.ui.IElementHelper;
import snownee.jade.impl.ui.ProgressElement;

import java.awt.*;

public class MatterTankTier3ComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    public static ResourceLocation MATTER_TANK_LOCATION;

    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (blockAccessor.getServerData().contains("MatterStack")) {
            MatterStack matterStack = MatterStack.loadMatterStackFromNBT(blockAccessor.getServerData().getCompound("MatterStack"));
            float[] floatColor = (float[])matterStack.getMatterType().getColor().get();
            Color color = new Color(floatColor[0], floatColor[1], floatColor[2], floatColor[3]);
            iTooltip.add(new ProgressElement((float)(matterStack.getAmount() / (double) ReplicationConfig.MatterTank.CAPACITY * Config.tankTier3), matterStack.isEmpty() ? Component.translatable("tooltip.titanium.tank.empty") : Component.translatable(matterStack.getTranslationKey()).append(" ").append(NumberUtils.getFormatedBigNumber(matterStack.getAmount())), IElementHelper.get().progressStyle().color(color.getRGB()).textColor(16777215), BoxStyle.getNestedBox(), false));
        }

    }

    public ResourceLocation getUid() {
        return MATTER_TANK_LOCATION;
    }

    public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
        MatterTankTier3BlockEntity blockEntity = (MatterTankTier3BlockEntity) blockAccessor.getBlockEntity();
        compoundTag.put("MatterStack", ((IMatterTank)blockEntity.getTanks().get(0)).getMatter().writeToNBT(new CompoundTag()));
    }

    static {
        MATTER_TANK_LOCATION = ResourceLocation.fromNamespaceAndPath(ReplicationAddonTiers.MOD_ID, "matter_tank_tier_3");
    }
}
