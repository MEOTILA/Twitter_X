package org.example.repository;

import org.example.Datasource;
import org.example.entity.Tweet;
import org.example.services.AuthenticationServices;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TweetRepository {
    private static final String CREATE_TABLE_TWEETS = """
            CREATE TABLE IF NOT EXISTS tweets (
            tweet_id SERIAL PRIMARY KEY,
            user_id INTEGER,
            tweet_text varchar(280) NOT NULL,
            like_count INTEGER NOT NULL,
            dislike_count INTEGER NOT NULL,
            retweet_count INTEGER NOT NULL,
            FOREIGN KEY (user_id)
            REFERENCES users(user_id));
            """;
    public TweetRepository() throws SQLException {
        // Initialize table on creation
        initTable();
    }

    public void initTable() throws SQLException {
        var statement = Datasource.getConnection().prepareStatement(TweetRepository.CREATE_TABLE_TWEETS);
        statement.execute();
        statement.close();
    }
    private static final String INSERT_SQL = """
            INSERT INTO TWEETS(user_id, tweet_text, like_count,
            dislike_count, retweet_count)
            VALUES (?, ?, ?, ?, ?)
            """;
    private static final String DELETE_BY_TWEET_ID = """
            DELETE FROM TWEETS
            WHERE tweet_id = ?
            """;

    private static final String FIND_BY_TWEET_ID = """
            SELECT * FROM TWEETS
            WHERE tweet_id = ?
            """;
    private static final String FIND_ALL_TWEETS = """
            SELECT * FROM TWEETS
            """;

    private static final String FIND_BY_USER_ID = """
            SELECT * FROM TWEETS
            WHERE user_id = ?
            """;

    private static final String UPDATE_TWEET_TEXT = """
            UPDATE TWEETS
            SET tweet_text = ?
            WHERE tweet_id = ?
            AND user_id = ?;
            """;
    private static final String UPDATE_TWEET_LIKE_COUNT = """
            UPDATE TWEETS
            SET like_count = ?
            WHERE tweet_id = ?;
            """;
    private static final String UPDATE_TWEET_DISLIKE_COUNT = """
            UPDATE TWEETS
            SET dislike_count = ?
            WHERE tweet_id = ?;
            """;
    private static final String UPDATE_TWEET_RETWEET_COUNT = """
            UPDATE TWEETS
            SET retweet_count = ?
            WHERE tweet_id = ?;
            """;
    private static final String DELETE_TWEET_WITH_USER_ID_AND_TWEET_ID = """
            DELETE FROM TWEETS
            WHERE tweet_id = ?
            AND user_id = ?
            """;


    public Tweet saveTweet(Tweet tweet) throws SQLException {
        int userId = AuthenticationServices.getLoggedInUser().getUserId();

        var statement = Datasource.getConnection().prepareStatement(INSERT_SQL);
        statement.setInt(1, userId);
        statement.setString(2, tweet.getTweetText());
        statement.setInt(3, 0);
        statement.setInt(4, 0);
        statement.setInt(5, 0);
        statement.execute();
        statement.close();

        return tweet;
    }

    public Tweet updateTweet(Tweet tweet) throws SQLException {
        int userId = AuthenticationServices.getLoggedInUser().getUserId();

        var statement = Datasource.getConnection().prepareStatement(UPDATE_TWEET_TEXT);
        statement.setString(1, tweet.getTweetText());
        statement.setInt(2, tweet.getTweetID());
        statement.setInt(3, userId);
        statement.execute();
        statement.close();

        return tweet;
    }

    public int deleteTweet(int tweetId) throws SQLException {
        int userId = AuthenticationServices.getLoggedInUser().getUserId();

        var statement = Datasource.getConnection().prepareStatement(DELETE_TWEET_WITH_USER_ID_AND_TWEET_ID);
        statement.setInt(1, tweetId);
        statement.setInt(2, userId);
        int rowsAffected = statement.executeUpdate();

        if (rowsAffected == 0) {
            return -1;
        } else {
            return 1;
        }
    }

    public Tweet retweet(Tweet tweet) throws SQLException{
        var statement = Datasource.getConnection().prepareStatement(FIND_BY_TWEET_ID);
        statement.execute();
        statement.close();
        return tweet;
    }

    public List<Tweet> showAllTweets() throws SQLException {
        var statement = Datasource.getConnection().prepareStatement(FIND_ALL_TWEETS);
        ResultSet resultSet = statement.executeQuery();

        List<Tweet> tweets = new ArrayList<>();
        while (resultSet.next()) {
            Tweet tweet = new Tweet();
            tweet.setTweetID(resultSet.getInt("tweet_id"));
            tweet.setUserID(resultSet.getInt("user_id"));
            tweet.setTweetText(resultSet.getString("tweet_text"));
            tweet.setLikeCount(resultSet.getInt("like_count"));
            tweet.setDislikeCount(resultSet.getInt("dislike_count"));
            tweet.setRetweetCount(resultSet.getInt("retweet_count"));

            tweets.add(tweet);
        }

        statement.close();
        return tweets;
    }

    public List<Tweet> showUserTweets() throws SQLException {
        int userId = AuthenticationServices.getLoggedInUser().getUserId();

        var statement = Datasource.getConnection().prepareStatement(FIND_BY_USER_ID);
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();

        List<Tweet> tweets = new ArrayList<>();
        while (resultSet.next()) {
            Tweet tweet = new Tweet();
            tweet.setTweetID(resultSet.getInt("tweet_id"));
            tweet.setTweetText(resultSet.getString("tweet_text"));
            tweet.setUserID(resultSet.getInt(userId));

            tweets.add(tweet);
        }
        statement.close();
        return tweets;
    }

    public Tweet likeTweet(Tweet tweet) throws SQLException {
        var statement = Datasource.getConnection().prepareStatement(UPDATE_TWEET_LIKE_COUNT);
        statement.setInt(1, tweet.getLikeCount());
        statement.setInt(2, tweet.getTweetID());
        statement.execute();
        statement.close();
        return tweet;
    }
    public Tweet dislikeTweet(Tweet tweet) throws SQLException{
        var statement = Datasource.getConnection().prepareStatement(UPDATE_TWEET_DISLIKE_COUNT);
        statement.setInt(1, tweet.getDislikeCount());
        statement.setInt(2, tweet.getTweetID());
        statement.execute();
        statement.close();
        return tweet;
    }
    public Tweet retweetTweet(Tweet tweet) throws SQLException{
        var statement = Datasource.getConnection().prepareStatement(UPDATE_TWEET_RETWEET_COUNT);
        statement.setInt(1, tweet.getRetweetCount());
        statement.setInt(2, tweet.getTweetID());
        statement.execute();
        statement.close();
        return tweet;
    }

}
