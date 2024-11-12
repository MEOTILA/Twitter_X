package org.example.repository;

import org.example.Datasource;

import java.sql.SQLException;

public class TagRepository {
    private static final String CREATE_TABLE_TAGS = """
            CREATE TABLE IF NOT EXISTS tags (
            tag_id SERIAL PRIMARY KEY,
            tag_name varchar(25) NOT NULL,
            tweet_id INTEGER,
            FOREIGN KEY (tweet_id)
            REFERENCES tweets(tweet_id));
            """;

    public TagRepository() throws SQLException {
        // Initialize table on creation
        initTable();
    }

    public void initTable() throws SQLException {
        var statement = Datasource.getConnection().prepareStatement(TagRepository.CREATE_TABLE_TAGS);
        statement.execute();
        statement.close();
    }
}
