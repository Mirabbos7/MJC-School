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
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NewsServiceImplTest {

    @Mock
    private DataSource dataSource;

    @Spy
    private Validator validator = Validator.getInstance();

    @InjectMocks
    private NewsServiceImpl service;

    private NewsModel model;
    private NewsDTO dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        model = new NewsModel(1L, "Test Title", "Test Content", LocalDateTime.now(), LocalDateTime.now(), 1L);
        dto = new NewsDTO("Test Title", "Test Content", 1L);
        dto.setId(1L);
        dto.setCreateDate(model.getCreateDate());
        dto.setLastUpdateDate(model.getLastUpdateDate());
    }

    @Test
    void getNewsById_notFound_shouldThrowException() {
        when(dataSource.readById(999L)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> service.getNewsById(999L));
    }

    @Test
    void deleteNews_existing_shouldReturnTrue() {
        when(dataSource.deleteNewsById(1L)).thenReturn(true);

        Boolean result = service.deleteNews(1L);

        assertTrue(result);
        verify(dataSource).deleteNewsById(1L);
    }

    @Test
    void createNews_invalid_shouldReturnNull() throws DataFormatException, TitleLException, ContentLException {
        NewsDTO invalid = new NewsDTO("Bad", "Good Content", 1L);
        invalid.setCreateDate(LocalDateTime.now());
        invalid.setLastUpdateDate(LocalDateTime.now());

        doThrow(new TitleLException("Title too short")).when(validator).validate(invalid);

        NewsDTO result = service.createNews(invalid);

        assertNull(result);
        verify(validator).validate(invalid);
    }

    @Test
    void updateNews_invalid_shouldReturnNull() throws DataFormatException, TitleLException, ContentLException {
        NewsDTO invalid = new NewsDTO("Valid Title", "Bad", 1L);
        invalid.setCreateDate(LocalDateTime.now());
        invalid.setLastUpdateDate(LocalDateTime.now());

        when(dataSource.readById(1L)).thenReturn(model);
        doThrow(new ContentLException("Content too short")).when(validator).validate(invalid);

        NewsDTO result = service.updateNews(1L, invalid);

        assertNull(result);
        verify(validator).validate(invalid);
    }
}
