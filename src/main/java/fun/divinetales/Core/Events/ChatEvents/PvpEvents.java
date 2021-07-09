package fun.divinetales.Core.Events.ChatEvents;

import java.util.Iterator;

import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Utils.ChatUtils.MessageUtils;
import fun.divinetales.Core.Utils.Data.ChatPlayerData;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PvpEvents implements Listener {
    CoreMain main = CoreMain.getInstance();

    MessageUtils msg = this.main.getMsgUtil();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onHit(EntityDamageByEntityEvent e) {
        Entity target = e.getEntity();
        Entity damager = e.getDamager();
        if (target instanceof Player) {
            if (damager instanceof Player) {
                ChatPlayerData a = CoreMain.getInstance().getPlayerManager().getPlayer(target.getUniqueId());
                ChatPlayerData b = CoreMain.getInstance().getPlayerManager().getPlayer(damager.getUniqueId());
                for (String al : a.getLanden()) {
                    Iterator<String> iterator = b.getLanden().iterator();
                    if (iterator.hasNext()) {
                        String bl = iterator.next();
                        if (al.equalsIgnoreCase(bl)) {
                            this.msg.sendNoPrefixMessage(damager, MessageUtils.Message.OWN_HIT);
                            e.setCancelled(true);
                            return;
                        }
                        if (this.main.getAllyManager().isAlly(al, bl)) {
                            this.msg.sendNoPrefixMessage(damager, MessageUtils.Message.ALLY_HIT);
                            e.setCancelled(true);
                            return;
                        }
                        return;
                    }
                }
            } else if (damager instanceof Projectile) {
                Projectile p = (Projectile)damager;
                if (p.getShooter() instanceof Player) {
                    ChatPlayerData a = CoreMain.getInstance().getPlayerManager().getPlayer(target.getUniqueId());
                    ChatPlayerData b = CoreMain.getInstance().getPlayerManager().getPlayer(((Player)p.getShooter()).getUniqueId());
                    for (String al : a.getLanden()) {
                        Iterator<String> iterator = b.getLanden().iterator();
                        if (iterator.hasNext()) {
                            String bl = iterator.next();
                            if (al.equalsIgnoreCase(bl)) {
                                this.msg.sendNoPrefixMessage((CommandSender)p.getShooter(), MessageUtils.Message.OWN_HIT);
                                e.setCancelled(true);
                                return;
                            }
                            if (this.main.getAllyManager().isAlly(al, bl)) {
                                this.msg.sendNoPrefixMessage((CommandSender)p.getShooter(), MessageUtils.Message.ALLY_HIT);
                                e.setCancelled(true);
                                return;
                            }
                            return;
                        }
                    }
                }
            }
        }
    }
}

