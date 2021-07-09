package fun.divinetales.Core.Dungeons.Commands.SubCommands.DungeonTeam;

import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Dungeons.Utils.TeamManager;
import fun.divinetales.Core.Utils.ChatUtils.MessageUtils;
import fun.divinetales.Core.Utils.CommandUtils.SubCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import static fun.divinetales.Core.Utils.ColorUtil.*;

public class TeamActive extends SubCommand {

    private TeamManager manager;
    MessageUtils msgUtil = CoreMain.getInstance().getMsgUtil();
    public TeamActive(TeamManager manager) {
        this.manager = manager;
    }

    @Override
    public String getName() {
        return "active";
    }

    @Override
    public String getDescription() {
        return "Shows active teams!";
    }

    @Override
    public String getSyntax() {
        return "/dungeonteam active";
    }

    @Override
    public void perform(Player player, String[] args) {

        for ( String teams  : manager.getTeams()) {

            player.sendMessage(color("&7&l-------------&8&l>>") + this.msgUtil.getCReplaceMessage(MessageUtils.Message.PLAYERDUNGEON) + color("&8&l<<&7&l-------------"));
            Player player1 = Bukkit.getPlayer(manager.getTeamOwner(teams));

            TextComponent msg = new TextComponent(color("&7&l>>") + color("&f&l" + teams) + color("&7&l<<"));
            msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(color("&f&l" + manager.getTeamOwner(teams)))));
            assert player1 != null;
            msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dungeonteam members " + manager.getTeam(player1)));
            player.spigot().sendMessage(msg);

            player.sendMessage(color("&7&l-------------------------------------------"));

        }

    }

}
