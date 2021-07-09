package fun.divinetales.Core.listeners;

import fun.divinetales.Core.Utils.InventoryUtils.GUIEntry;
import fun.divinetales.Core.Utils.InventoryUtils.GUIManager;
import fun.divinetales.Core.Utils.InventoryUtils.GUIUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class GUIListeners implements Listener {

    private GUIUtils gui;
    @SuppressWarnings("unused")
    private GUIManager manager;

    public GUIListeners(GUIUtils gui, GUIManager manager) {
        this.gui = gui;
        this.manager = manager;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getClickedInventory().equals(gui.getInventory())) {
            event.setCancelled(true);

            int clickedSlot = event.getSlot();

            for (GUIEntry entry : gui.getEntries()) {
                if (entry.getSlot() == clickedSlot) {
                    entry.onClick(event);
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent event) {
        if (event.getInventory().equals(gui.getInventory())) {
            gui.onOpen(event);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getInventory().equals(gui.getInventory())) {
            gui.onClose(event);

            InventoryClickEvent.getHandlerList().unregister(this);
            InventoryOpenEvent.getHandlerList().unregister(this);
            InventoryCloseEvent.getHandlerList().unregister(this);
        }
    }

}

