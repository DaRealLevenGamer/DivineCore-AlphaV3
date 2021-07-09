package fun.divinetales.Core.Utils;

import fun.divinetales.Core.CoreMain;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.logging.Logger;

public class ColorUtil {

    private static Logger logger = CoreMain.getPluginLogger();

    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void log(String... strings) {
        for (String string : strings) {
            logger.info(string);
        }
    }

    public static String deColor(String string) {
        return ChatColor.stripColor(string);
    }

    public static void msgPlayer(Player player, String... strings) {
        for (String string : strings) {
            player.sendMessage(color(string));
        }
    }

}
