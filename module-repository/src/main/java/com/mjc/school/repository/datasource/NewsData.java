package com.mjc.school.repository.datasource;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NewsData {
    private static final Logger logger = LoggerFactory.getLogger(NewsData.class);
    private List<String> newsList;
    private List<String> contentList;
    private List<NewsModel> news;
    private AuthorData authorDataSource;

    public NewsData(AuthorData authorDataSource) {
        newsList = new ArrayList<>();
        contentList = new ArrayList<>();
        news = new ArrayList<>();
        this.authorDataSource = authorDataSource;
        loadNews();
        loadContent();
        newsCreateList();
    }
    private Long getAuthorsId(int i) {
        List<AuthorModel> authors = authorDataSource.getAuthors();
        if (i < 0 || i >= authors.size()) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        return authors.get(authors.size() - 1 - i).getId();
    }
    public List<NewsModel> getNews() {
        return news;
    }
    private void newsCreateList() {
        for (int i = 0; i < newsList.size(); i++) {
            news.add(new NewsModel(
                    (long) i,
                    newsList.get(i),
                    contentList.get(i),
                    getCurrentDateTimeInISOFormat(),
                    getCurrentDateTimeInISOFormat(),
                    getAuthorsId(i)
            ));
        }
    }

    public List<String> getNewsList() {
        return newsList;
    }
    public List<String> getContentList() {
        return contentList;
    }

    private void loadNews() {
        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream("news.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                newsList.add(line);
            }
        } catch (IOException e) {
            logger.error("NewsDataSource (news) error" + e.getMessage());
        }
    }
    private void loadContent() {
        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream("content.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentList.add(line);
            }
        } catch (IOException e) {
            logger.error("NewsDataSource (content) error" + e.getMessage());
        }
    }
    private LocalDateTime getCurrentDateTimeInISOFormat() {
        return LocalDateTime.now();
    }
}
