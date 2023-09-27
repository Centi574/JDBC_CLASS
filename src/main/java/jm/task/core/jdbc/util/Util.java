package jm.task.core.jdbc.util;

import com.mysql.jdbc.Driver;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.*;

public class Util {
    // реализуйте настройку соеденения с БД

    public static final String URL = "jdbc:mysql://localhost:3306/jdbc_classes";
    public static final String LOGIN = "root";
    public static final String PASSWORD = "root";

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Util() {

    }

    public static Connection openConnection() {
        try {
            return DriverManager.getConnection(URL, LOGIN, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Hibernate version
    public static SessionFactory getSessionFactory() {
        return new Configuration().
                configure("hibernate.cfg.xml").
                addAnnotatedClass(User.class).
                buildSessionFactory();
    }
}