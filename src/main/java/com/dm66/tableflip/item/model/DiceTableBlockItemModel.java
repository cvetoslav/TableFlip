package com.dm66.tableflip.item.model;

import com.dm66.tableflip.TableFlipMod;
import com.dm66.tableflip.block.custom.DiceTableBlockEntity;
import com.dm66.tableflip.item.DiceTableBlockItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DiceTableBlockItemModel extends AnimatedGeoModel<DiceTableBlockItem>
{
    private static final ResourceLocation modelResource = new ResourceLocation(TableFlipMod.MOD_ID, "geo/dice_table.geo.json");
    private static final ResourceLocation textureResource = new ResourceLocation(TableFlipMod.MOD_ID, "textures/block/dice_table.png");
    private static final ResourceLocation animationResource = new ResourceLocation(TableFlipMod.MOD_ID, "animations/dice_table.animation.json");

    @Override
    public ResourceLocation getModelResource(DiceTableBlockItem object) {
        return modelResource;
    }

    @Override
    public ResourceLocation getTextureResource(DiceTableBlockItem object) {
        return textureResource;
    }

    @Override
    public ResourceLocation getAnimationResource(DiceTableBlockItem object) {
        return animationResource;
    }
}
