package com.dm66.tableflip.block.custom;

import com.dm66.tableflip.block.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class DiceTableBlockEntity extends BlockEntity implements IAnimatable
{
    protected static final AnimationBuilder IDLE = new AnimationBuilder().addAnimation("gamba", ILoopType.EDefaultLoopTypes.LOOP);

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public DiceTableBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntityTypes.DICE_TABLE_BE.get(), pos, state);
    }

    @Override
    public void registerControllers(final AnimationData data)
    {
        data.addAnimationController(new AnimationController<>(this, "idle", 0, this::deployAnimController));
    }

    protected <E extends DiceTableBlockEntity> PlayState deployAnimController(final AnimationEvent<E> event)
    {
        event.getController().setAnimation(IDLE);

        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory()
    {
        return this.factory;
    }
}
