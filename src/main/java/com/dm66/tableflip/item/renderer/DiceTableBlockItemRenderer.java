package com.dm66.tableflip.item.renderer;

import com.dm66.tableflip.item.DiceTableBlockItem;
import com.dm66.tableflip.item.model.DiceTableBlockItemModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class DiceTableBlockItemRenderer extends GeoItemRenderer<DiceTableBlockItem>
{
    public DiceTableBlockItemRenderer()
    {
        super(new DiceTableBlockItemModel());
    }
}
