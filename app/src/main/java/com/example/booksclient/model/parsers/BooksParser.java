package com.example.booksclient.model.parsers;

import com.example.booksclient.model.api.BookResponse;
import com.example.booksclient.model.api.GoogleBooksResponse;
import com.example.booksclient.model.domain.Book;
import com.example.booksclient.model.mappers.BookMapper;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class BooksParser {
    private BooksParser() {
        throw new IllegalStateException("Utility class");
    }

    public static List<Book> parseBooksJson(String json) {
        if( json == null || json.isEmpty()) {
            return new ArrayList<>();
        }
        List<Book> books = new ArrayList<>();
        Gson gson = new Gson();

        try {
            // Convert the JSON to a Java object using the classes above
            GoogleBooksResponse response = gson.fromJson(json, GoogleBooksResponse.class);

            if (response != null && response.hasItems()) {
                for (BookResponse bookResponse : response.getItems()) {
                    Book book = BookMapper.toDomain(bookResponse);
                    if (book != null) {
                        books.add(book);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Could not parse json: " + e.getMessage());
        }

        return books;
    }
}
