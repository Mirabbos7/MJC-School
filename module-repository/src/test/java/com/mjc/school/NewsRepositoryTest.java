package com.mjc.school;

import com.mjc.school.repository.impl.NewsRepository;
import com.mjc.school.repository.model.NewsModel;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NewsRepositoryTest {

    private NewsRepository newsRepository;

    @BeforeEach
    void setUp() {
        newsRepository = NewsRepository.getInstance();
    }

    @Test
    void testSingletonInstance() {
        NewsRepository anotherInstance = NewsRepository.getInstance();
        assertSame(newsRepository, anotherInstance, "getInstance() should always return the same instance");
    }

    @Test
    void testReadAll() {
        List<NewsModel> newsList = newsRepository.readAll();
        assertNotNull(newsList, "readAll() should not return null");
        assertFalse(newsList.isEmpty(), "readAll() should return a pre-populated list from DataSource");
    }

    @Test
    void testCreateAndReadById() {
        NewsModel newNews = new NewsModel(
                "JUnit Title",
                "JUnit Content",
                LocalDateTime.now(),
                LocalDateTime.now(),
                1L
        );

        NewsModel created = newsRepository.create(newNews);
        assertNotNull(created, "create() should return the created object");

        NewsModel fetched = newsRepository.readBy(created.getId());
        assertEquals(created.getId(), fetched.getId(), "readBy() should fetch the created news");
        assertEquals("JUnit Title", fetched.getTitle());
    }

    @Test
    void testUpdate() {
        // Create a news first
        NewsModel newNews = new NewsModel(
                "Old Title",
                "Old Content",
                LocalDateTime.now(),
                LocalDateTime.now(),
                1L
        );
        newsRepository.create(newNews);

        // Update fields
        newNews.setTitle("Updated Title");
        newNews.setContent("Updated Content");
        newNews.setLastUpdateDate(LocalDateTime.now());

        NewsModel updated = newsRepository.update(newNews);

        assertNotNull(updated, "update() should return the updated object");
        assertEquals("Updated Title", updated.getTitle());
        assertEquals("Updated Content", updated.getContent());
    }

    @Test
    void testDelete() {
        // Create and delete
        NewsModel toDelete = new NewsModel(
                "Delete Me",
                "Content",
                LocalDateTime.now(),
                LocalDateTime.now(),
                1L
        );
        newsRepository.create(toDelete);

        boolean deleted = newsRepository.delete(toDelete.getId());
        assertTrue(deleted, "delete() should return true if deletion succeeds");

        assertNull(newsRepository.readBy(toDelete.getId()), "Deleted news should not be found anymore");
    }

    @Test
    void testIfIdExist() {
        List<NewsModel> newsList = newsRepository.readAll();
        long existingId = newsList.get(0).getId();

        assertTrue(newsRepository.ifIdExist(existingId), "Existing ID should return true");
        assertFalse(newsRepository.ifIdExist(Long.MAX_VALUE), "Non-existing ID should return false");
    }
}
