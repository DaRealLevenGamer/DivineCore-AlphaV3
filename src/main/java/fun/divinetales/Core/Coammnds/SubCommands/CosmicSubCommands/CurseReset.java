package fun.divinetales.Core.Coammnds.SubCommands.CosmicSubCommands;

import fun.divinetales.Core.Events.MainEvent.CurseListeners;
import fun.divinetales.Core.Utils.CommandUtils.SubCommand;
import fun.divinetales.Core.Utils.CosmicUtils.CurseDefine;
import fun.divinetales.Core.Utils.CosmicUtils.CurseUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CurseReset extends SubCommand {
    @Override
    public String getName() {
        return "reset";
    }

    @Override
    public String getDescription() {
        return "resets a players reputation!";
    }

    @Override
    public String getSyntax() {
        return "/curse reset [player]";
    }

    @Override
    public void perform(Player sender, String[] args) {

        if (args.length > 1) {
            UUID player = Bukkit.getOfflinePlayer(args[1]).getUniqueId();
            if (CurseDefine.reputation.containsKey(player)) {
                CurseDefine.reputation.replace(player, 0.0D);
                CurseDefine.badrep.remove(player);
                CurseListeners.getRepConfig().getConfig().set(player.toString(), 0.0D);
                CurseListeners.getRepConfig().save();
                sender.sendMessage(CurseUtil.color(CurseDefine.reset));
            } else {
                if (CurseListeners.getRepConfig().getConfig().contains(player.toString())) {
                    CurseListeners.getRepConfig().getConfig().set(player.toString(), 0.0D);
                    CurseListeners.getRepConfig().save();
                    sender.sendMessage(CurseUtil.color(CurseDefine.reset));
                } else {
                    sender.sendMessage(CurseUtil.color(CurseDefine.notplayed));
                }
            }
        } else {
            sender.sendMessage(CurseUtil.color("&e/rep reset <name>"));
        }

    }
}
