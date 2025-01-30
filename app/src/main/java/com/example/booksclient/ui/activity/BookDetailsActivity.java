package com.example.booksclient.ui.activity;

import static android.view.View.VISIBLE;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.booksclient.MainActivity;
import com.example.booksclient.NativeApi;
import com.example.booksclient.R;
import com.example.booksclient.model.domain.Book;
import com.google.android.material.button.MaterialButton;

public class BookDetailsActivity extends AppCompatActivity {
    private ProgressBar detailsProgressBar;
    private MaterialButton favoriteButton;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        detailsProgressBar = findViewById(R.id.detailsProgressBar);

        showLoading();
        // Get data passed from the adapter
        book = (Book) getIntent().getSerializableExtra("BOOK");
        if (book == null) return;
        boolean isBookFavorite = NativeApi.isFavorite(book.getId());
        book.setBookFavorite(isBookFavorite);

        //Initialize favorite button
        favoriteButton = findViewById(R.id.favoriteButton);
        favoriteButton.setText(book.isBookFavorite() ?
                getString(R.string.unfavorite) :
                getString(R.string.favorite));
        favoriteButton.setOnClickListener(v -> toggleFavoriteBook());

        // Initialize views
        ImageView bookImage = findViewById(R.id.bookImage);
        TextView bookTitle = findViewById(R.id.bookTitle);
        TextView bookAuthor = findViewById(R.id.bookAuthor);
        TextView bookDescription = findViewById(R.id.bookDescription);
        Button buyLinkButton = findViewById(R.id.buyLinkButton);
        Button backButton = findViewById(R.id.backButton);

        // Set data to views
        String imageUrl = book.getImageUrl();
        Uri uri = Uri.parse(imageUrl);
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(this)
                    .load(uri)
                    .placeholder(R.drawable.baseline_menu_book_black_48)
                    .into(bookImage);
        } else {
            bookImage.setImageResource(R.drawable.baseline_menu_book_black_48);
        }

        bookTitle.setText(book.getTitle());
        bookAuthor.setText(book.getAuthor());
        bookDescription.setText(book.getDescription());
        backButton.setOnClickListener(v -> finish());

        hideLoading();

        if(!book.hasBuyLink()) return;

        buyLinkButton.setVisibility(VISIBLE);
        buyLinkButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(book.getBuyUrl()));

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                // Handle case where no app can handle the intent (optional)
                Toast.makeText(this, "No browser app found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toggleFavoriteBook() {
        if (book.isBookFavorite()) {
            //unfavorite book
            NativeApi.removeFromFavorites(book.getId());
            favoriteButton.setText(getString(R.string.favorite));
        } else {
            //favorite book
            NativeApi.addToFavorites(book.getId(), book.getBookJson());
            favoriteButton.setText(getString(R.string.unfavorite));
        }
    }

    private void showLoading() {
        detailsProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        detailsProgressBar.setVisibility(View.GONE);
    }
}
