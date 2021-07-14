package fun.divinetales.Core.Utils.MYSQL.Data.DungeonData;

import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Utils.MYSQL.ConfigUtils;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class SQLDungeonSetup {

    private final CoreMain plugin;
    private final ConfigUtils utils = CoreMain.getInstance().getConfigUtils();

    public SQLDungeonSetup(CoreMain main) {
        this.plugin = main;
    }

    public void createDungeonSetupTable() {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = CoreMain.getInstance().getSql().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS DUNGEON_SETTINGS " +
                    "(DUNGEON_ID INT, PLAYER_AMOUNT INT(100), DUNGEON_LEVEL INT, WORLD_TYPE VARCHAR(100), LOC_X INT, LOC_Y INT, LOC_Z INT, " +
                    "DIFFICULTY VARCHAR(100), PVP BOOLEAN, MONSTERS BOOLEAN, ANIMAL BOOLEAN, PRIMARY KEY(DUNGEON_ID))");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (utils.getBoolean("sql_debug")) {
                e.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }
    }

    public void createSetupPlacement(int dungeon_id) {
        try {
            if (!idExists(dungeon_id)) {
                PreparedStatement preparedStatement = plugin.getSql().getConnection().prepareStatement("INSERT IGNORE INTO DUNGEON_SETTINGS" +
                        " (DUNGEON_ID) VALUES (?)");
                preparedStatement.setInt(1, dungeon_id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            if (utils.getBoolean("sql_debug")) {
                throwables.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }
    }

    public boolean idExists(int dungeon_ID) {
        try {
            PreparedStatement ps = plugin.getSql().getConnection().prepareStatement("SELECT * FROM DUNGEON_SETTINGS WHERE DUNGEON_ID=?");
            ps.setInt(1, dungeon_ID);
            ResultSet set = ps.executeQuery();
            return set.next();
        } catch (SQLException throwables) {
            if (utils.getBoolean("sql_debug")) {
                throwables.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }
        return false;
    }

    public void setPAMount(int id, int amount) {
        try {
            if (idExists(id)) {
                PreparedStatement preparedStatement = plugin.getSql().getConnection().prepareStatement
                        ("UPDATE DUNGEON_SETTINGS SET PLAYER_AMOUNT=? WHERE DUNGEON_ID=?");
                preparedStatement.setInt(1, amount);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            if (utils.getBoolean("sql_debug")) {
                throwables.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }
    }

    public void setDLevel(int id, int level) {
        try {
            if (idExists(id)) {
                PreparedStatement preparedStatement = plugin.getSql().getConnection().prepareStatement
                        ("UPDATE DUNGEON_SETTINGS SET DUNGEON_LEVEL=? WHERE DUNGEON_ID=?");
                preparedStatement.setInt(1, level);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            if (utils.getBoolean("sql_debug")) {
                throwables.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }
    }

    public void setDWorldType(int id, String world_type) {
        try {
            if (idExists(id)) {
                PreparedStatement preparedStatement = plugin.getSql().getConnection().prepareStatement
                        ("UPDATE DUNGEON_SETTINGS SET WORLD_TYPE=? WHERE DUNGEON_ID=?");
                preparedStatement.setString(1, world_type);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            if (utils.getBoolean("sql_debug")) {
                throwables.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }
    }

    public void setDLoc(int id, int X, int Y, int Z) {
        try {
            if (idExists(id)) {
                PreparedStatement preparedStatement = plugin.getSql().getConnection().prepareStatement
                        ("UPDATE DUNGEON_SETTINGS SET LOC_Y=?,LOC_X=?,LOC_Z=? WHERE DUNGEON_ID=?");
                preparedStatement.setInt(1, X);
                preparedStatement.setInt(2, Y);
                preparedStatement.setInt(3, Z);
                preparedStatement.setInt(4, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            if (utils.getBoolean("sql_debug")) {
                throwables.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }
    }

    public void setDDifficulty(int id, String difficulty) {
        try {
            if (idExists(id)) {
                PreparedStatement preparedStatement = plugin.getSql().getConnection().prepareStatement
                        ("UPDATE DUNGEON_SETTINGS SET DIFFICULTY=? WHERE DUNGEON_ID=?");
                preparedStatement.setString(1, difficulty);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            if (utils.getBoolean("sql_debug")) {
                throwables.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }
    }

    public void setIS_PVP(int id, boolean pvp) {
        try {
            if (idExists(id)) {
                PreparedStatement preparedStatement = plugin.getSql().getConnection().prepareStatement
                        ("UPDATE DUNGEON_SETTINGS SET PVP=? WHERE DUNGEON_ID=?");
                preparedStatement.setBoolean(1, pvp);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            if (utils.getBoolean("sql_debug")) {
                throwables.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }
    }

    public void setIS_MONSTERS(int id, boolean monsters) {
        try {
            if (idExists(id)) {
                PreparedStatement preparedStatement = plugin.getSql().getConnection().prepareStatement
                        ("UPDATE DUNGEON_SETTINGS SET MONSTERS=? WHERE DUNGEON_ID=?");
                preparedStatement.setBoolean(1, monsters);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            if (utils.getBoolean("sql_debug")) {
                throwables.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }
    }

    public void setIS_ANIMAL(int id, boolean animals) {
        try {
            if (idExists(id)) {
                PreparedStatement preparedStatement = plugin.getSql().getConnection().prepareStatement
                        ("UPDATE DUNGEON_SETTINGS SET ANIMAL=? WHERE DUNGEON_ID=?");
                preparedStatement.setBoolean(1, animals);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            if (utils.getBoolean("sql_debug")) {
                throwables.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }
    }

}
