package fun.divinetales.Core.Coammnds.Chat;

import java.util.HashMap;
import java.util.Map;

import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.JSON.JSONMessage;
import fun.divinetales.Core.Utils.ChatUtils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class JoinCommand implements CommandExecutor, Listener {
    private final CoreMain main = CoreMain.getInstance();

    private final MessageUtils msg = this.main.getMsgUtil();

    public static Map<Player, DATA> players = new HashMap<>();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("mc.land")) {
            this.msg.sendMessage(sender, MessageUtils.Message.NO_PERMISSION);
            return true;
        }
        if (args.length < 3)
            return true;
        Player p = Bukkit.getPlayer(args[0]);
        players.put(p, new DATA(args[1], args[2]));
        JSONMessage.sendTitle(p, "", "uw skype in de chat.", 0, 30, 0);
        return false;
    }

    public static void start() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(CoreMain.getInstance(), () -> {
            for (Player p : JoinCommand.players.keySet()) {
                if (p.isOnline())
                    JSONMessage.sendTitle(p, "", "uw skype in de chat.", 10, 30, 10);
            }
        },20L, 40L);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (players.containsKey(p)) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mail send " + players.get(p).getLeider() + " " + p.getName() + " Skype: " + e.getMessage());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + p.getName() + " group set " + players.get(p).getLand());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawn " + p.getName());
            this.msg.sendMessage(p, "in uw land!", Boolean.TRUE);
            e.setCancelled(true);
            players.remove(p);
            JSONMessage.sendTitle(p, "", "", 10, 30, 10);
        }
    }

    private static class DATA {
        String land;

        String leider;

        public DATA(String land, String leider) {
            this.land = land;
            this.leider = leider;
        }

        public String getLand() {
            return this.land;
        }

        public String getLeider() {
            return this.leider;
        }
    }
}

