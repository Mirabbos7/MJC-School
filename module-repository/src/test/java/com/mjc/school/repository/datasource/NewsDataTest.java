package com.mjc.school.repository.datasource;

import com.mjc.school.repository.model.NewsModel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NewsDataTest {

    private final AuthorData authorData = new AuthorData();
    private final NewsData newsData = new NewsData(authorData);
    private final List<NewsModel> newsList = newsData.getNews();

    @Test
    void testNewsListSize() {
        assertEquals(20, newsData.getNewsList().size());
    }

    @Test
    void testContentListSize() {
        assertEquals(20, newsData.getContentList().size());
    }

    @Test
    void testNewsModelListSize() {
        assertEquals(20, newsList.size());
    }

    @Test
    void testFirstNewsModelTitle() {
        assertEquals("Глобальное потепление: новые данные", newsList.get(0).getTitle());
    }

    @Test
    void testFirstNewsModelContent() {
        assertEquals("Ожидается, что в ближайшие десятилетия средняя температура на Земле может повыситься на несколько градусов. Это вызовет изменения в климате, которые будут сказываться на жизни миллионов людей по всему миру.", newsList.get(0).getContent());
    }

    @Test
    void testFirstNewsModelCreateDateNotNull() {
        assertNotNull(newsList.get(0).getCreateDate());
    }

    @Test
    void testFirstNewsModelLastUpdateDateNotNull() {
        assertNotNull(newsList.get(0).getLastUpdateDate());
    }

    @Test
    void testFirstNewsModelAuthorIdNotNull() {
        assertNotNull(newsList.get(0).getAuthorId());
    }

    @Test
    void testNewsModelIdsAreUnique() {
        assertEquals(0L, newsList.get(0).getId());
        assertEquals(1L, newsList.get(1).getId());
        assertEquals(2L, newsList.get(2).getId());
    }
}
