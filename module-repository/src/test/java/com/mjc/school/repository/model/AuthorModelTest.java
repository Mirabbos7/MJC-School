package com.mjc.school.repository.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorModelTest {

    @Test
    void testConstructorAndGetters() {
        AuthorModel author = new AuthorModel(1L, "Test Author");

        assertEquals(1L, author.getId());
        assertEquals("Test Author", author.getName());
    }

    @Test
    void testSetters() {
        AuthorModel author = new AuthorModel(1L, "Original Name");
        author.setId(2L);
        author.setName("Updated Name");

        assertEquals(2L, author.getId());
        assertEquals("Updated Name", author.getName());
    }

    @Test
    void testEqualsSameObject() {
        AuthorModel author = new AuthorModel(1L, "Author");
        assertEquals(author, author);
    }

    @Test
    void testEqualsEqualObjects() {
        AuthorModel author1 = new AuthorModel(1L, "Author");
        AuthorModel author2 = new AuthorModel(1L, "Author");

        assertEquals(author1, author2);
    }

    @Test
    void testEqualsDifferentId() {
        AuthorModel author1 = new AuthorModel(1L, "Author");
        AuthorModel author2 = new AuthorModel(2L, "Author");

        assertNotEquals(author1, author2);
    }

    @Test
    void testEqualsDifferentName() {
        AuthorModel author1 = new AuthorModel(1L, "Author");
        AuthorModel author2 = new AuthorModel(1L, "Different");

        assertNotEquals(author1, author2);
    }

    @Test
    void testEqualsNull() {
        AuthorModel author = new AuthorModel(1L, "Author");
        assertNotEquals(null, author);
    }

    @Test
    void testEqualsDifferentClass() {
        AuthorModel author = new AuthorModel(1L, "Author");
        String notAuthor = "Not an author";

        assertNotEquals(author, notAuthor);
    }

    @Test
    void testHashCodeConsistency() {
        AuthorModel author1 = new AuthorModel(1L, "Author");
        AuthorModel author2 = new AuthorModel(1L, "Author");

        assertEquals(author1.hashCode(), author2.hashCode());
    }

    @Test
    void testToString() {
        AuthorModel author = new AuthorModel(1L, "Author");
        String expected = "AuthorModel{id=1, name='Author'}";

        assertEquals(expected, author.toString());
    }
}
