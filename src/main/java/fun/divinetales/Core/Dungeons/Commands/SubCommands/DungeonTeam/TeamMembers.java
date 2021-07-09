package fun.divinetales.Core.Dungeons.Commands.SubCommands.DungeonTeam;

import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Dungeons.Utils.TeamManager;
import fun.divinetales.Core.Utils.ChatUtils.MessageUtils;
import fun.divinetales.Core.Utils.CommandUtils.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

import static fun.divinetales.Core.Utils.ColorUtil.*;

public class TeamMembers extends SubCommand {

    MessageUtils msgUtil = CoreMain.getInstance().getMsgUtil();

    private TeamManager manager;
    public TeamMembers(TeamManager manager) {
        this.manager = manager;
    }

    @Override
    public String getName() {
        return "members";
    }

    @Override
    public String getDescription() {
        return "Gets all members of a dungeon team!";
    }

    @Override
    public String getSyntax() {
        return "/dungeonteam members [TeamName]";
    }

    @Override
    public void perform(Player player, String[] args) {

        if (args.length > 1) {

            if (!manager.getTeamExists(args[1])) {
                msgPlayer(player, color("&c&lTeam dose not exist!"));
                return;
            }

            Set<UUID> members = manager.getTeamMembers(args[1]);

            player.sendMessage(color("&7&l-------------&8&l>>") + this.msgUtil.getCReplaceMessage(MessageUtils.Message.PLAYERDUNGEON) + color("&8&l<<&7&l-------------"));
            for (UUID id : members) {

                msgPlayer(player, color("&f&l" + Bukkit.getPlayer(id).getName()));

            }

            player.sendMessage(color("&7&l-------------------------------------------"));

        }

    }
}
