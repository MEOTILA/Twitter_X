package org.example.repository;

import org.example.Datasource;
import org.example.exception.TwitterExceptions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserTweetInteractionsRepository {
    private static final String CREATE_TABLE_USER_TWEET_INTERACTIONS = """
    CREATE TABLE IF NOT EXISTS user_tweet_interactions (
    user_id INT,
    tweet_id INT,
    interaction_type VARCHAR(10),
    PRIMARY KEY (user_id, tweet_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (tweet_id) REFERENCES tweets(tweet_id));
    """;

    public UserTweetInteractionsRepository() throws SQLException {
        initTable();
    }

    public void initTable() throws SQLException {
        var statement = Datasource.getConnection().prepareStatement(
        UserTweetInteractionsRepository.CREATE_TABLE_USER_TWEET_INTERACTIONS);
        statement.execute();
        statement.close();
    }

    private static final String USER_INTERACTIONS = """
                     SELECT COUNT(*) FROM user_tweet_interactions
                     WHERE user_id = ? AND tweet_id = ?
                     AND interaction_type = ?
            """;
private static final String INSERT_TWEET_INTERACTIONS = """
        INSERT INTO user_tweet_interactions (
        user_id, tweet_id, interaction_type)
        VALUES (?, ?, ?)
        """;

    public boolean userHasInteractedWithTweet(int userId, int tweetId, String interactionType) throws SQLException {
        try (var stmt = Datasource.getConnection().prepareStatement(USER_INTERACTIONS)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, tweetId);
            stmt.setString(3, interactionType);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    public void insertUserTweetInteraction(int userId, int tweetId, String interactionType) throws SQLException {
        try (var stmt = Datasource.getConnection().prepareStatement(INSERT_TWEET_INTERACTIONS)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, tweetId);
            stmt.setString(3, interactionType);
            stmt.executeUpdate();
        }
    }

}


