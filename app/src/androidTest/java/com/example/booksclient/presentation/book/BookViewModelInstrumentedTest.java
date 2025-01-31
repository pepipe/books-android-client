package com.example.booksclient.presentation.book;

import android.os.Handler;
import android.os.Looper;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.lifecycle.Observer;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import com.example.booksclient.domain.model.Book;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class BookViewModelInstrumentedTest {
    @Rule
    public ActivityScenarioRule<BookActivity> activityScenarioRule =
            new ActivityScenarioRule<>(BookActivity.class);

    @Test
    public void addBooks_updatesLiveData() throws InterruptedException {
        List<Book> expectedBooks = Arrays.asList(
                new Book("1", "Title1", "Author1", "Description1", "ImageUrl1", "Thumbnail1", "BuyUrl1"),
                new Book("2", "Title2", "Author2", "Description2", "ImageUrl2", "Thumbnail2", "BuyUrl2")
        );

        CountDownLatch latch = new CountDownLatch(1);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            BookViewModel bookViewModel = new BookViewModel();
            Observer<List<Book>> observer = new Observer<List<Book>>() {
                @Override
                public void onChanged(List<Book> books) {
                    if (books != null && !books.isEmpty()) {
                        assertEquals(expectedBooks, books);
                        latch.countDown(); // Signal that update is done
                    }
                }
            };

            bookViewModel.getBooks().observeForever(observer);
            bookViewModel.addBooks("query");
            bookViewModel.getBooks().removeObserver(observer);
        });
    }

    @Test
    public void clearBooks_resetsBooks() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            BookViewModel bookViewModel = new BookViewModel();
            Observer<List<Book>> observer = new Observer<List<Book>>() {
                @Override
                public void onChanged(List<Book> books) {
                    assertEquals(new ArrayList<Book>(), books);
                    latch.countDown(); // Signal that update is done
                }
            };
            bookViewModel.getBooks().observeForever(observer);

            // Add some initial books and then clear them
            bookViewModel.addBooks("query");
            bookViewModel.clearBooks();
            bookViewModel.getBooks().removeObserver(observer);
        });
    }
}
