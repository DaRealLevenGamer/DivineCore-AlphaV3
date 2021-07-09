package fun.divinetales.Core.Utils.InventoryUtils;

import java.util.List;

import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

public abstract class GUIUtils {

    public GUIUtils(String identifier, int size, String title) {
        Identifier = identifier;
        Size = size;
        this.title = title;
    }

    private String Identifier;

    private int Size;
    private String title;

    private Inventory inventory;

    private List<GUIEntry> entries;

    public String getIdentifier() {
        return this.Identifier;
    }

    public int getSize() {
        return this.Size;
    }

    public String getTitle() {
        return this.title;
    }

    public Inventory getInventory() {
        return this.inventory;
    }


    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public List<GUIEntry> getEntries() {
        return entries;
    }

    public abstract void onOpen(InventoryOpenEvent event);

    public abstract void onClose(InventoryCloseEvent event);

}

