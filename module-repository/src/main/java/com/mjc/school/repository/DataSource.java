package com.mjc.school.repository;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;

import java.util.List;

public interface DataSource {
    List<AuthorModel> readAllAuthors();

    List<NewsModel> readAllNews();

    NewsModel readById(Long id);

    NewsModel createNews(NewsModel news);

    NewsModel updateNews(NewsModel news);

    Boolean deleteNewsById(Long id);
}
