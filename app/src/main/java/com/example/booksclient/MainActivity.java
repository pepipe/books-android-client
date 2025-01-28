package com.example.booksclient;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booksclient.ui.adapter.BooksAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int FETCH_BOOKS_MAX_RESULTS = 20;
    private int currentOffset = 0;
    private boolean isLoading = false;
    private BooksAdapter booksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Setup RecyclerView
        RecyclerView booksRecyclerView = findViewById(R.id.booksRecyclerView);
        booksRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        booksAdapter = new BooksAdapter(new ArrayList<>());
        booksRecyclerView.setAdapter(booksAdapter);

        // Add scroll listener for lazy loading
        booksRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                boolean isScrollVisible = recyclerView.canScrollVertically(-1) || recyclerView.canScrollVertically(1);
                if(!isScrollVisible) return;

                if (!recyclerView.canScrollVertically(1) && !isLoading) {
                    loadMoreBooks();
                }
            }
        });

        // Fetch books button
        findViewById(R.id.fetchBooksButton).setOnClickListener(v -> {
            loadMoreBooks(); // Fetch books
        });
    }

    private void loadMoreBooks() {
        isLoading = true;

        // Simulate fetching books (replace with JNI call or API call)
        new Thread(() -> {
            try {
                Thread.sleep(1000); // Simulate network delay

                // Generate dummy data for books
                List<String> newBooks = new ArrayList<>();
                for (int i = 0; i < FETCH_BOOKS_MAX_RESULTS; i++) {
                    newBooks.add("Book " + (currentOffset + i + 1));
                }

                // Update UI on the main thread
                runOnUiThread(() -> {
                    booksAdapter.addBooks(newBooks);
                    currentOffset += FETCH_BOOKS_MAX_RESULTS;
                    isLoading = false;
                });
            } catch (InterruptedException e) {
                runOnUiThread(() -> Toast.makeText(this, "Failed to load books", Toast.LENGTH_SHORT).show());
                isLoading = false;
            }
        }).start();
    }
}