package com.dm66.tableflip.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class Networking
{
    private static final String PROTOCOL_VERSION = "1";
    private static int ID = 0;
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("tableflip", "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void registerMessages()
    {
        INSTANCE.registerMessage(ID++, GameStatePacket.class, GameStatePacket::encode, GameStatePacket::new, GameStatePacket::handle);
    }

    public static void sendToServer(Object packet)
    {
        INSTANCE.sendToServer(packet);
    }

    public static void sendToClient(Object packet, ServerPlayer player)
    {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    public static void sendToAllClients(Object packet)
    {
        INSTANCE.send(PacketDistributor.ALL.noArg(), packet);
    }

}