package com.dm66.tableflip.networking;

import com.dm66.tableflip.block.custom.DiceTableBlockEntity;
import com.dm66.tableflip.logic.GameEngine;
import com.dm66.tableflip.logic.GameState;
import com.dm66.tableflip.logic.Move;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2S_MakeMove
{
    BlockPos blockEntityPosition;
    Move move;

    public C2S_MakeMove(FriendlyByteBuf buffer)
    {
        blockEntityPosition = buffer.readBlockPos();
        move = new Move(buffer.readInt(), buffer.readInt());
    }

    public C2S_MakeMove(BlockPos pos, Move move)
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
        ctx.get().enqueueWork(() -> {
            // Server side

            ServerPlayer p = ctx.get().getSender();

            if(!p.level.hasChunkAt(blockEntityPosition)) return;

            DiceTableBlockEntity be = (DiceTableBlockEntity) p.level.getBlockEntity(blockEntityPosition);

            if(be != null && GameEngine.isValidMove(be.getGameState(), move))
            {p.sendSystemMessage(Component.literal("c2s_make_move ? "));
                be.setGameState(GameEngine.makeMove(be.getGameState(), move));
                p.sendSystemMessage(Component.literal("c2s_make_move: " + move.initialPosition + ", " + move.finalPosition));
                p.sendSystemMessage(Component.literal("GS: " + be.getGameState().upperRow.get(0).size()));
                Networking.sendToAllClients(new S2C_GameStatePacket(blockEntityPosition, be.getGameState()));
                //Networking.sendToAllClients(new S2C_MakeMove(blockEntityPosition, move));
            }

        });
        ctx.get().setPacketHandled(true);
    }
}
