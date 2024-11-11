package org.example.services;

import org.example.entity.User;

public class AuthenticationServices {
    private static User loggedInUser;

    public static void setLoggedUser(User user) {
        loggedInUser = user;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void logout() {
        loggedInUser = null;
    }

    public static boolean isLoggedIn() {
        return loggedInUser != null;
    }
}
