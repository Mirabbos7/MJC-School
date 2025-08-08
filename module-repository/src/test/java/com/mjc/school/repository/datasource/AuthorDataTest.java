package com.mjc.school.repository.datasource;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorDataTest {

    private final AuthorData authorData = new AuthorData();

    @Test
    void testAuthorListSize() {
        assertEquals(20, authorData.getAuthorList().size());
    }

    @Test
    void testAuthorsSize() {
        assertEquals(20, authorData.getAuthors().size());
    }

    @Test
    void testFirstAuthorListEntry() {
        assertEquals("Ирина", authorData.getAuthorList().get(0));
    }

    @Test
    void testFirstAuthorModelName() {
        assertEquals("Ирина", authorData.getAuthors().get(0).getName());
    }

    @Test
    void testFirstAuthorModelId() {
        assertEquals(0L, authorData.getAuthors().get(0).getId());
    }

    @Test
    void testAuthorListNotNull() {
        assertNotNull(authorData.getAuthorList());
    }

    @Test
    void testAuthorsListNotNull() {
        assertNotNull(authorData.getAuthors());
    }
}

