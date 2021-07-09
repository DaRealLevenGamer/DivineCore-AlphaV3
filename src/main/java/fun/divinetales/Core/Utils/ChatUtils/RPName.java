package fun.divinetales.Core.Utils.ChatUtils;

import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Utils.Data.PlayerData.ProfileCreateEvent;
import org.bukkit.entity.Player;

public class RPName {


    public static String getName(Player player) {
        ProfileCreateEvent config = CoreMain.getInstance().getPlayerProfileManager().getPlayer(player.getUniqueId());

        if (config.getRPName().equals("Default") || config.getRPName() == null|| !config.getNameEnabled()) {
           return player.getName();
        } else {
           return config.getRPName();
        }
    }

}
