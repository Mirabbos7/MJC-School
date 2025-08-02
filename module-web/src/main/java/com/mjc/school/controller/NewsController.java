package com.mjc.school.controller;


import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import com.mjc.school.service.impl.NewsService;

import java.util.List;

public class NewsController {

    private final NewsService newsService = new NewsService();

    public NewsController() {
    }

    public List<NewsResponseDto> readAll() {
        return newsService.readAll();
    }

    public NewsResponseDto readById(long id) {
        return newsService.readById(id);
    }

    public NewsResponseDto create(NewsRequestDto dtoRequest) {
        return newsService.create(dtoRequest);
    }

    public NewsResponseDto update(NewsRequestDto dtoRequest) {
        return newsService.update(dtoRequest);
    }

    public boolean delete(long id) {
        return newsService.delete(id);
    }
}