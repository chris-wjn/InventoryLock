package net.cwjn.invlock.network;

import net.cwjn.invlock.Invlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class Network {

    private static final String PROTOCOL_VERSION = "1.0";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Invlock.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void init() {
        INSTANCE.registerMessage(0, UpdateServerPacket.class, UpdateServerPacket::encode, UpdateServerPacket::decode, UpdateServerPacket::handle);
        INSTANCE.registerMessage(1, UpdateClientPacket.class, UpdateClientPacket::encode, UpdateClientPacket::decode, UpdateClientPacket::handle);
    }

    

}
