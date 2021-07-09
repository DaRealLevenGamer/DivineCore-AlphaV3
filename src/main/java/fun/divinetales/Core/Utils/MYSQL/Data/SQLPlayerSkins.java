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

public class SQLPlayerSkins {

    private final CoreMain plugin;
    private final ConfigUtils utils = CoreMain.getInstance().getConfigUtils();

    public SQLPlayerSkins(CoreMain main) {
        this.plugin = main;
    }

    public void createSkinTable() {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = CoreMain.getInstance().getSql().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS PROFILE_SKIN  " +
                    "(USER_ID INT,PROFILE_NUM INT,SKIN_SIGNATURE VARCHAR(1000),SKIN_TEXTURE VARCHAR(1000))");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (utils.getBoolean("sql_debug")) {
                e.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }
    }

    public void createSkinProfile(Player player) {
        try {
            UUID id = player.getUniqueId();

            for (int i = 0; CoreMain.getInstance().getSqlPlayerProfile().getProfileAmount(id) < i; i++) {
                PreparedStatement preparedStatement = plugin.getSql().getConnection().prepareStatement("INSERT IGNORE INTO PROFILE_SKIN" +
                        " (USER_ID,PROFILE_NUM) VALUES (?,?)");
                preparedStatement.setInt(1, plugin.getSqlPlayerData().getId(player.getUniqueId()));
                preparedStatement.setInt(2, i);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String getSkinTexture(UUID uuid, int profileNum) {
        try {
            int playerID = plugin.getSqlPlayerData().getId(uuid);
            PreparedStatement ps = plugin.getSql().getConnection().prepareStatement("SELECT SKIN_TEXTURE FROM PROFILE_SKIN WHERE USER_ID=?,PROFILE_NUM=?");
            ps.setInt(1, playerID);
            ps.setInt(2, profileNum);
            ResultSet rs = ps.executeQuery();
            String texture;
            if (rs.next()) {
                texture = rs.getString("SKIN_TEXTURE");
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

    public String getSkinSignature(UUID uuid, int profileNum) {
        try {
            int playerID = plugin.getSqlPlayerData().getId(uuid);
            PreparedStatement ps = plugin.getSql().getConnection().prepareStatement("SELECT SKIN_SIGNATURE FROM PROFILE_SKIN WHERE USER_ID=?,PROFILE_NUM=?");
            ps.setInt(1, playerID);
            ps.setInt(2, profileNum);
            ResultSet rs = ps.executeQuery();
            String signature;
            if (rs.next()) {
                signature = rs.getString("SKIN_SIGNATURE");
                return signature;
            }
        } catch (SQLException throwables) {
            if (utils.getBoolean("sql_debug")) {
                throwables.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }
        return null;
    }

    public void setSkin(UUID uuid, String texture, String signature, int profileNum) {
        try {
            int playerID = plugin.getSqlPlayerData().getId(uuid);
            PreparedStatement ps = plugin.getSql().getConnection().prepareStatement("UPDATE PROFILE_SKIN SET SKIN_SIGNATURE=?,SKIN_TEXTURE=? WHERE ID=?,PROFILE_NUM=?");
            ps.setString(1, signature);
            ps.setString(2, texture);
            ps.setInt(4, playerID);
            ps.setInt(5, profileNum);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            if (utils.getBoolean("sql_debug")) {
                throwables.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }
    }

}
