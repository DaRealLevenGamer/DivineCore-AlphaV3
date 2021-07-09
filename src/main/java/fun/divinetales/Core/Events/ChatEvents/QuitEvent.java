package fun.divinetales.Core.Events.ChatEvents;

import fun.divinetales.Core.CoreMain;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class QuitEvent implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void on(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        CoreMain.getInstance().getPlayerManager().registerPlayer(p.getUniqueId());
        CoreMain.getInstance().getPlayerProfileManager().registerPlayer(p.getUniqueId());
    }
}

