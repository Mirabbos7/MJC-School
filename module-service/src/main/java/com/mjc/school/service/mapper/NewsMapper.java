package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface NewsMapper {

    NewsMapper INSTANCE = Mappers.getMapper(NewsMapper.class);

    List<NewsResponseDto> newsListToDtoList(List<NewsModel> newsLIst);

    NewsResponseDto newsToDto(NewsModel news);

    @Mappings(value = {@Mapping(target = "createDate", ignore = true), @Mapping(target = "lastUpdateDate", ignore = true)})
    NewsModel dtoToNews(NewsRequestDto newsRequest);
}
