package fun.divinetales.Core.Coammnds.SubCommands.CosmicSubCommands;

import fun.divinetales.Core.Utils.CommandUtils.SubCommand;
import fun.divinetales.Core.Utils.CosmicUtils.CurseDefine;
import fun.divinetales.Core.Utils.CosmicUtils.CurseUtil;
import org.bukkit.entity.Player;

public class CurseKills extends SubCommand {
    @Override
    public String getName() {
        return "kills";
    }

    @Override
    public String getDescription() {
        return "Disables the kill function of the rep function!";
    }

    @Override
    public String getSyntax() {
        return "/curse kills";
    }

    @Override
    public void perform(Player sender, String[] args) {

        if (CurseDefine.klls) {
            CurseDefine.klls = false;
            sender.sendMessage(CurseUtil.color(CurseDefine.disabled));
        } else {
            CurseDefine.klls = true;
            sender.sendMessage(CurseUtil.color(CurseDefine.enabled));
        }

    }
}
