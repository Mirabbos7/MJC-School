package com.mjc.school.service.impl;

import com.mjc.school.repository.DataSource;
import com.mjc.school.service.dto.NewsDTO;
import com.mjc.school.service.exceptions.ContentLException;
import com.mjc.school.service.exceptions.DataFormatException;
import com.mjc.school.service.exceptions.TitleLException;
import com.mjc.school.service.validators.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

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


    @Test
    void testGetNewsById_throwsIfNotFound() {
        when(dataSource.readById(10L)).thenReturn(null);

        Exception ex = assertThrows(NullPointerException.class, () -> newsService.getNewsById(10L));

        assertEquals("News with id 10 not found", ex.getMessage());
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
