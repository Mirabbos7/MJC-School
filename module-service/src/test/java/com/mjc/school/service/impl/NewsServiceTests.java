package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.NewsRepository;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import com.mjc.school.service.mapper.NewsMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsServiceTests {

    @Mock
    private NewsRepository newsRepository;

    private NewsMapper newsMapper = NewsMapper.INSTANCE;

    private NewsService newsService;

    @BeforeEach
    void setup() {
        newsService = new NewsService(newsRepository);
    }

    @Test
    void successfully_created() {
        NewsRequestDto dto = new NewsRequestDto("Title", "content", 1L);
        NewsModel savedModel = new NewsModel(1L, "Title", "content", LocalDateTime.now(), LocalDateTime.now(), 1L);

        when(newsRepository.create(any(NewsModel.class))).thenReturn(savedModel);

        NewsResponseDto result = newsService.create(dto);

        assertNotNull(result);
        assertEquals(1L, result.id());
    }

    @Test
    void readById_shouldReturnNewsResponseDto_whenNewsExists() {
        Long id = 1L;
        NewsModel newsModel = new NewsModel(id, "Title", "Content", LocalDateTime.now(), LocalDateTime.now(), 1L);

        when(newsRepository.readBy(id)).thenReturn(newsModel);

        NewsResponseDto response = newsService.readById(id);

        assertNotNull(response);
        assertEquals(id, response.id());
        assertEquals("Title", response.title());
    }

    @Test
    void readAll_shouldReturnListOfNewsResponseDto() {
        List<NewsModel> newsModels = List.of(
                new NewsModel(1L, "Title1", "Content1", LocalDateTime.now(), LocalDateTime.now(), 1L),
                new NewsModel(2L, "Title2", "Content2", LocalDateTime.now(), LocalDateTime.now(), 2L)
        );

        when(newsRepository.readAll()).thenReturn(newsModels);

        List<NewsResponseDto> responses = newsService.readAll();

        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertEquals("Title1", responses.get(0).title());
        assertEquals("Title2", responses.get(1).title());
    }

    @Test
    void update_shouldReturnUpdatedNewsResponseDto() {
        NewsRequestDto request = new NewsRequestDto("Updated Title", "Updated Content", 1L);
        NewsModel toUpdate = NewsMapper.INSTANCE.dtoToNews(request);
        toUpdate.setLastUpdateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        NewsModel updatedModel = new NewsModel(1L, toUpdate.getTitle(), toUpdate.getContent(),
                LocalDateTime.now(), toUpdate.getLastUpdateDate(), toUpdate.getAuthorId());

        when(newsRepository.update(any(NewsModel.class))).thenReturn(updatedModel);

        NewsResponseDto response = newsService.update(request);

        assertNotNull(response);
        assertEquals("Updated Title", response.title());
    }

    @Test
    void delete_shouldReturnTrue_whenNewsExists() {
        Long id = 1L;
        when(newsRepository.ifIdExist(id)).thenReturn(true);
        when(newsRepository.delete(id)).thenReturn(true);

        Boolean result = newsService.delete(id);

        assertTrue(result);
    }

    @Test
    void delete_shouldThrowException_whenNewsDoesNotExist() {
        Long id = 99L;
        when(newsRepository.ifIdExist(id)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            newsService.delete(id);
        });

        assertEquals("News not exists with id: 99", exception.getMessage());
    }
}