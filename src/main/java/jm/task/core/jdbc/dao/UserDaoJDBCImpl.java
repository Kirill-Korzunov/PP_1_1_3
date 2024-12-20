package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import jm.task.core.jdbc.util.Util;

public class UserDaoJDBCImpl implements UserDao {

    private static final Logger LOGGER = Logger.getLogger(UserDaoJDBCImpl.class.getName());
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users (id BIGINT AUTO_INCREMENT PRIMARY KEY," +
            " name VARCHAR(255), lastname VARCHAR(255), age TINYINT)";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS users";
    private static final String CLEAN_TABLE = "TRUNCATE TABLE users";
    private static final String INSERT_USER = "INSERT INTO users (name, lastname, age) VALUES (?, ?, ?)";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    private static final String SELECT_ALL_USER = "SELECT * FROM users";


    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Connection connection = Util.getMyConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE)) {
            preparedStatement.executeUpdate();
            LOGGER.info("Users таблица создана");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getMyConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DROP_TABLE)) {
            preparedStatement.executeUpdate();
            LOGGER.info("Users таблица удалена");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getMyConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            LOGGER.info("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getMyConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {
            preparedStatement.setLong(1, id);
            LOGGER.info("User с id - " + id + " удален из базы данных");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = Util.getMyConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USER);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                User user = new User(rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getByte("age"));
                userList.add(user);
            }
            return userList;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getMyConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CLEAN_TABLE)) {
            preparedStatement.executeUpdate();
            LOGGER.info("Users таблица очищена");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
