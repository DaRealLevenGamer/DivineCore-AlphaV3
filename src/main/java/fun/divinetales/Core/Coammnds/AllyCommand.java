package fun.divinetales.Core.Coammnds;

import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Utils.ChatUtils.MessageUtils;
import fun.divinetales.Core.Utils.Data.AllyData.AllyManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AllyCommand implements CommandExecutor {
    private final CoreMain main = CoreMain.getInstance();

    private final MessageUtils msg = this.main.getMsgUtil();

    private final AllyManager am = this.main.getAllyManager();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("mc.command.ally")) {
            this.msg.sendMessage(sender, MessageUtils.Message.NO_PERMISSION);
            return true;
        }
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            this.msg.sendMessage(sender, MessageUtils.Message.HELP_MESSAGE);
            this.msg.sendNoPrefixMessage(sender, MessageUtils.Message.HELP_FORMAT, "/" + label + " ally <land> <toAlly>", "Toggle land Ally");
            return true;
        }
        if (args[0].equalsIgnoreCase("ally")) {
            if (args.length < 3) {
                this.msg.sendMessage(sender, MessageUtils.Message.COMMAND_USAGE, "/" + label + " ally <land> <toAlly>");
                return true;
            }
            if (this.am.isValid(args[1])) {
                if (this.am.isValid(args[2])) {
                    if (args[1].equalsIgnoreCase(args[2])) {
                        this.msg.sendMessage(sender, MessageUtils.Message.ALLY_SELF, args[1].toLowerCase(), args[2].toLowerCase());
                        return true;
                    }
                    if (this.am.isAlly(args[1], args[2])) {
                        this.am.setAlly(args[1], args[2], false);
                        this.msg.sendMessage(sender, MessageUtils.Message.ALLY_REMOVE, args[1].toLowerCase(), args[2].toLowerCase());
                        return true;
                    }
                    this.am.setAlly(args[1], args[2], true);
                    this.msg.sendMessage(sender, MessageUtils.Message.ALLY_ADD, args[1].toLowerCase(), args[2].toLowerCase());
                    return true;
                }
                this.msg.sendMessage(sender, MessageUtils.Message.ALLY_NOT_EXIST, args[2].toLowerCase());
                return true;
            }
            this.msg.sendMessage(sender, MessageUtils.Message.ALLY_NOT_EXIST, args[1].toLowerCase());
            return true;
        }
        return false;
    }
}
