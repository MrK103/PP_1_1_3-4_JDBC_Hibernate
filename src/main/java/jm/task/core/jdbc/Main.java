package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        var service = new UserServiceImpl();
        service.createUsersTable();
        for (int j = 0; j < 4; j++) {
            service.saveUser("Ivan_"+j, "Ivanov", (byte) 20);
        }
        System.out.println(service.getAllUsers());
        service.cleanUsersTable();
        service.dropUsersTable();
        Util.closeConnection();
    }
}
