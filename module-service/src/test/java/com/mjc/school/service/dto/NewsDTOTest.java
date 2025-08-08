package com.mjc.school.service.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class NewsDTOTest {

    @Test
    void testDefaultConstructor_idIsNull() {
        NewsDTO dto = new NewsDTO();
        assertNull(dto.getId());
    }

    @Test
    void testDefaultConstructor_titleIsNull() {
        NewsDTO dto = new NewsDTO();
        assertNull(dto.getTitle());
    }

    @Test
    void testDefaultConstructor_contentIsNull() {
        NewsDTO dto = new NewsDTO();
        assertNull(dto.getContent());
    }

    @Test
    void testDefaultConstructor_createDateIsNull() {
        NewsDTO dto = new NewsDTO();
        assertNull(dto.getCreateDate());
    }

    @Test
    void testDefaultConstructor_lastUpdateDateIsNull() {
        NewsDTO dto = new NewsDTO();
        assertNull(dto.getLastUpdateDate());
    }

    @Test
    void testDefaultConstructor_authorIdIsNull() {
        NewsDTO dto = new NewsDTO();
        assertNull(dto.getAuthorId());
    }

    @Test
    void testPartialConstructor_setsTitle() {
        NewsDTO dto = new NewsDTO("Title", "Content", 1L);
        assertEquals("Title", dto.getTitle());
    }

    @Test
    void testPartialConstructor_setsContent() {
        NewsDTO dto = new NewsDTO("Title", "Content", 1L);
        assertEquals("Content", dto.getContent());
    }

    @Test
    void testPartialConstructor_setsAuthorId() {
        NewsDTO dto = new NewsDTO("Title", "Content", 1L);
        assertEquals(1L, dto.getAuthorId());
    }

    @Test
    void testPartialConstructor_setsCreateDate() {
        NewsDTO dto = new NewsDTO("Title", "Content", 1L);
        assertNotNull(dto.getCreateDate());
    }

    @Test
    void testPartialConstructor_setsLastUpdateDate() {
        NewsDTO dto = new NewsDTO("Title", "Content", 1L);
        assertNotNull(dto.getLastUpdateDate());
    }

    @Test
    void testFullConstructor_setsAllFields() {
        LocalDateTime date = LocalDateTime.now();
        NewsDTO dto = new NewsDTO(10L, "T", "C", date, date, 2L);

        assertEquals(10L, dto.getId());
    }

    @Test
    void testFullConstructor_setsTitle() {
        LocalDateTime date = LocalDateTime.now();
        NewsDTO dto = new NewsDTO(10L, "T", "C", date, date, 2L);

        assertEquals("T", dto.getTitle());
    }

    @Test
    void testFullConstructor_setsContent() {
        LocalDateTime date = LocalDateTime.now();
        NewsDTO dto = new NewsDTO(10L, "T", "C", date, date, 2L);

        assertEquals("C", dto.getContent());
    }

    @Test
    void testFullConstructor_setsCreateDate() {
        LocalDateTime date = LocalDateTime.now();
        NewsDTO dto = new NewsDTO(10L, "T", "C", date, date, 2L);

        assertEquals(date, dto.getCreateDate());
    }

    @Test
    void testFullConstructor_setsLastUpdateDate() {
        LocalDateTime date = LocalDateTime.now();
        NewsDTO dto = new NewsDTO(10L, "T", "C", date, date, 2L);

        assertEquals(date, dto.getLastUpdateDate());
    }

    @Test
    void testFullConstructor_setsAuthorId() {
        LocalDateTime date = LocalDateTime.now();
        NewsDTO dto = new NewsDTO(10L, "T", "C", date, date, 2L);

        assertEquals(2L, dto.getAuthorId());
    }

    @Test
    void testSettersAndGetters_id() {
        NewsDTO dto = new NewsDTO();
        dto.setId(99L);
        assertEquals(99L, dto.getId());
    }

    @Test
    void testSettersAndGetters_title() {
        NewsDTO dto = new NewsDTO();
        dto.setTitle("Hello");
        assertEquals("Hello", dto.getTitle());
    }

    @Test
    void testSettersAndGetters_content() {
        NewsDTO dto = new NewsDTO();
        dto.setContent("World");
        assertEquals("World", dto.getContent());
    }

    @Test
    void testSettersAndGetters_createDate() {
        NewsDTO dto = new NewsDTO();
        LocalDateTime date = LocalDateTime.now();
        dto.setCreateDate(date);
        assertEquals(date, dto.getCreateDate());
    }

    @Test
    void testSettersAndGetters_lastUpdateDate() {
        NewsDTO dto = new NewsDTO();
        LocalDateTime date = LocalDateTime.now();
        dto.setLastUpdateDate(date);
        assertEquals(date, dto.getLastUpdateDate());
    }

    @Test
    void testSettersAndGetters_authorId() {
        NewsDTO dto = new NewsDTO();
        dto.setAuthorId(123L);
        assertEquals(123L, dto.getAuthorId());
    }

    @Test
    void testToString_containsId() {
        NewsDTO dto = new NewsDTO(1L, "T", "C", LocalDateTime.of(2024, 1, 1, 0, 0),
                LocalDateTime.of(2024, 1, 2, 0, 0), 3L);
        assertTrue(dto.toString().contains("id=1"));
    }
}
