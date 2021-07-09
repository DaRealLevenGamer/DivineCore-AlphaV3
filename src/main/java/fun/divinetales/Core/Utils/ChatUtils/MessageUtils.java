package fun.divinetales.Core.Utils.ChatUtils;

import fun.divinetales.Core.Utils.Data.Config;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MessageUtils {
    private final Config cfg;

    public MessageUtils(Config cfg) {
        this.cfg = cfg;
    }

    public enum Message implements Messages {

        PREFIX("prefix"),
        DIVINESTAFF("staff_msg"),
        DIVINEPLAYER("divine_player"),
        EXCEPTION("exception_msg"),
        NO_PERMISSION("no_permission"),
        COMMAND_USAGE("command_usage"),
        HELP_FORMAT("help_format"),
        HELP_MESSAGE("help_message"),
        NEW_FORMAT("new_format"),
        GLOBAL_FORMAT("global_format"),
        ROLEPLAY_FORMAT("roleplay_format"),
        LAND_FORMAT("land_format"),
        SPY_FORMAT("spy_format"),
        SPY_ENABLED("spy_enabled"),
        SPY_DISABLED("spy_disabled"),
        ALLY_REMOVE("ally_remove"),
        ALLY_ADD("ally_add"),
        ALLY_NOT_EXIST("ally_not_exist"),
        ALLY_SELF("ally_self"),
        ALLY_HIT("ally_hit"),
        OWN_HIT("own_hit"),
        CHAT_MENU_TITLE("chat_menu_title"),
        CHAT_BUTTON_ENABLED("chat_button_enabled"),
        CHAT_BUTTON_DISABLED("chat_button_disabled"),
        LAND_CHAT("land_chat"),
        GLOBAL_CHAT("global_chat"),
        DEFAULT_CHAT("default_chat"),
        ROLEPLAY_CHAT("roleplay_chat"),
        DEFAULT_CHAT_SET("default_chat_set"),
        LAND_CHAT_NAME("land_chat_name"),
        GLOBAL_CHAT_NAME("global_chat_name"),
        ROLEPLAY_CHAT_NAME("roleplay_chat_name"),
        CHAT_RECIEVE_LAND("chat_recieve_land"),
        CHAT_NORECIEVE_LAND("chat_norecieve_land"),
        CHAT_RECIEVE_GLOBAL("chat_recieve_global"),
        CHAT_NORECIEVE_GLOBAL("chat_norecieve_global"),
        CHAT_RECIEVE_ROLEPLAY("chat_recieve_roleplay"),
        CHAT_NORECIEVE_ROLEPLAY("chat_norecieve_roleplay"),
        CHAT_LAND_DISABLED("chat_land_disabled"),
        CHAT_GLOBAL_DISABLED("chat_global_disabled"),
        CHAT_ROLEPLAY_DISABLED("chat_roleplay_disabled"),
        GLOBAL_CHAT_LORE("global_chat_lore"),
        ROLEPLAY_CHAT_LORE("roleplay_chat_lore"),
        LAND_CHAT_LORE("land_chat_lore"),
        DEFAULT_CHAT_LORE("default_chat_lore"),
        HEAL("heal"),
        ENABLED("enabled"),
        DISABLED("disabled"),
        ADDED("added"),
        REMOVED("removed"),
        NUMBER("number"),
        REP("rep"),
        RESET("reset"),
        PLAYERDUNGEON("divine_dungeon"),
        NOTPLAYED("notplayed");

        private final String path;

        Message(String path) {
            this.path = path;
        }

        public String getPath() {
            return this.path;
        }
    }

    public void sendMessage(CommandSender sender, Messages msg) {
        sendMessage(sender, getMessage(msg), Boolean.TRUE);
    }

    public void sendMessage(CommandSender sender, String msg, Boolean usePrefix) {
        String prefix = getMessage(Message.PREFIX);
        if (msg != null && !msg.equals("")) {
            if (usePrefix && prefix != null && !prefix.equals("")) {
                sender.sendMessage(c(prefix + " " + msg));
                return;
            }
            sender.sendMessage(c(msg));
        }
    }

    public void sendMessage(CommandSender sender, Messages msg, Object... args) {
        sendMessage(sender, getReplaceMessage(msg, args), Boolean.TRUE);
    }

    public void sendNoPrefixMessage(CommandSender sender, Messages msg) {
        sendMessage(sender, getMessage(msg), Boolean.FALSE);
    }

    public void sendNoPrefixMessage(CommandSender sender, Messages msg, Object... args) {
        sendMessage(sender, getReplaceMessage(msg, args), Boolean.FALSE);
    }

    public String getReplaceMessage(Messages msg, Object... args) {
        String message = getMessage(msg);
        for (int i = 0; i < args.length; i = i + 1)
            message = message.replaceAll("\\{" + i + "\\}", args[i].toString());
        return message;
    }

    public String getCReplaceMessage(Messages msg, Object... args) {
        String message = getMessage(msg);
        for (int i = 0; i < args.length; i = i + 1)
            message = message.replaceAll("\\{" + i + "\\}", args[i].toString());
        return c(message);
    }

    public String getCMessage(Messages msg) {
        return c(this.cfg.getConfig().getString(msg.getPath(), ""));
    }

    public String getMessage(Messages msg) {
        return this.cfg.getConfig().getString(msg.getPath(), "");
    }

    public String c(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public boolean getBoolean(String path) {
        return this.cfg.getConfig().getBoolean(path, false);
    }

    public String getColloredMSG(String message, boolean color, boolean format) {
        String newmessage = message;
        if (color)
            newmessage = newmessage.replaceAll("(&([a-f0-9A-F-rR]))", "");
        if (format)
            newmessage = newmessage.replaceAll("(&([k-oK-O]))", "");
        return newmessage;
    }

    public interface Messages {
        String getPath();
    }
}
