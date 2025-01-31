package com.example.booksclient.mapper;

import com.example.booksclient.data.network.model.BookResponse;
import com.example.booksclient.domain.model.Book;
import com.google.gson.Gson;

public class BookMapper {
    private BookMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static Book toDomain(BookResponse bookResponse) {
        if (bookResponse == null) {
            return null;
        }

        //need to convert url to use https to Glide work
        String thumbnailUrl = convertHttpToHttps(bookResponse.getSmallThumbnail());
        String imageUrl = convertHttpToHttps(bookResponse.getThumbnail());
        Book book = new Book(bookResponse.getId(), bookResponse.getTitle(),
                bookResponse.getAuthors(), bookResponse.getDescription(), imageUrl,
                thumbnailUrl, bookResponse.getBuyLink());

        Gson gson = new Gson();
        String bookJson = gson.toJson(bookResponse);
        book.setBookJson(bookJson);

        return book;
    }

    private static String convertHttpToHttps(String url) {
        if (url != null && url.startsWith("http://")) {
            return url.replace("http://", "https://");
        }
        return url;
    }
}
