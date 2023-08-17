package com.dm66.tableflip.block.renderer;

import com.dm66.tableflip.block.custom.DiceTableBlockEntity;
import com.dm66.tableflip.block.model.DiceTableBlockModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class DiceTableBlockEntityRenderer extends GeoBlockRenderer<DiceTableBlockEntity>
{
    public DiceTableBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context, new DiceTableBlockModel());
    }

    @Override
    public RenderType getRenderType(DiceTableBlockEntity animatable, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, ResourceLocation texture)
    {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
