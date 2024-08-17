package com.dm66.tableflip.networking;

import com.dm66.tableflip.block.custom.DiceTableBlockEntity;
import com.dm66.tableflip.logic.GameState;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import software.bernie.shadowed.eliotlash.mclib.math.functions.limit.Min;

import java.util.function.Supplier;

public class S2C_GameStatePacket
{
    BlockPos pos;
    GameState gs;

    public S2C_GameStatePacket(FriendlyByteBuf buffer)
    {
        pos = buffer.readBlockPos();
        gs = GameState.reconstruct(buffer);
    }

    public S2C_GameStatePacket(BlockPos BEpos, GameState state)
    {
        pos = BEpos;
        gs = state;
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(pos);
        gs.serialize(buffer);
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
        public static void handlePacket(S2C_GameStatePacket p, Supplier<NetworkEvent.Context> ctx)
        {
            assert Minecraft.getInstance().level != null;
            DiceTableBlockEntity be = (DiceTableBlockEntity) Minecraft.getInstance().level.getBlockEntity(p.pos);
            if(be != null)
            {
                be.setGameState(p.gs);
                Minecraft.getInstance().player.sendSystemMessage(Component.literal("state set!"));
            }
        }
    }
}
