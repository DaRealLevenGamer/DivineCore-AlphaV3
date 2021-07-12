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
import java.util.ArrayList;
import java.util.List;

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

        List<String> added_wasteland_regions = new ArrayList<>();

        MessageUtils msgUtil = CoreMain.getInstance().getMsgUtil();
        SQLRegionWasteland wasteland = new SQLRegionWasteland(CoreMain.getInstance());

        if (player.hasPermission("core.alignment.wasteland")) {

            World world = WorldGuard.getInstance().getPlatform().getMatcher().getWorldByName(player.getWorld().getName());

            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionManager regions = container.get(world);

            assert regions != null;
            for (ProtectedRegion region : regions.getRegions().values()) {
                if (region.getId().contains("Wasteland".toLowerCase())) {
                    assert world != null;
                    if (!wasteland.exists(region.getId())) {

                        wasteland.createWastelandPlacement(region.getId(), world.getName());
                        added_wasteland_regions.add(region.getId());

                    }
                }
            }

            if (added_wasteland_regions.size() == 0) {
                msgPlayer(player, msgUtil.getMessage(MessageUtils.Message.DIVINESTAFF) + color(" &c&lNo wasteland regions have been added!"));
            } else {
                msgPlayer(player, msgUtil.getMessage(MessageUtils.Message.DIVINESTAFF) + color(" &a&l" + added_wasteland_regions.size()
                + " &a&lWasteland Regions have been added!"));
            }

        }

    }
}
