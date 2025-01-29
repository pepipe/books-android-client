package com.example.booksclient.model;

import java.io.Serializable;

public class Book implements Serializable {
    private String title;
    private String author;
    private String description;
    private String imageUrl;
    private String thumbnailUrl;
    private String buyUrl;

    public Book(String title, String author, String description, String imageUrl, String thumbnailUrl, String buyUrl){
        this.title = title;
        this.author = author;
        this.description = description;
        this.imageUrl = imageUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.buyUrl = buyUrl;
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
}
