package com.example.booksclient.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.booksclient.model.domain.Book;

import java.util.ArrayList;
import java.util.List;

public class BookViewModel extends ViewModel {
    private MutableLiveData<List<Book>> booksLiveData = new MutableLiveData<>();

    public BookViewModel() {
        booksLiveData.setValue(new ArrayList<>());
    }

    public LiveData<List<Book>> getBooks() {
        return booksLiveData;
    }

    public void addBooks(List<Book> newBooks) {
        List<Book> currentBooks = booksLiveData.getValue();
        if (currentBooks != null) {
            currentBooks.addAll(newBooks);
            booksLiveData.setValue(currentBooks);
        }
    }
}
