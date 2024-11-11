package org.example.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Tweet {
    private int tweetID;
    private int userID;
    private String tweetText;
    private int likeCount;
    private int dislikeCount;
    private int retweetCount;
}
