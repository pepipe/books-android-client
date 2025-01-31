package com.example.booksclient.presentation.booksDetails;

import static android.view.View.VISIBLE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.booksclient.R;
import com.example.booksclient.databinding.ActivityBookDetailsBinding;
import com.example.booksclient.domain.model.Book;
import com.example.booksclient.utils.LogHelper;

public class BookDetailsActivity extends AppCompatActivity {
    private ActivityBookDetailsBinding binding;
    private BookDetailsViewModel bookDetailsViewModel;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupBookDetailsViewModel();
        loadBookData();
    }

    private void setupBookDetailsViewModel() {
        bookDetailsViewModel = new ViewModelProvider(this).get(BookDetailsViewModel.class);
        bookDetailsViewModel.getError().observe(this, errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadBookData(){
        showLoading();

        book = (Book) getIntent().getSerializableExtra("BOOK");
        if (book == null){
            LogHelper.e("Error loading book details.");
            return;
        }

        bookDetailsViewModel.checkIfFavorite(book.getId(), (isFavorite) -> {
            book.setBookFavorite(isFavorite);
            updateBookUI(book, BookDetailsActivity.this);
            setupButtons();
            hideLoading();
        });
    }

    private void updateBookUI(@NonNull Book book, Context context) {
        String imageUrl = book.getImageUrl();
        Uri uri = Uri.parse(imageUrl);
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(context)
                    .load(uri)
                    .placeholder(R.drawable.baseline_menu_book_black_48)
                    .into(binding.bookImage);
        } else {
            binding.bookImage.setImageResource(R.drawable.baseline_menu_book_black_48);
        }

        binding.bookTitle.setText(book.getTitle());
        binding.bookAuthor.setText(book.getAuthors());
        binding.bookDescription.setText(book.getDescription());
    }

    private void setupButtons() {
        binding.backButton.setOnClickListener(v -> finish());
        binding.favoriteButton.setText(book.isBookFavorite() ?
                getString(R.string.unfavorite) :
                getString(R.string.favorite));
        binding.favoriteButton.setOnClickListener(v -> toggleFavoriteBook());

        if(!book.hasBuyLink()) return;
        binding.buyLinkButton.setVisibility(VISIBLE);
        binding.buyLinkButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(book.getBuyUrl()));

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "No browser app found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toggleFavoriteBook() {
        if (book.isBookFavorite()) {
            bookDetailsViewModel.removeBookFromFavorites(book.getId());
            binding.favoriteButton.setText(getString(R.string.favorite));
            book.setBookFavorite(false);
            setResult(Activity.RESULT_OK);
        } else {
            bookDetailsViewModel.addBookToFavorites(book.getId(), book.getBookJson());
            binding.favoriteButton.setText(getString(R.string.unfavorite));
            book.setBookFavorite(true);
            setResult(Activity.RESULT_OK);
        }
    }

    private void showLoading() {
        binding.detailsProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        binding.detailsProgressBar.setVisibility(View.GONE);
    }
}
