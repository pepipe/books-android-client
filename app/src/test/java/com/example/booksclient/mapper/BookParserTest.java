package com.example.booksclient.mapper;

import com.example.booksclient.domain.model.Book;
import org.junit.Test;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class BookParserTest {

    @Test
    public void parseBooksJson_validJson_returnsBooks() {
        // Read JSON file from resources
        InputStreamReader reader = new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream("test-books.json"),
                StandardCharsets.UTF_8
        );

        StringBuilder jsonBuilder = new StringBuilder();
        char[] buffer = new char[1024];
        int read;
        try {
            while ((read = reader.read(buffer)) != -1) {
                jsonBuilder.append(buffer, 0, read);
            }
        } catch (Exception e) {
            fail("Failed to read JSON file: " + e.getMessage());
        }

        String json = jsonBuilder.toString();
        List<Book> books = BookParser.parseBooksJson(json);

        assertEquals(2, books.size());

        Book book1 = books.get(0);
        assertEquals("IDmpiQd5FwwC", book1.getId());
        assertEquals("Inside Cisco IOS Software Architecture", book1.getTitle());
        assertEquals("[Vijay Bollapragada, Curtis Murphy, Russ White]", book1.getAuthors());
        assertEquals("description book 1", book1.getDescription());
        assertEquals("image url (thumbnail)", book1.getImageUrl());
        assertEquals("thumbnail ulr (smallThumbnail)", book1.getThumbnailUrl());
        assertNull(book1.getBuyUrl());

        Book book2 = books.get(1);
        assertEquals("7CtNDwAAQBAJ", book2.getId());
        assertEquals("iOS Programming", book2.getTitle());
        assertEquals("[Mohit Thakkar]", book2.getAuthors());
        assertEquals("Description book2", book2.getDescription());
        assertEquals("thumbnail", book2.getImageUrl());
        assertEquals("smallThumbnail", book2.getThumbnailUrl());
        assertEquals("buyLink", book2.getBuyUrl());
    }

    @Test
    public void parseBooksJson_emptyJson_returnsEmptyList() {
        String json = "{}";
        List<Book> books = BookParser.parseBooksJson(json);
        assertTrue(books.isEmpty());
    }

    @Test
    public void parseBooksJson_nullJson_returnsEmptyList() {
        List<Book> books = BookParser.parseBooksJson(null);
        assertTrue(books.isEmpty());
    }

    @Test
    public void parseBooksJson_invalidJson_returnsEmptyList() {
        String json = "{invalid json}";
        List<Book> books = BookParser.parseBooksJson(json);
        assertTrue(books.isEmpty());
    }
}
