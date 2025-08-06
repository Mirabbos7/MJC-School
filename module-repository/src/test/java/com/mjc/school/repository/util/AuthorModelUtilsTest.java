package com.mjc.school.repository.util;

import com.mjc.school.repository.model.AuthorModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthorModelUtilsTest {

    private Field autoIdField;

    @BeforeEach
    void setUp() throws Exception {
        autoIdField = AuthorModel.class.getDeclaredField("autoId");
        autoIdField.setAccessible(true);
    }

    @Test
    void resetAutoId_setsStaticFieldToOne() throws Exception {
        // Arrange: set the static field to a random value first
        autoIdField.set(null, 50L);

        // Act
        AuthorModelUtils.resetAutoId();

        // Assert: check that it is now reset to 1L
        Object value = autoIdField.get(null);
        assertEquals(1L, value);
    }
}
