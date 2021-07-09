package fun.divinetales.Core.Events.MainEvent;

import fun.divinetales.Core.Utils.CosmicUtils.CurseDefine;
import fun.divinetales.Core.Utils.CosmicUtils.CurseUtil;
import fun.divinetales.Core.Utils.Data.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import static fun.divinetales.Core.Utils.ColorUtil.*;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class CurseListeners implements Listener {

    private static Config repConfig;


    public CurseListeners(Config cfg) {
        repConfig = cfg;
    }

    @EventHandler
    public void kill(EntityDeathEvent e) {
        if (CurseDefine.on && CurseDefine.klls &&
                e.getEntity() instanceof Player && e.getEntity().getKiller() != null)
            CurseDefine.reputation.replace(e.getEntity().getKiller().getUniqueId(),
                    CurseDefine.reputation.get(e.getEntity().getKiller().getUniqueId()) - CurseDefine.pkillrep);
    }

    @EventHandler
    public void jn(PlayerJoinEvent e) {
        UUID player = e.getPlayer().getUniqueId();
        if (repConfig.getConfig().contains(player.toString())) {
            CurseDefine.reputation.put(player, repConfig.getConfig().getDouble(player.toString()));
            if (CurseDefine.reputation.get(player) < 0.0D)
                CurseUtil.stage(player);
        } else {
            CurseDefine.reputation.put(player, 0.0D);
            repConfig.set(player.toString(), 0.0D);
        }
        CurseDefine.timer.put(player, CurseUtil.random());
        repConfig.save();
    }

    @EventHandler
    public void qt(PlayerQuitEvent e) {
        UUID u = e.getPlayer().getUniqueId();
        repConfig.set(u.toString(), CurseDefine.reputation.get(u));
        CurseDefine.reputation.remove(u);
        CurseDefine.timer.remove(u);
        CurseDefine.badrep.remove(u);
        repConfig.save();
    }

    @EventHandler
    public void et(EntityDamageByEntityEvent e) {
        if (CurseDefine.on && e.getEntity() instanceof Player)
            if (CurseDefine.badrep.containsKey(e.getEntity().getUniqueId())) {
                if (!(e.getDamager() instanceof Player))
                    e.setDamage(e.getDamage() * 2.5D * (Integer) CurseDefine.badrep.get(e.getEntity().getUniqueId()));
            } else {
                Player p = (Player) e.getEntity();
                if (p.getHealth() <= 4.0D) {
                    if (ThreadLocalRandom.current().nextInt() * 100 < 25)
                        p.setHealth(p.getHealth() + 4.0D);
                    p.sendMessage(color(CurseDefine.heal));
                }
            }
    }

    @EventHandler
    public void kc(PlayerKickEvent e) {
        UUID u = e.getPlayer().getUniqueId();
        repConfig.set(u.toString(), CurseDefine.reputation.get(u));
        CurseDefine.reputation.remove(u);
        CurseDefine.timer.remove(u);
        CurseDefine.badrep.remove(u);
        repConfig.save();
    }

    public static Config getRepConfig() {
        return repConfig;
    }

}
