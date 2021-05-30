package me.ims.salvageassister.managers;

import me.ims.salvageassister.Assister;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventManager {
    @SubscribeEvent
    public void onDrawScreen(GuiScreenEvent.DrawScreenEvent event) {
        Assister.handleDrawScreen(event);
    }

    @SubscribeEvent
    public void onGuiMouse(GuiScreenEvent.MouseInputEvent.Pre event) {
        Assister.handleMouseInput(event);
    }
}
