package com.example.booksclient.data.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;

import com.example.booksclient.domain.model.Book;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

public class BookRepositoryTest {
    @Mock
    private BookRepository bookRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //Note: showcase mocking the network requests
    @Test
    public void fetchBooks_checkResultCount() {
        List<Book> expectedBooks = Arrays.asList(
                new Book("1", "Title1", "Author1", "Description1", "ImageUrl1", "thumbnailUrl1", "BuyUrl1"),
                new Book("2", "Title2", "Author2", "Description2", "ImageUrl2", "thumbnailUrl2", "BuyUrl2")
        );

        doAnswer(invocation -> {
            BookRepository.BooksFetchCallback callback = invocation.getArgument(3);
            callback.onBooksFetched(expectedBooks);
            return null;
        }).when(bookRepository).fetchBooks(anyString(), anyInt(), anyInt(), any());

        bookRepository.fetchBooks("query", 0, 3, new BookRepository.BooksFetchCallback() {
            @Override
            public void onBooksFetched(List<Book> books) {
                assertEquals(2, books.size());
            }

            @Override
            public void onError(Exception e) {
                fail("Fetching books should not fail");
            }
        });
    }
}
