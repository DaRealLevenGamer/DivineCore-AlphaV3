package fun.divinetales.Core.Coammnds.SubCommands.CosmicSubCommands;

import fun.divinetales.Core.Events.MainEvent.CurseListeners;
import fun.divinetales.Core.Utils.CommandUtils.SubCommand;
import fun.divinetales.Core.Utils.CosmicUtils.CurseDefine;
import fun.divinetales.Core.Utils.CosmicUtils.CurseUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.UUID;

public class CurseAdd extends SubCommand {
    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "Adds both good and bad rep to players!";
    }

    @Override
    public String getSyntax() {
        return "/curse add [player] [value]";
    }

    @Override
    public void perform(Player sender, String[] args) {

        if (args.length > 2) {
            if (CurseUtil.number(args[2])) {
                UUID player = Bukkit.getOfflinePlayer(args[1]).getUniqueId();
                if (CurseDefine.reputation.containsKey(player)) {
                    CurseDefine.reputation.replace(player,
                            CurseDefine.reputation.get(player) + Double.parseDouble(args[2]));
                    if (CurseDefine.reputation.get(player) >= 0.0D) {
                        CurseDefine.badrep.remove(player);
                    } else {
                        CurseUtil.stagerep(player);
                    }
                    sender.sendMessage(CurseUtil.color(CurseDefine.added.replace("{rep}", args[2]).replace("{player}", args[1])));
                    CurseListeners.getRepConfig().getConfig().set(player.toString(), CurseListeners.getRepConfig().getConfig().getDouble(player.toString()) + Double.parseDouble(args[2]));
                    CurseListeners.getRepConfig().save();
                } else {
                    if (CurseListeners.getRepConfig().getConfig().contains(player.toString())) {
                        CurseListeners.getRepConfig().getConfig().set(player.toString(),
                                CurseListeners.getRepConfig().getConfig().getDouble(player.toString()) + Double.parseDouble(args[2]));
                        CurseListeners.getRepConfig().save();
                        sender.sendMessage(CurseUtil.color(CurseDefine.added.replace("{rep}", args[2]).replace("{player}", args[1])));
                    } else {
                        sender.sendMessage(CurseUtil.color(CurseDefine.notplayed));
                    }
                }
            } else {
                sender.sendMessage(CurseUtil.color(CurseDefine.number));
            }
        } else {
            sender.sendMessage(CurseUtil.color("&e/rep add <name> amount"));
        }

    }
}
