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
    void readAll_returnsNonEmptyList() {
        List<NewsModel> newsModels = newsRepository.readAll();
        assertNotNull(newsModels);
        assertFalse(newsModels.isEmpty());
    }

    @Test
    void readBy_existingId_returnsNews() {
        NewsModel existing = newsRepository.readAll().get(0);
        NewsModel found = newsRepository.readBy(existing.getId());
        assertNotNull(found);
        assertEquals(existing.getId(), found.getId());
    }

    @Test
    void readBy_nonExistingId_returnsNull() {
        assertNull(newsRepository.readBy(-999L));
    }

    @Test
    void create_addsNewsToList() {
        int sizeBefore = newsRepository.readAll().size();
        NewsModel newNews = new NewsModel(null, "Title", "Content", null, null, 1L);

        NewsModel created = newsRepository.create(newNews);

        assertNotNull(created.getId());
        assertEquals(sizeBefore + 1, newsRepository.readAll().size());
    }

    @Test
    void update_existingNews_updatesIt() {
        NewsModel existing = newsRepository.readAll().get(0);
        existing.setTitle("Updated Title");

        NewsModel updated = newsRepository.update(existing);

        assertEquals("Updated Title", updated.getTitle());
    }

    @Test
    void update_nonExistingNews_throwsException() {
        NewsModel nonExisting = new NewsModel(999L, "Title", "Content", LocalDateTime.now(), LocalDateTime.now(), 1L);
        assertThrows(NullPointerException.class, () -> newsRepository.update(nonExisting));
    }

    @Test
    void delete_existingNews_removesIt() {
        NewsModel first = newsRepository.readAll().get(0);
        int sizeBefore = newsRepository.readAll().size();

        boolean deleted = newsRepository.delete(first.getId());

        assertTrue(deleted);
        assertEquals(sizeBefore - 1, newsRepository.readAll().size());
    }

    @Test
    void ifIdExist_returnsTrueForExistingId() {
        NewsModel existing = newsRepository.readAll().get(0);
        assertTrue(newsRepository.ifIdExist(existing.getId()));
    }

    @Test
    void ifIdExist_returnsFalseForNonExistingId() {
        assertFalse(newsRepository.ifIdExist(9999999L));
    }
}
