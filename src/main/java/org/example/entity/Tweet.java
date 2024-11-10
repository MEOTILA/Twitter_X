package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Tweet {
    private int tweetID;
    private String tweetText;
    private int likeCount;
    private int dislikeCount;
    private int retweetCount;
}
