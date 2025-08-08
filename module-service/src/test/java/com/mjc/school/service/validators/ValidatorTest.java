package com.mjc.school.service.validators;

import com.mjc.school.service.dto.NewsDTO;
import com.mjc.school.service.exceptions.ContentLException;
import com.mjc.school.service.exceptions.DataFormatException;
import com.mjc.school.service.exceptions.TitleLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validator.getInstance();
    }

    @Test
    void validate_validNewsDTO_shouldReturnTrue() {
        NewsDTO dto = new NewsDTO("Valid Title", "Valid Content", 1L);
        dto.setCreateDate(LocalDateTime.now());
        dto.setLastUpdateDate(LocalDateTime.now());

        assertDoesNotThrow(() -> {
            boolean result = validator.validate(dto);
            assertTrue(result);
        });
    }

    @Test
    void validate_shortTitle_shouldThrowTitleLException() {
        NewsDTO dto = new NewsDTO("Shrt", "Valid Content", 1L);
        dto.setCreateDate(LocalDateTime.now());
        dto.setLastUpdateDate(LocalDateTime.now());

        TitleLException ex = assertThrows(TitleLException.class, () -> validator.validate(dto));
        assertEquals("Title length must be between 5 and 30 characters", ex.getMessage());
    }

    @Test
    void validate_shortContent_shouldThrowContentLException() {
        NewsDTO dto = new NewsDTO("Valid Title", "Shrt", 1L);
        dto.setCreateDate(LocalDateTime.now());
        dto.setLastUpdateDate(LocalDateTime.now());

        ContentLException ex = assertThrows(ContentLException.class, () -> validator.validate(dto));
        assertEquals("Content length must be between 5 and 255 characters", ex.getMessage());
    }

    @Test
    void validate_nullCreateDate_shouldThrowDataFormatException() {
        NewsDTO dto = new NewsDTO("Valid Title", "Valid Content", 1L);
        dto.setCreateDate(null);  // simulate invalid date
        dto.setLastUpdateDate(LocalDateTime.now());

        DataFormatException ex = assertThrows(DataFormatException.class, () -> validator.validate(dto));
        assertEquals("Dates must be in ISO 8601 format", ex.getMessage());
    }

    @Test
    void validate_nullLastUpdateDate_shouldThrowDataFormatException() {
        NewsDTO dto = new NewsDTO("Valid Title", "Valid Content", 1L);
        dto.setCreateDate(LocalDateTime.now());
        dto.setLastUpdateDate(null);  // simulate invalid date

        DataFormatException ex = assertThrows(DataFormatException.class, () -> validator.validate(dto));
        assertEquals("Dates must be in ISO 8601 format", ex.getMessage());
    }
}
