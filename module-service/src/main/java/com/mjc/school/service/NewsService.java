package com.mjc.school.service;

import com.mjc.school.service.dto.NewsDTO;

import java.util.List;

public interface NewsService {

    List<NewsDTO> getAllNews();

    NewsDTO getNewsById(Long id);

    NewsDTO createNews(NewsDTO news);

    NewsDTO updateNews(Long id, NewsDTO newsDto);

    Boolean deleteNews(Long id);
}
