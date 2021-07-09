package fun.divinetales.Core.Coammnds.SubCommands.CosmicSubCommands;

import fun.divinetales.Core.Utils.CommandUtils.SubCommand;
import fun.divinetales.Core.Utils.CosmicUtils.CurseDefine;
import fun.divinetales.Core.Utils.CosmicUtils.CurseUtil;
import org.bukkit.entity.Player;

public class CurseSet extends SubCommand {
    @Override
    public String getName() {
        return "set";
    }

    @Override
    public String getDescription() {
        return "Disables the rep function!";
    }

    @Override
    public String getSyntax() {
        return "/curse set";
    }

    @Override
    public void perform(Player sender, String[] args) {

        if (CurseDefine.on) {
            CurseDefine.on = false;
            sender.sendMessage(CurseUtil.color(CurseDefine.disabled));
        } else {
            CurseDefine.on = true;
            sender.sendMessage(CurseUtil.color(CurseDefine.enabled));
        }

    }
}
