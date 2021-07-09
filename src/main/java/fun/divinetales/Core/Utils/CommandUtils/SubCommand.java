package fun.divinetales.Core.Utils.CommandUtils;

import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.List;

public abstract class SubCommand {

    public abstract String getName();

    public abstract String getDescription();

    public abstract String getSyntax();

    public abstract void perform(Player player, String[] args) throws IOException;


}
