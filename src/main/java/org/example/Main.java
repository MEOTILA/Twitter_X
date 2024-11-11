package org.example;

import org.example.entity.Tweet;
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
                        userMenu(tweetServices);
                    }
                    break;
            }
        }
    }


    private static void userMenu(TweetServices tweetServices) throws SQLException {
        System.out.println("* Welcome Dear User! 😍 *");
        System.out.println("1. Explore all Tweets");
        System.out.println("2. Post a Tweet");
        System.out.println("3. Setting");
        System.out.println("4. Logout");
        System.out.println("Choose Your Action: ");

        String option = scanner.next();

        while (true){
            switch (option){
                case "1":
                    System.out.println("* Explore Tweets *");
                    List<Tweet> allTweets = tweetServices.showAllTweets();
                    for (Tweet tweet : allTweets) {
                        System.out.println("** Tweet Text: " + tweet.getTweetText());
                        System.out.println("** User ID: " + tweet.getUserID());
                        System.out.println("** Tweet ID: " + tweet.getTweetID());
                        System.out.println("-------");
                    }
                    break;

                case "2":
                    System.out.println("* New Tweet *");
                    System.out.println("Write your Tweet: ");
                    String tweetText = scanner.next();
                    tweetServices.postTweet(tweetText);
                    break;

                case "3":
                    userSetting();
                    break;
                case "4":
                    AuthenticationServices.logout();
                    System.out.println("You have been Logged out successfully.");
                    return;
            }
        }

    }

    private static void userSetting() {
        System.out.println("* Settings *");
        System.out.println("1. Change Username");
        System.out.println("2. Change Password");
        System.out.println("3. Change Display Name");
        System.out.println("4. Change Bio");
        System.out.println("5. Back");
        System.out.println("Choose Your Action: ");
        String option = scanner.next();


        switch (option){
            case "1":

                break;

            case "2":
                break;

            case "3":
                break;

            case "4":
                break;

            case "5":
                break;

        }
    }
}