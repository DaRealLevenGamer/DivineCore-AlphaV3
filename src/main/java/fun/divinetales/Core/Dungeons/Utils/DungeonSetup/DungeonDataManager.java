package fun.divinetales.Core.Dungeons.Utils.DungeonSetup;


import fun.divinetales.Core.Utils.Data.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DungeonDataManager {

    private final Map<String, DungeonData> dungeons = new HashMap<>();
    private List<String> register = new ArrayList<>();
    private Config newDungeon = new Config("DungeonData", "Registered", false);

    public DungeonData getDungeon(String name) {
        return (this.dungeons.get(name) == null) ? new DungeonData(name) : this.dungeons.get(name);
    }

    public void registerDungeon(String name) {
        this.dungeons.put(name, getDungeon(name));
        register.add(name);
        saveDungeon();
        getDungeon(name).load();
    }

    public void unregisterDungeon(String name) {
        getDungeon(name).save();
        this.dungeons.remove(name);
    }

    public void saveDungeon() {
        for (String name : register) {
            newDungeon.getConfig().set("Dungeons." + ".Name: " + name, true);
            newDungeon.save();
        }
    }

    public Boolean isRegistered(String aname) {
        return newDungeon.getConfig().getBoolean("Dungeons." + ".Name: " + aname);
    }

}
