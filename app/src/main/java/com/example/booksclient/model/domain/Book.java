package com.example.booksclient.model.domain;

import java.io.Serializable;

public class Book implements Serializable {
    private final String id;
    private final String title;
    private final String author;
    private final String description;
    private final String imageUrl;
    private final String thumbnailUrl;
    private final String buyUrl;
    private String bookJson;
    private boolean isFavorite;

    public Book(String id, String title, String author, String description, String imageUrl, String thumbnailUrl, String buyUrl){
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.imageUrl = imageUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.buyUrl = buyUrl;
    }

    public String getId(){
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getBuyUrl() {
        return buyUrl;
    }

    public boolean hasBuyLink() {
        return buyUrl != null && !buyUrl.isEmpty();
    }

    public String getBookJson(){
        return bookJson;
    }

    public void setBookJson(String bookJson) {
        this.bookJson = bookJson;
    }

    public boolean isBookFavorite(){
        return isFavorite;
    }

    public void setBookFavorite(boolean isFavorite){
        this.isFavorite = isFavorite;
    }
}
