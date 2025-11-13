package org.mob.replication_addon_tiers.client;

import com.buuz135.replication.ReplicationAttachments;
import com.buuz135.replication.api.matter_fluid.MatterStack;
import com.hrznstudio.titanium.event.handler.EventManager;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import org.mob.replication_addon_tiers.Config;
import org.mob.replication_addon_tiers.block.custom.matterTank.MatterTankTier1BlockEntity;
import org.mob.replication_addon_tiers.block.custom.matterTank.MatterTankTier2BlockEntity;
import org.mob.replication_addon_tiers.block.custom.matterTank.MatterTankTier3BlockEntity;
import org.mob.replication_addon_tiers.block.custom.matterTank.MatterTankTier4BlockEntity;
import org.mob.replication_addon_tiers.client.render.MatterTankTier1Renderer;
import org.mob.replication_addon_tiers.client.render.MatterTankTier2Renderer;
import org.mob.replication_addon_tiers.client.render.MatterTankTier3Renderer;
import org.mob.replication_addon_tiers.client.render.MatterTankTier4Renderer;
import org.mob.replication_addon_tiers.registry.ModRegistry;

import java.text.DecimalFormat;

public class ClientEvents {

    public static void init() {
        EventManager.forge(ItemTooltipEvent.class).process(pre -> {
            if (ItemStack.isSameItem(pre.getItemStack(), new ItemStack(ModRegistry.MATTER_TANK_TIER_1.get())) && pre.getItemStack().has(ReplicationAttachments.TILE)) {
                var tag = pre.getItemStack().get(ReplicationAttachments.TILE);
                var capacity = 256000 * Config.tankTier1;
                var matterStack = MatterStack.loadMatterStackFromNBT(tag.contains("tank") ? tag.getCompound("tank") : tag.getCompound("lockableMatterTankBundle").getCompound("Tank"));
                pre.getToolTip().add(1, Component.translatable("tooltip.titanium.tank.amount").withStyle(ChatFormatting.GOLD).append(Component.literal(ChatFormatting.WHITE + new DecimalFormat().format(matterStack.getAmount()) + ChatFormatting.GOLD + "/" + ChatFormatting.WHITE + new DecimalFormat().format(capacity))).append(Component.translatable("tooltip.replication.tank.unit").withStyle(ChatFormatting.DARK_AQUA)));
                pre.getToolTip().add(1, Component.literal(ChatFormatting.GOLD + Component.translatable("tooltip.replication.tank.matter").getString()).append(matterStack.isEmpty() ? Component.translatable("tooltip.titanium.tank.empty").withStyle(ChatFormatting.WHITE) : Component.translatable(matterStack.getTranslationKey())).withStyle(ChatFormatting.WHITE));
            }
        }).subscribe();

        EventManager.forge(ItemTooltipEvent.class).process(pre -> {
            if (ItemStack.isSameItem(pre.getItemStack(), new ItemStack(ModRegistry.MATTER_TANK_TIER_2.get())) && pre.getItemStack().has(ReplicationAttachments.TILE)) {
                var tag = pre.getItemStack().get(ReplicationAttachments.TILE);
                var capacity = 256000 * Config.tankTier2;
                var matterStack = MatterStack.loadMatterStackFromNBT(tag.contains("tank") ? tag.getCompound("tank") : tag.getCompound("lockableMatterTankBundle").getCompound("Tank"));
                pre.getToolTip().add(1, Component.translatable("tooltip.titanium.tank.amount").withStyle(ChatFormatting.GOLD).append(Component.literal(ChatFormatting.WHITE + new DecimalFormat().format(matterStack.getAmount()) + ChatFormatting.GOLD + "/" + ChatFormatting.WHITE + new DecimalFormat().format(capacity))).append(Component.translatable("tooltip.replication.tank.unit").withStyle(ChatFormatting.DARK_AQUA)));
                pre.getToolTip().add(1, Component.literal(ChatFormatting.GOLD + Component.translatable("tooltip.replication.tank.matter").getString()).append(matterStack.isEmpty() ? Component.translatable("tooltip.titanium.tank.empty").withStyle(ChatFormatting.WHITE) : Component.translatable(matterStack.getTranslationKey())).withStyle(ChatFormatting.WHITE));
            }
        }).subscribe();

        EventManager.forge(ItemTooltipEvent.class).process(pre -> {
            if (ItemStack.isSameItem(pre.getItemStack(), new ItemStack(ModRegistry.MATTER_TANK_TIER_3.get())) && pre.getItemStack().has(ReplicationAttachments.TILE)) {
                var tag = pre.getItemStack().get(ReplicationAttachments.TILE);
                var capacity = 256000 * Config.tankTier3;
                var matterStack = MatterStack.loadMatterStackFromNBT(tag.contains("tank") ? tag.getCompound("tank") : tag.getCompound("lockableMatterTankBundle").getCompound("Tank"));
                pre.getToolTip().add(1, Component.translatable("tooltip.titanium.tank.amount").withStyle(ChatFormatting.GOLD).append(Component.literal(ChatFormatting.WHITE + new DecimalFormat().format(matterStack.getAmount()) + ChatFormatting.GOLD + "/" + ChatFormatting.WHITE + new DecimalFormat().format(capacity))).append(Component.translatable("tooltip.replication.tank.unit").withStyle(ChatFormatting.DARK_AQUA)));
                pre.getToolTip().add(1, Component.literal(ChatFormatting.GOLD + Component.translatable("tooltip.replication.tank.matter").getString()).append(matterStack.isEmpty() ? Component.translatable("tooltip.titanium.tank.empty").withStyle(ChatFormatting.WHITE) : Component.translatable(matterStack.getTranslationKey())).withStyle(ChatFormatting.WHITE));
            }
        }).subscribe();

        EventManager.forge(ItemTooltipEvent.class).process(pre -> {
            if (ItemStack.isSameItem(pre.getItemStack(), new ItemStack(ModRegistry.MATTER_TANK_TIER_4.get())) && pre.getItemStack().has(ReplicationAttachments.TILE)) {
                var tag = pre.getItemStack().get(ReplicationAttachments.TILE);
                var capacity = 256000 * Config.tankTier4;
                var matterStack = MatterStack.loadMatterStackFromNBT(tag.contains("tank") ? tag.getCompound("tank") : tag.getCompound("lockableMatterTankBundle").getCompound("Tank"));
                pre.getToolTip().add(1, Component.translatable("tooltip.titanium.tank.amount").withStyle(ChatFormatting.GOLD).append(Component.literal(ChatFormatting.WHITE + new DecimalFormat().format(matterStack.getAmount()) + ChatFormatting.GOLD + "/" + ChatFormatting.WHITE + new DecimalFormat().format(capacity))).append(Component.translatable("tooltip.replication.tank.unit").withStyle(ChatFormatting.DARK_AQUA)));
                pre.getToolTip().add(1, Component.literal(ChatFormatting.GOLD + Component.translatable("tooltip.replication.tank.matter").getString()).append(matterStack.isEmpty() ? Component.translatable("tooltip.titanium.tank.empty").withStyle(ChatFormatting.WHITE) : Component.translatable(matterStack.getTranslationKey())).withStyle(ChatFormatting.WHITE));
            }
        }).subscribe();


    }

    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer((BlockEntityType<? extends MatterTankTier1BlockEntity>) ModRegistry.MATTER_TANK_TIER_1_BE.get(), MatterTankTier1Renderer::new);
        event.registerBlockEntityRenderer((BlockEntityType<? extends MatterTankTier2BlockEntity>) ModRegistry.MATTER_TANK_TIER_2_BE.get(), MatterTankTier2Renderer::new);
        event.registerBlockEntityRenderer((BlockEntityType<? extends MatterTankTier3BlockEntity>) ModRegistry.MATTER_TANK_TIER_3_BE.get(), MatterTankTier3Renderer::new);
        event.registerBlockEntityRenderer((BlockEntityType<? extends MatterTankTier4BlockEntity>) ModRegistry.MATTER_TANK_TIER_4_BE.get(), MatterTankTier4Renderer::new);
    }
}