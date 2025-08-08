package com.mjc.school.controller;

import com.mjc.school.service.dto.NewsDTO;
import com.mjc.school.service.NewsService;

import java.util.List;

public class Controller implements NewsWeb {
    private final NewsService newsService;

    public Controller(NewsService newsService) {
        this.newsService = newsService;
    }

    @Override
    public List<NewsDTO> getAllNews() {
        return newsService.getAllNews();
    }

    @Override
    public NewsDTO getNewsById(Long id) {
        return newsService.getNewsById(id);
    }

    @Override
    public NewsDTO createNews(NewsDTO newsDto) {
        return newsService.createNews(newsDto);
    }

    @Override
    public NewsDTO updateNews(Long id, NewsDTO newsDto) {
        return newsService.updateNews(id, newsDto);
    }

    @Override
    public void deleteNews(Long id) {
        newsService.deleteNews(id);
    }
}
