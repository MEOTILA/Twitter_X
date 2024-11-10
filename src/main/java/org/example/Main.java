package org.example;

import org.example.entity.User;
import org.example.repository.TagRepository;
import org.example.repository.TweetRepository;
import org.example.repository.UserRepository;
import org.example.services.UserServices;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {

//        UserRepository userRepository = new UserRepository();
//        TweetRepository tweetRepository = new TweetRepository();
//        TagRepository tagRepository = new TagRepository();
        UserServices userServices = new UserServices();


        System.out.println("Welcome to Twitter!");
        while (true) {
            System.out.println("1. Signup 🟢");
            System.out.println("2. Login 🔵");
            System.out.println("Choose You Action: ");
            String option = scanner.next();

            switch (option) {
                case "1":
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
                    System.out.println("Please Enter Your Username or Email 📩: ");
                    String usernameOrEmail = scanner.next();
                    System.out.println("Please Enter Your Password 🔑: ");
                    String LoggingPassword = scanner.next();
                    if(userServices.userLogin(usernameOrEmail, LoggingPassword)) {
                        userMenu();
                    }
                    break;
            }
        }
    }

    private static void userMenu() {
        System.out.println("Welcome Dear User😍");
    }
}







//        String username = "Sattar";
//        String password = "123";
//        String displayName = "MEOTILA";
//        String email = "sattar@gmail.com";
//        String bio = "Hello! I'm Sattar!";
//
//        userServices.userSignUp(username, password, displayName, email, bio);
//        userServices.userLogin("Sattar",
//                "$2a$06$ufppY88TDjte4T3REHjPGe5fzr1PosiPop8gEBpmSLnxX3N7FQgke");
//    }
