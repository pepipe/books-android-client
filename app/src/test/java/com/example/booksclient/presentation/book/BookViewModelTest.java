package com.example.booksclient.presentation.book;

import android.content.Context;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import com.example.booksclient.data.repository.BookRepository;
import com.example.booksclient.domain.model.Book;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private BookRepository bookRepository;

    @Mock
    private Context context;

    private BookViewModel bookViewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bookViewModel = new BookViewModel();//This will invoke 1 time the observer
    }

    @Test
    public void getBooks_initiallyEmpty() {
        Observer<List<Book>> observer = mock(Observer.class);
        bookViewModel.getBooks().observeForever(observer);

        verify(observer).onChanged(any());
    }

    @Test
    public void getError_initiallyEmpty() {
        Observer<String> observer = mock(Observer.class);
        bookViewModel.getError().observeForever(observer);

        verify(observer, never()).onChanged(any());
    }

    @Test
    public void clearBooks_resetsBooks() {
        Observer<List<Book>> observer = mock(Observer.class);
        bookViewModel.getBooks().observeForever(observer);

        bookViewModel.clearBooks();

        verify(observer, times(2)).onChanged(any());
        assertTrue(bookViewModel.getBooks().getValue().isEmpty());

        bookViewModel.getBooks().removeObserver(observer);
    }
}
