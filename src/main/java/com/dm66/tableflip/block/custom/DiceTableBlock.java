package com.dm66.tableflip.block.custom;

import com.dm66.tableflip.logic.GameState;
import com.dm66.tableflip.logic.GameType;
import com.dm66.tableflip.logic.Move;
import com.dm66.tableflip.networking.C2S_MakeMove;
import com.dm66.tableflip.networking.Networking;
import com.dm66.tableflip.networking.S2C_GameStatePacket;
import com.dm66.tableflip.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class DiceTableBlock extends Block implements EntityBlock
{
    public DiceTableBlock(Properties pProperties)
    {
        super(pProperties);
    }

    // Table Block right click method
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit)
    {
        if(pLevel.isClientSide())
        {
            DiceTableBlockEntity be = (DiceTableBlockEntity) pLevel.getBlockEntity(pPos);
            assert be != null;
            if(be.getGameState() == null) return InteractionResult.CONSUME;

            be.roll = true;

            Vec3 loc = pHit.getLocation();
            loc = loc.add(-pPos.getX(), -pPos.getY(), -pPos.getZ());

            int x = RenderUtil.getHoveredStack(loc);
            if(x != -1)
            {
                if(be.sel1 == -1) be.sel1 = x;
                else if(be.sel1 != x)
                {
                    be.sel2 = x;
                    Networking.sendToServer(new C2S_MakeMove(pPos, new Move(be.sel1, be.sel2)));
                    Minecraft.getInstance().player.sendSystemMessage(Component.literal("s1: " + be.sel1 + ", s2: " + be.sel2));
                    be.sel1 = be.sel2 = -1;
                }
            }
            else be.sel1 = -1;
        }
        else  // logical server
        {
            DiceTableBlockEntity be = (DiceTableBlockEntity) pLevel.getBlockEntity(pPos);
            assert be != null;

            // initialize GameState if not done yet
            if(be.getGameState() == null)
            {
                be.setGameState(GameState.init(GameType.BACKGAMMON_NORMAL));
                Networking.sendToAllClients(new S2C_GameStatePacket(pPos, be.getGameState()));
            }

        }
        return InteractionResult.CONSUME;
    }

    // hitbox definition
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
