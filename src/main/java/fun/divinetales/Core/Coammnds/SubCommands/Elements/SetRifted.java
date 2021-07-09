package fun.divinetales.Core.Coammnds.SubCommands.Elements;

import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Utils.ChatUtils.MessageUtils;
import fun.divinetales.Core.Utils.CommandUtils.SubCommand;
import fun.divinetales.Core.Utils.Data.PlayerData.ProfileCreateEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class SetRifted extends SubCommand {

    @Override
    public String getName() {
        return "setCursed";
    }

    @Override
    public String getDescription() {
        return "This element is only meant for Mario and his chosen! Do not run if You do not have permission!";
    }

    @Override
    public String getSyntax() {
        return "/playerelement setCursed [Player]";
    }

    @Override
    public void perform(Player opPlayer, String[] args) {
            Player player = Bukkit.getPlayer(args[1]);
            ProfileCreateEvent config = CoreMain.getInstance().getPlayerProfileManager().getPlayer(player.getUniqueId());
            config.setElement("&0&kRifted");
            config.save();
    }

}
