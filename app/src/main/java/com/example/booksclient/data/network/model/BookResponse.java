package com.example.booksclient.data.network.model;

public class BookResponse {
    String id;
    VolumeInfoResponse volumeInfo;
    SaleInfoResponse saleInfo;

    public String getId() {
        return id;
    }

    public String getTitle(){
        if(volumeInfo == null) return "";
        return volumeInfo.title;
    }

    public String getAuthors(){
        if(volumeInfo == null || volumeInfo.authors == null) return "";
        return volumeInfo.authors.toString();
    }

    public String getDescription(){
        return volumeInfo.description;
    }

    public String getSmallThumbnail(){
        if(volumeInfo == null || volumeInfo.imageLinks == null) return "";
        return volumeInfo.imageLinks.smallThumbnail;
    }

    public String getThumbnail(){
        if(volumeInfo == null || volumeInfo.imageLinks == null) return "";
        return volumeInfo.imageLinks.thumbnail;
    }

    public String getBuyLink(){
        if(saleInfo == null) return "";
        return saleInfo.buyLink;
    }
}