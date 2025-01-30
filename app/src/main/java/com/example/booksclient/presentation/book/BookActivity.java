package com.example.booksclient.presentation.book;

import android.app.Activity;
import android.content.Intent;
<<<<<<< HEAD
import android.content.pm.ActivityInfo;
=======
>>>>>>> f3fba76ebe3b3ba71cd17efd7aea36ea2289046d
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booksclient.R;
import com.example.booksclient.databinding.ActivityBookBinding;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {
    private ActivityBookBinding binding;
    private BookAdapter booksAdapter;
    private BookViewModel bookViewModel;
    private ActivityResultLauncher<Intent> resultLauncher;
    private String lastQuery;
    private boolean favoritesFilterOn = false;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
=======
>>>>>>> f3fba76ebe3b3ba71cd17efd7aea36ea2289046d
        binding = ActivityBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.queryInputField.setOnEditorActionListener((v, actionId, event) -> {
            if ((actionId == EditorInfo.IME_ACTION_DONE) ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                loadMoreBooks();
                return true;
            }
            return false;
        });

        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), this::handleActivityResult
        );

        setupBookViewModel();
        setupBooksRecyclerView();
        setupButtons();

        loadMoreBooks();//initial load
    }

    private void setupBookViewModel() {
        bookViewModel = new ViewModelProvider(this).get(BookViewModel.class);
        bookViewModel.getBooks().observe(this, books -> {
            isLoading = false;
            if (books != null && !books.isEmpty()) {
                booksAdapter.addBooks(books);
                hideLoading();
            }
        });
        bookViewModel.getError().observe(this, errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
        bookViewModel.setFavoritesPath(this);
    }

    private void setupBooksRecyclerView() {
        binding.booksRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        booksAdapter = new BookAdapter(new ArrayList<>(), this, resultLauncher);
        binding.booksRecyclerView.setAdapter(booksAdapter);

        binding.booksRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
    }

    private void setupButtons() {
        binding.favoritesButton.setText(getString(R.string.show_favorites));
        binding.favoritesButton.setOnClickListener(v -> toggleFavorites());
        binding.fetchBooksButton.setOnClickListener(v -> loadMoreBooks());
    }

    private void toggleFavorites() {
        favoritesFilterOn = !favoritesFilterOn;
        binding.favoritesButton.setText(favoritesFilterOn ?
                getString(R.string.hide_favorites) :
                getString(R.string.show_favorites));
        clearBooks();
        if(favoritesFilterOn){
            loadFavoriteBooks();
        }
    }

    private void loadMoreBooks() {
        if(isLoading || favoritesFilterOn) return;

        showLoading();
        isLoading = true;

        String query = String.valueOf(binding.queryInputField.getText());
        query = query.isEmpty() ? "iOS" : query;

        if (!query.equals(lastQuery)) {
            lastQuery = query;
            clearBooks(); // Clear books before loading new ones
        }

        bookViewModel.addBooks(query);
    }

    private void loadFavoriteBooks() {
        if(isLoading || !favoritesFilterOn) return;

        showLoading();
        isLoading = true;
        bookViewModel.loadFavoriteBooks();
    }

    private void clearBooks() {
        bookViewModel.clearBooks();
        booksAdapter.clearBooks();
    }

    private void handleActivityResult(ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK) {
            if (favoritesFilterOn) {
                clearBooks();
                loadFavoriteBooks();
            }
        }
    }

    private void showLoading() {
        binding.loadingCircle.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        binding.loadingCircle.setVisibility(View.GONE);
    }
}