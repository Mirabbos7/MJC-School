package com.mjc.school.repository.source;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.util.AuthorModelUtils;
import com.mjc.school.repository.util.DataReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DataSourceTests {

    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        dataSource = DataSource.getInstance();
    }

    @Test
    void testReadAuthors() {
        try (MockedStatic<DataReader> mockedStatic = mockStatic(DataReader.class)) {
            mockedStatic.when(() -> DataReader.read("author.txt"))
                    .thenReturn(List.of("John", "Alice", "Bob"));

            List<AuthorModel> authors = dataSource.readAuthors();

            assertEquals(3, authors.size());
            assertEquals("John", authors.get(0).getName());
        }
    }

    @Test
    void testReadNews() throws Exception {
        AuthorModelUtils.resetAutoId();

        try (MockedStatic<DataReader> mockedStatic = mockStatic(DataReader.class)) {
            mockedStatic.when(() -> DataReader.read("author.txt"))
                    .thenReturn(List.of("John", "Alice", "Bob"));
            mockedStatic.when(() -> DataReader.read("titles.txt"))
                    .thenReturn(List.of("Title 1", "Title 2", "Title 3"));
            mockedStatic.when(() -> DataReader.read("content.txt"))
                    .thenReturn(List.of("Content 1", "Content 2", "Content 3"));

            DataSource dataSource = new DataSource();

            List<AuthorModel> authors = dataSource.readAuthors();
            Set<Long> validAuthorIds = authors.stream()
                    .map(AuthorModel::getId)
                    .collect(Collectors.toSet());

            AuthorModelUtils.resetAutoId();

            List<NewsModel> newsList = dataSource.readNews();

            assertEquals(20, newsList.size());

            NewsModel first = newsList.get(0);
            assertNotNull(first.getTitle());
            assertNotNull(first.getContent());
            assertTrue(first.getId() >= 1 && first.getId() <= 20);

            assertTrue(validAuthorIds.contains(first.getAuthorId()));
        }
    }



}