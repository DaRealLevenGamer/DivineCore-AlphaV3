package fun.divinetales.Core.Coammnds.SubCommands.Elements;

import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Utils.CommandUtils.SubCommand;
import fun.divinetales.Core.Utils.Data.PlayerData.ProfileCreateEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;



public class SetAir extends SubCommand {

    @Override
    public String getName() {
        return "setAir";
    }

    @Override
    public String getDescription() {
        return "Sets a players Element to air!";
    }

    @Override
    public String getSyntax() {
        return "/playerelement setAir [Player]";
    }

    @Override
    public void perform(Player opPlayer, String[] args) {
        Player player = Bukkit.getPlayer(args[1]);
        ProfileCreateEvent config = CoreMain.getInstance().getPlayerProfileManager().getPlayer(player.getUniqueId());
        config.setElement("&f&lAirElement");
        config.save();
    }

}
