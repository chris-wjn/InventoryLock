package net.cwjn.invlock.network;

import net.cwjn.invlock.capability.DataProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateServerPacket {

    private final boolean locked;

    public UpdateServerPacket(boolean locked) {
        this.locked = locked;
    }

    public static void encode(UpdateServerPacket packet, FriendlyByteBuf buffer) {
        buffer.writeBoolean(packet.locked);
    }

    public static UpdateServerPacket decode(FriendlyByteBuf buffer) {
        return new UpdateServerPacket(buffer.readBoolean());
    }

    public static void handle(UpdateServerPacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            player.getCapability(DataProvider.DATA).ifPresent(h -> h.setLocked(packet.locked));
        });
        ctx.setPacketHandled(true);
    }

}
