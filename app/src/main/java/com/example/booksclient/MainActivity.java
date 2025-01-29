package com.example.booksclient;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booksclient.model.domain.Book;
import com.example.booksclient.model.parsers.BooksParser;
import com.example.booksclient.ui.viewmodel.BookViewModel;
import com.example.booksclient.ui.adapter.BooksAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private static final int FETCH_BOOKS_MAX_RESULTS = 5;
    private int currentOffset = 0;
    private boolean isLoading = false;
    private RecyclerView booksRecyclerView;
    private ProgressBar globalProgressBar;
    private BooksAdapter booksAdapter;
    private BookViewModel bookViewModel;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        globalProgressBar = findViewById(R.id.globalProgressBar);
        bookViewModel = new ViewModelProvider(this).get(BookViewModel.class);

        //Setup RecyclerView
        booksRecyclerView = findViewById(R.id.booksRecyclerView);
        booksRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        booksAdapter = new BooksAdapter(new ArrayList<>(), this);
        booksRecyclerView.setAdapter(booksAdapter);

        bookViewModel.getBooks().observe(this, books -> {
            if (books != null) {
                int currentBookCount = booksAdapter.getItemCount();
                List<Book> newBooks = books.subList(currentBookCount, books.size());
                booksAdapter.addBooks(newBooks);
                hideLoading();
            }
        });

        // Add scroll listener for lazy loading
        booksRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                boolean isScrollVisible = recyclerView.canScrollVertically(-1) || recyclerView.canScrollVertically(1);
                if (!isScrollVisible) return;

                if (!recyclerView.canScrollVertically(1) && !isLoading) {
                    loadMoreBooks();
                }
            }
        });

        // Fetch books button
        findViewById(R.id.fetchBooksButton).setOnClickListener(v -> loadMoreBooks());
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Save the scroll position
        if (booksRecyclerView != null && booksRecyclerView.getLayoutManager() != null) {
            GridLayoutManager layoutManager = (GridLayoutManager) booksRecyclerView.getLayoutManager();
            int scrollPosition = layoutManager.findFirstVisibleItemPosition();

            SharedPreferences sharedPreferences = getSharedPreferences("BookApp", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("scroll_position", scrollPosition);
            editor.apply();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Restore the scroll position after the books are loaded
        SharedPreferences sharedPreferences = getSharedPreferences("BookApp", MODE_PRIVATE);
        int scrollPosition = sharedPreferences.getInt("scroll_position", 0);

        // Observe LiveData to restore scroll position after data is updated
        bookViewModel.getBooks().observe(this, books -> {
            if (booksRecyclerView != null && booksRecyclerView.getLayoutManager() != null) {
                GridLayoutManager layoutManager = (GridLayoutManager) booksRecyclerView.getLayoutManager();
                layoutManager.scrollToPosition(scrollPosition);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current offset and other necessary state
        outState.putInt("currentOffset", currentOffset);
    }

    private void loadMoreBooks() {
        if(isLoading) return;

        showLoading();
        isLoading = true;

        // Execute the background task
        executorService.submit(() -> {
            try {
                String testNative = NativeApi.testNative();
                Log.i("JNI_TEST", "Result from native: " + testNative);
                String jsonResponse = NativeApi.fetchBooks("ios", currentOffset, FETCH_BOOKS_MAX_RESULTS);
                List<Book> newBooks = BooksParser.parseBooksJson(jsonResponse);

                // Update UI on the main thread
                runOnUiThread(() -> {
                    bookViewModel.addBooks(newBooks);
                    currentOffset += FETCH_BOOKS_MAX_RESULTS;
                    isLoading = false;
                });
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(this, "Failed to load books: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                isLoading = false;
            }
        });
    }

    private void showLoading() {
        globalProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        globalProgressBar.setVisibility(View.GONE);
    }
}