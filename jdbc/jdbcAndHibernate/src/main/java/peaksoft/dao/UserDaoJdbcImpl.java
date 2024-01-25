package peaksoft.dao;

import peaksoft.model.User;
import peaksoft.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbcImpl implements UserDao {
    private final Connection connection = Util.getConnection();
    public UserDaoJdbcImpl() {
    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
                CREATE TABLE IF NOT EXISTS users (
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(255),
                    last_name VARCHAR(255),
                    age INT
                )
                """);

        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблицы пользователей: " + e.getMessage());
        }
    }

    public void dropUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("""
                DROP TABLE IF EXISTS users;
                """);
            statement.close();
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении таблицы пользователей: " + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String insertQuery = "INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при сохранении пользователя: " + e.getMessage());
        }
    }

    public void removeUserById(long id) {
        String deleteQuery = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setLong(1, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("Пользователь с указанным идентификатором не найден.");
            } else {
                System.out.println("Пользователь с идентификатором " + id + " успешно удален.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении пользователя: " + e.getMessage());
        }
    }



    public List<User> getAllUsers() {
        String selectQuery = "SELECT * FROM users";
        List<User> userList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            User user = new User();

            while (resultSet.next()) {
                 user.setId(resultSet.getLong("id"));
                 user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getInt("age"));
                userList.add(user);
            }

        } catch (SQLException e) {
            System.out.println("Ошибка при получении списка пользователей: " + e.getMessage());

        }

        return userList;
    }

    public void cleanUsersTable() {
        String deleteAllQuery = "DELETE FROM users";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteAllQuery)) {
            preparedStatement.executeUpdate();
            System.out.println("Таблица пользователей очищена.");
        } catch (SQLException e) {
            System.out.println("Ошибка при очистке таблицы пользователей: " + e.getMessage());
        }
    }

}