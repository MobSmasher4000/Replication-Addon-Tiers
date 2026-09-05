package org.mob.replication_addon_tiers.client;

import com.buuz135.replication.Replication;
import com.buuz135.replication.ReplicationAttachments;
import com.buuz135.replication.ReplicationConfig;
import com.buuz135.replication.api.matter_fluid.MatterStack;
import com.hrznstudio.titanium.event.handler.EventManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Transformation;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.model.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.model.SimpleModelState;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import org.joml.Matrix4f;
import org.mob.replication_addon_tiers.Config;
import org.mob.replication_addon_tiers.block.custom.AdvancedReplicatorBlockEntity;
import org.mob.replication_addon_tiers.block.custom.matterTank.*;
import org.mob.replication_addon_tiers.client.render.*;
import org.mob.replication_addon_tiers.registry.ModRegistry;

import java.text.DecimalFormat;

public class ClientEvents {

    public static void init() {
        final int ORIGINAL_CAPACITY = ReplicationConfig.MatterTank.CAPACITY;
        EventManager.forge(ItemTooltipEvent.class).process(pre -> {
            if (ItemStack.isSameItem(pre.getItemStack(), new ItemStack(ModRegistry.MATTER_TANK_TIER_1.get())) && pre.getItemStack().has(ReplicationAttachments.TILE)) {
                var tag = pre.getItemStack().get(ReplicationAttachments.TILE);
                var capacity = ORIGINAL_CAPACITY * Config.tankTier1;
                var matterStack = MatterStack.loadMatterStackFromNBT(tag.contains("tank") ? tag.getCompound("tank") : tag.getCompound("lockableMatterTankBundle").getCompound("Tank"));
                pre.getToolTip().add(1, Component.translatable("tooltip.titanium.tank.amount").withStyle(ChatFormatting.GOLD).append(Component.literal(ChatFormatting.WHITE + new DecimalFormat().format(matterStack.getAmount()) + ChatFormatting.GOLD + "/" + ChatFormatting.WHITE + new DecimalFormat().format(capacity))).append(Component.translatable("tooltip.replication.tank.unit").withStyle(ChatFormatting.DARK_AQUA)));
                pre.getToolTip().add(1, Component.literal(ChatFormatting.GOLD + Component.translatable("tooltip.replication.tank.matter").getString()).append(matterStack.isEmpty() ? Component.translatable("tooltip.titanium.tank.empty").withStyle(ChatFormatting.WHITE) : Component.translatable(matterStack.getTranslationKey())).withStyle(ChatFormatting.WHITE));
            } else if (ItemStack.isSameItem(pre.getItemStack(),new ItemStack(ModRegistry.MATTER_TANK_TIER_1.get()))){
                var capacity = ORIGINAL_CAPACITY * Config.tankTier1;
                pre.getToolTip().add(Component.literal(Component.translatable("tooltip.replication_addon_tiers.capacity").getString() + capacity));
            }
        }).subscribe();

        EventManager.forge(ItemTooltipEvent.class).process(pre -> {
            if (ItemStack.isSameItem(pre.getItemStack(), new ItemStack(ModRegistry.MATTER_TANK_TIER_2.get())) && pre.getItemStack().has(ReplicationAttachments.TILE)) {
                var tag = pre.getItemStack().get(ReplicationAttachments.TILE);
                var capacity = ORIGINAL_CAPACITY * Config.tankTier2;
                var matterStack = MatterStack.loadMatterStackFromNBT(tag.contains("tank") ? tag.getCompound("tank") : tag.getCompound("lockableMatterTankBundle").getCompound("Tank"));
                pre.getToolTip().add(1, Component.translatable("tooltip.titanium.tank.amount").withStyle(ChatFormatting.GOLD).append(Component.literal(ChatFormatting.WHITE + new DecimalFormat().format(matterStack.getAmount()) + ChatFormatting.GOLD + "/" + ChatFormatting.WHITE + new DecimalFormat().format(capacity))).append(Component.translatable("tooltip.replication.tank.unit").withStyle(ChatFormatting.DARK_AQUA)));
                pre.getToolTip().add(1, Component.literal(ChatFormatting.GOLD + Component.translatable("tooltip.replication.tank.matter").getString()).append(matterStack.isEmpty() ? Component.translatable("tooltip.titanium.tank.empty").withStyle(ChatFormatting.WHITE) : Component.translatable(matterStack.getTranslationKey())).withStyle(ChatFormatting.WHITE));
            } else if (ItemStack.isSameItem(pre.getItemStack(),new ItemStack(ModRegistry.MATTER_TANK_TIER_2.get()))){
                var capacity = ORIGINAL_CAPACITY * Config.tankTier2;
                pre.getToolTip().add(Component.literal(Component.translatable("tooltip.replication_addon_tiers.capacity").getString() + capacity));
            }
        }).subscribe();

        EventManager.forge(ItemTooltipEvent.class).process(pre -> {
            if (ItemStack.isSameItem(pre.getItemStack(), new ItemStack(ModRegistry.MATTER_TANK_TIER_3.get())) && pre.getItemStack().has(ReplicationAttachments.TILE)) {
                var tag = pre.getItemStack().get(ReplicationAttachments.TILE);
                var capacity = ORIGINAL_CAPACITY * Config.tankTier3;
                var matterStack = MatterStack.loadMatterStackFromNBT(tag.contains("tank") ? tag.getCompound("tank") : tag.getCompound("lockableMatterTankBundle").getCompound("Tank"));
                pre.getToolTip().add(1, Component.translatable("tooltip.titanium.tank.amount").withStyle(ChatFormatting.GOLD).append(Component.literal(ChatFormatting.WHITE + new DecimalFormat().format(matterStack.getAmount()) + ChatFormatting.GOLD + "/" + ChatFormatting.WHITE + new DecimalFormat().format(capacity))).append(Component.translatable("tooltip.replication.tank.unit").withStyle(ChatFormatting.DARK_AQUA)));
                pre.getToolTip().add(1, Component.literal(ChatFormatting.GOLD + Component.translatable("tooltip.replication.tank.matter").getString()).append(matterStack.isEmpty() ? Component.translatable("tooltip.titanium.tank.empty").withStyle(ChatFormatting.WHITE) : Component.translatable(matterStack.getTranslationKey())).withStyle(ChatFormatting.WHITE));
            } else if (ItemStack.isSameItem(pre.getItemStack(),new ItemStack(ModRegistry.MATTER_TANK_TIER_3.get()))){
                var capacity = ORIGINAL_CAPACITY * Config.tankTier3;
                pre.getToolTip().add(Component.literal(Component.translatable("tooltip.replication_addon_tiers.capacity").getString() + capacity));
            }
        }).subscribe();

        EventManager.forge(ItemTooltipEvent.class).process(pre -> {
            if (ItemStack.isSameItem(pre.getItemStack(), new ItemStack(ModRegistry.MATTER_TANK_TIER_4.get())) && pre.getItemStack().has(ReplicationAttachments.TILE)) {
                var tag = pre.getItemStack().get(ReplicationAttachments.TILE);
                var capacity = ORIGINAL_CAPACITY * Config.tankTier4;
                var matterStack = MatterStack.loadMatterStackFromNBT(tag.contains("tank") ? tag.getCompound("tank") : tag.getCompound("lockableMatterTankBundle").getCompound("Tank"));
                pre.getToolTip().add(1, Component.translatable("tooltip.titanium.tank.amount").withStyle(ChatFormatting.GOLD).append(Component.literal(ChatFormatting.WHITE + new DecimalFormat().format(matterStack.getAmount()) + ChatFormatting.GOLD + "/" + ChatFormatting.WHITE + new DecimalFormat().format(capacity))).append(Component.translatable("tooltip.replication.tank.unit").withStyle(ChatFormatting.DARK_AQUA)));
                pre.getToolTip().add(1, Component.literal(ChatFormatting.GOLD + Component.translatable("tooltip.replication.tank.matter").getString()).append(matterStack.isEmpty() ? Component.translatable("tooltip.titanium.tank.empty").withStyle(ChatFormatting.WHITE) : Component.translatable(matterStack.getTranslationKey())).withStyle(ChatFormatting.WHITE));
            } else if (ItemStack.isSameItem(pre.getItemStack(),new ItemStack(ModRegistry.MATTER_TANK_TIER_4.get()))){
                var capacity = ORIGINAL_CAPACITY * Config.tankTier4;
                pre.getToolTip().add(Component.literal(Component.translatable("tooltip.replication_addon_tiers.capacity").getString() + capacity));
            }
        }).subscribe();

        EventManager.forge(ItemTooltipEvent.class).process(pre -> {
            if (ItemStack.isSameItem(pre.getItemStack(), new ItemStack(ModRegistry.MATTER_TANK_TIER_5.get())) && pre.getItemStack().has(ReplicationAttachments.TILE)) {
                var tag = pre.getItemStack().get(ReplicationAttachments.TILE);
                var capacity = ORIGINAL_CAPACITY * Config.tankTier5;
                var matterStack = MatterStack.loadMatterStackFromNBT(tag.contains("tank") ? tag.getCompound("tank") : tag.getCompound("lockableMatterTankBundle").getCompound("Tank"));
                pre.getToolTip().add(1, Component.translatable("tooltip.titanium.tank.amount").withStyle(ChatFormatting.GOLD).append(Component.literal(ChatFormatting.WHITE + new DecimalFormat().format(matterStack.getAmount()) + ChatFormatting.GOLD + "/" + ChatFormatting.WHITE + new DecimalFormat().format(capacity))).append(Component.translatable("tooltip.replication.tank.unit").withStyle(ChatFormatting.DARK_AQUA)));
                pre.getToolTip().add(1, Component.literal(ChatFormatting.GOLD + Component.translatable("tooltip.replication.tank.matter").getString()).append(matterStack.isEmpty() ? Component.translatable("tooltip.titanium.tank.empty").withStyle(ChatFormatting.WHITE) : Component.translatable(matterStack.getTranslationKey())).withStyle(ChatFormatting.WHITE));
            } else if (ItemStack.isSameItem(pre.getItemStack(),new ItemStack(ModRegistry.MATTER_TANK_TIER_5.get()))){
                var capacity = ORIGINAL_CAPACITY * Config.tankTier5;
                pre.getToolTip().add(Component.literal(Component.translatable("tooltip.replication_addon_tiers.capacity").getString() + capacity));
            }
        }).subscribe();

        EventManager.forge(ItemTooltipEvent.class).process(pre -> {
            if (ItemStack.isSameItem(pre.getItemStack(), new ItemStack(ModRegistry.MATTER_TANK_TIER_6.get())) && pre.getItemStack().has(ReplicationAttachments.TILE)) {
                var tag = pre.getItemStack().get(ReplicationAttachments.TILE);
                var capacity = ORIGINAL_CAPACITY * Config.tankTier6;
                var matterStack = MatterStack.loadMatterStackFromNBT(tag.contains("tank") ? tag.getCompound("tank") : tag.getCompound("lockableMatterTankBundle").getCompound("Tank"));
                pre.getToolTip().add(1, Component.translatable("tooltip.titanium.tank.amount").withStyle(ChatFormatting.GOLD).append(Component.literal(ChatFormatting.WHITE + new DecimalFormat().format(matterStack.getAmount()) + ChatFormatting.GOLD + "/" + ChatFormatting.WHITE + new DecimalFormat().format(capacity))).append(Component.translatable("tooltip.replication.tank.unit").withStyle(ChatFormatting.DARK_AQUA)));
                pre.getToolTip().add(1, Component.literal(ChatFormatting.GOLD + Component.translatable("tooltip.replication.tank.matter").getString()).append(matterStack.isEmpty() ? Component.translatable("tooltip.titanium.tank.empty").withStyle(ChatFormatting.WHITE) : Component.translatable(matterStack.getTranslationKey())).withStyle(ChatFormatting.WHITE));
            } else if (ItemStack.isSameItem(pre.getItemStack(),new ItemStack(ModRegistry.MATTER_TANK_TIER_6.get()))){
                var capacity = ORIGINAL_CAPACITY * Config.tankTier6;
                pre.getToolTip().add(Component.literal(Component.translatable("tooltip.replication_addon_tiers.capacity").getString() + capacity));
            }
        }).subscribe();

        EventManager.forge(ItemTooltipEvent.class).process(pre -> {
            if (ItemStack.isSameItem(pre.getItemStack(), new ItemStack(ModRegistry.MATTER_TANK_TIER_7.get())) && pre.getItemStack().has(ReplicationAttachments.TILE)) {
                var tag = pre.getItemStack().get(ReplicationAttachments.TILE);
                var capacity = ORIGINAL_CAPACITY * Config.tankTier7;
                var matterStack = MatterStack.loadMatterStackFromNBT(tag.contains("tank") ? tag.getCompound("tank") : tag.getCompound("lockableMatterTankBundle").getCompound("Tank"));
                pre.getToolTip().add(1, Component.translatable("tooltip.titanium.tank.amount").withStyle(ChatFormatting.GOLD).append(Component.literal(ChatFormatting.WHITE + new DecimalFormat().format(matterStack.getAmount()) + ChatFormatting.GOLD + "/" + ChatFormatting.WHITE + new DecimalFormat().format(capacity))).append(Component.translatable("tooltip.replication.tank.unit").withStyle(ChatFormatting.DARK_AQUA)));
                pre.getToolTip().add(1, Component.literal(ChatFormatting.GOLD + Component.translatable("tooltip.replication.tank.matter").getString()).append(matterStack.isEmpty() ? Component.translatable("tooltip.titanium.tank.empty").withStyle(ChatFormatting.WHITE) : Component.translatable(matterStack.getTranslationKey())).withStyle(ChatFormatting.WHITE));
            } else if (ItemStack.isSameItem(pre.getItemStack(),new ItemStack(ModRegistry.MATTER_TANK_TIER_7.get()))){
                var capacity = ORIGINAL_CAPACITY * Config.tankTier7;
                pre.getToolTip().add(Component.literal(Component.translatable("tooltip.replication_addon_tiers.capacity").getString() + capacity));
            }
        }).subscribe();

        EventManager.forge(ItemTooltipEvent.class).process(pre -> {
            if (ItemStack.isSameItem(pre.getItemStack(), new ItemStack(ModRegistry.MATTER_TANK_TIER_8.get())) && pre.getItemStack().has(ReplicationAttachments.TILE)) {
                var tag = pre.getItemStack().get(ReplicationAttachments.TILE);
                var capacity = ORIGINAL_CAPACITY * Config.tankTier8;
                var matterStack = MatterStack.loadMatterStackFromNBT(tag.contains("tank") ? tag.getCompound("tank") : tag.getCompound("lockableMatterTankBundle").getCompound("Tank"));
                pre.getToolTip().add(1, Component.translatable("tooltip.titanium.tank.amount").withStyle(ChatFormatting.GOLD).append(Component.literal(ChatFormatting.WHITE + new DecimalFormat().format(matterStack.getAmount()) + ChatFormatting.GOLD + "/" + ChatFormatting.WHITE + new DecimalFormat().format(capacity))).append(Component.translatable("tooltip.replication.tank.unit").withStyle(ChatFormatting.DARK_AQUA)));
                pre.getToolTip().add(1, Component.literal(ChatFormatting.GOLD + Component.translatable("tooltip.replication.tank.matter").getString()).append(matterStack.isEmpty() ? Component.translatable("tooltip.titanium.tank.empty").withStyle(ChatFormatting.WHITE) : Component.translatable(matterStack.getTranslationKey())).withStyle(ChatFormatting.WHITE));
            } else if (ItemStack.isSameItem(pre.getItemStack(),new ItemStack(ModRegistry.MATTER_TANK_TIER_8.get()))){
                var capacity = ORIGINAL_CAPACITY * Config.tankTier8;
                pre.getToolTip().add(Component.literal(Component.translatable("tooltip.replication_addon_tiers.capacity").getString() + capacity));
            }
        }).subscribe();


        EventManager.mod(EntityRenderersEvent.RegisterRenderers.class).process(event -> {
            event.registerBlockEntityRenderer((BlockEntityType<? extends AdvancedReplicatorBlockEntity>)ModRegistry.ADVANCED_REPLICATOR_BE.get(), p_173571_ -> new AdvancedReplicatorRenderer());
            event.registerBlockEntityRenderer((BlockEntityType<? extends MatterTankTier1BlockEntity>) ModRegistry.MATTER_TANK_TIER_1_BE.get(), MatterTankTier1Renderer::new);
            event.registerBlockEntityRenderer((BlockEntityType<? extends MatterTankTier2BlockEntity>) ModRegistry.MATTER_TANK_TIER_2_BE.get(), MatterTankTier2Renderer::new);
            event.registerBlockEntityRenderer((BlockEntityType<? extends MatterTankTier3BlockEntity>) ModRegistry.MATTER_TANK_TIER_3_BE.get(), MatterTankTier3Renderer::new);
            event.registerBlockEntityRenderer((BlockEntityType<? extends MatterTankTier4BlockEntity>) ModRegistry.MATTER_TANK_TIER_4_BE.get(), MatterTankTier4Renderer::new);
            event.registerBlockEntityRenderer((BlockEntityType<? extends MatterTankTier5BlockEntity>) ModRegistry.MATTER_TANK_TIER_5_BE.get(), MatterTankTier5Renderer::new);
            event.registerBlockEntityRenderer((BlockEntityType<? extends MatterTankTier6BlockEntity>) ModRegistry.MATTER_TANK_TIER_6_BE.get(), MatterTankTier6Renderer::new);
            event.registerBlockEntityRenderer((BlockEntityType<? extends MatterTankTier7BlockEntity>) ModRegistry.MATTER_TANK_TIER_7_BE.get(), MatterTankTier7Renderer::new);
            event.registerBlockEntityRenderer((BlockEntityType<? extends MatterTankTier8BlockEntity>) ModRegistry.MATTER_TANK_TIER_8_BE.get(), MatterTankTier8Renderer::new);
        }).subscribe();
        EventManager.mod(ModelEvent.BakingCompleted.class).process(event -> {
            AdvancedReplicatorRenderer.PLATE = bakeModel(ResourceLocation.fromNamespaceAndPath(Replication.MOD_ID, "block/replicator_plate"), event.getModelBakery());
        }).subscribe();
//        EventManager.forge(RenderHighlightEvent.Block.class).process(ClientEvents::blockOverlayEvent).subscribe();
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

    private static BakedModel bakeModel(ResourceLocation model, ModelBakery modelBakery){
        var modelResourceLocation = new ModelResourceLocation(model, "standalone");
        UnbakedModel unbakedModel = modelBakery.getModel(model);
        ModelBaker baker = modelBakery.new ModelBakerImpl((modelLoc, material) -> material.sprite(), modelResourceLocation);
        return unbakedModel.bake(baker, Material::sprite, new SimpleModelState(Transformation.identity()));
    }

//    public static void blockOverlayEvent(RenderHighlightEvent.Block event) {
//        if (event.getTarget() != null) {
//            BlockHitResult traceResult = event.getTarget();
//            BlockState og = Minecraft.getInstance().level.getBlockState(traceResult.getBlockPos());
//            if (og.getBlock() instanceof AdvancedReplicatorBlock advancedReplicatorBlock && Minecraft.getInstance().level.getBlockEntity(traceResult.getBlockPos()) instanceof AdvancedReplicatorBlockEntity advancedReplicatorBlockEntity) {
//                VoxelShape body = advancedReplicatorBlock.getShapePlate(og).getFirst();
//                VoxelShape plate = advancedReplicatorBlock.getShapePlate(og).getSecond();
//                BlockPos blockpos = event.getTarget().getBlockPos();
//                event.setCanceled(true);
//
//                PoseStack stack = new PoseStack();
//                stack.pushPose();
//                Camera info = event.getCamera();
//                //stack.mulPose(Axis.XP.rotationDegrees(info.getXRot()));
//                //stack.mulPose(Axis.YP.rotationDegrees(info.getYRot() + 180));
//                double d0 = info.getPosition().x();
//                double d1 = info.getPosition().y();
//                double d2 = info.getPosition().z();
//                VertexConsumer builder = event.getMultiBufferSource().getBuffer(RenderType.LINES);
//                drawShape(stack, builder, body, blockpos.getX() - d0, blockpos.getY() - d1, blockpos.getZ() - d2, 0, 0, 0, 0.4F);
//                stack.translate(0 , -AdvancedReplicatorBlockEntity.LOWER_PROGRESS,0);
//
//                var progress = (AdvancedReplicatorBlockEntity.getProgress() /* + event.getPartialTick() /100f*/) / (float) AdvancedReplicatorBlockEntity.getMaxProgress();
//                //progress = 0;
//
//                stack.translate(0, AdvancedReplicatorBlockEntity.LOWER_PROGRESS * progress, 0);
//                drawShape(stack, builder, plate, blockpos.getX() - d0, blockpos.getY() - d1, blockpos.getZ() - d2, 0, 0, 0, 0.4F);
//                stack.popPose();
//
//            }
//        }
//    }

}