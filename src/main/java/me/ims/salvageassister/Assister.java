package me.ims.salvageassister;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraftforge.client.event.GuiScreenEvent;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.lwjgl.input.Mouse;

public class Assister {
    private static final Minecraft mc = Minecraft.getMinecraft();
    // TODO Add more salvageables
    private static String[] salvageables = {
            "Zombie Soldier Helm", "Zombie Soldier Chest", "Zombie Soldier Leg", "Zombie Soldier Boots",
            "Zombie Soldier Cutlass", "Dreadlord Sword"
    };

    public static void handleDrawScreen(GuiScreenEvent.DrawScreenEvent event) {
        if(!(event.gui instanceof GuiChest)) return;
        try {
            // lowerChestInventory = field_147015_w
            String chestName = ((IInventory)FieldUtils.readDeclaredField(event.gui, "field_147015_w", true)).getDisplayName().getUnformattedText();

            if(!chestName.equals("Salvage Dungeon Item")) return;

            int i = 0;
            for(Slot slot : ((GuiContainer)event.gui).inventorySlots.inventorySlots) {
                if(slot.getStack() == null) continue;
                for(String name : salvageables) {
                    if (slot.getStack().getDisplayName().contains(name)) {
                        // lowerChestInventory = field_147015_w
                        int guiTop = (event.gui.height - ((222 - 108) + (((IInventory)FieldUtils.readDeclaredField(event.gui, "field_147015_w", true)).getSizeInventory() / 9) * 18)) / 2;
                        //FieldUtils.writeField(event.gui, "zLevel", 300F, true);
                        //((RenderItem)FieldUtils.readField(event.gui, "itemRender", true)).zLevel = 300F;
                        //SalvageAssister.logger.info(FieldUtils.readField(event.gui, "zLevel", true));
                        Gui.drawRect(((event.gui.width - 176) / 2) + slot.xDisplayPosition, guiTop + slot.yDisplayPosition, ((event.gui.width - 176) / 2) + slot.xDisplayPosition + 16, guiTop + slot.yDisplayPosition + 16, 0xff0000A0);
                        //FieldUtils.writeField(event.gui, "zLevel", 0F, true);
                        //((RenderItem)FieldUtils.readField(event.gui, "itemRender", true)).zLevel = 0F;
                    }
                }

                i++;
            }
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void handleMouseInput(GuiScreenEvent.MouseInputEvent.Pre event) {
        if(!(event.gui instanceof GuiChest)) return;
        try {
            // lowerChestInventory = field_147015_w
            String chestName = ((IInventory)FieldUtils.readDeclaredField(event.gui, "field_147015_w", true)).getDisplayName().getUnformattedText();

            if(!chestName.equals("Salvage Dungeon Item")) return;
            int i = Mouse.getEventX() * event.gui.width / mc.displayWidth;
            int j = event.gui.height - Mouse.getEventY() * event.gui.height / mc.displayHeight - 1;

            Slot slot = getSlotAtPosition((GuiChest)event.gui, i, j);
            if(slot != null) {
                if(slot.getStack() != null) {
                    if(slot.getStack().getDisplayName().equalsIgnoreCase(" ") || slot.getStack().getDisplayName().contains("Salvage Dungeon Item") || slot.getStack().getDisplayName().contains("Go Back") || slot.getStack().getDisplayName().contains("Close") || slot.getStack().getDisplayName().contains("Your Essence")) return;
                }
            }
            event.setCanceled(true);

            int k = Mouse.getEventButton();
            if(Mouse.getEventButtonState()) {
                Slot hoveredSlot = getSlotAtPosition((GuiChest)event.gui, i, j);

                if(hoveredSlot == null) return;

                mc.playerController.windowClick(((GuiChest)event.gui).inventorySlots.windowId, hoveredSlot.slotNumber, 0, 1, mc.thePlayer);
            } else if(k != -1) {

            }
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }

    private static Slot getSlotAtPosition(GuiChest guiContainer, int x, int y) throws IllegalAccessException {
        for (int i = 0; i < guiContainer.inventorySlots.inventorySlots.size(); ++i)
        {
            Slot slot = guiContainer.inventorySlots.inventorySlots.get(i);

            // lowerChestInventory = field_147015_w
            if (isMouseOverSlot(slot, x, y, (guiContainer.width - 176) / 2, (guiContainer.height - ((222 - 108) + (((IInventory)FieldUtils.readDeclaredField(guiContainer, "field_147015_w", true)).getSizeInventory() / 9) * 18)) / 2))
            {
                return slot;
            }
        }

        return null;
    }

    private static boolean isMouseOverSlot(Slot slotIn, int mouseX, int mouseY, int guiLeft, int guiTop) {
        return isPointInRegion(slotIn.xDisplayPosition, slotIn.yDisplayPosition, 16, 16, mouseX, mouseY, guiLeft, guiTop);
    }

    private static boolean isPointInRegion(int left, int top, int right, int bottom, int pointX, int pointY, int guiLeft, int guiTop)
    {
        pointX = pointX - guiLeft;
        pointY = pointY - guiTop;
        return pointX >= left - 1 && pointX < left + right + 1 && pointY >= top - 1 && pointY < top + bottom + 1;
    }
}
