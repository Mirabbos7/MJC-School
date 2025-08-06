package com.mjc.school.service.validator;

import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.exceptions.ValidatorException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTests {

    @Test
    void validId_doesNotThrow() {
        assertDoesNotThrow(() -> Validator.validateAuthorId(1L));
        assertDoesNotThrow(() -> Validator.validateAuthorId(30L));
    }

    @Test
    void validateAuthorId_invalidId_throwsException() {
        assertThrows(ValidatorException.class, () -> Validator.validateAuthorId(0L));
        assertThrows(ValidatorException.class, () -> Validator.validateAuthorId(-1L));
        assertThrows(ValidatorException.class, () -> Validator.validateAuthorId(31L));
    }

    @Test
    void validateNewsId_validateNewsId_validId_doesNotThrow() {
        assertDoesNotThrow(() -> Validator.validateNewsId(1L));
    }

    @Test
    void invalidId_throwsException() {
        assertThrows(ValidatorException.class, () -> Validator.validateNewsId(0L));
        assertThrows(ValidatorException.class, () -> Validator.validateNewsId(-10L));
    }

    @Test
    void validDto_doesNotThrow() {
        NewsRequestDto dto = new NewsRequestDto("ValidTitle", "ValidContent", 1L);
        assertDoesNotThrow(() -> Validator.validateDtoRequest(dto));
    }

    @Test
    void nullTitle_throwsException() {
        NewsRequestDto dto = new NewsRequestDto(null, "ValidContent", 1L);
        assertThrows(ValidatorException.class, () -> Validator.validateDtoRequest(dto));
    }

    @Test
    void shortTitle_throwsException() {
        NewsRequestDto dto = new NewsRequestDto("abc", "ValidContent", 1L);
        assertThrows(ValidatorException.class, () -> Validator.validateDtoRequest(dto));
    }

    @Test
    void longTitle_throwsException() {
        String longTitle = "a".repeat(31);
        NewsRequestDto dto = new NewsRequestDto(longTitle, "ValidContent", 1L);
        assertThrows(ValidatorException.class, () -> Validator.validateDtoRequest(dto));
    }

    @Test
    void shortContent_throwsException() {
        NewsRequestDto dto = new NewsRequestDto("ValidTitle", "abc", 1L);
        assertThrows(ValidatorException.class, () -> Validator.validateDtoRequest(dto));
    }

    @Test
    void longContent_throwsException() {
        String longContent = "a".repeat(256);
        NewsRequestDto dto = new NewsRequestDto("ValidTitle", longContent, 1L);
        assertThrows(ValidatorException.class, () -> Validator.validateDtoRequest(dto));
    }

    @Test
    void invalidAuthor_throwsException() {
        NewsRequestDto dto = new NewsRequestDto("ValidTitle", "ValidContent", 0L);
        assertThrows(ValidatorException.class, () -> Validator.validateDtoRequest(dto));
    }
}
