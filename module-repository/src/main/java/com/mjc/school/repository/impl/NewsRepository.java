package com.mjc.school.repository.impl;

import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.source.DataSource;

import java.time.LocalDateTime;
import java.util.List;

public class NewsRepository {

    private final DataSource dataSource;
    private final List<NewsModel> newsList;
    private long nextId;

    private static final NewsRepository instance = new NewsRepository();

    public NewsRepository() {
        dataSource = DataSource.getInstance();
        this.newsList = dataSource.readNews();
        nextId = 1;
        for (NewsModel news : newsList) {
            if (news.getId() >= nextId) {
                nextId = news.getId() + 1;
            }
        }
    }

    public static NewsRepository getInstance() {
        return instance;
    }

    public List<NewsModel> readAll() {
        return this.newsList;
    }

    public NewsModel readBy(Long id) {
        for (NewsModel news : newsList) {
            if (news.getId().equals(id)) {
                return news;
            }
        }
        return null;
    }

    public NewsModel create(NewsModel news) {
        NewsModel newNews = new NewsModel(
                nextId++,
                news.getTitle(),
                news.getContent(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                news.getAuthorId()
        );
        newsList.add(newNews);
        return newNews;
    }

    public NewsModel update(NewsModel news) {
        NewsModel tmp = null;
        for (NewsModel inst : newsList) {
            if (inst.getId().equals(news.getId())) {
                tmp = inst;
            }
        }
        if (tmp != null) {
            tmp.setAuthorId(news.getAuthorId());
            tmp.setTitle(news.getTitle());
            tmp.setContent(news.getContent());
            tmp.setLastUpdateDate(news.getLastUpdateDate());
            return tmp;
        } else throw new NullPointerException("News not found with id: " + news.getId());
    }

    public Boolean delete(Long id) {
        for (int i = 0; i < newsList.size(); i++) {
            if (newsList.get(i).getId().equals(id)) {
                newsList.remove(i);
                return true;
            }
        }
        return false;
    }

    public Boolean ifIdExist(long id) {
        for (NewsModel news : newsList) {
            if (news.getId() == id) {
                return true;
            }
        }
        return false;
    }
}