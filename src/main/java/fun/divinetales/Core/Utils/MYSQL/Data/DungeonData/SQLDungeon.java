package fun.divinetales.Core.Utils.MYSQL.Data.DungeonData;

import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Utils.MYSQL.ConfigUtils;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class SQLDungeon {

    private final CoreMain plugin;
    private final ConfigUtils utils = CoreMain.getInstance().getConfigUtils();

    public SQLDungeon(CoreMain main) {
        this.plugin = main;
    }

    public void createDungeonTable() {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = CoreMain.getInstance().getSql().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS DUNGEON_DATA " +
                    "(DUNGEON_NAME VARCHAR(100),DUNGEON_ID INT NOT NULL AUTO_INCREMENT,LOCKED BOOLEAN, PRIMARY KEY(DUNGEON_ID))");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (utils.getBoolean("sql_debug")) {
                e.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }
    }

    public void createDungeonPlacement(String dungeon_name, boolean is_locked) {
        try {
            if (!exists(dungeon_name)) {
                PreparedStatement preparedStatement = plugin.getSql().getConnection().prepareStatement("INSERT IGNORE INTO DUNGEON_DATA" +
                        " (DUNGEON_NAME,LOCKED) VALUES (?,?)");
                preparedStatement.setString(1, dungeon_name);
                preparedStatement.setBoolean(1, is_locked);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            if (utils.getBoolean("sql_debug")) {
                throwables.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }
    }

    public boolean exists(String dungeon_name) {
        try {
            PreparedStatement ps = plugin.getSql().getConnection().prepareStatement("SELECT * FROM DUNGEON_DATA WHERE DUNGEON_NAME=?");
            ps.setString(1 , dungeon_name);
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

    public boolean idExists(int dungeon_ID) {
        try {
            PreparedStatement ps = plugin.getSql().getConnection().prepareStatement("SELECT * FROM DUNGEON_DATA WHERE DUNGEON_ID=?");
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

    public void setLocked(int id, boolean locked) {
        try {
            if (idExists(id)) {
                PreparedStatement preparedStatement = plugin.getSql().getConnection().prepareStatement
                        ("UPDATE DUNGEON_DATA SET LOCKED=? WHERE DUNGEON_ID=?");
                preparedStatement.setBoolean(1, locked);
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
