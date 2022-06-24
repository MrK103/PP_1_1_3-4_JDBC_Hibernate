package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class Util {
    private static Connection connection;
    private static final String DRIVER_KEY = "db.driver";
    private static final String URl_KEY = "db.url";
    private static final String LOGIN_KEY = "db.login";
    private static final String PASS_KEY = "db.password";


    private static SessionFactory sessionFactory;

    public static SessionFactory getConnectionHibernate() {
        if (sessionFactory == null) {
            try {
                Properties properties = new Properties();
                properties.put(Environment.DRIVER, PropertiesUtil.get(DRIVER_KEY));
                properties.put(Environment.URL, PropertiesUtil.get(URl_KEY));
                properties.put(Environment.USER, PropertiesUtil.get(LOGIN_KEY));
                properties.put(Environment.PASS, PropertiesUtil.get(PASS_KEY));
                properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
                properties.put(Environment.SHOW_SQL, true);

                Configuration configuration = new Configuration();
                configuration.setProperties(properties);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static void closeConnection() {
        try {
            if (sessionFactory != null) {
                if (!sessionFactory.isClosed()) sessionFactory.close();
            }
            if (connection != null) {
                if (!connection.isClosed()) connection.close();
            }

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
