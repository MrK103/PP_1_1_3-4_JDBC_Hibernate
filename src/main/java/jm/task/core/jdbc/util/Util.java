package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Util {
    private static Connection connection;
    private static final String DRIVER_KEY = "db.driver";
    private static final String URl_KEY = "db.url";
    private static final String LOGIN_KEY = "db.login";
    private static final String PASS_KEY = "db.password";

    static {
        try {
            Class.forName(PropertiesUtil.get(DRIVER_KEY));
        } catch (ClassNotFoundException e) {
            e.getStackTrace();
        }
    }

    private Connection openConnection() {
        try {
            connection = DriverManager.getConnection(PropertiesUtil.get(URl_KEY), PropertiesUtil.get(LOGIN_KEY), PropertiesUtil.get(PASS_KEY));
            System.out.println("Connection was opened");
            return connection;
        } catch (SQLException e) {
            System.out.println("error");
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() {
        try {
            if (!connection.isClosed()) connection.close();
            System.out.println("Connection was closed");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = openConnection();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
