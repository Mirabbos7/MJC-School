package com.mjc.school.service.impl;

import com.mjc.school.repository.DataSource;
import com.mjc.school.repository.dataSource.NewsData;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.NewsMapper;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.NewsDTO;
import com.mjc.school.service.exceptions.ContentLException;
import com.mjc.school.service.exceptions.DataFormatException;
import com.mjc.school.service.exceptions.TitleLException;
import com.mjc.school.service.validators.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NewsServiceImpl implements NewsService {
    private static final Logger logger = LoggerFactory.getLogger(NewsServiceImpl.class);
    private final DataSource dataSource;
    private final Validator validator;

    public NewsServiceImpl(DataSource dataSource, Validator validator) {
        this.dataSource = dataSource;
        this.validator = validator;
    }

    @Override
    public List<NewsDTO> getAllNews() {
        return dataSource.readAllNews()
                .stream()
                .map(NewsMapper.INSTANCE::newsToNewsDTO)
                .toList();
    }

    @Override
    public NewsDTO getNewsById(Long id) {
        NewsModel news = dataSource.readById(id);

        if (news == null) {
            throw new NullPointerException("News with id " + id + " not found");
        }
        return NewsMapper.INSTANCE.newsToNewsDTO(news);
    }

    @Override
    public NewsDTO createNews(NewsDTO news) {
        LocalDateTime date=LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

        try {
            validator.validate(news);
            return NewsMapper.INSTANCE.newsToNewsDTO(dataSource.createNews(
                    new NewsModel(
                    getId(dataSource),
                    news.getTitle(),
                    news.getContent(),
                    date,
                    date,
                    news.getAuthorId()
            )));
        } catch(TitleLException | ContentLException | DataFormatException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public NewsDTO updateNews(Long id, NewsDTO newsDto) {
        LocalDateTime date=LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

        try {
            validator.validate(newsDto);
            return NewsMapper.INSTANCE.newsToNewsDTO(dataSource.updateNews(
                    new NewsModel(
                            id,
                            newsDto.getTitle(),
                            newsDto.getContent(),
                            dataSource.readById(id).getCreateDate(),
                            date,
                            newsDto.getAuthorId()
                    )
            ));
        } catch(TitleLException | ContentLException | DataFormatException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Boolean deleteNews(Long id) {
        return dataSource.deleteNewsById(id); //check
    }
    private Long getId(DataSource dataSource) {
        return (long) (dataSource.readAllNews().size() + 1);
    }
}
