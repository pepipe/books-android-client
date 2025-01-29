package com.example.booksclient.ui.activity;

import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.booksclient.MainActivity;
import com.example.booksclient.R;
import com.example.booksclient.model.domain.Book;

public class BookDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        // Get data passed from the adapter
        Book book = (Book) getIntent().getSerializableExtra("BOOK");
        if (book == null) return;

        // Initialize views
        ImageView bookImage = findViewById(R.id.bookImage);
        TextView bookTitle = findViewById(R.id.bookTitle);
        TextView bookAuthor = findViewById(R.id.bookAuthor);
        TextView bookDescription = findViewById(R.id.bookDescription);
        Button buyLinkButton = findViewById(R.id.buyLinkButton);
        Button backButton = findViewById(R.id.backButton);

        // Set data to views
        bookTitle.setText(book.getTitle());
        bookAuthor.setText(book.getAuthor());
        bookDescription.setText(book.getDescription());
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(BookDetailsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        if(!book.hasBuyLink()) return;

        buyLinkButton.setVisibility(VISIBLE);
        buyLinkButton.setOnClickListener(v -> {
            // Handle buy action here (e.g., open a link)
        });
    }
}
