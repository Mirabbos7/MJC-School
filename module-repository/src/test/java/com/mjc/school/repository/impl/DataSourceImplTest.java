package com.mjc.school.repository.impl;

import com.mjc.school.repository.dataSource.AuthorData;
import com.mjc.school.repository.dataSource.NewsData;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DataSourceImplTest {

    private AuthorData authorData;
    private NewsData newsData;
    private DataSourceImpl dataSource;

    private final List<AuthorModel> mockAuthors = new ArrayList<>();
    private final List<NewsModel> mockNews = new ArrayList<>();

    @BeforeEach
    void setUp() {
        authorData = mock(AuthorData.class);
        newsData = mock(NewsData.class);

        when(authorData.getAuthors()).thenReturn(mockAuthors);
        when(newsData.getNews()).thenReturn(mockNews);

        dataSource = new DataSourceImpl(authorData, newsData);
    }

    @Test
    void testReadAllAuthors() {
        mockAuthors.add(new AuthorModel(1L, "Test Author"));
        List<AuthorModel> result = dataSource.readAllAuthors();

        assertEquals(1, result.size());
        assertEquals("Test Author", result.get(0).getName());
    }

    @Test
    void testReadAllNews() {
        mockNews.add(new NewsModel(1L, "Title", "Content", now(), now(), 1L));
        List<NewsModel> result = dataSource.readAllNews();

        assertEquals(1, result.size());
        assertEquals("Title", result.get(0).getTitle());
    }

    @Test
    void testReadByIdFound() {
        mockNews.add(new NewsModel(1L, "Found", "Content", now(), now(), 1L));
        NewsModel result = dataSource.readById(1L);

        assertNotNull(result);
        assertEquals("Found", result.getTitle());
    }

    @Test
    void testReadByIdNotFound() {
        NewsModel result = dataSource.readById(999L);
        assertNull(result);
    }

    @Test
    void testCreateNews() {
        NewsModel newNews = new NewsModel(null, "Created", "Some content", now(), now(), 1L);
        mockNews.add(new NewsModel(1L, "Existing", "Old content", now(), now(), 1L));

        NewsModel result = dataSource.createNews(newNews);

        assertNotNull(result.getId());
        assertEquals(2L, result.getId()); // ID should be max + 1
        assertTrue(mockNews.contains(result));
    }

    @Test
    void testUpdateNewsFound() {
        LocalDateTime createDate = now();
        NewsModel existing = new NewsModel(1L, "Old", "Old content", createDate, now(), 1L);
        mockNews.add(existing);

        NewsModel updated = new NewsModel(1L, "New Title", "New Content", createDate, now().plusMinutes(5), 2L);
        NewsModel result = dataSource.updateNews(updated);

        assertNotNull(result);
        assertEquals("New Title", existing.getTitle());
        assertEquals("New Content", existing.getContent());
        assertEquals(2L, existing.getAuthorId());
    }

    @Test
    void testUpdateNewsNotFound() {
        NewsModel updated = new NewsModel(999L, "Title", "Content", now(), now(), 1L);
        NewsModel result = dataSource.updateNews(updated);

        assertNull(result);
    }

    @Test
    void testDeleteNewsByIdFound() {
        NewsModel news = new NewsModel(1L, "Title", "Content", now(), now(), 1L);
        mockNews.add(news);

        boolean deleted = dataSource.deleteNewsById(1L);

        assertTrue(deleted);
        assertFalse(mockNews.contains(news));
    }

    @Test
    void testDeleteNewsByIdNotFound() {
        boolean deleted = dataSource.deleteNewsById(999L);

        assertFalse(deleted);
    }

    private LocalDateTime now() {
        return LocalDateTime.now();
    }
}
