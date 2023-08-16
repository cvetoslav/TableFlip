package com.dm66.tableflip.block.custom;

import com.dm66.tableflip.block.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class DiceTableBlockEntity extends BlockEntity implements IAnimatable
{
    public DiceTableBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(ModBlockEntityTypes.DICE_TABLE_BE.get(), pPos, pBlockState);
    }

    @Override
    public void registerControllers(AnimationData data)
    {

    }

    @Override
    public AnimationFactory getFactory()
    {
        return null;
    }
}
