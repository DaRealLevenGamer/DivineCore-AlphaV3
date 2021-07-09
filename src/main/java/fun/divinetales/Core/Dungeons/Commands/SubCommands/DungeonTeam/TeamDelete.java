package fun.divinetales.Core.Dungeons.Commands.SubCommands.DungeonTeam;

import fun.divinetales.Core.Dungeons.Utils.TeamManager;
import fun.divinetales.Core.Utils.CommandUtils.SubCommand;
import org.bukkit.entity.Player;
import static fun.divinetales.Core.Utils.ColorUtil.*;

public class TeamDelete extends SubCommand {

    private TeamManager manager;

    public TeamDelete(TeamManager manager) {
        this.manager = manager;
    }

    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public String getDescription() {
        return "Deletes a team!";
    }

    @Override
    public String getSyntax() {
        return "/dungeonteam delete Team";
    }

    @Override
    public void perform(Player player, String[] args) {

        if (args.length > 1) {

            if (!manager.isInTeam(player)) {
                msgPlayer(player, color("&c&lYour not in a team!"));
                return;
            }

            if (manager.deleteTeam(player, manager.getTeam(player))) {
                msgPlayer(player, color("&c&lTeam has been deleted!"));
            } else if (!manager.deleteTeam(player, manager.getTeam(player))) {
                msgPlayer(player, color("&c&lTeam could not be deleted!"));
            }

        }

    }
}
