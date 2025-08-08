package com.mjc.school.repository.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NewsModelTest {

    private final LocalDateTime dateTime = LocalDateTime.of(2023, 8, 8, 12, 0);

    private NewsModel createNews() {
        return new NewsModel(1L, "Title", "Content", dateTime, dateTime, 100L);
    }

    @Test
    void testConstructorAndGetters() {
        NewsModel news = createNews();

        assertEquals(1L, news.getId());
        assertEquals("Title", news.getTitle());
        assertEquals("Content", news.getContent());
        assertEquals(dateTime, news.getCreateDate());
        assertEquals(dateTime, news.getLastUpdateDate());
        assertEquals(100L, news.getAuthorId());
    }

    @Test
    void testSetters() {
        NewsModel news = new NewsModel(null, null, null, null, null, null);
        LocalDateTime newTime = LocalDateTime.of(2025, 1, 1, 10, 30);

        news.setId(2L);
        news.setTitle("New Title");
        news.setContent("New Content");
        news.setCreateDate(newTime);
        news.setLastUpdateDate(newTime);
        news.setAuthorId(200L);

        assertEquals(2L, news.getId());
        assertEquals("New Title", news.getTitle());
        assertEquals("New Content", news.getContent());
        assertEquals(newTime, news.getCreateDate());
        assertEquals(newTime, news.getLastUpdateDate());
        assertEquals(200L, news.getAuthorId());
    }

    @Test
    void testEqualsSameObject() {
        NewsModel news = createNews();
        assertEquals(news, news);
    }

    @Test
    void testEqualsEqualObjects() {
        NewsModel news1 = createNews();
        NewsModel news2 = createNews();
        assertEquals(news1, news2);
    }

    @Test
    void testEqualsDifferentId() {
        NewsModel news1 = createNews();
        NewsModel news2 = new NewsModel(999L, "Title", "Content", dateTime, dateTime, 100L);
        assertNotEquals(news1, news2);
    }

    @Test
    void testEqualsDifferentTitle() {
        NewsModel news1 = createNews();
        NewsModel news2 = new NewsModel(1L, "Other", "Content", dateTime, dateTime, 100L);
        assertNotEquals(news1, news2);
    }

    @Test
    void testEqualsDifferentContent() {
        NewsModel news1 = createNews();
        NewsModel news2 = new NewsModel(1L, "Title", "Other", dateTime, dateTime, 100L);
        assertNotEquals(news1, news2);
    }

    @Test
    void testEqualsDifferentCreateDate() {
        NewsModel news1 = createNews();
        NewsModel news2 = new NewsModel(1L, "Title", "Content", dateTime.plusDays(1), dateTime, 100L);
        assertNotEquals(news1, news2);
    }

    @Test
    void testEqualsDifferentLastUpdateDate() {
        NewsModel news1 = createNews();
        NewsModel news2 = new NewsModel(1L, "Title", "Content", dateTime, dateTime.plusDays(1), 100L);
        assertNotEquals(news1, news2);
    }

    @Test
    void testEqualsDifferentAuthorId() {
        NewsModel news1 = createNews();
        NewsModel news2 = new NewsModel(1L, "Title", "Content", dateTime, dateTime, 999L);
        assertNotEquals(news1, news2);
    }

    @Test
    void testEqualsNull() {
        NewsModel news = createNews();
        assertNotEquals(news, null);
    }

    @Test
    void testEqualsDifferentClass() {
        NewsModel news = createNews();
        assertNotEquals(news, "NotANewsModel");
    }

    @Test
    void testHashCodeConsistency() {
        NewsModel news1 = createNews();
        NewsModel news2 = createNews();
        assertEquals(news1.hashCode(), news2.hashCode());
    }

    @Test
    void testToString() {
        NewsModel news = createNews();
        String expected = "NewsModel{" +
                "id=1" +
                ", title='Title'" +
                ", content='Content'" +
                ", createDate=" + dateTime +
                ", lastUpdateDate=" + dateTime +
                ", authorId=100" +
                '}';
        assertEquals(expected, news.toString());
    }
}
