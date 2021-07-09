package fun.divinetales.Core.Utils.ChatUtils;

import fun.divinetales.Core.CoreMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ChatInvUtils {

    private final MessageUtils msg;

    public ChatInvUtils(CoreMain main) {
        this.msg = main.getMsgUtil();
    }

    public void setupChatInventory(Player p, boolean lc, int toggle, boolean gc, boolean gchat) {

        ItemStack item1;
        ItemMeta item1m;
        ItemStack item2;
        ItemMeta item2m;
        ItemStack item3;
        ItemMeta item3m;
        ItemStack item4;
        ItemMeta item4m;
        Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, this.msg.getCMessage(MessageUtils.Message.CHAT_MENU_TITLE));
        if (lc) {
            item1 = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
            item1m = item1.getItemMeta();
            assert item1m != null;
            item1m.setDisplayName(this.msg.getCReplaceMessage(MessageUtils.Message.LAND_CHAT, this.msg.getCMessage(MessageUtils.Message.CHAT_BUTTON_ENABLED)));
        } else {
            item1 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            item1m = item1.getItemMeta();
            assert item1m != null;
            item1m.setDisplayName(this.msg.getCReplaceMessage(MessageUtils.Message.LAND_CHAT, this.msg.getCMessage(MessageUtils.Message.CHAT_BUTTON_DISABLED)));
        }
        item1m.setLore(getLore(this.msg.getCReplaceMessage(MessageUtils.Message.LAND_CHAT_LORE)));
        item1.setItemMeta(item1m);
        if (toggle == 0) {
            item2 = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
            item2m = item2.getItemMeta();
            assert item2m != null;
            item2m.setDisplayName(this.msg.getCReplaceMessage(MessageUtils.Message.DEFAULT_CHAT, this.msg.getCMessage(MessageUtils.Message.LAND_CHAT_NAME)));
        } else if (toggle == 1) {
            item2 = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
            item2m = item2.getItemMeta();
            assert item2m != null;
            item2m.setDisplayName(this.msg.getCReplaceMessage(MessageUtils.Message.DEFAULT_CHAT, this.msg.getCMessage(MessageUtils.Message.GLOBAL_CHAT_NAME)));
        } else {
            item2 = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
            item2m = item2.getItemMeta();
            assert item2m != null;
            item2m.setDisplayName(this.msg.getCReplaceMessage(MessageUtils.Message.DEFAULT_CHAT, this.msg.getCMessage(MessageUtils.Message.ROLEPLAY_CHAT_NAME)));
        }
        item2m.setLore(getLore(this.msg.getCReplaceMessage(MessageUtils.Message.DEFAULT_CHAT_LORE)));
        item2.setItemMeta(item2m);
        if (gc) {
            item3 = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
            item3m = item3.getItemMeta();
            assert item3m != null;
            item3m.setDisplayName(this.msg.getCReplaceMessage(MessageUtils.Message.ROLEPLAY_CHAT, this.msg.getCMessage(MessageUtils.Message.CHAT_BUTTON_ENABLED)));
        } else {
            item3 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            item3m = item3.getItemMeta();
            assert item3m != null;
            item3m.setDisplayName(this.msg.getCReplaceMessage(MessageUtils.Message.ROLEPLAY_CHAT, this.msg.getCMessage(MessageUtils.Message.CHAT_BUTTON_DISABLED)));
        }
        item3m.setLore(getLore(this.msg.getCReplaceMessage(MessageUtils.Message.ROLEPLAY_CHAT_LORE)));
        item3.setItemMeta(item3m);
        if (gchat) {
            item4 = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
            item4m = item4.getItemMeta();
            assert item4m != null;
            item4m.setDisplayName(this.msg.getCReplaceMessage(MessageUtils.Message.GLOBAL_CHAT, this.msg.getCMessage(MessageUtils.Message.CHAT_BUTTON_ENABLED)));
        } else {
            item4 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            item4m = item4.getItemMeta();
            assert item4m != null;
            item4m.setDisplayName(this.msg.getCReplaceMessage(MessageUtils.Message.GLOBAL_CHAT, this.msg.getCMessage(MessageUtils.Message.CHAT_BUTTON_DISABLED)));
        }
        item4m.setLore(getLore(this.msg.getCReplaceMessage(MessageUtils.Message.GLOBAL_CHAT_LORE)));
        item4.setItemMeta(item4m);
        inv.setItem(0, item1);
        inv.setItem(1, item3);
        inv.setItem(2, item4);
        inv.setItem(4, item2);
        p.openInventory(inv);

    }

    private List<String> getLore(String input) {
        String[] split = input.split("(?<=\\})|(?=\\{)");
        List<String> list = new ArrayList<>();
        String last = "";
        for (String s : split) {
            if (s.equalsIgnoreCase("{0}")) {
                list.add("");
            } else {
                list.add(last + s);
                last = ChatColor.getLastColors(s);
            }
        }
        return list;
    }

}
