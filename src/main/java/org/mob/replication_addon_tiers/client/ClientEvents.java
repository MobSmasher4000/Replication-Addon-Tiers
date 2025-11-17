package org.mob.replication_addon_tiers.client;

import com.buuz135.replication.Replication;
import com.buuz135.replication.ReplicationAttachments;
import com.buuz135.replication.api.matter_fluid.MatterStack;
import com.buuz135.replication.block.ReplicatorBlock;
import com.buuz135.replication.block.tile.ReplicatorBlockEntity;
import com.buuz135.replication.client.render.*;
import com.hrznstudio.titanium.event.handler.EventManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Transformation;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RenderHighlightEvent;
import net.neoforged.neoforge.client.model.SimpleModelState;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import org.joml.Matrix4f;
import org.mob.replication_addon_tiers.Config;
import org.mob.replication_addon_tiers.block.ReplicatorAdvancedBlock;
import org.mob.replication_addon_tiers.block.custom.ReplicatorAdvancedBlockEntity;
import org.mob.replication_addon_tiers.block.custom.matterTank.MatterTankTier1BlockEntity;
import org.mob.replication_addon_tiers.block.custom.matterTank.MatterTankTier2BlockEntity;
import org.mob.replication_addon_tiers.block.custom.matterTank.MatterTankTier3BlockEntity;
import org.mob.replication_addon_tiers.block.custom.matterTank.MatterTankTier4BlockEntity;
import org.mob.replication_addon_tiers.client.render.*;
import org.mob.replication_addon_tiers.registry.ModRegistry;

import java.text.DecimalFormat;
import java.util.Objects;

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

        EventManager.mod(ModelEvent.BakingCompleted.class).process((event) -> {
            ReplicatorRenderer.PLATE = bakeModel(ResourceLocation.fromNamespaceAndPath(Replication.MOD_ID, "block/replicator_plate"), event.getModelBakery());


        }).subscribe();
    }

    private static BakedModel bakeModel(ResourceLocation model, ModelBakery modelBakery) {
        ModelResourceLocation modelResourceLocation = new ModelResourceLocation(model, "standalone");
        UnbakedModel unbakedModel = modelBakery.getModel(model);
        Objects.requireNonNull(modelBakery);
        ModelBaker baker = new ModelBakery.ModelBakerImpl(modelBakery, (modelLoc, material) -> material.sprite(), modelResourceLocation);
        return unbakedModel.bake(baker, Material::sprite, new SimpleModelState(Transformation.identity()));
    }

    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer((BlockEntityType<? extends MatterTankTier1BlockEntity>) ModRegistry.MATTER_TANK_TIER_1_BE.get(), MatterTankTier1Renderer::new);
        event.registerBlockEntityRenderer((BlockEntityType<? extends MatterTankTier2BlockEntity>) ModRegistry.MATTER_TANK_TIER_2_BE.get(), MatterTankTier2Renderer::new);
        event.registerBlockEntityRenderer((BlockEntityType<? extends MatterTankTier3BlockEntity>) ModRegistry.MATTER_TANK_TIER_3_BE.get(), MatterTankTier3Renderer::new);
        event.registerBlockEntityRenderer((BlockEntityType<? extends MatterTankTier4BlockEntity>) ModRegistry.MATTER_TANK_TIER_4_BE.get(), MatterTankTier4Renderer::new);
        event.registerBlockEntityRenderer(ModRegistry.REPLICATOR_ADVANCED_BLOCK_BE.get(), (context) -> new ReplicatorAdvancedRenderer());
    }

    public static void blockOverlayEvent(RenderHighlightEvent.Block event) {
        if (event.getTarget() != null) {
            BlockHitResult traceResult = event.getTarget();
            BlockState og = Minecraft.getInstance().level.getBlockState(traceResult.getBlockPos());
            Block var5 = og.getBlock();
            if (var5 instanceof ReplicatorAdvancedBlock) {
                ReplicatorAdvancedBlock replicatorAdvancedBlock = (ReplicatorAdvancedBlock) var5;
                BlockEntity var18 = Minecraft.getInstance().level.getBlockEntity(traceResult.getBlockPos());
                if (var18 instanceof ReplicatorAdvancedBlockEntity) {
                    ReplicatorAdvancedBlockEntity replicatorAdvancedBlockEntity = (ReplicatorAdvancedBlockEntity) var18;
                    VoxelShape body = (VoxelShape)replicatorAdvancedBlock.getShapePlate(og).getFirst();
                    VoxelShape plate = (VoxelShape)replicatorAdvancedBlock.getShapePlate(og).getSecond();
                    BlockPos blockpos = event.getTarget().getBlockPos();
                    event.setCanceled(true);
                    PoseStack stack = new PoseStack();
                    stack.pushPose();
                    Camera info = event.getCamera();
                    double d0 = info.getPosition().x();
                    double d1 = info.getPosition().y();
                    double d2 = info.getPosition().z();
                    VertexConsumer builder = event.getMultiBufferSource().getBuffer(RenderType.LINES);
                    drawShape(stack, builder, body, (double)blockpos.getX() - d0, (double)blockpos.getY() - d1, (double)blockpos.getZ() - d2, 0.0F, 0.0F, 0.0F, 0.4F);
                    stack.translate(0.0F, -0.563F, 0.0F);
                    float progress = (float)replicatorAdvancedBlockEntity.getProgress() / (float)replicatorAdvancedBlockEntity.getMaxProgress();
                    stack.translate(0.0F, 0.563F * progress, 0.0F);
                    drawShape(stack, builder, plate, (double)blockpos.getX() - d0, (double)blockpos.getY() - d1, (double)blockpos.getZ() - d2, 0.0F, 0.0F, 0.0F, 0.4F);
                    stack.popPose();
                }
            }
        }

    }

    private static void drawShape(PoseStack matrixStackIn, VertexConsumer bufferIn, VoxelShape shapeIn, double xIn, double yIn, double zIn, float red, float green, float blue, float alpha) {
        Matrix4f matrix4f = matrixStackIn.last().pose();
        PoseStack.Pose posestack$pose = matrixStackIn.last();
        shapeIn.forAllEdges((p_230013_12_, p_230013_14_, p_230013_16_, p_230013_18_, p_230013_20_, p_230013_22_) -> {
            float f = (float)(p_230013_18_ - p_230013_12_);
            float f1 = (float)(p_230013_20_ - p_230013_14_);
            float f2 = (float)(p_230013_22_ - p_230013_16_);
            float f3 = Mth.sqrt(f * f + f1 * f1 + f2 * f2);
            f /= f3;
            f1 /= f3;
            f2 /= f3;
            bufferIn.addVertex(matrix4f, (float)(p_230013_12_ + xIn), (float)(p_230013_14_ + yIn), (float)(p_230013_16_ + zIn)).setColor(red, green, blue, alpha).setNormal(posestack$pose, f, f1, f2);
            bufferIn.addVertex(matrix4f, (float)(p_230013_18_ + xIn), (float)(p_230013_20_ + yIn), (float)(p_230013_22_ + zIn)).setColor(red, green, blue, alpha).setNormal(posestack$pose, f, f1, f2);
        });
    }

}