package fun.divinetales.Core.Coammnds.Chat;

import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Utils.ChatUtils.MessageUtils;
import fun.divinetales.Core.Utils.Data.ChatPlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatSpyCommand implements CommandExecutor {
    private final CoreMain main = CoreMain.getInstance();

    private final MessageUtils msg = this.main.getMsgUtil();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("mc.chat.spy")) {
            this.msg.sendMessage(sender, MessageUtils.Message.NO_PERMISSION);
            return true;
        }
        if (sender instanceof Player) {
            Player p = (Player)sender;
            ChatPlayerData mp = CoreMain.getInstance().getPlayerManager().getPlayer(p.getUniqueId());
            if (mp.hasSpy()) {
                mp.setSpy(Boolean.FALSE);
                this.msg.sendMessage(sender, MessageUtils.Message.SPY_DISABLED);
            } else {
                mp.setSpy(Boolean.TRUE);
                this.msg.sendMessage(sender, MessageUtils.Message.SPY_ENABLED);
            }
        } else {
            return true;
        }
        return false;
    }
}


