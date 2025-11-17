package org.mob.replication_addon_tiers.client.render;

import com.buuz135.replication.calculation.MatterCompound;
import com.buuz135.replication.calculation.MatterValue;
import com.buuz135.replication.calculation.client.ClientReplicationCalculation;
import com.hrznstudio.titanium.block.RotatableBlock;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.joml.Matrix4f;
import org.mob.replication_addon_tiers.block.custom.ReplicatorAdvancedBlockEntity;

public class ReplicatorAdvancedRenderer implements BlockEntityRenderer<ReplicatorAdvancedBlockEntity> {
    private static RenderType AREA_TYPE = createRenderType();
    public static BakedModel PLATE = null;

    public static RenderType createRenderType() {
        RenderType.CompositeState state = RenderType.CompositeState.builder().setShaderState(new RenderStateShard.ShaderStateShard(GameRenderer::getPositionColorShader)).setTransparencyState(new RenderStateShard.TransparencyStateShard("translucent_transparency", () -> {
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        }, () -> {
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
        })).createCompositeState(true);
        return RenderType.create("working_area_render", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 256, false, true, state);
    }

    public void render(ReplicatorAdvancedBlockEntity entity, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int combinedLightIn, int combinedOverlayIn) {
        Direction facing = (Direction)entity.getBlockState().getValue(RotatableBlock.FACING_HORIZONTAL);
        if (facing == Direction.EAST) {
            poseStack.translate(1.0F, 0.0F, 0.0F);
            poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
        } else if (facing == Direction.SOUTH) {
            poseStack.translate(1.0F, 0.0F, 1.0F);
            poseStack.mulPose(Axis.YP.rotationDegrees(-180.0F));
        } else if (facing == Direction.WEST) {
            poseStack.translate(0.0F, 0.0F, 1.0F);
            poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
        }

        poseStack.pushPose();
        float[] color = new float[]{1.0F, 1.0F, 1.0F, 0.0F};
        if (!entity.getCraftingStack().isEmpty() && entity.getAction() == 0) {
            MatterCompound matterCompound = ClientReplicationCalculation.getMatterCompound(entity.getCraftingStack());
            if (matterCompound != null) {
                double total = (double)0.0F;

                for(MatterValue matterValue : matterCompound.getValues().values()) {
                    total += matterValue.getAmount();
                }

                double currentProgress = (double)((float)entity.getProgress() / (float)entity.getMaxProgress()) * 1.4;
                int progressTotal = 0;

                for(MatterValue matterValue : matterCompound.getValues().values()) {
                    if (((double)progressTotal + matterValue.getAmount()) / total >= currentProgress) {
                        color = (float[])matterValue.getMatter().getColor().get();
                        break;
                    }

                    progressTotal = (int)((double)progressTotal + matterValue.getAmount());
                }
            }
        }

        this.renderPlane(poseStack, multiBufferSource, Block.box((double)2.0F, (double)0.0F, (double)2.0F, (double)14.0F, (double)1.0F, (double)12.0F).bounds(), (double)0.0F, 0.15, (double)0.0F, color[0], color[1], color[2], color[3] == 0.0F ? 0.0F : 0.75F);
        this.renderFaces(poseStack, multiBufferSource, Block.box((double)4.0F, (double)0.0F, (double)2.0F, (double)12.0F, (double)4.0F, (double)12.0F).bounds(), (double)0.0F, -0.2, (double)0.0F, 1.0F, 1.0F, 1.0F, 0.005F);
        poseStack.translate(0.0F, -0.563F, 0.0F);
        float progress = ((float)entity.getProgress() + partialTicks / 100.0F) / (float)entity.getMaxProgress();
        poseStack.translate(0.0F, 0.563F * progress - 0.001F, 0.0F);
        Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(poseStack.last(), multiBufferSource.getBuffer(RenderType.solid()), (BlockState)null, PLATE, 255.0F, 255.0F, 255.0F, combinedLightIn, combinedOverlayIn);
        poseStack.translate(0.5F, 0.56F, 0.45F);
        float scale = 0.4F;
        BakedModel model = Minecraft.getInstance().getItemRenderer().getModel(entity.getCraftingStack(), Minecraft.getInstance().level, (LivingEntity)null, 0);
        if (model.isGui3d()) {
            scale = 0.75F;
        }

        poseStack.scale(scale, scale, scale);
        if (entity.getAction() == 0 && !entity.isCurrentTaskAFailure()) {
            Minecraft.getInstance().getItemRenderer().renderStatic(entity.getCraftingStack(), ItemDisplayContext.FIXED, combinedLightIn, combinedOverlayIn, poseStack, multiBufferSource, entity.getLevel(), 0);
        }

        poseStack.popPose();
    }

    private void renderPlane(PoseStack stack, MultiBufferSource renderTypeBuffer, AABB pos, double x, double y, double z, float red, float green, float blue, float alpha) {
        float x1 = (float)(pos.minX + x);
        float x2 = (float)(pos.maxX + x);
        float y1 = (float)(pos.minY + y);
        float y2 = (float)(pos.maxY + y);
        float z1 = (float)(pos.minZ + z);
        float z2 = (float)(pos.maxZ + z);
        Matrix4f matrix = stack.last().pose();
        VertexConsumer buffer = renderTypeBuffer.getBuffer(AREA_TYPE);
        buffer.addVertex(matrix, x1, y2, z1).setColor(red, green, blue, alpha);
        buffer.addVertex(matrix, x1, y2, z2).setColor(red, green, blue, alpha);
        buffer.addVertex(matrix, x2, y2, z2).setColor(red, green, blue, alpha);
        buffer.addVertex(matrix, x2, y2, z1).setColor(red, green, blue, alpha);
    }

    private void renderFaces(PoseStack stack, MultiBufferSource renderTypeBuffer, AABB pos, double x, double y, double z, float red, float green, float blue, float alpha) {
        float x1 = (float)(pos.minX + x);
        float x2 = (float)(pos.maxX + x);
        float y1 = (float)(pos.minY + y);
        float y2 = (float)(pos.maxY + y);
        float z1 = (float)(pos.minZ + z);
        float z2 = (float)(pos.maxZ + z);
        Matrix4f matrix = stack.last().pose();
        VertexConsumer buffer = renderTypeBuffer.getBuffer(AREA_TYPE);
        buffer.addVertex(matrix, x1, y1, z1).setColor(red, green, blue, alpha);
        buffer.addVertex(matrix, x1, y2, z1).setColor(red, green, blue, alpha);
        buffer.addVertex(matrix, x2, y2, z1).setColor(red, green, blue, alpha);
        buffer.addVertex(matrix, x2, y1, z1).setColor(red, green, blue, alpha);
        buffer.addVertex(matrix, x1, y1, z2).setColor(red, green, blue, alpha);
        buffer.addVertex(matrix, x2, y1, z2).setColor(red, green, blue, alpha);
        buffer.addVertex(matrix, x2, y2, z2).setColor(red, green, blue, alpha);
        buffer.addVertex(matrix, x1, y2, z2).setColor(red, green, blue, alpha);
        buffer.addVertex(matrix, x1, y1, z1).setColor(red, green, blue, alpha);
        buffer.addVertex(matrix, x2, y1, z1).setColor(red, green, blue, alpha);
        buffer.addVertex(matrix, x2, y1, z2).setColor(red, green, blue, alpha);
        buffer.addVertex(matrix, x1, y1, z2).setColor(red, green, blue, alpha);
        buffer.addVertex(matrix, x1, y2, z1).setColor(red, green, blue, alpha);
        buffer.addVertex(matrix, x1, y2, z2).setColor(red, green, blue, alpha);
        buffer.addVertex(matrix, x2, y2, z2).setColor(red, green, blue, alpha);
        buffer.addVertex(matrix, x2, y2, z1).setColor(red, green, blue, alpha);
        buffer.addVertex(matrix, x1, y1, z1).setColor(red, green, blue, alpha);
        buffer.addVertex(matrix, x1, y1, z2).setColor(red, green, blue, alpha);
        buffer.addVertex(matrix, x1, y2, z2).setColor(red, green, blue, alpha);
        buffer.addVertex(matrix, x1, y2, z1).setColor(red, green, blue, alpha);
        buffer.addVertex(matrix, x2, y1, z1).setColor(red, green, blue, alpha);
        buffer.addVertex(matrix, x2, y2, z1).setColor(red, green, blue, alpha);
        buffer.addVertex(matrix, x2, y2, z2).setColor(red, green, blue, alpha);
        buffer.addVertex(matrix, x2, y1, z2).setColor(red, green, blue, alpha);
    }
}
