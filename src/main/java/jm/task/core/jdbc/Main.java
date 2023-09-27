package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Petr", "Petrov", (byte) 23);
        userService.saveUser("Alex", "Sidorov", (byte) 25);
        userService.saveUser("Anna", "K", (byte) 21);
        userService.saveUser("Vasilii", "Petrov", (byte) 20);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
