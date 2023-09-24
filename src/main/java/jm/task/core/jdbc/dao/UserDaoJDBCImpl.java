package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    List<User> userList = new ArrayList<>();
    private String createTable = """
            CREATE TABLE some_schema.user_table
            (
            id BIGINT PRIMARY KEY AUTO_INCREMENT ,
            name TEXT NOT NULL ,
            last_name TEXT NOT NULL ,
            age SMALLINT NOT NULL
            )
                        
            """;
    private String dropTable = """
            DROP TABLE some_schema.user_table
            """;

    private String removeUser = """
            DELETE FROM some_schema.user_table
            WHERE id = ?
            """;
    private String clearUserTable = """
            DELETE FROM some_schema.user_table
            """;

    private String saveUser = """
            INSERT INTO some_schema.user_table (name, last_name, age)
            VALUES (?,?,?)
            """;

    private String getUsers = """
            SELECT name, last_name, age FROM some_schema.user_table
            """;


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        try (Connection connection = Util.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(createTable);) {

            preparedStatement.execute();

        }catch (SQLSyntaxErrorException e) {

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(dropTable)) {
            preparedStatement.execute();
        } catch (SQLSyntaxErrorException e) {

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(saveUser)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);

            preparedStatement.execute();

            System.out.printf("User с именем – %s добавлен в базу данных \n", name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(removeUser)) {

            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        try (Connection connection = Util.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getUsers)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userList.add(new User(resultSet.getString("name"), resultSet.getString("last_name"), resultSet.getByte("age")));

                if (!userList.isEmpty())
                    System.out.println(userList.get(userList.size() - 1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(clearUserTable)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
