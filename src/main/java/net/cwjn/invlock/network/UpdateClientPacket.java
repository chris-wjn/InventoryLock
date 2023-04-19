package net.cwjn.invlock.network;

import net.cwjn.invlock.Invlock;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateClientPacket {

    private final boolean locked;

    public UpdateClientPacket(boolean locked) {
        this.locked = locked;
    }

    public static void encode(UpdateClientPacket packet, FriendlyByteBuf buffer) {
        buffer.writeBoolean(packet.locked);
    }

    public static UpdateClientPacket decode(FriendlyByteBuf buffer) {
        return new UpdateClientPacket(buffer.readBoolean());
    }

    public static void handle(UpdateClientPacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> Invlock.isLocked = packet.locked);
        });
        ctx.setPacketHandled(true);
    }

}
