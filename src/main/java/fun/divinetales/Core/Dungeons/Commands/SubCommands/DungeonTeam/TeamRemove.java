package fun.divinetales.Core.Dungeons.Commands.SubCommands.DungeonTeam;

import fun.divinetales.Core.Dungeons.Utils.TeamManager;
import fun.divinetales.Core.Utils.CommandUtils.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import static fun.divinetales.Core.Utils.ColorUtil.*;

public class TeamRemove extends SubCommand {

    private TeamManager manager;
    public TeamRemove(TeamManager manager) {
        this.manager = manager;
    }

    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public String getDescription() {
        return "removes a player from the team!";
    }

    @Override
    public String getSyntax() {
        return "/dungeonteam remove [player]";
    }

    @Override
    public void perform(Player player, String[] args) {

        Player player1 = Bukkit.getPlayer(args[1]);
        if (player1 == null) {
            msgPlayer(player, color("&c&lThat player dose not exist!"));
            return;
        }

        if (args.length > 1) {

            if (!manager.isInTeam(player)) {
                msgPlayer(player, color("&c&lYou need to be in a team to run that command!"));
                return;
            }
            if (!manager.getTeam(player1).equals(manager.getTeam(player))) {
                msgPlayer(player, color("&c&lThat player is not in your team!"));
                return;
            }

            if (manager.getPlayerRole(player).equals("owner")) {
                msgPlayer(player, color("&c&lYOu can't kick yourself!"));
                return;
            }

            manager.removePlayer(player1);
            msgPlayer(player, color("&f&lPlayers: " + player1.getDisplayName() + " &f&lHas been removed from the party!"));
        }

    }
}
