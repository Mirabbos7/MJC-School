package com.mjc.school.controller;

import com.mjc.school.service.dto.NewsDTO;

import java.util.List;

public interface NewsWeb {
    List<NewsDTO> getAllNews();

    NewsDTO getNewsById(Long id);

    NewsDTO createNews(NewsDTO newsDto);

    NewsDTO updateNews(Long id, NewsDTO newsDto);

    void deleteNews(Long id);

}
