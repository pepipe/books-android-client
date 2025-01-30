package com.example.booksclient;

import java.util.List;

public class NativeApi {
    static {
        System.loadLibrary("BooksSDK");
    }

    public static native String fetchBooks(String query, int startIndex, int maxResults);
    public static native void addToFavorites(String bookId, String bookJson);
    public static native void removeFromFavorites(String bookId);
    public static native boolean isFavorite(String bookId);
    public static native List<String> getFavoriteBooks();
    public static native void setFavoritesFilePath(String favoritesFilePath);
    public static native String testNative();
}
