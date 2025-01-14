package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    create table if not exists user(
                        id int auto_increment primary key ,
                        name varchar(128) not null,
                        lastName varchar(128),
                        age tinyint,
                        UNIQUE (name,lastName)
                    )
                    """);
            System.out.println("The database was successfully created");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("drop table if exists user;");
            System.out.println("Database was successfully deleted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO user(name, lastname, age) VALUES(?,?,?)";
        try (var statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            connection.commit();
            System.out.println(name + " was successfully added to the database");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }
    }
    public void removeUserById(long id) {
        String sql = ("delete from user where id = " + id);
        try (var statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
            connection.commit();
            System.out.printf("The user with id \"%d\" was successfully deleted\n", id);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        String sql = ("select * from user");
        List<User> userList = new ArrayList<>();
        try (var resultSet = connection.createStatement().executeQuery(sql)) {
            while (resultSet.next()){
                User user = new User(
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age")
                );
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public void cleanUsersTable() {
        String sql = "truncate table user;";
        try (var statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
            connection.commit();
            System.out.println("All users have been deleted");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }
}
