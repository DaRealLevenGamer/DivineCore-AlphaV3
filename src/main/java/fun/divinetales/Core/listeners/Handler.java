package fun.divinetales.Core.listeners;


import fun.divinetales.Core.CoreMain;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;


public class Handler implements Listener {
    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player damager = (Player)event.getDamager();
            Player entity = (Player)event.getEntity();
            String otherGroup = (String) CoreMain.getInstance().getPexGroups().get(getPrimaryGroup(damager.getName()));
            if (getPrimaryGroup(entity.getName()).equalsIgnoreCase(otherGroup))
                event.setCancelled(true);
        }
    }

    public String getPrimaryGroup(String playerName) {
        PermissionUser user = PermissionsEx.getPermissionManager().getUser(playerName);
        if (user == null)
            return null;
        if (user.getParentIdentifiers().size() > 0)
            return user.getParentIdentifiers().get(0);
        return null;
    }
}


