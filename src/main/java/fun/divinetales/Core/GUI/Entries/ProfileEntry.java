package fun.divinetales.Core.GUI.Entries;

import fun.divinetales.Core.Utils.InventoryUtils.GUIEntry;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;

public class ProfileEntry extends GUIEntry {
    public ProfileEntry(Material material, String name, List<String> lore, int quantity, int slot) {
        super(material, name, lore, quantity, slot);
    }

    @Override
    public void onClick(InventoryClickEvent event) {

    }

    @Override
    public void onUpdate() {

    }
}
