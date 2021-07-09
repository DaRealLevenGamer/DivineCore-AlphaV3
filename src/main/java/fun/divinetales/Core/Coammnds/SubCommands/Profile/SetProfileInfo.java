package fun.divinetales.Core.Coammnds.SubCommands.Profile;

import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Utils.CommandUtils.SubCommand;
import fun.divinetales.Core.Utils.MYSQL.Data.SQLPlayerProfile;
import org.bukkit.entity.Player;
import static fun.divinetales.Core.Utils.ColorUtil.*;

import java.io.IOException;

public class SetProfileInfo extends SubCommand {
    @Override
    public String getName() {
        return "setInfo";
    }

    @Override
    public String getDescription() {
        return "sets the information for a profile!";
    }

    @Override
    public String getSyntax() {
        return "/profile setInfo [profile] [RPName] [Age] [Race]";
    }

    @Override
    public void perform(Player player, String[] args) throws IOException {

        SQLPlayerProfile profile = CoreMain.getInstance().getSqlPlayerProfile();

        if (args.length < 4) {
            msgPlayer(player, color("&c&lInvalid arguments, pls input " + getSyntax()));
            return;
        }

        int profileNum = Integer.parseInt(args[1]);
        String rpName = args[2];


        int age = Integer.parseInt(args[3]);
        String Race = args[4];

        if (profile.doseProfileExists(player.getUniqueId(), profileNum)) {
            profile.setProfileInfo(player.getUniqueId(), profileNum, rpName, age, null, Race);
            msgPlayer(player, "&a&lProfile info has been set!");
        } else {
            msgPlayer(player, "&c&lThat is not a valid profile number!");
        }

    }


}
