package fun.divinetales.Core.Coammnds.SubCommands.ASubcommands;

import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import fun.divinetales.Core.Alignments.Utils.ATypes;
import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Utils.ChatUtils.MessageUtils;
import fun.divinetales.Core.Utils.CommandUtils.SubCommand;
import fun.divinetales.Core.Utils.MYSQL.Data.RegionData.SQLRegionData;
import fun.divinetales.Core.Utils.MYSQL.Data.RegionData.SQLRegionWasteland;
import org.bukkit.entity.Player;
import static fun.divinetales.Core.Utils.ColorUtil.*;
import java.io.IOException;

public class SetRegionA extends SubCommand {
    @Override
    public String getName() {
        return "setRegion";
    }

    @Override
    public String getDescription() {
        return "Sets a regions alignment!";
    }

    @Override
    public String getSyntax() {
        return "/alignment setRegion [RegionID] [Alignment]";
    }

    @Override
    public void perform(Player player, String[] args) throws IOException {

        MessageUtils msgUtil = CoreMain.getInstance().getMsgUtil();
        SQLRegionData data = new SQLRegionData(CoreMain.getInstance());
        SQLRegionWasteland wasteland = new SQLRegionWasteland(CoreMain.getInstance());

        if (args.length < 3) {
            msgPlayer(player, msgUtil.getCReplaceMessage(MessageUtils.Message.DIVINESTAFF) + color(" &c&lInvalid syntax Exception: " + getSyntax()));
            return;
        }

        String region_name = args[1];
        String alignment_type = args[2];

        World world = WorldGuard.getInstance().getPlatform().getMatcher().getWorldByName(player.getWorld().getName());

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(world);

        assert regions != null;
        if (regions.hasRegion(region_name)) {

           if (!data.exists(region_name)) {

               switch (ATypes.valueOf(alignment_type)) {

                   case lawful:
                       data.createRegionPlacement(region_name, player.getWorld().getName(), "TYPE_LAWFUL");
                       msgPlayer(player, msgUtil.getCReplaceMessage(MessageUtils.Message.DIVINESTAFF) + color(" &9&lRegion " + region_name + " &9&lhas been set to &f&lLawful"));
                        break;

                   case neutral:
                       data.createRegionPlacement(region_name, player.getWorld().getName(), "TYPE_NEUTRAL");
                       msgPlayer(player, msgUtil.getCReplaceMessage(MessageUtils.Message.DIVINESTAFF) + color(" &9&lRegion " + region_name + " &9&lhas been set to &6&lNeutral"));
                       break;

                   case chaotic:
                       data.createRegionPlacement(region_name, player.getWorld().getName(), "TYPE_CHAOTIC");
                       msgPlayer(player, msgUtil.getCReplaceMessage(MessageUtils.Message.DIVINESTAFF) + color(" &9&lRegion " + region_name + " &9&lhas been set to &4&lChaotic"));
                       break;

                   case wasteland:
                       wasteland.createWastelandPlacement(region_name, player.getWorld().getName());
                       msgPlayer(player, msgUtil.getCReplaceMessage(MessageUtils.Message.DIVINESTAFF) + color(" &9&lRegion " + region_name + " &9&lhas been set to &c&lWasteland"));
                       break;
               }

           }

        }


    }
}
