package com.mjc.school.controller;

import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import com.mjc.school.service.impl.NewsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NewsControllerTests {

    private NewsService newsService;
    private NewsController newsController;

    @BeforeEach
    void setUp() {
        newsService = mock(NewsService.class);
        newsController = new NewsController(newsService);
    }

    @Test
    void readAll_returnsListFromService() {
        NewsResponseDto response = new NewsResponseDto(
                1L, "Title", "Content",
                LocalDateTime.now(), LocalDateTime.now(), 1L
        );
        when(newsService.readAll()).thenReturn(List.of(response));

        List<NewsResponseDto> result = newsController.readAll();

        assertEquals(1, result.size());
        assertEquals("Title", result.get(0).title());
        verify(newsService).readAll();
    }

    @Test
    void readById_returnsDtoFromService() {
        NewsResponseDto response = new NewsResponseDto(
                2L, "Another Title", "Another Content",
                LocalDateTime.now(), LocalDateTime.now(), 2L
        );
        when(newsService.readById(2L)).thenReturn(response);

        NewsResponseDto result = newsController.readById(2L);

        assertNotNull(result);
        assertEquals(2L, result.id());
        verify(newsService).readById(2L);
    }

    @Test
    void create_callsServiceAndReturnsDto() {
        NewsRequestDto request = new NewsRequestDto("Create Title", "Create Content", 3L);
        NewsResponseDto response = new NewsResponseDto(
                3L, "Create Title", "Create Content",
                LocalDateTime.now(), LocalDateTime.now(), 3L
        );
        when(newsService.create(request)).thenReturn(response);

        NewsResponseDto result = newsController.create(request);

        assertEquals("Create Title", result.title());
        verify(newsService).create(request);
    }

    @Test
    void update_callsServiceAndReturnsDto() {
        NewsRequestDto request = new NewsRequestDto("Updated Title", "Updated Content", 4L);
        NewsResponseDto response = new NewsResponseDto(
                4L, "Updated Title", "Updated Content",
                LocalDateTime.now(), LocalDateTime.now(), 4L
        );
        when(newsService.update(request)).thenReturn(response);

        NewsResponseDto result = newsController.update(request);

        assertEquals("Updated Title", result.title());
        verify(newsService).update(request);
    }

    @Test
    void delete_returnsTrueFromService() {
        when(newsService.delete(5L)).thenReturn(true);

        assertTrue(newsController.delete(5L));
        verify(newsService).delete(5L);
    }

    @Test
    void delete_returnsFalseFromService() {
        when(newsService.delete(99L)).thenReturn(false);

        assertFalse(newsController.delete(99L));
        verify(newsService).delete(99L);
    }
}
