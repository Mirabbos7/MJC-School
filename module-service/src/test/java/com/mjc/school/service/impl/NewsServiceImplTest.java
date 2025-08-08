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

class NewsServiceImplTest {

    private DataSource dataSource;
    private Validator validator;
    private NewsServiceImpl newsService;

    @BeforeEach
    void setUp() {
        dataSource = mock(DataSource.class);
        validator = mock(Validator.class);
        newsService = new NewsServiceImpl(dataSource, validator);
    }

    @Test
    void getAllNews_shouldReturnAllNewsAsDTOs() {
        NewsModel model = new NewsModel(1L, "Title", "Content", now(), now(), 1L);
        when(dataSource.readAllNews()).thenReturn(List.of(model));

        List<NewsDTO> result = newsService.getAllNews();

        assertEquals(1, result.size());
        assertEquals("Title", result.get(0).getTitle());
        verify(dataSource).readAllNews();
    }

    @Test
    void getNewsById_existing_shouldReturnDTO() {
        NewsModel model = new NewsModel(2L, "Some Title", "Some Content", now(), now(), 1L);
        when(dataSource.readById(2L)).thenReturn(model);

        NewsDTO result = newsService.getNewsById(2L);

        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("Some Title", result.getTitle());
        verify(dataSource).readById(2L);
    }

    @Test
    void getNewsById_notFound_shouldThrowException() {
        when(dataSource.readById(100L)).thenReturn(null);

        NullPointerException ex = assertThrows(NullPointerException.class, () -> newsService.getNewsById(100L));
        assertEquals("News with id 100 not found", ex.getMessage());
    }

    @Test
    void createNews_valid_shouldCreateAndReturnDTO() throws TitleLException, ContentLException, DataFormatException {
        NewsDTO dto = new NewsDTO("Test Title", "Test Content", 1L);
        when(dataSource.readAllNews()).thenReturn(List.of());
        when(dataSource.createNews(any())).thenAnswer(i -> i.getArguments()[0]);

        NewsDTO result = newsService.createNews(dto);

        assertNotNull(result);
        assertEquals("Test Title", result.getTitle());
        verify(validator).validate(dto);
        verify(dataSource).createNews(any(NewsModel.class));
    }

    @Test
    void createNews_invalid_shouldReturnNull() throws TitleLException, ContentLException, DataFormatException {
        NewsDTO dto = new NewsDTO("bad", "bad", 1L);
        doThrow(new TitleLException("Invalid title")).when(validator).validate(dto);

        NewsDTO result = newsService.createNews(dto);

        assertNull(result);
        verify(validator).validate(dto);
        verify(dataSource, never()).createNews(any());
    }

    @Test
    void updateNews_valid_shouldUpdateAndReturnDTO() throws TitleLException, ContentLException, DataFormatException {
        NewsDTO dto = new NewsDTO("Updated Title", "Updated Content", 1L);
        LocalDateTime oldDate = now().minusDays(1);
        NewsModel existing = new NewsModel(5L, "Old", "Old", oldDate, oldDate, 1L);

        when(dataSource.readById(5L)).thenReturn(existing);
        when(dataSource.updateNews(any())).thenAnswer(i -> i.getArguments()[0]);

        NewsDTO result = newsService.updateNews(5L, dto);

        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        verify(validator).validate(dto);
        verify(dataSource).updateNews(any());
    }

    @Test
    void updateNews_invalid_shouldReturnNull() throws TitleLException, ContentLException, DataFormatException {
        NewsDTO dto = new NewsDTO("Bad", "Bad", 1L);
        doThrow(new ContentLException("Invalid content")).when(validator).validate(dto);

        NewsDTO result = newsService.updateNews(5L, dto);

        assertNull(result);
        verify(validator).validate(dto);
        verify(dataSource, never()).updateNews(any());
    }

    @Test
    void deleteNews_shouldReturnTrue() {
        when(dataSource.deleteNewsById(10L)).thenReturn(true);

        Boolean result = newsService.deleteNews(10L);

        assertTrue(result);
        verify(dataSource).deleteNewsById(10L);
    }

    private LocalDateTime now() {
        return LocalDateTime.now();
    }
}
