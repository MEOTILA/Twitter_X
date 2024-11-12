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

    public Tweet updateTweet(int tweetId, String updatedTweetText) throws SQLException {
        Tweet tweet = new Tweet();
        tweet.setTweetID(tweetId);
        tweet.setTweetText(updatedTweetText);
        System.out.println("Your Tweet has been updated!");
        return tweetRepository.updateTweet(tweet);
    }
    public void deleteTweet (int tweetId) throws SQLException {
        tweetRepository.deleteTweet(tweetId);
        if(tweetRepository.deleteTweet(tweetId) == -1){
            System.out.println("error!");
        }else{
            System.out.println("Tweet has been deleted!");
        }
    }

    /*public List<Tweet> showAllTweets() throws SQLException {
        return tweetRepository.showAllTweets();
    }*/

    public void showAllTweets() throws SQLException {
        List<Tweet> allTweets = tweetRepository.showAllTweets();

        for (Tweet tweet : allTweets) {
            System.out.println("** Tweet Text 📝: " + tweet.getTweetText());
            System.out.println("** User ID 👨🏻‍⚖️: " + tweet.getUserID());
            System.out.print("** Tweet ID 🌐: " + tweet.getTweetID());
            System.out.print(" * Like: " + tweet.getLikeCount() + "💖");
            System.out.print(" * Dislike:" + tweet.getDislikeCount() + "👎🏻");
            System.out.print(" * Retweet: " + tweet.getRetweetCount() + "🔁");
            System.out.println("\n---------------------------------------------");
        }
    }

    public List<Tweet> showUserTweets() throws SQLException {
        return tweetRepository.showUserTweets();
    }

    public Tweet likeTweetById(int tweetId) throws SQLException {
        Tweet tweet = new Tweet();
        tweet.setTweetID(tweetId);
        tweet.setLikeCount(tweet.getLikeCount() + 1);
        tweetRepository.likeTweet(tweet);
        return tweet;
    }
    public Tweet dislikeTweetById(int tweetId) throws SQLException {
        Tweet tweet = new Tweet();
        tweet.setTweetID(tweetId);
        tweet.setDislikeCount(tweet.getDislikeCount() + 1);
        tweetRepository.dislikeTweet(tweet);
        return tweet;
    }
    public Tweet retweetTweetById(int tweetId) throws SQLException {
        Tweet tweet = new Tweet();
        tweet.setTweetID(tweetId);
        tweet.setRetweetCount(tweet.getRetweetCount() + 1);
        tweetRepository.retweetTweet(tweet);
        return tweet;
    }
}
