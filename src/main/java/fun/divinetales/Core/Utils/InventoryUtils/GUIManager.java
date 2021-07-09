package fun.divinetales.Core.Utils.InventoryUtils;

import java.util.ArrayList;

import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.listeners.GUIListeners;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


public class GUIManager {

    private final JavaPlugin plugin;

    public void openGUI(Player player, GUIUtils gui) {

        player.openInventory(setupInventory(gui));
        plugin.getServer().getPluginManager().registerEvents(new GUIListeners(gui, this), CoreMain.getInstance());

    }

    private Inventory setupInventory(GUIUtils gui) {

        String title = ChatColor.translateAlternateColorCodes('&', gui.getTitle());
        Inventory inventory = Bukkit.createInventory(null, gui.getSize(), title);

        for(GUIEntry entry : gui.getEntries()) {

            inventory.setItem(entry.getSlot(), buildEntry(entry));

        }

        gui.setInventory(inventory);
        return inventory;

    }

    private ItemStack buildEntry(GUIEntry entry) {

        ItemStack filer1 = new ItemStack(entry.getType(null), entry.getQuantity());
        ItemMeta filer1meta = filer1.getItemMeta();

        filer1meta.setDisplayName(entry.getName());

        if (entry.getLore() != null && entry.getLore().size() > 0) {

            ArrayList<String> lore = new ArrayList<>();

            for (String currentLine : entry.getLore()) {

                String line = ChatColor.translateAlternateColorCodes('&', currentLine);
                lore.add(line);

            }
            filer1meta.setLore(lore);
        }

        filer1.setItemMeta(filer1meta);
        return filer1;

    }

    public GUIManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }
}

