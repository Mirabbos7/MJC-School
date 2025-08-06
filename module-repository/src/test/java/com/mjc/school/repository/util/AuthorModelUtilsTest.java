package com.mjc.school.repository.util;

import com.mjc.school.repository.model.AuthorModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorModelUtilsTest {

    @Test
    void testResetAutoId() throws Exception {
        AuthorModelUtils.resetAutoId();

        AuthorModel author3 = new AuthorModel("Charlie");

        assertEquals(1L, author3.getId());
    }
}