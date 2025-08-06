package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.NewsRepository;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import com.mjc.school.service.mapper.NewsMapper;
import com.mjc.school.service.validator.Validator;
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

    @InjectMocks
    private NewsService newsService;

    private NewsModel sampleModel;
    private NewsRequestDto sampleRequest;
    private NewsResponseDto sampleResponse;

    @BeforeEach
    void setUp() {
        sampleModel = new NewsModel(
                1L,
                "Test Title",
                "Test Content",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
                10L
        );

        sampleRequest = new NewsRequestDto("Test Title", "Test Content", 10L);
        sampleResponse = NewsMapper.INSTANCE.newsToDto(sampleModel);
    }

    @Test
    void testReadAll() {
        when(newsRepository.readAll()).thenReturn(List.of(sampleModel));

        List<NewsResponseDto> result = newsService.readAll();

        assertEquals(1, result.size());
        assertEquals(sampleModel.getTitle(), result.get(0).title());
    }

    @Test
    void testReadById() {
        long id = 1L;
        when(newsRepository.readBy(id)).thenReturn(sampleModel);

        NewsResponseDto result = newsService.readById(id);

        assertNotNull(result);
        assertEquals(sampleModel.getTitle(), result.title());
        verify(newsRepository).readBy(id);
    }


    @Test
    void testCreate() {
        when(newsRepository.create(any())).thenReturn(sampleModel);

        NewsResponseDto result = newsService.create(sampleRequest);

        assertNotNull(result);
        assertEquals(sampleRequest.title(), result.title());
        verify(newsRepository).create(any());
    }


    @Test
    void testUpdate() {
        try (MockedStatic<Validator> mockedValidator = Mockito.mockStatic(Validator.class)) {
            when(newsRepository.update(any())).thenReturn(sampleModel);

            NewsResponseDto result = newsService.update(sampleRequest);

            assertNotNull(result);
            assertEquals(sampleRequest.title(), result.title());
            verify(newsRepository).update(any());
            mockedValidator.verify(() -> Validator.validateDtoRequest(sampleRequest));
        }
    }


    @Test
    void testDelete() {
        long id = 1L;
        when(newsRepository.ifIdExist(id)).thenReturn(true);
        when(newsRepository.delete(id)).thenReturn(true);

        boolean result = newsService.delete(id);

        assertTrue(result);
        verify(newsRepository).delete(id);
        verify(newsRepository).ifIdExist(id);
    }
}