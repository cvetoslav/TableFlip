package com.dm66.tableflip.networking;

import com.dm66.tableflip.block.custom.DiceTableBlockEntity;
import com.dm66.tableflip.logic.GameState;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class GameStatePacket
{
    BlockPos pos;
    GameState gs;

    public GameStatePacket(FriendlyByteBuf buffer)
    {
        pos = buffer.readBlockPos();
        gs = GameState.reconstruct(buffer.readByteArray());
    }

    public GameStatePacket(BlockPos BEpos, GameState state)
    {
        pos = BEpos;
        gs = state;
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(pos);
        buffer.writeBytes(gs.serialize());
    }

    public void handle(Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() ->
                // Make sure it's only executed on the physical client
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandlerClass.handlePacket(this, ctx))
        );
        ctx.get().setPacketHandled(true);
    }

    private static class ClientPacketHandlerClass
    {
        public static void handlePacket(GameStatePacket p, Supplier<NetworkEvent.Context> ctx)
        {
            assert Minecraft.getInstance().level != null;
            DiceTableBlockEntity be = (DiceTableBlockEntity) Minecraft.getInstance().level.getBlockEntity(p.pos);
            if(be != null)
            {
                be.setGameState(p.gs);
            }
        }
    }
}
