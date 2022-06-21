package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    public static String URl = "jdbc:mysql://localhost:3306/public";
    public static String driver = "com.mysql.cj.jdbc.Driver";
    public static String login = "root";
    public static String pass = "Success100SS";
    public static Connection connection;

    static {
        try {
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
