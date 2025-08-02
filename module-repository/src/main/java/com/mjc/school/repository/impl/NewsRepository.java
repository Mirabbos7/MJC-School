package com.mjc.school.repository.impl;

import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.source.DataSource;

import java.util.List;

public class NewsRepository {

    private final DataSource dataSource;

    private static final NewsRepository instance = new NewsRepository();
    private final List<NewsModel> newsList;

    public NewsRepository() {
        dataSource = DataSource.getInstance();
        this.newsList = dataSource.readNews();
    }

    public static NewsRepository getInstance() {
        return instance;
    }

    public List<NewsModel> readAll() {
        return this.newsList;
    }

    public NewsModel readBy(Long id) {
        for (NewsModel news : newsList) {
            if (news.getId() == id) {
                return news;
            }
        }
        return null;
    }

    public NewsModel create(NewsModel news) {
        newsList.add(news);
        return news;
    }

    public NewsModel update(NewsModel news) {
        NewsModel tmp = null;
        for (NewsModel inst : newsList) {
            if (inst.getId() == news.getId()) {
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
            if (newsList.get(i).getId() == id) {
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
