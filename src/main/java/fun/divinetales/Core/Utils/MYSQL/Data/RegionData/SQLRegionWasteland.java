package fun.divinetales.Core.Utils.MYSQL.Data.RegionData;

import fun.divinetales.Core.CoreMain;
import fun.divinetales.Core.Utils.MYSQL.ConfigUtils;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class SQLRegionWasteland {

    private final CoreMain plugin;
    private final ConfigUtils utils = CoreMain.getInstance().getConfigUtils();

    public SQLRegionWasteland(CoreMain main) {
        this.plugin = main;
    }

    public void createWastelandTable() {

        PreparedStatement preparedStatement;
        try {
            preparedStatement = CoreMain.getInstance().getSql().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS REGION_DATA_WASTELAND " +
                    "(WASTELAND_NAME VARCHAR(100),WASTELAND_ID INT NOT NULL AUTO_INCREMENT,WASTELAND_WORLD VARCHAR(100), PRIMARY KEY (WASTELAND_ID))");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (utils.getBoolean("sql_debug")) {
                e.printStackTrace();
            }
            Bukkit.getLogger().log(Level.SEVERE, "SQL has Encountered an error!");
        }
    }

    public void createWastelandPlacement(String name, String world_Name, String alignment) {
        try {
            if (!exists(name)) {
                PreparedStatement preparedStatement = plugin.getSql().getConnection().prepareStatement("INSERT IGNORE INTO REGION_DATA" +
                        " (WASTELAND_NAME,WASTELAND_WORLD) VALUES (?,?)");
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

    public boolean exists(String wasteland_name) {
        try {
            PreparedStatement ps = plugin.getSql().getConnection().prepareStatement("SELECT * FROM REGION_DATA_WASTELAND WHERE WASTELAND_NAME=?");
            ps.setString(1 , wasteland_name);
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
