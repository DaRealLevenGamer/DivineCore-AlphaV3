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

public class SQLChangeSkin {


    private final CoreMain plugin;
    private final ConfigUtils utils = CoreMain.getInstance().getConfigUtils();


    public SQLChangeSkin(CoreMain plugin) {
        this.plugin = plugin;
    }

    public void createSkinTable() {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = CoreMain.getInstance().getSql().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS PLAYER_SKINS " +
                    "(USER_ID INT,PROFILE_NUM INT,IS_SKIN BOOLEAN,SKIN_TEXTURE VARCHAR(1000),SKIN_SIGNATURE VARCHAR(1000),PRIMARY KEY(USER_ID))");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (utils.getBoolean("sql_debug")) {
                e.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }
    }

    public void createPlayerSkin(Player player) {
        try {
            UUID id = player.getUniqueId();
                PreparedStatement preparedStatement = plugin.getSql().getConnection().prepareStatement("INSERT IGNORE INTO PLAYER_SKINS" +
                        " (USER_ID,PROFILE_NUM,IS_SKIN) VALUES (?,?,?)");
                preparedStatement.setInt(1, CoreMain.getInstance().getSqlPlayerData().getId(player.getUniqueId()));
                preparedStatement.setInt(2, 1);
                preparedStatement.setBoolean(3, false);
                preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            if (utils.getBoolean("sql_debug")) {
                throwables.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }
    }

    public boolean is_Skin(UUID uuid) {
        try {
            PreparedStatement ps = plugin.getSql().getConnection().prepareStatement("SELECT IS_SKIN FROM PLAYER_SKINS WHERE USER_ID=?");
            ps.setInt(1 , CoreMain.getInstance().getSqlPlayerData().getId(uuid));
            ResultSet set = ps.executeQuery();
            if (set.next()) {
                return set.getBoolean("IS_SKIN");
            }
        } catch (SQLException throwables) {
            if (utils.getBoolean("sql_debug")) {
                throwables.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }
        return false;
    }

    public int getProfileAmount(UUID uuid) {
        try {
            PreparedStatement ps = plugin.getSql().getConnection().prepareStatement("SELECT * FROM PLAYER_SKINS WHERE USER_ID=?");
            ps.setInt(1 , CoreMain.getInstance().getSqlPlayerData().getId(uuid));
            ResultSet set = ps.executeQuery();
            int profile;
            if (set.next()) {
                profile = set.getInt("PLAYER_SKINS");
                return profile;
            }
        } catch (SQLException throwables) {
            if (utils.getBoolean("sql_debug")) {
                throwables.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }
        return 0;
    }

    public void setSkinInfo(UUID uuid, int profileNum, String texture, String signature) {
        try {
            int playerID = plugin.getSqlPlayerData().getId(uuid);
            PreparedStatement ps = plugin.getSql().getConnection().prepareStatement("UPDATE PLAYER_SKINS SET SKIN_TEXTURE=?,SKIN_SIGNATURE=?,IS_SKIN=? WHERE USER_ID=? AND PROFILE_NUM=?");
            ps.setString(1, texture);
            ps.setString(2, signature);
            ps.setBoolean(3, true);
            ps.setInt(4, CoreMain.getInstance().getSqlPlayerData().getId(uuid));
            ps.setInt(5, profileNum);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            if (utils.getBoolean("sql_debug")) {
                throwables.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }
    }

    public String getSkinTexture(UUID uuid) {
        try {
            PreparedStatement ps = plugin.getSql().getConnection().prepareStatement("SELECT SKIN_TEXTURE FROM PLAYER_SKINS WHERE USER_ID=? AND PROFILE_NUM=?");
            ps.setInt(1 , CoreMain.getInstance().getSqlPlayerData().getId(uuid));
            ps.setInt(2, 1);
            ResultSet set = ps.executeQuery();
            String texture;
            if (set.next()) {
                texture = set.getString("SKIN_TEXTURE");
                return texture;
            }
        } catch (SQLException throwables) {
            if (utils.getBoolean("sql_debug")) {
                throwables.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }
        return null;
    }

    public String getSkinSignature(UUID uuid) {
        try {
            PreparedStatement ps = plugin.getSql().getConnection().prepareStatement("SELECT SKIN_SIGNATURE FROM PLAYER_SKINS WHERE USER_ID=? AND PROFILE_NUM=?");
            ps.setInt(1 , CoreMain.getInstance().getSqlPlayerData().getId(uuid));
            ps.setInt(2, 1);
            ResultSet set = ps.executeQuery();
            String texture;
            if (set.next()) {
                texture = set.getString("SKIN_SIGNATURE");
                return texture;
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
