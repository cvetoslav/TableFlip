package com.dm66.tableflip.networking;

import com.dm66.tableflip.block.custom.DiceTableBlockEntity;
import com.dm66.tableflip.logic.BackgammonGameEngine;
import com.dm66.tableflip.logic.GameEngine;
import com.dm66.tableflip.logic.GameState;
import com.dm66.tableflip.logic.Move;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2C_MakeMove
{
    BlockPos blockEntityPosition;
    Move move;

    public S2C_MakeMove(FriendlyByteBuf buffer)
    {
        blockEntityPosition = buffer.readBlockPos();
        move = new Move(buffer.readInt(), buffer.readInt());
    }

    public S2C_MakeMove(BlockPos pos, Move move)
    {
        blockEntityPosition = pos;
        this.move = move;
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(blockEntityPosition);
        buffer.writeInt(move.initialPosition);
        buffer.writeInt(move.finalPosition);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() ->
                // Make sure it's only executed on the physical client
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> S2C_MakeMove.ClientPacketHandlerClass.handlePacket(this, ctx))
        );
        ctx.get().setPacketHandled(true);
    }

    private static class ClientPacketHandlerClass
    {
        public static void handlePacket(S2C_MakeMove p, Supplier<NetworkEvent.Context> ctx)
        {
            assert Minecraft.getInstance().level != null;
            DiceTableBlockEntity be = (DiceTableBlockEntity) Minecraft.getInstance().level.getBlockEntity(p.blockEntityPosition);
            if(be != null)
            {
                be.setGameState(GameEngine.makeMove(be.getGameState(), p.move));
            }
        }
    }
}
