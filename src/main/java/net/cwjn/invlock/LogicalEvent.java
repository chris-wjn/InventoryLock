package net.cwjn.invlock;

import net.cwjn.invlock.capability.Data;
import net.cwjn.invlock.capability.DataProvider;
import net.cwjn.invlock.network.Network;
import net.cwjn.invlock.network.UpdateServerPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class LogicalEvent {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onItemPickup(EntityItemPickupEvent event) {
        Player player = event.getEntity();
        if (player != null) {
            event.setCanceled(player.getCapability(DataProvider.DATA).orElse(new Data()).isLocked());
        }
    }

    @SubscribeEvent
    public static void onJoinWorld(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity().getLevel().isClientSide()) {
            Network.INSTANCE.sendToServer(new UpdateServerPacket(Invlock.isLocked));
        }
    }

}
