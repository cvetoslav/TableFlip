package com.dm66.tableflip.block.custom;

import com.dm66.tableflip.block.ModBlockEntityTypes;
import com.dm66.tableflip.logic.GameState;
import net.minecraft.client.Game;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
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
    protected static final AnimationBuilder IDLE = new AnimationBuilder().addAnimation("animation.dice_table.rng", ILoopType.EDefaultLoopTypes.LOOP);

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public DiceTableBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntityTypes.DICE_TABLE_BE.get(), pos, state);
    }

    private Vec3 last_loc = null;
    private GameState state = null;

    public GameState getGameState(){return state;}

    public void setGameState(GameState state){this.state = state;}

    public Vec3 getLastLoc()
    {
        return last_loc;
    }

    public void setLastLoc(Vec3 loc)
    {
        last_loc = loc;
    }

    @Override
    public void registerControllers(final AnimationData data)
    {
        data.addAnimationController(new AnimationController<>(this, "Random", 0, this::deployAnimController));
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
