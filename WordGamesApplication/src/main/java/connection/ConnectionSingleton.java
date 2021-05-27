package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionSingleton {
    private static ConnectionSingleton instance = null;
    private static Connection connection = null;
    private ConnectionSingleton() {
        Properties connectionProps = new Properties();
        connectionProps.put("user","root");
        connectionProps.put("password","");
        try {
            connection = DriverManager.getConnection(
                    "jdbc:" + "mysql" + "://" +
                            "127.0.0.1" +
                            ":" + 3306 + "/wordgames_database",
                    connectionProps);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if(instance == null)
            instance = new ConnectionSingleton();
        return ConnectionSingleton.connection;
    }
}