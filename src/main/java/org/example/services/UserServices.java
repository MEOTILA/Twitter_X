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
        var checkingUserByUsername = userRepository.findByUsername(usernameOrEmail);
        //var checkingUserByEmail = userRepository.findByEmail(usernameOrEmail);

        if (checkingUserByUsername != null) {
            if (checkingUserByUsername.getPassword().equals(password)) {
                AuthenticationServices.setLoggedUser(checkingUserByUsername);
                return true;
            }
        }
        System.out.println("Wrong username or password ❗");
        return false;
    }
}
