package org.example.services;

import org.example.entity.Tweet;
import org.example.repository.TweetRepository;
import org.example.repository.UserRepository;

import java.sql.SQLException;
import java.util.List;

public class TweetServices {

    TweetRepository tweetRepository;

    public TweetServices() throws SQLException {
        tweetRepository = new TweetRepository();
    }

    public Tweet postTweet (String tweetText) throws SQLException {

        Tweet tweet = new Tweet();
        tweet.setTweetText(tweetText);
        return tweetRepository.saveTweet(tweet);
    }

    public Tweet updateTweet (String updatedTweetText) throws SQLException {
        Tweet tweet = new Tweet();
        tweet.setTweetText(updatedTweetText);
        return tweetRepository.updateTweet(tweet);
    }

    public void deleteTweet (int tweetId) throws SQLException {
        tweetRepository.deleteTweet(tweetId);
    }

    public List<Tweet> showAllTweets() throws SQLException {
        return tweetRepository.showAllTweets();
    }
}
