package com.mjc.school.repository.dataSource;

import com.mjc.school.repository.model.AuthorModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AuthorData {
    private static final Logger logger = LoggerFactory.getLogger(AuthorData.class);
    private List<String> authorList;
    private List<AuthorModel> authors;
    public AuthorData() {
        authorList = new ArrayList<>();
        authors = new ArrayList<>();
        loadAuthors();
        authorsCreateList();
    }
    public List<AuthorModel> getAuthors() {
        return authors;
    }
    public List<String> getAuthorList() {
        return authorList;
    }
    private void loadAuthors() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("author.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                authorList.add(line);
            }
        } catch (IOException e) {
            logger.error("AuthorDataSource read error" + e.getMessage());
        }
    }
    private void authorsCreateList() {
        long count = 0;
        for (String author : authorList) {
            authors.add(new AuthorModel(count++, author));
        }
    }
}
