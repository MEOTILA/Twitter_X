package org.example.services;

import org.example.entity.User;
import org.example.repository.UserRepository;

import java.sql.SQLException;
import java.time.LocalDate;

public class UserServices {
    //Login
    //SignUp
    UserRepository userRepository;

    public UserServices() throws SQLException {
        userRepository = new UserRepository();
    }

    public User userSignUp(String username, String password, String displayName,
                           String email, String bio) throws SQLException {
        var checkingUser = userRepository.findByUsername(username);

        if (checkingUser != null) {
            System.out.println("Username is already taken ❗");
            return null;
        }
        User signingUpUser = new User();
        signingUpUser.setUsername(username);
        signingUpUser.setPassword(password);
        signingUpUser.setDisplayName(displayName);
        signingUpUser.setEmail(email);
        signingUpUser.setBio(bio);
        signingUpUser.setAccountCreateDate(LocalDate.now());
        return userRepository.save(signingUpUser);
    }

    public boolean userLogin(String usernameOrEmail, String password) throws SQLException {
        User user = userRepository.findByUsername(usernameOrEmail);
        if (user == null) {
            user = userRepository.findByEmail(usernameOrEmail);
        }

        if (user != null)
        {
            if (user.getPassword().equals(password)) {
                AuthenticationServices.setLoggedUser(user);
                System.out.println("Welcome Dear " + AuthenticationServices.getLoggedInUser().getUsername() + "😍");
                return true;
            }
        }
        System.out.println("Wrong username or password ❗");
        return false;
    }

    public User updateUsername(String updatedUsername) throws SQLException {
        User user = new User();
        user.setUsername(updatedUsername);
        System.out.println("Your Username has been updated!");
        return userRepository.updateUsername(user);
    }

    public User updatePassword(String updatedPassword) throws SQLException {
        User user = new User();
        user.setPassword(updatedPassword);
        System.out.println("Your Password has been updated!");
        return userRepository.updatePassword(user);
    }

    public User updateDisplayName(String updatedDisplayName) throws SQLException {
        User user = new User();
        user.setDisplayName(updatedDisplayName);
        System.out.println("Your Display Name has been updated!");
        return userRepository.updateDisplayName(user);
    }

    public User updateBio(String updatedBio) throws SQLException {
        User user = new User();
        user.setBio(updatedBio);
        System.out.println("Your Bio has been updated!");
        return userRepository.updateBio(user);
    }

    //    public void logout () {
//        AuthenticationServices.logout();
//        System.out.println("Good Bye Dear " +
//                AuthenticationServices.getLoggedInUser().getDisplayName() + "👋🏻");
//    }

    public void logout() {
        User user = AuthenticationServices.getLoggedInUser();
        if (user != null) {
            System.out.println("Good Bye Dear " + user.getDisplayName() + "👋🏻");
            AuthenticationServices.logout();
        } else {
            System.out.println("Error!");
        }
    }
}
