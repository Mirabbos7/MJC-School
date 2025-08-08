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
    private NewsDTO validNewsDTO;

    @BeforeEach
    void setUp() {
        validator = Validator.getInstance();
        validNewsDTO = new NewsDTO("Valid Title Here", "Valid content that is long enough to pass validation", 1L);
        validNewsDTO.setId(1L);
        validNewsDTO.setCreateDate(LocalDateTime.now());
        validNewsDTO.setLastUpdateDate(LocalDateTime.now());
    }

    @Test
    void validate_validNewsDTO_shouldReturnTrue() throws TitleLException, ContentLException, DataFormatException {
        boolean result = validator.validate(validNewsDTO);

        assertTrue(result);
    }

    @Test
    void validate_titleTooShort_shouldThrowTitleException() {
        NewsDTO invalidNews = new NewsDTO("Short", "Valid content that is long enough to pass validation", 1L);
        invalidNews.setCreateDate(LocalDateTime.now());
        invalidNews.setLastUpdateDate(LocalDateTime.now());

        assertThrows(TitleLException.class, () -> validator.validate(invalidNews));
    }

    @Test
    void validate_titleTooLong_shouldThrowTitleException() {
        NewsDTO invalidNews = new NewsDTO("This title is way too long and exceeds thirty characters",
                "Valid content that is long enough to pass validation", 1L);
        invalidNews.setCreateDate(LocalDateTime.now());
        invalidNews.setLastUpdateDate(LocalDateTime.now());

        assertThrows(TitleLException.class, () -> validator.validate(invalidNews));
    }

    @Test
    void validate_titleExactly5Characters_shouldThrowTitleException() {
        NewsDTO invalidNews = new NewsDTO("12345", "Valid content that is long enough to pass validation", 1L);
        invalidNews.setCreateDate(LocalDateTime.now());
        invalidNews.setLastUpdateDate(LocalDateTime.now());

        assertThrows(TitleLException.class, () -> validator.validate(invalidNews));
    }

    @Test
    void validate_titleExactly30Characters_shouldReturnTrue() throws TitleLException, ContentLException, DataFormatException {
        NewsDTO validNews = new NewsDTO("123456789012345678901234567890",
                "Valid content that is long enough to pass validation", 1L);
        validNews.setCreateDate(LocalDateTime.now());
        validNews.setLastUpdateDate(LocalDateTime.now());

        boolean result = validator.validate(validNews);

        assertTrue(result);
    }

    @Test
    void validate_contentTooShort_shouldThrowContentException() {
        NewsDTO invalidNews = new NewsDTO("Valid Title", "Short", 1L);
        invalidNews.setCreateDate(LocalDateTime.now());
        invalidNews.setLastUpdateDate(LocalDateTime.now());

        assertThrows(ContentLException.class, () -> validator.validate(invalidNews));
    }

    @Test
    void validate_contentTooLong_shouldThrowContentException() {
        StringBuilder longContent = new StringBuilder();
        for (int i = 0; i < 256; i++) {
            longContent.append("a");
        }

        NewsDTO invalidNews = new NewsDTO("Valid Title", longContent.toString(), 1L);
        invalidNews.setCreateDate(LocalDateTime.now());
        invalidNews.setLastUpdateDate(LocalDateTime.now());

        assertThrows(ContentLException.class, () -> validator.validate(invalidNews));
    }

    @Test
    void validate_contentExactly5Characters_shouldThrowContentException() {
        NewsDTO invalidNews = new NewsDTO("Valid Title", "12345", 1L);
        invalidNews.setCreateDate(LocalDateTime.now());
        invalidNews.setLastUpdateDate(LocalDateTime.now());

        assertThrows(ContentLException.class, () -> validator.validate(invalidNews));
    }

    @Test
    void validate_contentExactly255Characters_shouldReturnTrue() throws TitleLException, ContentLException, DataFormatException {
        StringBuilder validContent = new StringBuilder();
        for (int i = 0; i < 255; i++) {
            validContent.append("a");
        }

        NewsDTO validNews = new NewsDTO("Valid Title", validContent.toString(), 1L);
        validNews.setCreateDate(LocalDateTime.now());
        validNews.setLastUpdateDate(LocalDateTime.now());

        boolean result = validator.validate(validNews);

        assertTrue(result);
    }

    @Test
    void validate_nullCreateDate_shouldThrowDataFormatException() {
        NewsDTO invalidNews = new NewsDTO("Valid Title", "Valid content that is long enough to pass validation", 1L);
        invalidNews.setCreateDate(null);
        invalidNews.setLastUpdateDate(LocalDateTime.now());

        assertThrows(DataFormatException.class, () -> validator.validate(invalidNews));
    }

    @Test
    void validate_nullLastUpdateDate_shouldThrowDataFormatException() {
        NewsDTO invalidNews = new NewsDTO("Valid Title", "Valid content that is long enough to pass validation", 1L);
        invalidNews.setCreateDate(LocalDateTime.now());
        invalidNews.setLastUpdateDate(null);

        assertThrows(DataFormatException.class, () -> validator.validate(invalidNews));
    }

    @Test
    void validate_bothDatesNull_shouldThrowDataFormatException() {
        NewsDTO invalidNews = new NewsDTO("Valid Title", "Valid content that is long enough to pass validation", 1L);
        invalidNews.setCreateDate(null);
        invalidNews.setLastUpdateDate(null);

        assertThrows(DataFormatException.class, () -> validator.validate(invalidNews));
    }

    @Test
    void validate_validDates_shouldReturnTrue() throws TitleLException, ContentLException, DataFormatException {
        LocalDateTime now = LocalDateTime.now();
        NewsDTO validNews = new NewsDTO("Valid Title", "Valid content that is long enough to pass validation", 1L);
        validNews.setCreateDate(now);
        validNews.setLastUpdateDate(now);

        boolean result = validator.validate(validNews);

        assertTrue(result);
    }

    @Test
    void validate_titleAndContentBothInvalid_shouldThrowTitleException() {
        // Title exception should be thrown first (it's checked first in the code)
        NewsDTO invalidNews = new NewsDTO("Short", "Short", 1L);
        invalidNews.setCreateDate(LocalDateTime.now());
        invalidNews.setLastUpdateDate(LocalDateTime.now());

        assertThrows(TitleLException.class, () -> validator.validate(invalidNews));
    }

    @Test
    void validate_contentAndDateBothInvalid_shouldThrowContentException() {
        // Content exception should be thrown first (it's checked before date validation)
        NewsDTO invalidNews = new NewsDTO("Valid Title", "Short", 1L);
        invalidNews.setCreateDate(null);
        invalidNews.setLastUpdateDate(LocalDateTime.now());

        assertThrows(ContentLException.class, () -> validator.validate(invalidNews));
    }

    @Test
    void validate_allFieldsInvalid_shouldThrowTitleException() {
        // Title exception should be thrown first
        NewsDTO invalidNews = new NewsDTO("Short", "Short", 1L);
        invalidNews.setCreateDate(null);
        invalidNews.setLastUpdateDate(null);

        assertThrows(TitleLException.class, () -> validator.validate(invalidNews));
    }

    @Test
    void validate_edgeCaseTitle6Characters_shouldReturnTrue() throws TitleLException, ContentLException, DataFormatException {
        NewsDTO validNews = new NewsDTO("123456", "Valid content that is long enough to pass validation", 1L);
        validNews.setCreateDate(LocalDateTime.now());
        validNews.setLastUpdateDate(LocalDateTime.now());

        boolean result = validator.validate(validNews);

        assertTrue(result);
    }

    @Test
    void validate_edgeCaseContent6Characters_shouldReturnTrue() throws TitleLException, ContentLException, DataFormatException {
        NewsDTO validNews = new NewsDTO("Valid Title", "123456", 1L);
        validNews.setCreateDate(LocalDateTime.now());
        validNews.setLastUpdateDate(LocalDateTime.now());

        boolean result = validator.validate(validNews);

        assertTrue(result);
    }

    @Test
    void validate_singletonInstance_shouldReturnSameInstance() {
        Validator instance1 = Validator.getInstance();
        Validator instance2 = Validator.getInstance();

        assertSame(instance1, instance2);
    }
}
