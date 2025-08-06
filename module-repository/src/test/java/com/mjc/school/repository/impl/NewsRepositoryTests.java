package com.mjc.school.repository.impl;

import com.mjc.school.repository.model.NewsModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NewsRepositoryTests {

    private NewsRepository newsRepository;

    @BeforeEach
    void setup() {
        newsRepository = NewsRepository.getInstance();
    }

    @Test
    void readAllTest() {
        List<NewsModel> newsModels = newsRepository.readAll();

        assertNotNull(newsModels);
        assertFalse(newsModels.isEmpty());
    }

    @Test
    void readById() {
        NewsModel news = NewsRepository.getInstance().readAll().get(0);
        NewsModel found = NewsRepository.getInstance().readBy(news.getId());

        assertNotNull(found);
        assertEquals(news.getId(), found.getId());
    }

    @Test
    void create_shouldAddNewsToList() {
        NewsRepository repo = new NewsRepository();
        int sizeBefore = repo.readAll().size();

        NewsModel newNews = new NewsModel(
                null, "Title", "Content", null, null, 1L
        );

        NewsModel created = repo.create(newNews);
        int sizeAfter = repo.readAll().size();

        assertEquals(sizeBefore + 1, sizeAfter);
        assertTrue(repo.readAll().contains(created));
    }


    @Test
    void update() {
        NewsModel newsModel = new NewsModel(10L, "title", "content", LocalDateTime.now(), LocalDateTime.now(), 1L);
        newsRepository.update(newsModel);
        assertTrue(newsRepository.readAll().contains(newsModel));
    }

    @Test
    void delete() {
        int sizeBefore = NewsRepository.getInstance().readAll().size();

        NewsRepository.getInstance().delete(1L);

        int sizeAfter = NewsRepository.getInstance().readAll().size();

        assertEquals(sizeBefore, sizeAfter + 1);
    }

    @Test
    void ifIdExistReturnTrue() {
        NewsModel newsModel = NewsRepository.getInstance().readAll().get(0);
        assertTrue(newsRepository.ifIdExist(newsModel.getId()));
    }

    @Test
    void ifIdExistReturnFalse() {

        assertFalse(newsRepository.ifIdExist(9999999L));

    }
}