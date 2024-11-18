package org.example.services;

import org.example.entity.Tweet;
import org.example.entity.User;
import org.example.exception.TwitterExceptions;
import org.example.repository.TagRepository;
import org.example.repository.TweetRepository;
import org.example.repository.UserRepository;
import org.example.repository.UserTweetInteractionsRepository;

import java.sql.SQLException;
import java.util.List;

public class TweetServices {

    TweetRepository tweetRepository;
    UserRepository userRepository = new UserRepository();
    TagServices tagServices = new TagServices();
    UserTweetInteractionsRepository userTweetInteractionsRepository = new UserTweetInteractionsRepository();
    public TweetServices() throws SQLException {
        tweetRepository = new TweetRepository();
    }

//    public Tweet postTweet (String tweetText) throws SQLException {
//
//        Tweet tweet = new Tweet();
//        tweet.setTweetText(tweetText);
//        return tweetRepository.saveTweet(tweet);
//    }

    public Tweet postTweet(String tweetText, List<String> tags) throws SQLException {
        Tweet tweet = new Tweet();
        tweet.setTweetText(tweetText);

        tweet = tweetRepository.saveTweet(tweet);

        for (String tagName : tags) {
            tagServices.saveTag(tagName, tweet.getTweetID());
        }

        return tweet;
    }

//    public Tweet updateTweet(int tweetId, String updatedTweetText) throws SQLException {
//        try {
//            Tweet tweet = new Tweet();
//            tweet.setTweetID(tweetId);
//            tweet.setTweetText(updatedTweetText);
//            System.out.println("Your Tweet has been updated!");
//            return tweetRepository.updateTweet(tweet);
//        }catch (TwitterExceptions g){
//            System.out.println("failed to update tweet! " + g.getMessage());
//            throw new TwitterExceptions("failed to update tweet! "  + g.getMessage());
//        }
//    }

    public Tweet updateTweet(int tweetId, String updatedTweetText) throws SQLException {
        try {
            Tweet tweet = tweetRepository.findTweetById(tweetId);
            if (tweet == null) {
                throw new TwitterExceptions("Tweet not found");
            }

            tweet.setTweetText(updatedTweetText);
            tweetRepository.updateTweet(tweet);

            System.out.println("Your Tweet has been updated!");
            return tweet;
        } catch (TwitterExceptions n) {
            throw new TwitterExceptions("Error updating tweet: " + n.getMessage());
        }
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
        try {
            List<Tweet> allTweets = tweetRepository.showAllTweets();

        //List<User> allDisplayName = userRepository.
        for (Tweet tweet : allTweets) {
            System.out.println("** Tweet Text 📝: " + tweet.getTweetText());
            System.out.println("** User ID 👨🏻‍⚖️: " + tweet.getUserID());
            System.out.print("** Tweet ID 🌐: " + tweet.getTweetID());
            System.out.print(" * Like: " + tweet.getLikeCount() + "💖");
            System.out.print(" * Dislike:" + tweet.getDislikeCount() + "👎🏻");
            System.out.print(" * Retweet: " + tweet.getRetweetCount() + "🔁");
            System.out.println("\n------------------------------------------------------------");
        }
        }catch (TwitterExceptions r){
            System.out.println("Error fetching tweets: " + r.getMessage());
        }
    }

    /*public void showUserTweets() throws SQLException, TwitterExceptions {
        List<Tweet> userTweets = tweetRepository.showUserTweets();

        for (Tweet tweet : userTweets) {
            System.out.println("** Tweet Text 📝: " + tweet.getTweetText());
            System.out.println("** User ID 👨🏻‍⚖️: " + tweet.getUserID());
            System.out.print("** Tweet ID 🌐: " + tweet.getTweetID());
            System.out.print(" * Like: " + tweet.getLikeCount() + "💖");
            System.out.print(" * Dislike:" + tweet.getDislikeCount() + "👎🏻");
            System.out.print(" * Retweet: " + tweet.getRetweetCount() + "🔁");
            System.out.println("\n------------------------------------------------------------");
        }
        if (userTweets.isEmpty()){
            System.out.println("You have no Tweets! ");
        }
    }*/

    public void showUserTweets() throws SQLException {
        try {
            List<Tweet> userTweets = tweetRepository.showUserTweets();

            if (!userTweets.isEmpty()) {
                for (Tweet tweet : userTweets) {
                    System.out.println("** Tweet Text 📝: " + tweet.getTweetText());
                    System.out.println("** User ID 👨🏻‍⚖️: " + tweet.getUserID());
                    System.out.print("** Tweet ID 🌐: " + tweet.getTweetID());
                    System.out.print(" * Like: " + tweet.getLikeCount() + "💖");
                    System.out.print(" * Dislike:" + tweet.getDislikeCount() + "👎🏻");
                    System.out.print(" * Retweet: " + tweet.getRetweetCount() + "🔁");
                    System.out.println("\n------------------------------------------------------------");
                }
            } else {
                System.out.println("You have no Tweets! ");
            }
        } catch (TwitterExceptions e) {
            System.out.println("Error fetching user tweets: " + e.getMessage());
        }
    }

    /*public Tweet likeTweetById(int tweetId) throws SQLException {
        try {
            Tweet tweet = tweetRepository.findTweetById(tweetId);
            if (tweet == null) {
                throw new TwitterExceptions("Tweet not found");
            }

            tweet.setLikeCount(tweet.getLikeCount() + 1);
            tweetRepository.likeTweet(tweet);
            return tweet;
        } catch (SQLException | TwitterExceptions d) {
            System.out.println("Failed to like tweet: " + d.getMessage());
            throw d;
        }
    }*/

    public Tweet likeTweetById(int tweetId) throws SQLException, TwitterExceptions {
        int userId = AuthenticationServices.getLoggedInUser().getUserId();

        if (userTweetInteractionsRepository.userHasInteractedWithTweet(
                userId, tweetId, "like")) {
            throw new TwitterExceptions("You have already liked this tweet.");
        }

        Tweet tweet = tweetRepository.findTweetById(tweetId);
        if (tweet == null) {
            throw new TwitterExceptions("Tweet not found");
        }

        tweet.setLikeCount(tweet.getLikeCount() + 1);
        tweetRepository.likeTweet(tweet);

        userTweetInteractionsRepository.insertUserTweetInteraction(
                userId, tweetId, "like");

        return tweet;
    }

    public Tweet dislikeTweetById(int tweetId) throws SQLException {
        try{ Tweet tweet = tweetRepository.findTweetById(tweetId);
        if (tweet == null) {
            throw new TwitterExceptions("Tweet not found");
        }

        tweet.setDislikeCount(tweet.getDislikeCount() + 1);
        tweetRepository.dislikeTweet(tweet);
        return tweet;
    } catch (SQLException | TwitterExceptions d) {
        System.out.println("Failed to Dislike tweet: " + d.getMessage());
        throw d;
    }
    }

    /*public Tweet dislikeTweetById(int tweetId) throws SQLException, TwitterExceptions {
        int userId = AuthenticationServices.getLoggedInUser().getUserId();

        if (userTweetInteractionsRepository.userHasInteractedWithTweet(
                userId, tweetId, "dislike")) {
            throw new TwitterExceptions("You have already disliked this tweet.");
        }

        Tweet tweet = tweetRepository.findTweetById(tweetId);
        if (tweet == null) {
            throw new TwitterExceptions("Tweet not found");
        }

        tweet.setDislikeCount(tweet.getDislikeCount() + 1);
        tweetRepository.dislikeTweet(tweet);

        userTweetInteractionsRepository.insertUserTweetInteraction(
                userId, tweetId, "dislike");

        return tweet;
    }*/


    public Tweet retweetTweetById(int tweetId) throws SQLException {
        try {
            int userId = AuthenticationServices.getLoggedInUser().getUserId();
            Tweet tweet = tweetRepository.findTweetById(tweetId);

            if (tweet == null) {
                throw new TwitterExceptions("Tweet not found");
            }

            tweet.setRetweetCount(tweet.getRetweetCount() + 1);
            tweetRepository.retweetTweetCount(tweet);

            return tweetRepository.retweetTweet(tweetId, userId);
        } catch (SQLException | TwitterExceptions e) {
            System.out.println("Failed to retweet: " + e.getMessage());
            throw e;
        }
    }

    /*public Tweet likeTweetById(int tweetId) throws SQLException {

        try {
            Tweet tweet = tweetRepository.findTweetById(tweetId);

        tweet.setLikeCount(tweet.getLikeCount() + 1);
        tweetRepository.likeTweet(tweet);
        return tweet;
        }catch (TwitterExceptions d){
            System.out.println("Failed to Like Tweet1 " +d.getMessage());
            throw d;
        }
    }*/

    /*public Tweet retweetTweetById(int tweetId) throws SQLException {
        Tweet tweet = tweetRepository.findTweetById(tweetId);
        tweet.setRetweetCount(tweet.getRetweetCount() + 1);
        tweetRepository.retweetTweet(tweet);
        return tweet;
    }*/

    /*public Tweet retweetTweetById(int tweetId) throws SQLException {
        int userId = AuthenticationServices.getLoggedInUser().getUserId();
        Tweet tweet = tweetRepository.findTweetById(tweetId);

        tweet.setRetweetCount(tweet.getRetweetCount() + 1);
        tweetRepository.retweetTweetCount(tweet);

        return tweetRepository.retweetTweet(tweetId, userId);
    }*/











        /*public Tweet likeTweetById(int tweetId) throws SQLException {
        Tweet tweet = new Tweet(); //fetch tweet
        tweet.setTweetID(tweetId);
        tweet.setLikeCount(tweet.getLikeCount() + 1);
        tweetRepository.likeTweet(tweet);
        return tweet;
    }*/

    /*public Tweet dislikeTweetById(int tweetId) throws SQLException {
        Tweet tweet = new Tweet();
        tweet.setTweetID(tweetId);
        tweet.setDislikeCount(tweet.getDislikeCount() + 1);
        tweetRepository.dislikeTweet(tweet);
        return tweet;
    }*/

    /*public Tweet retweetTweetById(int tweetId) throws SQLException {
        Tweet tweet = new Tweet();
        tweet.setTweetID(tweetId);
        tweet.setRetweetCount(tweet.getRetweetCount() + 1);
        tweetRepository.retweetTweet(tweet);
        return tweet;
    }*/
}
