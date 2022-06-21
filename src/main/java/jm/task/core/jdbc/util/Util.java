package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static Connection connection;

    static {
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String URl = "jdbc:mysql://localhost:3306/public";
            String login = "root";
            String pass = "Success100SS";

            Class.forName(driver);
            connection = DriverManager.getConnection(URl, login, pass);

            System.out.println("Connection succesfull!");
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Connection failed");
            e.getStackTrace();
        }
    }

    public Connection getConnection(){
        return connection;
    }
}
