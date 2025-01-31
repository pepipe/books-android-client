package com.example.booksclient.data.repository;

import com.example.booksclient.domain.model.Book;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

// NOTE: this test is only to showcase Parameterized
// it actually do network request, so there isn't isolation in this test
@RunWith(Parameterized.class)
public class BookRepositoryParameterizedTest {
    private final String query;
    private final int offset;
    private final int maxResults;
    private final int expectedCount;

    private BookRepository bookRepository;

    public BookRepositoryParameterizedTest(String query, int startIndex, int maxResults, int expectedCount) {
        this.query = query;
        this.offset = startIndex;
        this.maxResults = maxResults;
        this.expectedCount = expectedCount;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"iOS", 0, 3, 3},
                {"iOS", 0, 10, 10},
                {"iOS", 10, 10, 0},
                {"iOS", 0, 2, 2},
                {"iOS", 1, 1, 1},
                {"iOS", 0, 0, 0},
                {"iOS", 12, 10, 0}
        });
    }

    @Before
    public void setUp() {
        bookRepository = new BookRepository();
    }

    @Test
    public void fetchBooks_checkResultCount() {
        bookRepository.fetchBooks(query, offset, maxResults,new BookRepository.BooksFetchCallback() {
            @Override
            public void onBooksFetched(List<Book> books) {
                Assert.assertFalse(books.isEmpty());
                assertEquals(expectedCount, books.size());
            }

            @Override
            public void onError(Exception e) {
                fail("Error fetching");
            }
        });

    }
}
