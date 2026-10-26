package org.mob.replication_addon_tiers.compat.jade;

import com.buuz135.replication.api.matter_fluid.MatterStack;
import com.buuz135.replication.util.NumberUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.mob.replication_addon_tiers.ReplicationAddonTiers;
import org.mob.replication_addon_tiers.block.custom.matter_tank.MatterTankTierBlock;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.BoxStyle;
import snownee.jade.api.ui.JadeUI;
import snownee.jade.api.view.ProgressView;

import java.awt.Color;

public class MatterTankTierProvider implements IBlockComponentProvider {

    public static final Identifier MATTER_TANK_LOCATION = Identifier.fromNamespaceAndPath(ReplicationAddonTiers.MOD_ID, "matter_tank_tier");

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (blockAccessor.getServerData().contains("MatterStack")) {
            CompoundTag matterTag = blockAccessor.getServerData().getCompound("MatterStack").get();
            MatterStack matterStack = MatterStack.loadMatterStackFromNBT(matterTag);

            float[] floatColor = matterStack.getMatterType().getColor().get();
            Color color = new Color(floatColor[0], floatColor[1], floatColor[2], floatColor[3]);

            int capacity = 1; // Fallback to avoid division by zero
            if (blockAccessor.getBlock() instanceof MatterTankTierBlock tankBlock) {
                capacity = tankBlock.getTier().getCapacity();
            }

            // Calculate progress bounded between 0.0 and 1.0
            float progress = (float) Math.max(0, Math.min(1, (double) matterStack.getAmount() / capacity));

            Component text = matterStack.isEmpty()
                    ? Component.translatable("tooltip.titanium.tank.empty")
                    : Component.translatable(matterStack.getTranslationKey())
                    .append(" ")
                    .append(NumberUtils.getFormatedBigNumber(matterStack.getAmount()));

            iTooltip.add(JadeUI.progress(new ProgressView(
                    ProgressView.Part.of(progress, color.getRGB()),
                    text,
                    JadeUI.progressStyle(),
                    BoxStyle.nestedBox()
            )));
        }
    }

    @Override
    public Identifier getUid() {
        return MATTER_TANK_LOCATION;
    }
}