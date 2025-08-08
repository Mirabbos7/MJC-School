package com.mjc.school.repository.impl;

import com.mjc.school.repository.DataSource;
import com.mjc.school.repository.datasource.AuthorData;
import com.mjc.school.repository.datasource.NewsData;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;

import java.util.List;
import java.util.Optional;

public class DataSourceImpl implements DataSource {
    private final NewsData newsDataSource;
    private final AuthorData authorDataSource;

    public DataSourceImpl(AuthorData authorDataSource, NewsData newsDataSource) {
        this.authorDataSource = authorDataSource;
        this.newsDataSource = newsDataSource;
    }

    @Override
    public List<AuthorModel> readAllAuthors() {
        return authorDataSource.getAuthors();
    }

    @Override
    public List<NewsModel> readAllNews() {
        return newsDataSource.getNews();
    }

    @Override
    public NewsModel readById(Long id) {
        return newsDataSource.getNews().stream()
                .filter(news -> news.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public NewsModel createNews(NewsModel news) {
        news.setId(generateNewIdForNews());
        newsDataSource.getNews().add(news);
        return news;
    }

    private long generateNewIdForNews() {
        return newsDataSource.getNews().stream()
                .mapToLong(NewsModel::getId)
                .max()
                .orElse(0L) + 1;
    }

    @Override
    public NewsModel updateNews(NewsModel news) {
        Optional<NewsModel> existingNews = newsDataSource.getNews().stream()
                .filter(news1 -> news1.getId().equals(news.getId()))
                .findFirst();

        existingNews.ifPresent(news1 -> {
            news1.setTitle(news.getTitle());
            news1.setContent(news.getContent());
            news1.setLastUpdateDate(news.getLastUpdateDate());
            news1.setAuthorId(news.getAuthorId());
        });
        return existingNews.orElse(null);
    }

    @Override
    public Boolean deleteNewsById(Long id) {
        return newsDataSource.getNews().removeIf(news -> news.getId().equals(id));
    }
}
