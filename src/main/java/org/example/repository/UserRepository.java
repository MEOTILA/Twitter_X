package org.example.repository;

import org.example.Datasource;
import org.example.entity.User;
import org.example.services.AuthenticationServices;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


public class UserRepository {

    private static final String CREATE_TABLE_USER = """
            CREATE TABLE IF NOT EXISTS users (
            user_id SERIAL PRIMARY KEY,
            username varchar(250) UNIQUE NOT NULL,
            password varchar(250) NOT NULL,
            display_name varchar(250) NOT NULL,
            email varchar(50) UNIQUE NOT NULL,
            bio varchar(250),
            account_create_Date Date NOT NULL);
            """;

    public UserRepository() throws SQLException {
        // Initialize table on creation
        initTable();
    }

    public void initTable() throws SQLException {
        var statement = Datasource.getConnection().prepareStatement(UserRepository.CREATE_TABLE_USER);
        statement.execute();
        statement.close();
    }
    private static final String INSERT_SQL = """
            INSERT INTO users(username, password, display_name, email, bio, account_create_date)
            VALUES (?, crypt(?, gen_salt('bf')), ?, ?, ?, ?)
            """;
    private static final String DELETE_BY_ID = """
            DELETE FROM USERS
            WHERE user_id = ?
            """;

    private static final String FIND_BY_ID = """
            SELECT * FROM USERS
            WHERE user_id = ?
            """;

    private static final String FIND_BY_USERNAME = """
            SELECT * FROM USERS
            WHERE username = ?
            """;
    private static final String FIND_BY_DISPLAY_NAME = """
            SELECT * FROM USERS
            WHERE display_name = ?
            """;
    private static final String FIND_BY_EMAIL = """
            SELECT * FROM USERS
            WHERE email = ?
            """;

    private static final String UPDATE_USER = """
            UPDATE USERS
            SET username = ? , password = crypt(?, gen_salt('bf')) , display_name = ? , bio = ?
            WHERE user_id = ?;
            """;
    private static final String UPDATE_USERNAME = """
            UPDATE USERS
            SET username = ?
            WHERE user_id = ?;
            """;
    private static final String UPDATE_PASSWORD = """
            UPDATE USERS
            SET password = crypt(?, gen_salt('bf'))
            WHERE user_id = ?;
            """;
    private static final String UPDATE_DISPLAY_NAME = """
            UPDATE USERS
            SET display_name = ?
            WHERE user_id = ?;
            """;
    private static final String UPDATE_BIO = """
            UPDATE USERS
            SET bio = ?
            WHERE user_id = ?;
            """;

    public User save(User user) throws SQLException {
        var statement = Datasource.getConnection().prepareStatement(INSERT_SQL);
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getDisplayName());
        statement.setString(4, user.getEmail());
        statement.setString(5, user.getBio());

        java.sql.Date sqlDate = java.sql.Date.valueOf(user.getAccountCreateDate());
        statement.setDate(6, sqlDate);

        statement.execute();
        statement.close();
        return user;
    }

    public void deleteById(int id) throws SQLException {
        try (var statement = Datasource.getConnection().prepareStatement(DELETE_BY_ID)) {
            statement.setLong(1, id);
            var affectedRows = statement.executeUpdate();
            System.out.println("# of user deleted: " + affectedRows);
        }
    }

    public User findById(int id) throws SQLException {
        try (var statement = Datasource.getConnection().prepareStatement(FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            User user = null;
            if (resultSet.next()) {
                int user_id = resultSet.getInt(1);
                String username = resultSet.getString(2);
                String password = resultSet.getString(3);
                String display_name = resultSet.getString(4);
                String email = resultSet.getString(5);
                String bio = resultSet.getString(6);
                LocalDate account_create_date = resultSet.getDate(7).toLocalDate();

                user = new User (user_id, username, password, display_name,
                        email, bio, account_create_date);
            }

            return user;
        }
    }

    public User findByUsername(String username) throws SQLException {
        try (var statement = Datasource.getConnection().prepareStatement(FIND_BY_USERNAME)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            User user = null;
            if (resultSet.next()) {
                int user_id = resultSet.getInt(1);
                String userName = resultSet.getString(2);
                String password = resultSet.getString(3);
                String display_name = resultSet.getString(4);
                String email = resultSet.getString(5);
                String bio = resultSet.getString(6);
                LocalDate account_create_date = resultSet.getDate(7).toLocalDate();

                user = new User (user_id, userName, password, display_name,
                        email, bio, account_create_date);
            }

            return user;
        }
    }
    public User findByEmail(String email) throws SQLException {
        try (var statement = Datasource.getConnection().prepareStatement(FIND_BY_EMAIL)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            User user = null;
            if (resultSet.next()) {
                int user_id = resultSet.getInt(1);
                String userName = resultSet.getString(2);
                String password = resultSet.getString(3);
                String display_name = resultSet.getString(4);
                String eMail = resultSet.getString(5);
                String bio = resultSet.getString(6);
                LocalDate account_create_date = resultSet.getDate(7).toLocalDate();

                user = new User (user_id, userName, password, display_name,
                        eMail, bio, account_create_date);
            }

            return user;
        }
    }

    public User updateUser(User user) throws SQLException {
        var statement = Datasource.getConnection().prepareStatement(UPDATE_USER);
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.setString(3,user.getDisplayName());
        statement.setString(4,user.getBio());

        statement.execute();
        statement.close();
        return user;
    }
    public User updateUsername(User user) throws SQLException {
        int userId = AuthenticationServices.getLoggedInUser().getUserId();

        var statement = Datasource.getConnection().prepareStatement(UPDATE_USERNAME);
        statement.setString(1, user.getUsername());
        statement.setInt(2, userId);

        statement.execute();
        statement.close();
        return user;
    }

    public User updatePassword(User user) throws SQLException {
        int userId = AuthenticationServices.getLoggedInUser().getUserId();

        var statement = Datasource.getConnection().prepareStatement(UPDATE_PASSWORD);
        statement.setString(1, user.getPassword());
        statement.setInt(2, userId);

        statement.execute();
        statement.close();
        return user;
    }

    public User updateDisplayName(User user) throws SQLException {
        int userId = AuthenticationServices.getLoggedInUser().getUserId();

        var statement = Datasource.getConnection().prepareStatement(UPDATE_DISPLAY_NAME);
        statement.setString(1, user.getDisplayName());
        statement.setInt(2, userId);

        statement.execute();
        statement.close();
        return user;
    }

    public User updateBio(User user) throws SQLException {
        int userId = AuthenticationServices.getLoggedInUser().getUserId();

        var statement = Datasource.getConnection().prepareStatement(UPDATE_BIO);
        statement.setString(1, user.getBio());
        statement.setInt(2, userId);

        statement.execute();
        statement.close();
        return user;
    }
}
