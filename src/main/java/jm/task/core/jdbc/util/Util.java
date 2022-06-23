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

    public static void closeConnection() {
        try {
            if (!connection.isClosed() || connection != null) connection.close();
            System.out.println("Connection was closed");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                try {
                    Class.forName(PropertiesUtil.get(DRIVER_KEY));
                    connection = DriverManager.getConnection(PropertiesUtil.get(URl_KEY), PropertiesUtil.get(LOGIN_KEY), PropertiesUtil.get(PASS_KEY));
                    connection.setAutoCommit(false);
                    System.out.println("Connection was opened");
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
