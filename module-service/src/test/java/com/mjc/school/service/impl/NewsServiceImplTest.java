package com.mjc.school.service.impl;

import com.mjc.school.repository.DataSource;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.NewsDTO;
import com.mjc.school.service.exceptions.ContentLException;
import com.mjc.school.service.exceptions.DataFormatException;
import com.mjc.school.service.exceptions.TitleLException;
import com.mjc.school.service.validators.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NewsServiceImplTest {

    private DataSource dataSource;
    private Validator validator;
    private NewsServiceImpl newsService;

    private final LocalDateTime now = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        dataSource = mock(DataSource.class);
        validator = mock(Validator.class);
        newsService = new NewsServiceImpl(dataSource, validator);
    }

    // --- getAllNews()

    @Test
    void testGetAllNews_returnsCorrectSize() {
        when(dataSource.readAllNews()).thenReturn(List.of(
                new NewsModel(1L, "T", "C", now, now, 1L)
        ));

        int resultSize = newsService.getAllNews().size();

        assertEquals(1, resultSize);
    }

    @Test
    void testGetAllNews_returnsCorrectTitle() {
        when(dataSource.readAllNews()).thenReturn(List.of(
                new NewsModel(1L, "Expected Title", "C", now, now, 1L)
        ));

        String title = newsService.getAllNews().get(0).getTitle();

        assertEquals("Expected Title", title);
    }

    // --- getNewsById()

    @Test
    void testGetNewsById_returnsCorrectTitle() {
        when(dataSource.readById(1L)).thenReturn(
                new NewsModel(1L, "Single", "C", now, now, 1L)
        );

        String title = newsService.getNewsById(1L).getTitle();

        assertEquals("Single", title);
    }

    @Test
    void testGetNewsById_throwsIfNotFound() {
        when(dataSource.readById(10L)).thenReturn(null);

        Exception ex = assertThrows(NullPointerException.class, () -> newsService.getNewsById(10L));

        assertEquals("News with id 10 not found", ex.getMessage());
    }

    // --- createNews()

    @Test
    void testCreateNews_success_returnsNotNull() throws Exception {
        NewsDTO dto = new NewsDTO("T", "C", 1L);
        NewsModel model = new NewsModel(1L, "T", "C", now, now, 1L);

        when(dataSource.readAllNews()).thenReturn(List.of());
        when(dataSource.createNews(any())).thenReturn(model);

        NewsDTO result = newsService.createNews(dto);

        assertNotNull(result);
    }

    @Test
    void testCreateNews_success_returnsCorrectTitle() throws Exception {
        NewsDTO dto = new NewsDTO("T", "C", 1L);
        NewsModel model = new NewsModel(1L, "T", "C", now, now, 1L);

        when(dataSource.readAllNews()).thenReturn(List.of());
        when(dataSource.createNews(any())).thenReturn(model);

        String title = newsService.createNews(dto).getTitle();

        assertEquals("T", title);
    }

    @Test
    void testCreateNews_validationFails_returnsNull() throws Exception {
        NewsDTO dto = new NewsDTO("Invalid", "C", 1L);
        doThrow(new TitleLException("Invalid title")).when(validator).validate(dto);

        NewsDTO result = newsService.createNews(dto);

        assertNull(result);
    }

    @Test
    void testCreateNews_throwsContentLException_returnsNull() throws Exception {
        NewsDTO dto = new NewsDTO("T", "BadContent", 1L);
        doThrow(new ContentLException("Invalid content")).when(validator).validate(dto);

        NewsDTO result = newsService.createNews(dto);

        assertNull(result);
    }

    @Test
    void testCreateNews_throwsDataFormatException_returnsNull() throws Exception {
        NewsDTO dto = new NewsDTO("T", "C", 1L);
        doThrow(new DataFormatException("Invalid format")).when(validator).validate(dto);

        NewsDTO result = newsService.createNews(dto);

        assertNull(result);
    }

    // --- updateNews()

    @Test
    void testUpdateNews_success_returnsNotNull() throws Exception {
        NewsDTO dto = new NewsDTO("U", "Updated", 1L);
        NewsModel oldModel = new NewsModel(1L, "Old", "Old", now.minusDays(1), now.minusDays(1), 1L);

        when(dataSource.readById(1L)).thenReturn(oldModel);
        when(dataSource.updateNews(any())).thenReturn(
                new NewsModel(1L, "U", "Updated", oldModel.getCreateDate(), now, 1L)
        );

        NewsDTO result = newsService.updateNews(1L, dto);

        assertNotNull(result);
    }

    @Test
    void testUpdateNews_success_returnsCorrectTitle() throws Exception {
        NewsDTO dto = new NewsDTO("NewTitle", "Updated", 1L);
        NewsModel oldModel = new NewsModel(1L, "Old", "Old", now.minusDays(1), now.minusDays(1), 1L);

        when(dataSource.readById(1L)).thenReturn(oldModel);
        when(dataSource.updateNews(any())).thenReturn(
                new NewsModel(1L, "NewTitle", "Updated", oldModel.getCreateDate(), now, 1L)
        );

        String title = newsService.updateNews(1L, dto).getTitle();

        assertEquals("NewTitle", title);
    }

    @Test
    void testUpdateNews_validationFails_returnsNull() throws Exception {
        NewsDTO dto = new NewsDTO("Bad", "Bad", 1L);
        doThrow(new ContentLException("Error")).when(validator).validate(dto);

        NewsDTO result = newsService.updateNews(1L, dto);

        assertNull(result);
    }

    @Test
    void testUpdateNews_throwsTitleLException_returnsNull() throws Exception {
        NewsDTO dto = new NewsDTO("BadTitle", "Good", 1L);
        doThrow(new TitleLException("Bad")).when(validator).validate(dto);

        NewsDTO result = newsService.updateNews(1L, dto);

        assertNull(result);
    }

    @Test
    void testUpdateNews_throwsDataFormatException_returnsNull() throws Exception {
        NewsDTO dto = new NewsDTO("Good", "Good", 1L);
        doThrow(new DataFormatException("Format")).when(validator).validate(dto);

        NewsDTO result = newsService.updateNews(1L, dto);

        assertNull(result);
    }

    // --- deleteNews()

    @Test
    void testDeleteNews_returnsTrue() {
        when(dataSource.deleteNewsById(1L)).thenReturn(true);

        boolean result = newsService.deleteNews(1L);

        assertTrue(result);
    }

    @Test
    void testDeleteNews_returnsFalse() {
        when(dataSource.deleteNewsById(99L)).thenReturn(false);

        boolean result = newsService.deleteNews(99L);

        assertFalse(result);
    }
}
