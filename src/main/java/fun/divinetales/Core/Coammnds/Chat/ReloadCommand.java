package fun.divinetales.Core.Coammnds.Chat;

import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Utils.ChatUtils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
    static CoreMain main = CoreMain.getInstance();

    static MessageUtils msg = main.getMsgUtil();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("mc.reload")) {
            Bukkit.getPluginManager().disablePlugin(main);
            Bukkit.getPluginManager().enablePlugin(main);
            sender.sendMessage("Complete");
        } else {
            msg.sendMessage(sender, MessageUtils.Message.NO_PERMISSION);
            return true;
        }
        return false;
    }
}

