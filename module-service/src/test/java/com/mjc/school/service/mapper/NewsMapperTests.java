package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NewsMapperTests {

    private final NewsMapper mapper = NewsMapper.INSTANCE;

    @Test
    void shouldMapNewsModelToDto() {
        LocalDateTime now = LocalDateTime.now();
        NewsModel model = new NewsModel(
                1L, "Test Title", "Test Content", now, now, 5L
        );

        NewsResponseDto dto = mapper.newsToDto(model);

        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.title()).isEqualTo("Test Title");
        assertThat(dto.content()).isEqualTo("Test Content");
        assertThat(dto.createDate()).isEqualTo(now);
        assertThat(dto.lastUpdateDate()).isEqualTo(now);
        assertThat(dto.authorId()).isEqualTo(5L);
    }

    @Test
    void shouldMapDtoToNewsModelIgnoringDates() {
        NewsRequestDto dto = new NewsRequestDto("Title", "Content", 10L);

        NewsModel model = mapper.dtoToNews(dto);

        assertThat(model.getTitle()).isEqualTo("Title");
        assertThat(model.getContent()).isEqualTo("Content");
        assertThat(model.getAuthorId()).isEqualTo(10L);
        assertThat(model.getCreateDate()).isNull();
        assertThat(model.getLastUpdateDate()).isNull();
    }

    @Test
    void shouldMapNewsListToDtoList() {
        LocalDateTime now = LocalDateTime.now();
        List<NewsModel> models = List.of(
                new NewsModel(1L, "T1", "C1", now, now, 1L),
                new NewsModel(2L, "T2", "C2", now, now, 2L)
        );

        List<NewsResponseDto> dtos = mapper.newsListToDtoList(models);

        assertThat(dtos).hasSize(2);
        assertThat(dtos).extracting("id").containsExactly(1L, 2L);
    }
}
