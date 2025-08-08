package com.mjc.school.controller;

import com.mjc.school.service.dto.NewsDTO;
import com.mjc.school.service.NewsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControllerTest {

    private NewsService newsService;
    private Controller controller;
    private final LocalDateTime now = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        newsService = mock(NewsService.class);
        controller = new Controller(newsService);
    }

    @Test
    void testGetAllNews_returnsSameList() {
        List<NewsDTO> expected = List.of(
                new NewsDTO(1L, "T1", "C1", now, now, 1L),
                new NewsDTO(2L, "T2", "C2", now, now, 2L)
        );
        when(newsService.getAllNews()).thenReturn(expected);

        List<NewsDTO> actual = controller.getAllNews();

        assertSame(expected, actual);
    }

    @Test
    void testGetNewsById_returnsExpectedDto() {
        NewsDTO expected = new NewsDTO(1L, "T", "C", now, now, 1L);
        when(newsService.getNewsById(1L)).thenReturn(expected);

        NewsDTO actual = controller.getNewsById(1L);

        assertSame(expected, actual);
    }

    @Test
    void testCreateNews_returnsCreatedDto() {
        NewsDTO input = new NewsDTO("New", "Content", 1L);
        NewsDTO expected = new NewsDTO(3L, "New", "Content", now, now, 1L);
        when(newsService.createNews(input)).thenReturn(expected);

        NewsDTO actual = controller.createNews(input);

        assertSame(expected, actual);
    }

    @Test
    void testUpdateNews_returnsUpdatedDto() {
        NewsDTO input = new NewsDTO("Updated", "Content", 1L);
        NewsDTO expected = new NewsDTO(1L, "Updated", "Content", now, now, 1L);
        when(newsService.updateNews(1L, input)).thenReturn(expected);

        NewsDTO actual = controller.updateNews(1L, input);

        assertSame(expected, actual);
    }

    @Test
    void testDeleteNews_invokesServiceWithoutException() {
        when(newsService.deleteNews(5L)).thenReturn(true);

        assertDoesNotThrow(() -> controller.deleteNews(5L));
    }
}
