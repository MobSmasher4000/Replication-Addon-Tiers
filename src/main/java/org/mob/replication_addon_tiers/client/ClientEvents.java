package org.mob.replication_addon_tiers.client;

import com.buuz135.replication.ReplicationAttachments;
import com.buuz135.replication.ReplicationConfig;
import com.buuz135.replication.api.matter_fluid.MatterStack;
import com.buuz135.replication.block.tile.*;
import com.buuz135.replication.client.render.*;
import com.hrznstudio.titanium.event.handler.EventManager;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import org.mob.replication_addon_tiers.ModConfig;
import org.mob.replication_addon_tiers.ReplicationAddonTiersRegistry;
import org.mob.replication_addon_tiers.block.custom.matter_tank.MatterTankTierBlock;
import org.mob.replication_addon_tiers.block.custom.matter_tank.TankTier;
import org.mob.replication_addon_tiers.block.custom.replicator.*;
import org.mob.replication_addon_tiers.block.custom.matter_network_pipe.*;
import org.mob.replication_addon_tiers.block.custom.disintegrator.*;
import org.mob.replication_addon_tiers.block.entity.replicator.*;
import org.mob.replication_addon_tiers.block.entity.matter_network_pipe.*;
import org.mob.replication_addon_tiers.block.entity.matter_tank.*;
import org.mob.replication_addon_tiers.block.entity.disintegrator.*;
import org.mob.replication_addon_tiers.client.render.*;

import java.text.DecimalFormat;

public class ClientEvents {

    public static void init(){
        EventManager.forge(ItemTooltipEvent.class).process(pre -> {
            ItemStack stack = pre.getItemStack();

            if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof MatterTankTierBlock tierBlock) {

                long amount = 0;
                boolean isEmpty = true;
                Component matterComponent = Component.translatable("tooltip.titanium.tank.empty").withStyle(ChatFormatting.WHITE);

                if (stack.has(ReplicationAttachments.TILE)) {
                    var tag = stack.get(ReplicationAttachments.TILE);
                    var tankTag = tag.contains("tank") ? tag.getCompoundOrEmpty("tank") : tag.getCompoundOrEmpty("lockableMatterTankBundle").getCompoundOrEmpty("Tank");
                    var matterStack = MatterStack.loadMatterStackFromNBT(tankTag);

                    amount = (long) matterStack.getAmount();
                    isEmpty = matterStack.isEmpty();

                    if (!isEmpty) {
                        matterComponent = Component.translatable(matterStack.getTranslationKey()).withStyle(ChatFormatting.WHITE);
                    }
                }

                String capacityText = new DecimalFormat().format(tierBlock.getTier().getCapacity());

                pre.getToolTip().add(1, Component.translatable("tooltip.titanium.tank.amount")
                        .withStyle(ChatFormatting.GOLD)
                        .append(Component.literal(ChatFormatting.WHITE + new DecimalFormat().format(amount) + ChatFormatting.GOLD + "/" + ChatFormatting.WHITE + capacityText))
                        .append(Component.translatable("tooltip.replication.tank.unit").withStyle(ChatFormatting.DARK_AQUA)));

                pre.getToolTip().add(1, Component.literal(ChatFormatting.GOLD + Component.translatable("tooltip.replication.tank.matter").getString())
                        .append(matterComponent));
            }

            // Replicator
            // Advanced
            if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof AdvancedReplicatorBlock advancedReplicatorBlock){
                pre.getToolTip().add(Component.translatable("tooltip.replication_addon_tiers.replicator", ModConfig.Replicator.ADVANCED_REPLICATOR_MAX_PROGRESS * 2, ReplicationConfig.Replicator.MAX_PROGRESS * 2));
                pre.getToolTip().add(Component.translatable("tooltip.replication_addon_tiers.advanced_replicator_1"));

            }

            // Elite
            if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof EliteReplicatorBlock eliteReplicatorBlock){
                pre.getToolTip().add(Component.translatable("tooltip.replication_addon_tiers.replicator", ModConfig.Replicator.ELITE_REPLICATOR_MAX_PROGRESS * 2, ReplicationConfig.Replicator.MAX_PROGRESS * 2));
                pre.getToolTip().add(Component.translatable("tooltip.replication_addon_tiers.advanced_replicator_1"));

            }

            // Disintegrator
            // Advanced
            if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof AdvancedDisintegratorBlock advancedDisintegratorBlock){
                pre.getToolTip().add(Component.translatable("tooltip.replication_addon_tiers.disintegrator", ModConfig.Disintegrator.Advanced.MAX_PROGRESS, ReplicationConfig.Disintegrator.MAX_PROGRESS));
            }

            // Elite
            if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof EliteDisintegratorBlock eliteDisintegratorBlock){
                pre.getToolTip().add(Component.translatable("tooltip.replication_addon_tiers.disintegrator", ModConfig.Disintegrator.Elite.MAX_PROGRESS, ReplicationConfig.Disintegrator.MAX_PROGRESS));
            }

            // Matter Network Pipe
            // Advanced
            if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof AdvancedMatterPipeBlock advancedMatterPipeBlock){
                pre.getToolTip().add(Component.translatable("tooltip.replication_addon_tiers.matter_network_pipe", ModConfig.MatterPipe.ADVANCED_MATTER_PIPE * ReplicationConfig.MatterPipe.POWER_TRANSFER, ReplicationConfig.MatterPipe.POWER_TRANSFER));
            }

            // Elite
            if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof EliteMatterPipeBlock eliteMatterPipeBlock){
                pre.getToolTip().add(Component.translatable("tooltip.replication_addon_tiers.matter_network_pipe", ModConfig.MatterPipe.ELITE_MATTER_PIPE * ReplicationConfig.MatterPipe.POWER_TRANSFER, ReplicationConfig.MatterPipe.POWER_TRANSFER));
            }

            // Ultimate
            if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof UltimateMatterPipeBlock ultimateMatterPipeBlock){
                pre.getToolTip().add(Component.translatable("tooltip.replication_addon_tiers.matter_network_pipe", ModConfig.MatterPipe.ULTIMATE_MATTER_PIPE * ReplicationConfig.MatterPipe.POWER_TRANSFER, ReplicationConfig.MatterPipe.POWER_TRANSFER));
            }


        }).subscribe();

        EventManager.mod(ModelEvent.BakingCompleted.class).process(event -> {
            AdvancedReplicatorRenderer.setPlate(event.getModelManager().getStandaloneModel(ReplicatorRenderer.PLATE_KEY));
            EliteReplicatorRenderer.setPlate(event.getModelManager().getStandaloneModel(ReplicatorRenderer.PLATE_KEY));
            AdvancedDisintegratorRenderer.setBlade(event.getModelManager().getStandaloneModel(DisintegratorRenderer.BLADE_KEY));
            EliteDisintegratorRenderer.setBlade(event.getModelManager().getStandaloneModel(DisintegratorRenderer.BLADE_KEY));
        }).subscribe();

        EventManager.mod(EntityRenderersEvent.RegisterRenderers.class).process(event -> {
            event.registerBlockEntityRenderer((BlockEntityType<? extends AdvancedReplicatorBlockEntity>) ReplicationAddonTiersRegistry.Blocks.ADVANCED_REPLICATOR.type().get(), AdvancedReplicatorRenderer::new);
            event.registerBlockEntityRenderer((BlockEntityType<? extends EliteReplicatorBlockEntity>) ReplicationAddonTiersRegistry.Blocks.ELITE_REPLICATOR.type().get(), EliteReplicatorRenderer::new);

            event.registerBlockEntityRenderer((BlockEntityType<? extends AdvancedMatterPipeBlockEntity>) ReplicationAddonTiersRegistry.Blocks.ADVANCED_MATTER_NETWORK_PIPE.type().get(), AdvancedMatterPipeRenderer::new);
            event.registerBlockEntityRenderer((BlockEntityType<? extends EliteMatterPipeBlockEntity>) ReplicationAddonTiersRegistry.Blocks.ELITE_MATTER_NETWORK_PIPE.type().get(), EliteMatterPipeRenderer::new);
            event.registerBlockEntityRenderer((BlockEntityType<? extends UltimateMatterPipeBlockEntity>) ReplicationAddonTiersRegistry.Blocks.ULTIMATE_MATTER_NETWORK_PIPE.type().get(), UltimateMatterPipeRenderer::new);

            for (TankTier tier : TankTier.values()) {
                BlockEntityType<MatterTankTierBlockEntity> type = ReplicationAddonTiersRegistry.Blocks.getMatterTankType(tier);
                event.registerBlockEntityRenderer(type, MatterTankTierRenderer::new);
            }

            event.registerBlockEntityRenderer((BlockEntityType<? extends AdvancedDisintegratorBlockEntity>) ReplicationAddonTiersRegistry.Blocks.ADVANCED_DISINTEGRATOR.type().get(), AdvancedDisintegratorRenderer::new);
            event.registerBlockEntityRenderer((BlockEntityType<? extends EliteDisintegratorBlockEntity>) ReplicationAddonTiersRegistry.Blocks.ELITE_DISINTEGRATOR.type().get(), EliteDisintegratorRenderer::new);
        }).subscribe();
    }
}