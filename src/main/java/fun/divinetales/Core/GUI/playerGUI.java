package fun.divinetales.Core.GUI;

import fun.divinetales.Core.GUI.Entries.ProfileEntry;
import fun.divinetales.Core.GUI.Entries.SettingsEntry;
import fun.divinetales.Core.GUI.Entries.TimeEntry;
import fun.divinetales.Core.Utils.InventoryUtils.GUIEntry;
import fun.divinetales.Core.Utils.InventoryUtils.GUIUtils;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import static fun.divinetales.Core.Utils.ColorUtil.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class playerGUI extends GUIUtils {
    private List<GUIEntry> entries;
    private List<String> settingsLore = new ArrayList<>();

    public playerGUI(String identifier, int size, String title) {
        super(identifier, size, title);

        this.entries = Arrays.asList(

                new SettingsEntry(Material.COMPASS, color("&8&lSettings"), settingsLore, 1, 15)

        );

    }

    public void setLore() {
        settingsLore.add(color("&f&lOpens the players settings!"));
    }

    @Override
    public List<GUIEntry> getEntries() {
        return this.entries;
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        
    }

    @Override
    public void onClose(InventoryCloseEvent event) {

    }
}
