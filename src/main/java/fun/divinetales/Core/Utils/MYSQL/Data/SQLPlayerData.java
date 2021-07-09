package fun.divinetales.Core.Utils.MYSQL.Data;

import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Utils.MYSQL.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;

public class SQLPlayerData {

    private final CoreMain plugin;
    private final ConfigUtils utils = CoreMain.getInstance().getConfigUtils();


    public SQLPlayerData(CoreMain plugin) {
        this.plugin = plugin;
    }

    public void createPlayerTable() {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = CoreMain.getInstance().getSql().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS PLAYER_DATA " +
                    "(NAME VARCHAR(100),ID INT NOT NULL AUTO_INCREMENT,UUID VARCHAR(100),ELEMENT VARCHAR(100),CHANGE_SKIN BOOLEAN,ACTIVE_PROFILE INT,PRIMARY KEY (ID))");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (utils.getBoolean("sql_debug")) {
                e.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }
    }

    public void createPlayer(Player player) {
        try {
            UUID id = player.getUniqueId();
            if (!exists(id)) {
                PreparedStatement preparedStatement = plugin.getSql().getConnection().prepareStatement("INSERT IGNORE INTO PLAYER_DATA" +
                        " (NAME,UUID,CHANGE_SKIN,ACTIVE_PROFILE) VALUES (?,?,?,?)");
                preparedStatement.setString(1, player.getName());
                preparedStatement.setString(2, id.toString());
                preparedStatement.setBoolean(3, false);
                preparedStatement.setInt(4, 1);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            if (utils.getBoolean("sql_debug")) {
                throwables.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }
    }

    public boolean exists(UUID uuid) {
        try {
            PreparedStatement ps = plugin.getSql().getConnection().prepareStatement("SELECT * FROM PLAYER_DATA WHERE UUID=?");
            ps.setString(1 , uuid.toString());
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

    public int getId(UUID uuid) {
        try {
            PreparedStatement ps = plugin.getSql().getConnection().prepareStatement("SELECT ID FROM PLAYER_DATA WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            int profileNum;
            if (rs.next()) {
                profileNum = rs.getInt("ID");
                return profileNum;
            }
        } catch (SQLException throwables) {
            if (utils.getBoolean("sql_debug")) {
                throwables.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }
        return 0;
    }

    public void setElement(UUID uuid, String element) {
        try {

            int playerID = getId(uuid);
            PreparedStatement ps = plugin.getSql().getConnection().prepareStatement("UPDATE PLAYER_DATA SET ELEMENT=? WHERE ID=?");
            ps.setString(1, element);
            ps.setInt(2, playerID);
            ps.executeUpdate();
        } catch (SQLException e) {
            if (utils.getBoolean("sql_debug")) {
                e.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }

    }

    public String getElement(UUID uuid) {
        try {
            int playerID = plugin.getSqlPlayerData().getId(uuid);
            PreparedStatement ps = plugin.getSql().getConnection().prepareStatement("SELECT ELEMENT FROM PLAYER_DATA WHERE ID=?");
            ps.setInt(1, playerID);
            ResultSet rs = ps.executeQuery();
            String element;
            if (rs.next()) {
                element = rs.getString("ELEMENT");
                return element;
            }
        } catch (SQLException throwables) {
            if (utils.getBoolean("sql_debug")) {
                throwables.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }
        return null;
    }

}
