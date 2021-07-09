package fun.divinetales.Core.Dungeons.Commands.SubCommands.DungeonTeam;

import fun.divinetales.Core.Dungeons.Utils.TeamManager;
import fun.divinetales.Core.Utils.CommandUtils.SubCommand;
import org.bukkit.entity.Player;
import static fun.divinetales.Core.Utils.ColorUtil.*;

public class TeamLeave extends SubCommand {

    private TeamManager manager;
    public TeamLeave(TeamManager manager) {
        this.manager = manager;
    }

    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getDescription() {
        return "Lets a player leave a team!";
    }

    @Override
    public String getSyntax() {
        return "/dungeonteam leave";
    }

    @Override
    public void perform(Player player, String[] args) {

        if (!manager.isInTeam(player)) {
            msgPlayer(player, color("&c&lYou need to be in a team!"));
            return;
        }

        if (manager.getPlayerRole(player).equals("owner")) {
            msgPlayer(player, color("&c&lYou cant leave your own team!"));
            return;
        }

        String team = manager.getTeam(player);

        manager.leaveTeam(player);
        msgPlayer(player, color("&f&lYou left the team! " + team));

    }
}
