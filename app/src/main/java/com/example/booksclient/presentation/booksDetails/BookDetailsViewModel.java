package com.example.booksclient.presentation.booksDetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.booksclient.data.repository.BookRepository;
import com.example.booksclient.utils.LogHelper;

public class BookDetailsViewModel extends ViewModel {
    private final BookRepository bookRepository;
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public BookDetailsViewModel(){
        bookRepository = new BookRepository();
    }

    public LiveData<String> getError() {
        return errorLiveData;
    }

    public void checkIfFavorite(String bookId, final IsFavoriteCallback callback) {
        bookRepository.isBookFavorite(bookId, new BookRepository.IsBookFavoriteCallback() {
            @Override
            public void onIsFavoriteBook(boolean isFavorite) {
                callback.onResult(isFavorite);
            }

            @Override
            public void onError(Exception e) {
                errorLiveData.postValue("Failed check if book(" + bookId + ") is favorite: "
                        + e.getMessage());
                callback.onResult(false);
                LogHelper.e("Failed check if book(" + bookId + ") is favorite.", e);
            }
        });
    }

    public void removeBookFromFavorites(String bookId) {
        bookRepository.removeBookFromFavorites(bookId, new BookRepository.ManageFavoritesCallback() {
            @Override
            public void onSuccess() {}

            @Override
            public void onError(Exception e) {
                errorLiveData.postValue("Failed to remove book(" + bookId + ") from favorites: "
                        + e.getMessage());
                LogHelper.e("Failed to remove book(\" + bookId + \") from favorites.", e);
            }
        });
    }

    public void addBookToFavorites(String bookId, String bookJson) {
        bookRepository.addBookToFavorites(bookId, bookJson, new BookRepository.ManageFavoritesCallback() {
            @Override
            public void onSuccess() {}

            @Override
            public void onError(Exception e) {
                errorLiveData.postValue("Failed to add book(" + bookId + ") to favorites: "
                        + e.getMessage());
                LogHelper.e("Failed to add book(\" + bookId + \") to favorites.", e);
            }
        });
    }

    public interface IsFavoriteCallback {
        void onResult(boolean isFavorite);
    }
}
