package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {
    private int userId;
    private String username;
    private String password;
    private String displayName;
    private String email;
    private String bio;
    private LocalDate accountCreateDate;
}
