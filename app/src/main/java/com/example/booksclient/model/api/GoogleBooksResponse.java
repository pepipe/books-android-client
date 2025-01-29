package com.example.booksclient.model.api;

import java.util.List;

public class GoogleBooksResponse {
    List<BookResponse> items;

    public Boolean hasItems(){
        return items != null && !items.isEmpty();
    }

    public List<BookResponse> getItems() {
        return items;
    }
}
