package fun.divinetales.Core.Dungeons.Utils.DungeonSetup;

import fun.divinetales.Core.Utils.Data.Config;

public class DungeonData {


    private static Config DungeonConfigData;

    public DungeonData(String name) {
        DungeonConfigData = new Config("DungeonData", name, false);
    }

    private String DungeonName;
    private int DungeonID;
    private boolean Locked;
    private int PlayerAmount;
    private int DungeonLevel;
    private String worldType;
    private int Spawn_X;
    private int Spawn_Y;
    private int Spawn_Z;
    private String Difficulty;
    private boolean Pvp;
    private boolean Monsters;
    private boolean Animals;


    public void load() {

        this.DungeonName = DungeonConfigData.getConfig().getString("DungeonName", "ND");
        this.DungeonID = DungeonConfigData.getConfig().getInt("DungeonName", 0);
        this.Locked = DungeonConfigData.getConfig().getBoolean("DungeonName", false);
        this.PlayerAmount = DungeonConfigData.getConfig().getInt("DungeonName", 2);
        this.DungeonLevel = DungeonConfigData.getConfig().getInt("DungeonName", 0);

    }

    public void save() {
        DungeonConfigData.set("DungeonName", this.DungeonName);
        DungeonConfigData.set("DungeonID", this.DungeonID);
        DungeonConfigData.set("Locked", this.Locked);
        DungeonConfigData.set("PlayerAmount", this.PlayerAmount);
        DungeonConfigData.set("DungeonLevel", this.DungeonLevel);
        DungeonConfigData.save();
    }

    public static Config getDungeonConfigData() {
        return DungeonConfigData;
    }

    public String getDungeonName() {
        return DungeonName;
    }

    public void setDungeonName(String dungeonName) {
        DungeonName = dungeonName;
    }

    public int getDungeonID() {
        return DungeonID;
    }

    public void setDungeonID(int dungeonID) {
        DungeonID = dungeonID;
    }

    public boolean isLocked() {
        return Locked;
    }

    public void setLocked(boolean locked) {
        Locked = locked;
    }

    public int getPlayerAmount() {
        return PlayerAmount;
    }

    public void setPlayerAmount(int playerAmount) {
        PlayerAmount = playerAmount;
    }

    public int getDungeonLevel() {
        return DungeonLevel;
    }

    public void setDungeonLevel(int dungeonLevel) {
        DungeonLevel = dungeonLevel;
    }

    public String getWorldType() {
        return worldType;
    }

    public void setWorldType(String worldType) {
        this.worldType = worldType;
    }

    public int getSpawn_X() {
        return Spawn_X;
    }

    public void setSpawn_X(int spawn_X) {
        Spawn_X = spawn_X;
    }

    public int getSpawn_Y() {
        return Spawn_Y;
    }

    public void setSpawn_Y(int spawn_Y) {
        Spawn_Y = spawn_Y;
    }

    public int getSpawn_Z() {
        return Spawn_Z;
    }

    public void setSpawn_Z(int spawn_Z) {
        Spawn_Z = spawn_Z;
    }

    public String getDifficulty() {
        return Difficulty;
    }

    public void setDifficulty(String difficulty) {
        Difficulty = difficulty;
    }

    public boolean isPvp() {
        return Pvp;
    }

    public void setPvp(boolean pvp) {
        Pvp = pvp;
    }

    public boolean isMonsters() {
        return Monsters;
    }

    public void setMonsters(boolean monsters) {
        Monsters = monsters;
    }

    public boolean isAnimals() {
        return Animals;
    }

    public void setAnimals(boolean animals) {
        Animals = animals;
    }
}
