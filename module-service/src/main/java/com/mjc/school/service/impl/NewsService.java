package com.mjc.school.service.impl;

import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import com.mjc.school.service.mapper.NewsMapper;
import com.mjc.school.repository.impl.NewsRepository;
import com.mjc.school.service.validator.Validator;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class NewsService {

    private NewsRepository newsRepository = NewsRepository.getInstance();

    private Validator newValidator = new Validator();

    public NewsService(NewsRepository newsRepository){
        this.newsRepository = newsRepository;
    }

    public NewsService() {
    }

    public List<NewsResponseDto> readAll() {
        return NewsMapper.INSTANCE.newsListToDtoList(newsRepository.readAll());
    }

    public NewsResponseDto readById(Long id) {
        Validator.validateNewsId(id);
        Validator.validateNewsId(id);
        return NewsMapper.INSTANCE.newsToDto(newsRepository.readBy(id));
    }

    public NewsResponseDto create(NewsRequestDto news) {
        Validator.validateDtoRequest(news);
        NewsModel model = NewsMapper.INSTANCE.dtoToNews(news);
        model.setCreateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        model.setLastUpdateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return NewsMapper.INSTANCE.newsToDto(newsRepository.create(model));
    }

    public NewsResponseDto update(NewsRequestDto news) {
        Validator.validateDtoRequest(news);
        NewsModel model = NewsMapper.INSTANCE.dtoToNews(news);
        model.setLastUpdateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return NewsMapper.INSTANCE.newsToDto(newsRepository.update(model));
    }

    public Boolean delete(Long id) {
        validateNewsExistence(id);
        return newsRepository.delete(id);
    }

    private void validateNewsExistence(long id) {
        if (!newsRepository.ifIdExist(id)) {
            throw new RuntimeException("News not exists with id: " + id);
        }
    }
}