package fun.divinetales.Core.Coammnds.SubCommands.Core;

import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Utils.ChatUtils.MessageUtils;
import fun.divinetales.Core.Utils.CommandUtils.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import static fun.divinetales.Core.Utils.ColorUtil.*;
import java.io.IOException;

public class FreezeCommand extends SubCommand {
    @Override
    public String getName() {
        return "freeze";
    }

    @Override
    public String getDescription() {
        return "Freezes a player and stops them from moving!";
    }

    @Override
    public String getSyntax() {
        return "/core freeze [player]";
    }

    @Override
    public void perform(Player player, String[] args) throws IOException {

        MessageUtils msgUtil = CoreMain.getInstance().getMsgUtil();

        if (args.length > 1) {

            if (player.hasPermission("core.freeze.player")) {
                Player target = Bukkit.getPlayer(args[1]);

                assert target != null;
                target.setWalkSpeed(0.0F);
                target.setFlySpeed(0.0F);
                target.sendTitle(color("&3&lYou have been frozen!"), color("&c&lPlease wait for a staff to approach you.."), 0, 100, 0);
                msgPlayer(player, msgUtil.getCReplaceMessage(MessageUtils.Message.DIVINESTAFF) + color(" &a&lPlayer ") + target.getName() + color(" &a&lhas been &3&lFROZEN"));

            }

        }

    }
}
