package fun.divinetales.Core.Dungeons.Commands.SubCommands.DungeonTeam;

import fun.divinetales.Core.Dungeons.Utils.Accept;
import fun.divinetales.Core.Dungeons.Utils.TeamManager;
import fun.divinetales.Core.Utils.CommandUtils.SubCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import static fun.divinetales.Core.Utils.ColorUtil.*;

public class TeamInvite extends SubCommand {

    private TeamManager manager;
    public TeamInvite(TeamManager manager) {
        this.manager = manager;
    }

    @Override
    public String getName() {
        return "invite";
    }

    @Override
    public String getDescription() {
        return "invites a player to the team!";
    }

    @Override
    public String getSyntax() {
        return "/dungeonteam invite [player]";
    }

    @Override
    public void perform(Player player, String[] args) {



            if (args.length > 1) {

                Player invited = Bukkit.getPlayer(args[1]);

                if (invited == null) {
                    msgPlayer(player, color("&c&lThat player dose not exist!"));
                    return;
                }

                if (!manager.isInTeam(player)) {
                    msgPlayer(player, color("&c&lYou need to be in a team!"));
                    msgPlayer(player, color("&f&lRun /dungeon create [Name]"));
                    return;
                }

                assert invited != null;
                if (manager.isInTeam(invited)) {
                    msgPlayer(player, color("&c&lPlayer is already in a team!"));
                    return;
                }

                Accept.invite(player, invited);
                msgPlayer(invited, color("&c&lPlayer: " + player.getDisplayName() + " &f&lHas invited you to there party: &6&l" + manager.getTeam(player) + color("&f&l!")));

                TextComponent accept = new TextComponent();
                TextComponent deny = new TextComponent();

                accept.setText(color("&7[&f&lAccept&7] "));
                deny.setText(color("&7[&c&lDeny&7]"));

                accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dungeon accept " + player.getName()));
                deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dungeon deny " + player.getName()));

                invited.spigot().sendMessage(accept, deny);

            }

    }
}
