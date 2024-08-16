package com.dm66.tableflip.networking;

import com.dm66.tableflip.block.custom.DiceTableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2S_RequestGameStatePacket
{
    BlockPos blockEntityPosition;

    public C2S_RequestGameStatePacket(FriendlyByteBuf buf)
    {
        blockEntityPosition = buf.readBlockPos();
    }

    public C2S_RequestGameStatePacket(BlockPos pos)
    {
        blockEntityPosition = pos;
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(blockEntityPosition);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {
            // Server side

            ServerPlayer p = ctx.get().getSender();

            if(!p.level.hasChunkAt(blockEntityPosition)) return;

            DiceTableBlockEntity be = (DiceTableBlockEntity) p.level.getBlockEntity(blockEntityPosition);

            if(be != null) Networking.sendToClient(new S2C_GameStatePacket(blockEntityPosition, be.getGameState()), p);

        });
        ctx.get().setPacketHandled(true);
    }
}
