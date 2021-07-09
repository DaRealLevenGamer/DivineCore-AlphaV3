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

public class SQLPlayerProfile {

    private final CoreMain plugin;
    private final ConfigUtils utils = CoreMain.getInstance().getConfigUtils();

    public  SQLPlayerProfile (CoreMain main) {
        this.plugin = main;
    }

    public void createProfileTable() {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = CoreMain.getInstance().getSql().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS PLAYER_PROFILE  " +
                    "(USER_ID INT,PROFILE_NUM INT,RP_NAME VARCHAR(100),RP_AGE INT,ELEMENT VARCHAR(100),KINGDOM VARCHAR(100),RACE VARCHAR(100),PRIMARY KEY(USER_ID))");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (utils.getBoolean("sql_debug")) {
                e.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }
    }

    public int getProfileAmount(UUID uuid) {
        try {
            int playerID = plugin.getSqlPlayerData().getId(uuid);
            PreparedStatement ps = plugin.getSql().getConnection().prepareStatement("SELECT COUNT(USER_ID) FROM PLAYER_PROFILE WHERE USER_ID=?");
            ps.setInt(1, playerID);
            ResultSet rs = ps.executeQuery();
            int profileNum;
            if (rs.next()) {
                profileNum = rs.getInt("USER_ID");
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

    public int getProfileNumNN(UUID uuid) {
        if (getProfileAmount(uuid) != 0) {
            return getProfileAmount(uuid) + 1;
        } else {
            return 1;
        }
    }


    public void createProfile(Player player) {
        try {
            UUID id = player.getUniqueId();
                int playerID = plugin.getSqlPlayerData().getId(id);
                PreparedStatement preparedStatement = plugin.getSql().getConnection().prepareStatement("INSERT IGNORE INTO PLAYER_PROFILE" +
                        " (USER_ID,PROFILE_NUM) VALUES (?,?)");
                preparedStatement.setInt(1, playerID);
                preparedStatement.setInt(2, 1);
                preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            if (utils.getBoolean("sql_debug")) {
                throwables.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }
    }

    public void createAnotherProfile(Player player) {
        try {
            UUID id = player.getUniqueId();
            int playerID = plugin.getSqlPlayerData().getId(id);
            PreparedStatement preparedStatement = plugin.getSql().getConnection().prepareStatement("INSERT IGNORE INTO PLAYER_PROFILE" +
                    " (USER_ID,PROFILE_NUM) VALUES (?,?)");
            preparedStatement.setInt(1, playerID);
            preparedStatement.setInt(2, getProfileNumNN(id));
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            if (utils.getBoolean("sql_debug")) {
                throwables.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }
    }

    public void setProfileInfo(UUID uuid, int profileNum, String name, int age, String kingdom, String race) {
        try {
            int playerID = plugin.getSqlPlayerData().getId(uuid);
            PreparedStatement ps = plugin.getSql().getConnection().prepareStatement("UPDATE PLAYER_PROFILE SET RP_NAME=?,RP_AGE=?,KINGDOM=?,RACE=? WHERE USER_ID=? AND PROFILE_NUM=?");
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, kingdom);
            ps.setString(4, race);
            ps.setInt(5, playerID);
            ps.setInt(6, profileNum);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            if (utils.getBoolean("sql_debug")) {
                throwables.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }
    }

    public boolean doseProfileExists(UUID uuid, int i) {
        try {

            PreparedStatement ps = plugin.getSql().getConnection().prepareStatement("SELECT PROFILE_NUM FROM PLAYER_PROFILE WHERE EXISTS (SELECT PROFILE_NUM FROM PLAYER_PROFILE WHERE PROFILE_NUM=? AND USER_ID=?)");
            ps.setInt(1, i);
            ps.setInt(2, plugin.getSqlPlayerData().getId(uuid));
            ResultSet set = ps.executeQuery();
            if (set.next()) {
                return set.getBoolean("PROFILE_NUM");
            }

        } catch(SQLException throwables) {
            if (utils.getBoolean("sql_debug")) {
                throwables.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }
        return false;
    }

}
