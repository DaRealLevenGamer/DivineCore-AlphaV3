package fun.divinetales.Core.Coammnds.SubCommands.CosmicSubCommands;

import fun.divinetales.Core.Events.MainEvent.CurseListeners;
import fun.divinetales.Core.Utils.CommandUtils.SubCommand;
import fun.divinetales.Core.Utils.CosmicUtils.CurseDefine;
import fun.divinetales.Core.Utils.CosmicUtils.CurseUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CurseShow extends SubCommand {
    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "shows a players rep!";
    }

    @Override
    public String getSyntax() {
        return "/curse show [player]";
    }

    @Override
    public void perform(Player sender, String[] args) {

        if (args.length > 1) {
            UUID player = Bukkit.getOfflinePlayer(args[1]).getUniqueId();
            if (CurseDefine.reputation.containsKey(player)) {
                sender.sendMessage(CurseUtil.color(CurseDefine.rep.replace("{rep}", String.valueOf(CurseDefine.reputation.get(Bukkit.getPlayer(args[1]).getUniqueId())))));
            } else {
                if (CurseListeners.getRepConfig().getConfig().contains(player.toString())) {
                    sender.sendMessage(CurseUtil.color(CurseDefine.rep.replace("{rep}", String.valueOf(
                            CurseListeners.getRepConfig().getConfig().getDouble(Bukkit.getPlayer(args[1]).getUniqueId().toString())))));
                } else {
                    sender.sendMessage(CurseUtil.color(CurseDefine.notplayed));
                }
            }
        } else {
            sender.sendMessage(CurseUtil.color("&e/rep show <name>"));
        }

    }
}
