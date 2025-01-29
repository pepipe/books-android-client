package com.example.booksclient.model.mappers;

import com.example.booksclient.model.api.BookResponse;
import com.example.booksclient.model.domain.Book;
import com.google.gson.Gson;

public class BookMapper {
    private BookMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static Book toDomain(BookResponse bookResponse) {
        if (bookResponse == null) {
            return null;
        }

        Book book = new Book(bookResponse.getId(), bookResponse.getTitle(),
                bookResponse.getAuthors(), bookResponse.getDescription(), bookResponse.getThumbnail(),
                bookResponse.getSmallThumbnail(), bookResponse.getBuyLink());

        Gson gson = new Gson();
        String bookJson = gson.toJson(bookResponse);
        book.setBookJson(bookJson);

        return book;
    }
}
