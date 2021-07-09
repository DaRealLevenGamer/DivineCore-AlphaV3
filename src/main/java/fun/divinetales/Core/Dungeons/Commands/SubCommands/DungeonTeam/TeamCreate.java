package fun.divinetales.Core.Dungeons.Commands.SubCommands.DungeonTeam;

import fun.divinetales.Core.Dungeons.Utils.TeamManager;
import fun.divinetales.Core.Utils.CommandUtils.SubCommand;
import org.bukkit.entity.Player;
import static fun.divinetales.Core.Utils.ColorUtil.*;

public class TeamCreate extends SubCommand {

    private TeamManager manager;

    public TeamCreate(TeamManager manager) {
        this.manager = manager;
    }

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "Creates an team to play dungeons!";
    }

    @Override
    public String getSyntax() {
        return "/dungeonteam create [TeamName]";
    }

    @Override
    public void perform(Player player, String[] args) {

        if (args.length > 1) {

            if (manager.isInTeam(player)) {
                msgPlayer(player, color("&c&lYour already in a team!"));
                return;
            }


            manager.createTeam(player, args[1]);
            msgPlayer(player, color("&6&lTeam has been created!"));

        }

    }
}
