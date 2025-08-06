package com.mjc.school.repository.util;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

public class DataReaderTest {

    @Test
    void testRead_withMockedFile() {
        try (MockedStatic<DataReader> mockedStatic = mockStatic(DataReader.class)) {
            mockedStatic.when(() -> DataReader.read("author.txt"))
                    .thenReturn(List.of("John", "Alice", "Bob"));

            List<String> result = DataReader.read("author.txt");

            assertEquals(3, result.size());
            assertEquals("John", result.get(0));
        }
    }
}