package org.example.services;


import org.example.entity.Tag;
import org.example.exception.TwitterExceptions;
import org.example.repository.TagRepository;

import java.sql.SQLException;

public class TagServices {
    TagRepository tagRepository;

    public TagServices() throws SQLException, SQLException {
        tagRepository = new TagRepository();
    }

    public void saveTag(String tagName, int tweetId) throws SQLException {
        try {
            tagRepository.saveTag(tagName, tweetId);

        }catch (SQLException e){
            throw new SQLException (e.getMessage());
        }
        catch (TwitterExceptions b){
            throw new TwitterExceptions("Error saving tag!" + b.getMessage());
        }
    }
}
