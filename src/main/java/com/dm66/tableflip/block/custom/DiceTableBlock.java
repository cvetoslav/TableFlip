package com.dm66.tableflip.block.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.stream.Stream;

public class DiceTableBlock extends Block implements EntityBlock
{
    public DiceTableBlock(Properties pProperties)
    {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit)
    {
        if(pLevel.isClientSide())
        {
            Vec3 loc = pHit.getLocation();
            loc = loc.add(-pPos.getX(), -pPos.getY(), -pPos.getZ());

            DiceTableBlockEntity be = (DiceTableBlockEntity) pLevel.getBlockEntity(pPos);
            assert be != null;
            be.setLastLoc(loc);

            pPlayer.sendSystemMessage(Component.literal("Hit result: X" + loc.x + ", Y" + loc.y + " Z" + loc.z));
        }
        return InteractionResult.CONSUME;
    }

    private static final VoxelShape shape = Stream.of(
            Block.box(0, 8, 0, 16, 9, 16),
            Block.box(1, 0, 14, 2, 8, 15),
            Block.box(14, 0, 14, 15, 8, 15),
            Block.box(1, 0, 1, 2, 8, 2),
            Block.box(14, 0, 1, 15, 8, 2)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    @Override
    public @NotNull VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        return shape;
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState)
    {
        return new DiceTableBlockEntity(pPos, pState);
    }
}
