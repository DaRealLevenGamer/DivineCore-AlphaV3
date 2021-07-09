package fun.divinetales.Core.Dungeons.Utils;

import fun.divinetales.Core.CoreMain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static fun.divinetales.Core.Utils.ColorUtil.*;
import static fun.divinetales.Core.Utils.ColorUtil.color;

import java.util.HashMap;
import java.util.UUID;

public class Accept {

    private static HashMap<UUID, UUID> invitedList = new HashMap<>();

    public static void invite(Player inviter, Player acceptor) {
        invitedList.put(acceptor.getUniqueId(), inviter.getUniqueId());

            Bukkit.getScheduler().runTaskLater(CoreMain.getInstance(), () -> {
                if (invitedList.containsKey(acceptor.getUniqueId())) {
                    invitedList.remove(acceptor.getUniqueId(), inviter.getUniqueId());
                    msgPlayer(inviter, color("&c&lPlayer did not accept your invite!"));
                }
            }, 600);
    }

    public static void accept(Player inviter, Player acceptor, TeamManager manager) {
        if (invitedList.containsKey(acceptor.getUniqueId()) && invitedList.containsValue(inviter.getUniqueId())) {
            manager.addPlayer(acceptor, manager.getTeam(inviter), "Default");
            invitedList.remove(acceptor.getUniqueId(), inviter.getUniqueId());
            msgPlayer(inviter, color("&f&lPlayer: " + acceptor.getDisplayName() + " &f&lHas joined the party!"));
        } else {
            msgPlayer(acceptor, color("&c&lYou need to be invited!"));
        }

    }

    public static void deny(Player inviter, Player acceptor) {
        if (invitedList.containsKey(acceptor.getUniqueId()) && invitedList.containsValue(inviter.getUniqueId()))  {
            invitedList.remove(acceptor.getUniqueId(), inviter.getUniqueId());
            msgPlayer(inviter, color("&c&lPlayer has denied your request! LMAO XD"));
        } else {
            msgPlayer(acceptor, color("&c&lYou need to be invited!"));
        }

    }

}
