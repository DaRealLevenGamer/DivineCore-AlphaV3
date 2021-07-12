package fun.divinetales.Core.Coammnds.SubCommands.ASubcommands;

import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Utils.ChatUtils.MessageUtils;
import fun.divinetales.Core.Utils.CommandUtils.SubCommand;
import fun.divinetales.Core.Utils.MYSQL.Data.RegionData.SQLRegionWasteland;
import org.bukkit.entity.Player;
import static fun.divinetales.Core.Utils.ColorUtil.*;
import java.io.IOException;

public class setAllWasteland extends SubCommand {

    @Override
    public String getName() {
        return "setWastelandAll";
    }

    @Override
    public String getDescription() {
        return "Gets all wastelands and inputs them into the database!";
    }

    @Override
    public String getSyntax() {
        return "/alignments setWastelandAll";
    }

    @Override
    public void perform(Player player, String[] args) throws IOException {

        MessageUtils msgUtil = CoreMain.getInstance().getMsgUtil();
        SQLRegionWasteland wasteland = new SQLRegionWasteland(CoreMain.getInstance());

        if (player.hasPermission("core.alignment.wasteland")) {

            World world = WorldGuard.getInstance().getPlatform().getMatcher().getWorldByName(player.getWorld().getName());

            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionManager regions = container.get(world);

            assert regions != null;
            for (ProtectedRegion region : regions.getRegions().values()) {
                if (region.getId().startsWith("WastelandRegion")) {
                    assert world != null;
                    wasteland.createWastelandPlacement(region.getId(), world.getName(), "TYPE_WASTELAND");
                    msgPlayer(player, color(msgUtil.getCReplaceMessage(MessageUtils.Message.DIVINESTAFF) + " &a&lWasteland regions have been set!"));
                }
            }

        }

    }
}
