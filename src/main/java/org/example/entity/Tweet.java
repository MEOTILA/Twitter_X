package org.example.entity;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Tweet {
    private int tweetID;
    private int userID; //User replacement
    private String tweetText;
    private int likeCount;
    private int dislikeCount;
    private int retweetCount;
    private List<Tag> tags;

    public Tweet(int tweetID, int userID, String tweetText, int dislikeCount, int likeCount, int retweetCount) {
        this.tweetID = tweetID;
        this.userID = userID;
        this.tweetText = tweetText;
        this.dislikeCount = dislikeCount;
        this.likeCount = likeCount;
        this.retweetCount = retweetCount;
    }
}

