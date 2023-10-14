package com.dm66.tableflip.block.renderer;

import com.dm66.tableflip.TableFlipMod;
import com.dm66.tableflip.block.custom.DiceTableBlockEntity;
import com.dm66.tableflip.block.model.DiceTableBlockModel;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;
import software.bernie.geckolib3.util.EModelRenderCycle;

public class DiceTableBlockEntityRenderer extends GeoBlockRenderer<DiceTableBlockEntity>
{
    public DiceTableBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context, new DiceTableBlockModel());
    }

    @Override
    public void render(DiceTableBlockEntity tile, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight)
    {
        super.render(tile, partialTick, poseStack, bufferSource, packedLight);
        renderBoard(tile, partialTick, poseStack, bufferSource, packedLight);
        renderPointerCube(tile, partialTick, poseStack, bufferSource, packedLight);
    }

    private void renderPointerCube(DiceTableBlockEntity tile, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight)
    {
        Vec3 loc = tile.getLastLoc();
        if(loc == null) return;

        GeoModel model = modelProvider.getModel(new ResourceLocation(TableFlipMod.MOD_ID, "geo/pointer_cube.geo.json"));
        model.getBone("pull").get().setPosition(-(float)(loc.x * 16 - 8), (float) loc.y * 16, (float) loc.z * 16 - 8);

        doRenderingStuff(tile, partialTick, poseStack, bufferSource, packedLight, model, getTextureLocation(tile), false);
    }

    private void renderBoard(DiceTableBlockEntity tile, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight)
    {
        GeoModel model = modelProvider.getModel(new ResourceLocation(TableFlipMod.MOD_ID, "geo/dice_table_board.geo.json"));
        ResourceLocation texture = new ResourceLocation(TableFlipMod.MOD_ID, "textures/block/dice_table_board.png");

        doRenderingStuff(tile, partialTick, poseStack, bufferSource, packedLight, model, texture, true);
    }

    private void doRenderingStuff(DiceTableBlockEntity tile, float partialTick, PoseStack pPoseStack, MultiBufferSource bufferSource, int packedLight, GeoModel pModel, ResourceLocation pTexture, boolean hasTexture)
    {
        doRenderingStuff(tile, partialTick, pPoseStack, bufferSource, packedLight, pModel, pTexture, Color.WHITE, hasTexture);
    }
    private void doRenderingStuff(DiceTableBlockEntity tile, float partialTick, PoseStack pPoseStack, MultiBufferSource bufferSource, int packedLight, GeoModel pModel, ResourceLocation pTexture, Color pColor, boolean hasTexture)
    {
        this.dispatchedMat = pPoseStack.last().pose().copy();
        this.setCurrentModelRenderCycle(EModelRenderCycle.INITIAL);
        pPoseStack.pushPose();
        pPoseStack.translate(0, 0.01f, 0);
        pPoseStack.translate(0.5, 0, 0.5);

        if(hasTexture) RenderSystem.setShaderTexture(0, pTexture);

        render(pModel, tile, partialTick, RenderType.entityTranslucent(pTexture), pPoseStack, bufferSource, null, packedLight, OverlayTexture.NO_OVERLAY,
                pColor.getRed() / 255f, pColor.getGreen() / 255f,
                pColor.getBlue() / 255f, pColor.getAlpha() / 255f);
        pPoseStack.popPose();
    }

    @Override
    public RenderType getRenderType(DiceTableBlockEntity animatable, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, ResourceLocation texture)
    {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
