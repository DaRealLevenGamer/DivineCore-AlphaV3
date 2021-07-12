package fun.divinetales.Core.Coammnds.Core;

import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Utils.ChatUtils.MessageUtils;
import fun.divinetales.Core.Utils.CommandUtils.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;

import static fun.divinetales.Core.Utils.ColorUtil.color;
import static fun.divinetales.Core.Utils.ColorUtil.msgPlayer;

public class UnFreezeCommand extends SubCommand {
    @Override
    public String getName() {
        return "UnFreeze";
    }

    @Override
    public String getDescription() {
        return "Unfreezes a frozen player!";
    }

    @Override
    public String getSyntax() {
        return "/core unfreeze [player]";
    }

    @Override
    public void perform(Player player, String[] args) throws IOException {

        MessageUtils msgUtil = CoreMain.getInstance().getMsgUtil();

        if (args.length > 1) {

            if (player.hasPermission("core.freeze.player")) {
                Player target = Bukkit.getPlayer(args[1]);

                assert target != null;
                if (!(target.getWalkSpeed() == 0.0F)) {
                    msgPlayer(player, msgUtil.getCReplaceMessage(MessageUtils.Message.DIVINESTAFF) + color(" &c&lPlayer ") + target.getName() + color(" &c&lIs not Frozen!"));
                    return;
                }

                target.setWalkSpeed(0.2F);
                target.setFlySpeed(0.2F);
                target.sendTitle(color("&3&lYou have been UnFrozen!"), color("&c&lSorry for the inconvenience.."), 0, 100, 0);
            }

        }

    }
}
