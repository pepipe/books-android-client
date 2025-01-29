package com.example.booksclient.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.booksclient.model.domain.Book;

import java.util.ArrayList;
import java.util.List;

public class BookViewModel extends ViewModel {
    private MutableLiveData<List<Book>> books = new MutableLiveData<>();

    public BookViewModel() {
        books.setValue(new ArrayList<>());
    }

    public LiveData<List<Book>> getBooks() {
        return books;
    }

    public void addBooks(List<Book> newBooks) {
        List<Book> currentBooks = books.getValue();
        if (currentBooks != null) {
            currentBooks.addAll(newBooks);
            books.setValue(currentBooks);
        }
    }
}
