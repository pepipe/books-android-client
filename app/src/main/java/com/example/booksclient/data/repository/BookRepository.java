package com.example.booksclient.data.repository;

import android.os.Handler;
import android.os.Looper;

import com.example.booksclient.NativeApi;
import com.example.booksclient.domain.model.Book;
import com.example.booksclient.mapper.BookParser;
import com.example.booksclient.mapper.FavoriteBooksHelper;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookRepository {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public void fetchBooks(String query, int offset, int maxResults, final BooksFetchCallback callback) {
        executorService.submit(() -> {
            try {
                String jsonResponse = NativeApi.fetchBooks(query, offset, maxResults);
                List<Book> newBooks = BookParser.parseBooksJson(jsonResponse);
                callback.onBooksFetched(newBooks);
            } catch (Exception e) {
                callback.onError(e);
            }
        });
    }

    public void getFavoriteBooks(final BooksFetchCallback callback) {
        executorService.submit(() -> {
            try{
                List<String> favoriteBooks = NativeApi.getFavoriteBooks();
                String favoriteBooksJson = FavoriteBooksHelper.createBookResponse(favoriteBooks);
                List<Book> newBooks = BookParser.parseBooksJson(favoriteBooksJson);
                callback.onBooksFetched(newBooks);
            } catch (Exception e) {
                callback.onError(e);
            }
        });
    }

    public void isBookFavorite(String bookId, final IsBookFavoriteCallback callback){
        executorService.submit(() -> {
            try {
                boolean isFavorite = NativeApi.isFavorite(bookId);
                new Handler(Looper.getMainLooper()).post(() -> callback.onIsFavoriteBook(isFavorite));

            } catch (Exception e) {
                callback.onError(e);
            }
        });
    }

    public void removeBookFromFavorites(String bookId, final ManageFavoritesCallback callback) {
        executorService.submit(() ->{
           try{
               NativeApi.removeFromFavorites(bookId);
               new Handler(Looper.getMainLooper()).post(callback::onSuccess);
           } catch (Exception e) {
               callback.onError(e);
           }
        });
    }

    public void addBookToFavorites(String bookId, String bookJson, final ManageFavoritesCallback callback) {
        executorService.submit(() -> {
           try {
               NativeApi.addToFavorites(bookId, bookJson);
               new Handler(Looper.getMainLooper()).post(callback::onSuccess);
           } catch (Exception e) {
               callback.onError(e);
           }
        });
    }

    public void setFavoritesFilePath(String favoritesPath) {
        NativeApi.setFavoritesFilePath(favoritesPath);
    }

    public interface BooksFetchCallback {
        void onBooksFetched(List<Book> books);
        void onError(Exception e);
    }

    public interface IsBookFavoriteCallback {
        void onIsFavoriteBook(boolean isFavorite);
        void onError(Exception e);
    }

    public interface ManageFavoritesCallback {
        void onSuccess();
        void onError(Exception e);
    }
}
