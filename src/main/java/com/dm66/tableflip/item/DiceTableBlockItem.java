package com.dm66.tableflip.item;

import com.dm66.tableflip.item.renderer.DiceTableBlockItemRenderer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.function.Consumer;

public class DiceTableBlockItem extends BlockItem implements IAnimatable
{
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public DiceTableBlockItem(Block pBlock, Properties pProperties)
    {
        super(pBlock, pProperties);
    }

    @Override
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer)
    {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions()
        {
            private DiceTableBlockItemRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer()
            {
                if (this.renderer == null)
                    this.renderer = new DiceTableBlockItemRenderer();

                return this.renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(new AnimationController<>(this, "NoAnimation", 0, this::deployAnimController));
    }

    private <T extends IAnimatable> PlayState deployAnimController(AnimationEvent<T> tAnimationEvent)
    {
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory()
    {
        return this.factory;
    }
}
