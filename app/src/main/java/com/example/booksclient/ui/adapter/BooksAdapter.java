package com.example.booksclient.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {
    private List<String> books;

    public BooksAdapter(List<String> books){
        this.books = books;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        holder.bookTitle.setText(books.get(position));
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
            bookTitle = itemView.findViewById(android.R.id.text1);
        }
    }
}
