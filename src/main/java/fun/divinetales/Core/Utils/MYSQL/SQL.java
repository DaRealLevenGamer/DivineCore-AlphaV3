package fun.divinetales.Core.Utils.MYSQL;

import fun.divinetales.Core.CoreMain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQL {

    private final ConfigUtils utils = CoreMain.getInstance().getConfigUtils();

    private final String host;

    {
        assert false;
        host = utils.getMessage(ConfigUtils.getConfig.MYSQLHost);
    }

    private final String port = utils.getMessage(ConfigUtils.getConfig.MYSQLPort);
    private final String database = utils.getMessage(ConfigUtils.getConfig.MYSQLDataBase);
    private final String user = utils.getMessage(ConfigUtils.getConfig.MYSQLUser);
    private final String pass = utils.getMessage(ConfigUtils.getConfig.MYSQLPass);

    private Connection connection;

    public boolean isConnected() {
        return (connection != null);
    }

    public void Connect() throws ClassNotFoundException, SQLException {
        if (!isConnected()) {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false", user, pass);
        }
    }

    public void Disconnect() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

}
