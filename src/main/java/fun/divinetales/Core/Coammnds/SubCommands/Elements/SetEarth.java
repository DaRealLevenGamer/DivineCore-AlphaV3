package fun.divinetales.Core.Coammnds.SubCommands.Elements;

import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Utils.CommandUtils.SubCommand;
import fun.divinetales.Core.Utils.Data.Config;
import fun.divinetales.Core.Utils.Data.PlayerData.ProfileCreateEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;



public class SetEarth extends SubCommand {


    @Override
    public String getName() {
        return "setEarth";
    }

    @Override
    public String getDescription() {
        return "Sets a players Element to earth!";
    }

    @Override
    public String getSyntax() {
        return "/playerelement setEarth [Player]";
    }

    @Override
    public void perform(Player opPlayer, String[] args) {
        Player player = Bukkit.getPlayer(args[1]);
        ProfileCreateEvent config = CoreMain.getInstance().getPlayerProfileManager().getPlayer(player.getUniqueId());
        config.setElement("&a&lEarthElement");
        config.save();
    }
}
