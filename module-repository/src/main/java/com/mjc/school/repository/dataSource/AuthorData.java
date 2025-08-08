package com.mjc.school.repository.dataSource;

import com.mjc.school.repository.model.AuthorModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AuthorData {
    private List<String> authorList;
    private List<AuthorModel> authors;
    public AuthorData() {
        authorList = new ArrayList<String>();
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
            System.err.println("AuthorDataSource read error" + e.getMessage());
        }
    }
    private void authorsCreateList() {
        long count = 0;
        for (String author : authorList) {
            authors.add(new AuthorModel(count++, author));
        }
    }
}
