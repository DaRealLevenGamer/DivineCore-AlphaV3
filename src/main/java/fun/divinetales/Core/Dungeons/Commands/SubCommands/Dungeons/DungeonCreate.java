package fun.divinetales.Core.Dungeons.Commands.SubCommands.Dungeons;

import fun.divinetales.Core.Dungeons.Utils.DungeonSetup.DungeonData;
import fun.divinetales.Core.Dungeons.Utils.DungeonSetup.DungeonDataManager;
import fun.divinetales.Core.Utils.CommandUtils.SubCommand;
import org.bukkit.entity.Player;

import java.util.Objects;

import static fun.divinetales.Core.Utils.ColorUtil.*;

public class DungeonCreate extends SubCommand {

    private DungeonDataManager manager;
    public DungeonCreate(DungeonDataManager manager) {
        this.manager = manager;
    }

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "Creates the files for a new Dungeon!";
    }

    @Override
    public String getSyntax() {
        return "/dungeons create";
    }

    @Override
    public void perform(Player player, String[] args) {

        if (args.length > 1) {


            if (!manager.isRegistered(args[1])) {
                manager.registerDungeon(args[1]);
                msgPlayer(player, color("&f&lDungeon: " + args[1] + " &f&lHas been created!"));
            } else {
                msgPlayer(player, color("That dungeon already exists!"));
            }


        }

    }
}
