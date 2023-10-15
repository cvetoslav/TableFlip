package com.dm66.tableflip.block.renderer;

import com.dm66.tableflip.TableFlipMod;
import com.dm66.tableflip.block.custom.DiceTableBlockEntity;
import com.dm66.tableflip.block.model.DiceTableBlockModel;
import com.dm66.tableflip.logic.GameState;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;
import software.bernie.geckolib3.util.EModelRenderCycle;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
        renderCheckers(tile, partialTick, poseStack, bufferSource, packedLight);
        //renderPointerCube(tile, partialTick, poseStack, bufferSource, packedLight);
    }

    private void renderCheckers(DiceTableBlockEntity tile, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight)
    {
        GameState gs = tile.getGameState();
        if(gs == null) return;

        GeoModel model = modelProvider.getModel(new ResourceLocation(TableFlipMod.MOD_ID, "geo/checker.geo.json"));

        /* Random bullshit go */
        float[] rowX = {6.5f, 5.5f, 4.49f, 3.49f, 2.49f, 1.48f, -0.55f, -1.55f, -2.575f, -3.575f, -4.575f, -5.575f};
        // upper row
        for (int i=0; i<rowX.length; i++)
        {
            List<Boolean> list = gs.upperRow.get(i).stream().toList();
            float y = 8.82f, z = 4.45f, delta = -0.875f;
            for(Boolean checker : list)
            {
                model.getBone("pull").get().setPosition(-rowX[i] - 0.24f, y, z + 0.24f);
                if(checker) doRenderingStuff(tile, partialTick, poseStack, bufferSource, packedLight, model, getTextureLocation(tile), Color.BLACK, false);
                else doRenderingStuff(tile, partialTick, poseStack, bufferSource, packedLight, model, getTextureLocation(tile), false);
                z += delta;
            }
        }

        //lower row
        for (int i=0; i<rowX.length; i++)
        {
            List<Boolean> list = gs.lowerRow.get(i).stream().toList();
            float y = 8.82f, z = -4.9f, delta = +0.875f;
            for(Boolean checker : list)
            {
                model.getBone("pull").get().setPosition(-rowX[i] - 0.24f, y, z + 0.24f);
                if(checker) doRenderingStuff(tile, partialTick, poseStack, bufferSource, packedLight, model, getTextureLocation(tile), Color.BLACK, false);
                else doRenderingStuff(tile, partialTick, poseStack, bufferSource, packedLight, model, getTextureLocation(tile), false);
                z += delta;
            }
        }

        // middle bar
        float x = 0.46f, y = 8.82f, z = -1.715f, delta = 0.25f;
        for(int i=0; i<gs.knockedWhiteCount; i++)
        {
            model.getBone("pull").get().setPosition(-x - 0.24f, y, z + 0.24f);
            doRenderingStuff(tile, partialTick, poseStack, bufferSource, packedLight, model, getTextureLocation(tile), false);
            y += delta;
        }

        x = 0.46f; y = 8.82f; z = 1.135f; delta = 0.25f;
        for(int i=0; i<gs.knockedBlackCount; i++)
        {
            model.getBone("pull").get().setPosition(-x - 0.24f, y, z + 0.24f);
            doRenderingStuff(tile, partialTick, poseStack, bufferSource, packedLight, model, getTextureLocation(tile), Color.BLACK, false);
            y += delta;
        }

        // side bar
        x = -6.9f; y = 8.82f; z = -5.175f; delta = 0.25f;
        model.getBone("pull").get().setRotation((float) (Math.PI / 2), 0, 0);
        for(int i=0; i<gs.outWhiteCount; i++)
        {
            model.getBone("pull").get().setPosition(-x - 0.24f, y, z + 0.24f);
            doRenderingStuff(tile, partialTick, poseStack, bufferSource, packedLight, model, getTextureLocation(tile), false);
            z += delta;
        }

        x = -6.9f; y = 8.82f; z = 4.7f; delta = -0.25f;
        for(int i=0; i<gs.outBlackCount; i++)
        {
            model.getBone("pull").get().setPosition(-x - 0.24f, y, z + 0.24f);
            doRenderingStuff(tile, partialTick, poseStack, bufferSource, packedLight, model, getTextureLocation(tile), Color.BLACK, false);
            z += delta;
        }
        model.getBone("pull").get().setRotation(0, 0, 0);

        /* Random bullshit stop */

        //model.getBone("pull").get().setPosition(6.5f, 8.82f, 4.45f);
        //doRenderingStuff(tile, partialTick, poseStack, bufferSource, packedLight, model, getTextureLocation(tile), false);

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
