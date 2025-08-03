package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-03T19:36:58+0500",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.2.jar, environment: Java 17.0.14 (Amazon.com Inc.)"
)
public class NewsMapperImpl implements NewsMapper {

    @Override
    public List<NewsResponseDto> newsListToDtoList(List<NewsModel> newsLIst) {
        if ( newsLIst == null ) {
            return null;
        }

        List<NewsResponseDto> list = new ArrayList<NewsResponseDto>( newsLIst.size() );
        for ( NewsModel newsModel : newsLIst ) {
            list.add( newsToDto( newsModel ) );
        }

        return list;
    }

    @Override
    public NewsResponseDto newsToDto(NewsModel news) {
        if ( news == null ) {
            return null;
        }

        Long id = null;
        String title = null;
        String content = null;
        LocalDateTime createDate = null;
        LocalDateTime lastUpdateDate = null;
        Long authorId = null;

        id = news.getId();
        title = news.getTitle();
        content = news.getContent();
        createDate = news.getCreateDate();
        lastUpdateDate = news.getLastUpdateDate();
        authorId = news.getAuthorId();

        NewsResponseDto newsResponseDto = new NewsResponseDto( id, title, content, createDate, lastUpdateDate, authorId );

        return newsResponseDto;
    }

    @Override
    public NewsModel dtoToNews(NewsRequestDto newsRequest) {
        if ( newsRequest == null ) {
            return null;
        }

        String title = null;
        String content = null;
        Long authorId = null;

        title = newsRequest.title();
        content = newsRequest.content();
        authorId = newsRequest.authorId();

        LocalDateTime createDate = null;
        LocalDateTime lastUpdateDate = null;

        NewsModel newsModel = new NewsModel( title, content, createDate, lastUpdateDate, authorId );

        return newsModel;
    }
}
