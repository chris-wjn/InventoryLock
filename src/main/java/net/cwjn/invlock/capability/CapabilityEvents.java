package net.cwjn.invlock.capability;

import net.cwjn.invlock.Invlock;
import net.cwjn.invlock.network.Network;
import net.cwjn.invlock.network.UpdateClientPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = Invlock.MOD_ID)
public class CapabilityEvents {

    @SubscribeEvent
    public void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(Data.class);
    }

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player&& !event.getObject().getCapability(DataProvider.DATA).isPresent()) {
            event.addCapability(new ResourceLocation(Invlock.MOD_ID, "data"), new DataProvider());
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().reviveCaps();
            LazyOptional<Data> optNewData = event.getEntity().getCapability(DataProvider.DATA);
            LazyOptional<Data> optOldData = event.getOriginal().getCapability(DataProvider.DATA);
            optNewData.ifPresent(newCap -> optOldData.ifPresent(newCap::copyFrom));
            Network.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getEntity()), new UpdateClientPacket(event.getEntity().getCapability(DataProvider.DATA).orElse(new Data()).isLocked()));
            event.getOriginal().invalidateCaps();
        }
    }

}
