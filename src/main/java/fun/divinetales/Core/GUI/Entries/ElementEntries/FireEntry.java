package fun.divinetales.Core.GUI.Entries.ElementEntries;

import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Utils.ChatUtils.MessageUtils;
import fun.divinetales.Core.Utils.Data.PlayerData.ProfileCreateEvent;
import fun.divinetales.Core.Utils.InventoryUtils.GUIEntry;
import fun.divinetales.Core.Utils.MYSQL.Data.SQLPlayerData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;
import java.util.UUID;

import static fun.divinetales.Core.Utils.ColorUtil.color;
import static fun.divinetales.Core.Utils.ColorUtil.msgPlayer;

public class FireEntry extends GUIEntry {

    public FireEntry(Material material, String name, List<String> lore, int quantity, int slot) {
        super(material, name, lore, quantity, slot);
    }

    @Override
    public void onClick(InventoryClickEvent event) {

        SQLPlayerData data = CoreMain.getInstance().getSqlPlayerData();
        MessageUtils msgUtil = CoreMain.getInstance().getMsgUtil();

        Player player = (Player) event.getWhoClicked();
        UUID uuid = player.getUniqueId();

        if (CoreMain.getInstance().getSql().isConnected() && CoreMain.getInstance().getSqlPlayerData().exists(uuid)) {
            data.setElement(uuid, "&c&lFireElement");
        } else {
            ProfileCreateEvent config = CoreMain.getInstance().getPlayerProfileManager().getPlayer(player.getUniqueId());
            config.setElement("&c&lFireElement");
            config.save();
        }
        player.closeInventory();
        msgPlayer(player, msgUtil.getCReplaceMessage(MessageUtils.Message.DIVINEPLAYER) + color(" &f&lYou now have the " + "&c&lFireElement"));


    }

    @Override
    public void onUpdate() {

    }
}
