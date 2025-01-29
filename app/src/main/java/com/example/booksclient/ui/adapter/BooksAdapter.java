package com.example.booksclient.ui.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booksclient.R;

import java.util.Arrays;
import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {
    private List<String> books;

    public BooksAdapter(List<String> books){
        this.books = books;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        String title = books.get(position);

        // Limit to 5 words (you can adjust this value)
        String[] words = title.split(" ");
        if (words.length > 5) {
            title = TextUtils.join(" ", Arrays.copyOfRange(words, 0, 5)) + "...";
        }

        holder.bookTitle.setText(title);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void addBooks(List<String> newBooks) {
        books.addAll(newBooks);
        notifyDataSetChanged();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView bookTitle;

        public BookViewHolder(View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.bookTitle);
        }
    }
}
