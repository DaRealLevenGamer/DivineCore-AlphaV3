package fun.divinetales.Core.Coammnds.SubCommands.Core;

import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Utils.ChatUtils.MessageUtils;
import fun.divinetales.Core.Utils.CommandUtils.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import static fun.divinetales.Core.Utils.ColorUtil.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class staffmsg extends SubCommand {
    @Override
    public String getName() {
        return "playermsg";
    }

    @Override
    public String getDescription() {
        return "Send a private message to a player in the form of a broadcast";
    }

    @Override
    public String getSyntax() {
        return "/core playermsg [player [MSG]";
    }

    @Override
    public void perform(Player player, String[] args) throws IOException {

        MessageUtils msgUtil = CoreMain.getInstance().getMsgUtil();

        if (!(args.length > 0)){
            msgPlayer(player, color(getSyntax()));
            return;
        }

        if (!player.hasPermission("core.playermsg")){
            msgPlayer(player, msgUtil.getCReplaceMessage(MessageUtils.Message.NO_PERMISSION));
            return;
        }

        String message = args[2];
        String StaffFormat = msgUtil.getCReplaceMessage(MessageUtils.Message.DIVINESTAFF);

        Player reciever = Bukkit.getPlayer(args[1]);

        if (reciever == null) {
            msgPlayer(player, color("&a&lPlayer dose not exist!"));
            return;
        }

        reciever.sendMessage(StaffFormat + " " + color(message));



    }
}
