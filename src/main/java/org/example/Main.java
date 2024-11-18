package org.example;

import org.example.exception.TwitterExceptions;
import org.example.services.TagServices;
import org.example.services.TweetServices;
import org.example.services.UserServices;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {

        UserServices userServices = new UserServices();
        TweetServices tweetServices = new TweetServices();
        TagServices tagServices = new TagServices();


        System.out.println("* Welcome to Twitter!📱 *");
        while (true) {
            System.out.println("1. Signup ✅");
            System.out.println("2. Login 🔰");
            System.out.println("Choose You Action: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    System.out.println("* Signup ✅ *");
                    System.out.println("Please Enter Your Username 📜:");
                    String username = scanner.nextLine();
                    System.out.println("Please Enter Your Password 🔑:");
                    String password = scanner.nextLine();
                    System.out.println("Please Enter Your Display Name 📑:");
                    String displayName = scanner.nextLine();
                    System.out.println("Please Enter Your Email 📩:");
                    String email = scanner.nextLine();
                    System.out.println("Please Enter Your Bio 📋:");
                    String bio = scanner.nextLine();
                    try {
                        userServices.userSignUp(username, password, displayName, email, bio);
                        System.out.println("Your Account has been created!✅");
                    }catch (TwitterExceptions e){
                        System.out.println("Signup failed: " + e.getMessage());
                    }
                    break;

                case "2":
                    System.out.println("* Login 🔰 *");
                    System.out.println("Please Enter Your Username or Email 📩: ");
                    String usernameOrEmail = scanner.nextLine();
                    System.out.println("Please Enter Your Password 🔑: ");
                    String LoggingPassword = scanner.nextLine();
                    try {
                        if (userServices.userLogin(usernameOrEmail, LoggingPassword)) {
                            userMenu(userServices, tweetServices,tagServices);
                        }
                    } catch (TwitterExceptions e) {
                        System.out.println("Login failed: " + e.getMessage());
                    }
            }
        }
    }


    private static void userMenu(UserServices userServices, TweetServices tweetServices,
                                 TagServices tagServices) throws SQLException {
        while (true){
        System.out.println("* User Menu 📋 *");
        System.out.println("1. Explore all Tweets 🌐");
        System.out.println("2. Explore Your Tweets 📨️️️");
        System.out.println("3. Post a Tweet 💌");
        System.out.println("4. Update a Tweet 🔄");
        System.out.println("5. Delete a Tweet ❌");
        System.out.println("6. Setting 💻");
        System.out.println("7. Logout 👋🏻");
        System.out.println("Choose Your Action: ");

        String option = scanner.nextLine();
//
//            try {
//                int num = Integer.parseInt(option);
//                System.out.println("You entered an integer: " + num);
//            } catch (TwitterExceptions s) {
//                System.out.println("You did not enter an integer." + s.getMessage());
//                s.printStackTrace();
//            }


            switch (option) {
                case "1":
                    while (true) {
                        System.out.println("* Explore Tweets! 🌐 *");
                        tweetServices.showAllTweets();
                        System.out.println("Choose your Action: \n1. Like💖 \n2. Dislike👎🏻 \n3. Retweet🔁 \n4. Back⏪");
                            String operationChoose = scanner.nextLine();

                        if (operationChoose.equalsIgnoreCase("1")) {
                            System.out.println("Enter Tweet ID to Like: ");
                            int tweetID = scanner.nextInt();
                            tweetServices.likeTweetById(tweetID);
                        }
                        if (operationChoose.equalsIgnoreCase("2")) {
                            System.out.println("Enter Tweet ID to Dislike: ");
                            int tweetID = scanner.nextInt();
                            tweetServices.dislikeTweetById(tweetID);
                        }
                        if (operationChoose.equalsIgnoreCase("3")) {
                            System.out.println("Enter Tweet ID to Retweet: ");
                            int tweetID = scanner.nextInt();
                            tweetServices.retweetTweetById(tweetID);
                        } else if (operationChoose.equalsIgnoreCase("4")) {
                            break;
                        }
                        break;
                    }

                case "2":
                    System.out.println("* Your Tweets 📨️ *");
                    tweetServices.showUserTweets();
                    break;

                case "3":
                    /*while (true) {
//                        System.out.println("* New Tweet 💌 *");
//                        System.out.println("Write your Tweet: ");
//                        String tweetText = scanner.nextLine();
//                        tweetServices.postTweet(tweetText);
//                        System.out.println("Your Tweet has been posted successfully!");
//                        System.out.println("Do you want to post another tweet (y/n)?");
//                        String postAnotherTweet = scanner.nextLine();
//                        if (postAnotherTweet.equalsIgnoreCase("n")) {
//                            break;
//                        }
                        System.out.println("* New Tweet 💌 *");
                        System.out.println("Write your Tweet: ");
                        String tweetText = scanner.nextLine();

                        System.out.println("Enter tags separated by commas (e.g., java, python, spring): ");
                        String tagsInput = scanner.nextLine();
                        List<String> tags = Arrays.asList(tagsInput.split(","));

                        tweetServices.postTweet(tweetText, tags);
                        System.out.println("Your Tweet has been posted successfully!");
                        System.out.println("Do you want to post another tweet (y/n)?");
                        String postAnotherTweet = scanner.nextLine();
                        if (postAnotherTweet.equalsIgnoreCase("n")) {
                            break;
                        }
                    }
                        break;*/
                    System.out.println("* New Tweet *");
                    System.out.println("Write your Tweet: ");
                    String tweetText = scanner.nextLine();
                    System.out.println("Enter tags separated by commas (e.g., java, python): ");
                    String tagsInput = scanner.nextLine();
                    List<String> tags = Arrays.asList(tagsInput.split(","));

                    tweetServices.postTweet(tweetText, tags);
                    System.out.println("Your Tweet has been posted successfully ✅");
                        break;

                case "4":
                    System.out.println("* Update a Tweet 🔄 *");
                    tweetServices.showUserTweets();
                    System.out.println("** Enter Tweet ID to Update: ");
                    int tweetIdToUpdate = scanner.nextInt();
                    System.out.println("** Enter Updated Tweet Text: ");
                    String updatedTweetText = scanner.next();
                    tweetServices.updateTweet(tweetIdToUpdate, updatedTweetText);
                    break;

                case "5":
                    System.out.println("* Delete a Tweet ❌ *");
                    tweetServices.showUserTweets();
                    System.out.println("Enter Your Tweet ID to Delete: ");
                    int tweetIdToDelete = scanner.nextInt();
                    tweetServices.showUserTweets();

                    tweetServices.deleteTweet(tweetIdToDelete);
                    break;

                case "6":
                    userSetting(userServices);
                    break;
                case "7":
                    userServices.logout();
                    return;
            }
        }
    }

    private static void userSetting(UserServices userServices) throws SQLException {
        while (true) {
            System.out.println("* Settings 💻 *");
            System.out.println("1. Change Username 📑");
            System.out.println("2. Change Password 🔑");
            System.out.println("3. Change Display Name 👨🏻‍💼");
            System.out.println("4. Change Bio 📃");
            System.out.println("5. Back ⏪");
            System.out.println("Choose Your Action: ");
            String option = scanner.nextLine();


            switch (option) {
                case "1":
                    System.out.println("* Change Username 📑 *");
                    System.out.println("Enter Your New Username: ");
                    String newUserName = scanner.nextLine();
                    userServices.updateUsername(newUserName);

                    break;

                case "2":
                    System.out.println("* Change Password 🔑 *");
                    System.out.println("Enter Your New Password: ");
                    String newPassword = scanner.nextLine();
                    userServices.updatePassword(newPassword);
                    break;

                case "3":
                    System.out.println("* Change Display Name 👨🏻‍💼 *");
                    System.out.println("Enter Your New Display Name: ");
                    String newDisplayName = scanner.nextLine();
                    userServices.updateDisplayName(newDisplayName);
                    break;

                case "4":
                    System.out.println("* Change Bio 📃 *");
                    System.out.println("Enter Your new Bio: ");
                    String newBio = scanner.nextLine();
                    userServices.updateBio(newBio);
                    break;

                case "5":
                    return;
            }
        }
    }
}