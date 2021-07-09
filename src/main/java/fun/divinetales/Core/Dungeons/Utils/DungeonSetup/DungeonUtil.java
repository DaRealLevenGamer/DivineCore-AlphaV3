package fun.divinetales.Core.Dungeons.Utils.DungeonSetup;


import com.grinderwolf.swm.api.SlimePlugin;
import com.grinderwolf.swm.api.exceptions.*;
import com.grinderwolf.swm.api.loaders.SlimeLoader;
import com.grinderwolf.swm.api.world.SlimeWorld;
import com.grinderwolf.swm.api.world.properties.SlimeProperties;
import com.grinderwolf.swm.api.world.properties.SlimePropertyMap;
import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Dungeons.Utils.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import static fun.divinetales.Core.Utils.ColorUtil.*;

import java.io.IOException;

public class DungeonUtil {

    SlimePlugin swmLoader = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");
    private final DungeonDataManager manager = new DungeonDataManager();

    public void loadWorld(String dungeonName, Player player) {

        DungeonData dManager = manager.getDungeon(dungeonName);
        SlimeLoader loader = swmLoader.getLoader("file");
        TeamManager manager = new TeamManager();

        try {

            SlimePropertyMap dungeonProperties = new SlimePropertyMap();
            dungeonProperties.setValue(SlimeProperties.DIFFICULTY, dManager.getDifficulty());
            dungeonProperties.setValue(SlimeProperties.SPAWN_X, dManager.getSpawn_X());
            dungeonProperties.setValue(SlimeProperties.SPAWN_Y, dManager.getSpawn_Y());
            dungeonProperties.setValue(SlimeProperties.SPAWN_Z, dManager.getSpawn_Z());
            dungeonProperties.setValue(SlimeProperties.PVP, dManager.isPvp());
            dungeonProperties.setValue(SlimeProperties.ALLOW_MONSTERS, dManager.isMonsters());
            dungeonProperties.setValue(SlimeProperties.ALLOW_ANIMALS, dManager.isAnimals());
            dungeonProperties.setValue(SlimeProperties.WORLD_TYPE, dManager.getWorldType());

            Bukkit.getScheduler().runTaskAsynchronously(CoreMain.getInstance(), () -> {
                try {
                    SlimeWorld world = swmLoader.loadWorld(loader, dungeonName, false, dungeonProperties);

                    Bukkit.getScheduler().runTask(CoreMain.getInstance(), () -> {
                        try {
                            world.clone(manager.getTeam(player), loader, false);
                        } catch (WorldAlreadyExistsException | IOException e) {
                            e.printStackTrace();
                        }
                    });

                } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException | WorldInUseException e) {
                    msgPlayer(player, color("&c&lDungeon has failed to load cancelling process!"));
                }
            });

        } catch (Exception e) {
            msgPlayer(player, color("&c&lDungeon failed to load!"));
            e.printStackTrace();
        }


    }

}
