package org.example;

import org.example.entity.Tweet;
import org.example.entity.User;
import org.example.services.AuthenticationServices;
import org.example.services.TweetServices;
import org.example.services.UserServices;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {

        UserServices userServices = new UserServices();
        TweetServices tweetServices = new TweetServices();


        System.out.println("* Welcome to Twitter! *");
        while (true) {
            System.out.println("1. Signup 🟢");
            System.out.println("2. Login 🔵");
            System.out.println("Choose You Action: ");
            String option = scanner.next();

            switch (option) {
                case "1":
                    System.out.println("* Signup *");
                    System.out.println("Please Enter Your Username 📜:");
                    String username = scanner.next();
                    System.out.println("Please Enter Your Password 🔑:");
                    String password = scanner.next();
                    System.out.println("Please Enter Your Display Name 📑:");
                    String displayName = scanner.next();
                    System.out.println("Please Enter Your Email 📩:");
                    String email = scanner.next();
                    System.out.println("Please Enter Your Bio 📋:");
                    String bio = scanner.next();
                    userServices.userSignUp(username, password, displayName, email, bio);
                    System.out.println("Your Account has been created!✅");
                    break;

                case "2":
                    System.out.println("* Login *");
                    System.out.println("Please Enter Your Username or Email 📩: ");
                    String usernameOrEmail = scanner.next();
                    System.out.println("Please Enter Your Password 🔑: ");
                    String LoggingPassword = scanner.next();
                    if (userServices.userLogin(usernameOrEmail, LoggingPassword)) {
                        userMenu(userServices, tweetServices);
                    }
                    break;
            }
        }
    }


    private static void userMenu(UserServices userServices, TweetServices tweetServices) throws SQLException {
        while (true){
        System.out.println("* User Menu *");
        System.out.println("1. Explore all Tweets");
        System.out.println("2. Post a Tweet");
        System.out.println("3. Update a Tweet");
        System.out.println("4. Delete a Tweet");
        System.out.println("5. Setting");
        System.out.println("6. Logout");
        System.out.println("Choose Your Action: ");

        String option = scanner.next();


            switch (option) {
                case "1":
                    System.out.println("* Explore Tweets *");
                    List<Tweet> allTweets = tweetServices.showAllTweets();
                    for (Tweet tweet : allTweets) {
                        System.out.println("** Tweet Text: " + tweet.getTweetText());
                        System.out.println("** User ID: " + tweet.getUserID());
                        System.out.println("** Tweet ID: " + tweet.getTweetID());
                        System.out.println("-------");
                    }
                    userMenu(userServices, tweetServices);
                    break;

                case "2":
                    while (true) {
                        System.out.println("* New Tweet *");
                        System.out.println("Write your Tweet: ");
                        String tweetText = scanner.next();
                        tweetServices.postTweet(tweetText);
                        System.out.println("Your Tweet has been posted successfully!");
                        System.out.println("Do you want to post another tweet (y/n)?");
                        String postAnotherTweet = scanner.next();
                        if (postAnotherTweet.equalsIgnoreCase("n")) {
                            //userMenu(userServices, tweetServices);
                            break;
                        }
                    }
                        break;
                case "3":
                    System.out.println("* Update a Tweet *");
                    //todo: tweetServices.showUserTweets();
                    System.out.println("** Enter Tweet ID to Update: ");
                    int tweetIdToUpdate = scanner.nextInt();
                    System.out.println("** Enter Updated Tweet Text: ");
                    String updatedTweetText = scanner.next();
                    tweetServices.updateTweet(tweetIdToUpdate, updatedTweetText);
                    break;

                case "4":
                    System.out.println("* Delete a Tweet *");
                    System.out.println("Enter Your Tweet ID to Delete: ");
                    tweetServices.showUserTweets();
                    int tweetIdToDelete = scanner.nextInt();
                    //todo: tweetServices.showUserTweets();

                    tweetServices.deleteTweet(tweetIdToDelete);
                    break;

                case "5":
                    userSetting(userServices);
                    break;
                case "6":
                    AuthenticationServices.logout();
                    System.out.println("You have been Logged out successfully.");
                    return;
            }
        }

    }

    private static void userSetting(UserServices userServices) throws SQLException {
        while (true) {
            System.out.println("* Settings *");
            System.out.println("1. Change Username");
            System.out.println("2. Change Password");
            System.out.println("3. Change Display Name");
            System.out.println("4. Change Bio");
            System.out.println("5. Back");
            System.out.println("Choose Your Action: ");
            String option = scanner.next();


            switch (option) {
                case "1":
                    System.out.println("* Change Username *");
                    System.out.println("Enter Your New Username: ");
                    String newUserName = scanner.next();
                    userServices.updateUsername(newUserName);

                    break;

                case "2":
                    System.out.println("* Change Password *");
                    System.out.println("Enter Your New Password: ");
                    String newPassword = scanner.next();
                    userServices.updatePassword(newPassword);
                    break;

                case "3":
                    System.out.println("* Change Display Name *");
                    System.out.println("Enter Your New Display Name: ");
                    String newDisplayName = scanner.next();
                    userServices.updateDisplayName(newDisplayName);
                    break;

                case "4":
                    System.out.println("* Change Bio *");
                    System.out.println("Enter Your new Bio: ");
                    String newBio = scanner.next();
                    userServices.updateBio(newBio);
                    break;

                case "5":
                    return;
            }
        }
    }
}