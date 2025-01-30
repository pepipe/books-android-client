package com.example.booksclient.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

public class FavoriteBooksHelper {
    private FavoriteBooksHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static String createBookResponse(List<String> jsonStrings) {
        Gson gson = new Gson();
        JsonArray itemsArray = new JsonArray();

        for (String jsonString : jsonStrings) {
            itemsArray.add(gson.toJsonTree(gson.fromJson(jsonString, Object.class)));
        }

        JsonObject rootObject = new JsonObject();
        rootObject.add("items", itemsArray);

        return gson.toJson(rootObject);
    }
}
