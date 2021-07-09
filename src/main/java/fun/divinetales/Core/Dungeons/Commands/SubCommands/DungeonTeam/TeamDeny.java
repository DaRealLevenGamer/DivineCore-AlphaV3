package fun.divinetales.Core.Dungeons.Commands.SubCommands.DungeonTeam;

import fun.divinetales.Core.Dungeons.Utils.Accept;
import fun.divinetales.Core.Utils.CommandUtils.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static fun.divinetales.Core.Utils.ColorUtil.color;
import static fun.divinetales.Core.Utils.ColorUtil.msgPlayer;

public class TeamDeny extends SubCommand {

    @Override
    public String getName() {
        return "deny";
    }

    @Override
    public String getDescription() {
        return "Denys a team invite!";
    }

    @Override
    public String getSyntax() {
        return "/dungeonteam deny [Invitor]";
    }

    @Override
    public void perform(Player player, String[] args) {

        if (args.length > 1) {
            Player player1 = Bukkit.getPlayer(args[1]);

            if (player1 == null) {
                msgPlayer(player, color("&c&lThat player dose not exist!"));
                return;
            }

            Accept.deny(player1, player);
        }

    }
}
