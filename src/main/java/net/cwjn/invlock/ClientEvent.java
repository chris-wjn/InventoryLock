package net.cwjn.invlock;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ClientEvent {

    @SubscribeEvent
    public static void onInitGui(ScreenEvent.Init event) {
        Screen screen = event.getScreen();
        if (screen instanceof InventoryScreen || screen instanceof CreativeModeInventoryScreen) {
            int x = (screen.width - (screen instanceof CreativeModeInventoryScreen ? 157 : 176)) / 2 + 164;
            int y = (screen.height - (screen instanceof CreativeModeInventoryScreen ? 136 : 166)) / 2 + 3;
            event.addListener(new LockButton(x, y));
        }
    }

}
