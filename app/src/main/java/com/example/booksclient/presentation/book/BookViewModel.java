package com.example.booksclient.presentation.book;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.booksclient.data.repository.BookRepository;
import com.example.booksclient.domain.model.Book;
import com.example.booksclient.utils.LogHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookViewModel extends ViewModel {
    private static final int FETCH_BOOKS_MAX_RESULTS = 20;
    private final BookRepository bookRepository;
    private final MutableLiveData<List<Book>> booksLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private int currentOffset = 0;

    public BookViewModel() {
        bookRepository = new BookRepository();
        booksLiveData.setValue(new ArrayList<>());
    }

    public LiveData<List<Book>> getBooks() {
        return booksLiveData;
    }

    public LiveData<String> getError() {
        return errorLiveData;
    }

    public void clearBooks(){
        currentOffset = 0;
        booksLiveData.setValue(new ArrayList<>());
    }

    public void addBooks(String query) {
        bookRepository.fetchBooks(query, currentOffset, FETCH_BOOKS_MAX_RESULTS, new BookRepository.BooksFetchCallback() {
            @Override
            public void onBooksFetched(List<Book> books) {
                currentOffset += FETCH_BOOKS_MAX_RESULTS;
                booksLiveData.postValue(books);
            }

            @Override
            public void onError(Exception e) {
                errorLiveData.postValue("Failed to load books: " + e.getMessage());
                LogHelper.e("Problem loading books", e);
            }
        });
    }

    public void loadFavoriteBooks() {
        bookRepository.getFavoriteBooks(new BookRepository.BooksFetchCallback() {
            @Override
            public void onBooksFetched(List<Book> books) {
                currentOffset += FETCH_BOOKS_MAX_RESULTS;
                booksLiveData.postValue(books);
            }

            @Override
            public void onError(Exception e) {
                errorLiveData.postValue("Failed to load favorites books: " + e.getMessage());
                LogHelper.e("Problem loading favorites books", e);
            }
        });
    }

    public void setFavoritesPath(Context context) {
        String favoritesPath = Objects.requireNonNull(context.getExternalFilesDir(null)).getAbsolutePath() + "/favorites.txt";
        bookRepository.setFavoritesFilePath(favoritesPath);
    }
}
