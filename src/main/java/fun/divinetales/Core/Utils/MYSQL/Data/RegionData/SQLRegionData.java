package fun.divinetales.Core.Utils.MYSQL.Data.RegionData;

import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Utils.MYSQL.ConfigUtils;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class SQLRegionData {

    private final CoreMain plugin;
    private final ConfigUtils utils = CoreMain.getInstance().getConfigUtils();

    public SQLRegionData(CoreMain main) {
        this.plugin = main;
    }

    public void createRegionTable() {

        PreparedStatement preparedStatement;
        try {
            preparedStatement = CoreMain.getInstance().getSql().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS REGION_DATA " +
                    "(REGION_NAME VARCHAR(100),REGION_ID INT NOT NULL AUTO_INCREMENT,REGION_WORLD VARCHAR(100),REGION_ALIGNMENT VARCHAR(100), PRIMARY KEY (ID))");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (utils.getBoolean("sql_debug")) {
                e.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }
    }

    public void createRegionPlacement(String name, String world_Name, String alignment) {
        try {
            if (!exists(name)) {
                PreparedStatement preparedStatement = plugin.getSql().getConnection().prepareStatement("INSERT IGNORE INTO REGION_DATA" +
                        " (REGION_NAME,REGION_WORLD,REGION_ALIGNMENT) VALUES (?,?,?)");
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, world_Name);
                preparedStatement.setString(3, alignment);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            if (utils.getBoolean("sql_debug")) {
                throwables.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }
    }

    public boolean exists(String region_name) {
        try {
            PreparedStatement ps = plugin.getSql().getConnection().prepareStatement("SELECT * FROM REGION_DATA WHERE REGION_NAME=?");
            ps.setString(1 , region_name);
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

}
