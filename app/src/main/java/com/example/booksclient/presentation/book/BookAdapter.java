package com.example.booksclient.presentation.book;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.booksclient.R;
import com.example.booksclient.domain.model.Book;
import com.example.booksclient.presentation.booksDetails.BookDetailsActivity;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private final List<Book> books;
    private final Context context;
    private final ActivityResultLauncher<Intent> resultLauncher;

    public BookAdapter(List<Book> books, Context context, ActivityResultLauncher<Intent> resultLauncher) {
        this.books = books;
        this.context = context;
        this.resultLauncher = resultLauncher;
    }

        @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);

        String title = book.getTitle();
        int maxLength = 22;  // Set max length according to your layout constraints
        if (title.length() > maxLength) {
            title = title.substring(0, maxLength) + "...";
        }

        //load book data
        holder.bookTitle.setText(title);
        String thumbnailUrl = book.getThumbnailUrl();
        Uri uri = Uri.parse(thumbnailUrl);
        if (thumbnailUrl != null && !thumbnailUrl.isEmpty()) {
            Glide.with(context)
                    .load(uri)
                    .placeholder(R.drawable.baseline_menu_book_20)
                    .into(holder.bookThumbnail);
        } else {
            holder.bookThumbnail.setImageResource(R.drawable.baseline_menu_book_20);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, BookDetailsActivity.class);
            intent.putExtra("BOOK", book);
            resultLauncher.launch(intent);
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void addBooks(List<Book> newBooks) {
        int previousSize = books.size();
        books.addAll(newBooks);
        notifyItemRangeInserted(previousSize, newBooks.size());
    }

    public void clearBooks() {
        int size = books.size();
        if (size > 0) {
            books.clear();
            notifyItemRangeRemoved(0, size);
        }
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView bookTitle;
        ImageView bookThumbnail;

        public BookViewHolder(View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.bookTitle);
            bookThumbnail = itemView.findViewById(R.id.bookThumbnail);
        }
    }
}
