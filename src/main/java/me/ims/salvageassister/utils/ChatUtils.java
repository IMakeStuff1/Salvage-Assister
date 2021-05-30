package me.ims.salvageassister.utils;

import me.ims.salvageassister.SalvageAssister;
import net.minecraft.util.ChatComponentText;

public class ChatUtils {
    public static void printToChat(String message) {
        SalvageAssister.mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(message));
    }
}
