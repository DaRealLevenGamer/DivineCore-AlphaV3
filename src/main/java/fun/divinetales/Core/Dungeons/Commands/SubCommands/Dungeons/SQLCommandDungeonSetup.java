package fun.divinetales.Core.Dungeons.Commands.SubCommands.Dungeons;

import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Dungeons.Utils.EnumParameter;
import fun.divinetales.Core.Utils.CommandUtils.SubCommand;
import fun.divinetales.Core.Utils.MYSQL.Data.DungeonData.SQLDungeon;
import fun.divinetales.Core.Utils.MYSQL.Data.DungeonData.SQLDungeonSetup;
import org.bukkit.entity.Player;
import static fun.divinetales.Core.Utils.ColorUtil.*;
import java.io.IOException;

public class SQLCommandDungeonSetup extends SubCommand {
    @Override
    public String getName() {
        return "setup";
    }

    @Override
    public String getDescription() {
        return "Sets info for a dungeon!";
    }

    @Override
    public String getSyntax() {
        return "/dungeons setup [ID] [Parameters] [Answer]";
    }

    @Override
    public void perform(Player player, String[] args) throws IOException {

        SQLDungeonSetup setup = new SQLDungeonSetup(CoreMain.getInstance());
        SQLDungeon dungeon = new SQLDungeon(CoreMain.getInstance());

        if (args.length > 1) {

            int D_ID = Integer.parseInt(args[1]);
            String parameter = args[1];
            String answer = args[2];

            if (!CoreMain.getInstance().getSql().isConnected()) {
                msgPlayer(player, "&cException SQL Is not connected!");
                return;
            }

            if (!dungeon.idExists(D_ID) || !setup.idExists(D_ID)) {
                msgPlayer(player, "&cDungeonID Dose not exist, please try again!");
                return;
            }

            switch (EnumParameter.valueOf(parameter.toLowerCase())) {

                case locked:
                    dungeon.setLocked(D_ID, Boolean.getBoolean(answer));
                    msgPlayer(player, "&aDungeon Locked has been changed!");
                    break;

                case playeramount:
                    setup.setPAMount(D_ID, Integer.parseInt(answer));
                    msgPlayer(player, "&aDungeon PlayerAmount has been changed!");
                    break;

                case dungeonlevel:
                    setup.setDLevel(D_ID, Integer.parseInt(answer));
                    msgPlayer(player, "&aDungeon Level has been changed!");
                    break;

                case difficulty:
                    setup.setDDifficulty(D_ID, answer);
                    msgPlayer(player, "&aDungeon Difficulty has been changed!");
                    break;

                case setspawn:
                    setup.setDLoc(D_ID, player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
                    msgPlayer(player, "&aDungeon Location has been changed!");
                    break;

                case worldtype:
                    setup.setDWorldType(D_ID, answer);
                    msgPlayer(player, "&aDungeon WorldType has been changed!");
                    break;

                case pvp:
                    setup.setIS_PVP(D_ID, Boolean.getBoolean(answer));
                    msgPlayer(player, "&aDungeon PVP Rule has been changed!");
                    break;

                case animals:
                    setup.setIS_ANIMAL(D_ID, Boolean.getBoolean(answer));
                    msgPlayer(player, "&aDungeon Animal Rule has been changed!");
                    break;

                case monsters:
                    setup.setIS_MONSTERS(D_ID, Boolean.getBoolean(answer));
                    msgPlayer(player, "&aDungeon Monster Rule has been changed!");
                    break;
            }

        }

    }
}
