package fun.divinetales.Core.Utils.MYSQL;
import fun.divinetales.Core.Utils.Data.Config;
import org.bukkit.ChatColor;

public class ConfigUtils {
    private final Config cfg;

    public ConfigUtils(Config cfg) {
        this.cfg = cfg;
    }

    public enum getConfig implements Messages {

        MYSQLUser("sql_user"),
        MYSQLPass("sql_pass"),
        MYSQLHost("sql_host"),
        MYSQLPort("sql_port"),
        MYSQLDataBase("sql_db");
        private final String path;

        getConfig(String path) {
            this.path = path;
        }

        public String getPath() {
            return this.path;
        }
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
