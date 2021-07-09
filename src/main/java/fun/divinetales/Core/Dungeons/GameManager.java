package fun.divinetales.Core.Dungeons;

import com.grinderwolf.swm.api.SlimePlugin;
import fun.divinetales.Core.CoreMain;
import org.bukkit.Bukkit;

public class GameManager {

    private final CoreMain plugin;
    private SlimePlugin swmPlugin = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");
    public GameState gameState = GameState.LOBBY;

    public GameManager(CoreMain plugin) {
        this.plugin = plugin;
    }

    public void setGameState(GameState gameState) {

        this.gameState = gameState;

        switch (gameState) {



        }

    }

    public void cleanUp() {

    }

}
