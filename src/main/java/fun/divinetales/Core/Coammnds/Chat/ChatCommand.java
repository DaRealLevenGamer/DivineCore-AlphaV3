package fun.divinetales.Core.Coammnds.Chat;

import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Utils.Data.ChatPlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatCommand implements CommandExecutor {
    private final CoreMain main = CoreMain.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            ChatPlayerData mp = this.main.getPlayerManager().getPlayer(p.getUniqueId());
            this.main.getInventoryUtil().setupChatInventory(p, mp.hasEnabledLChat(), mp.hasGlobalChat(), mp.hasEnabledGChat(), mp.hasGChat());
        } else {
            return true;
        }
        return false;
    }
}


