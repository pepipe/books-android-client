package com.example.booksclient.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booksclient.ui.BookDetailsActivity;
import com.example.booksclient.R;
import com.example.booksclient.model.Book;

import java.util.Arrays;
import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {
    private final List<Book> books;
    private final Context context;

    public BooksAdapter(List<Book> books, Context context){
        this.books = books;
        this.context = context;
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
        String[] words = title.split(" ");
        if (words.length > 5) {
            title = TextUtils.join(" ", Arrays.copyOfRange(words, 0, 5)) + "...";
        }

        holder.bookTitle.setText(title);
        //TODO set with the actual data
        holder.bookThumbnail.setImageResource(R.drawable.baseline_menu_book_20);
//        Glide.with(context)
//                .load(book.getImageUrl())  // Use URL or resource ID here
//                .into(holder.bookThumbnail);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, BookDetailsActivity.class);
            intent.putExtra("BOOK", book);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void addBooks(List<Book> newBooks) {
        int previousSize = books.size();
        books.addAll(newBooks);
        notifyItemRangeInserted(previousSize, books.size());
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
