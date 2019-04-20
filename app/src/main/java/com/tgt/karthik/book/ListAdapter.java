package com.tgt.karthik.book;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private final LayoutInflater mInflater;
    private List<Books> contactListFiltered;
    List<Books> data;

    public ListAdapter(Context context, List<Books> data) {
        mInflater = LayoutInflater.from(context);
        this.contactListFiltered = data;
        this.data = data;
    }

    @NonNull
    @Override
    public ListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mItemView = mInflater.inflate(R.layout.book_list, viewGroup, false);
        return new ListViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ListViewHolder listViewHolder, int i) {
        Books currentBook = contactListFiltered.get(i);
        listViewHolder.book_name.setText(currentBook.getOriginal_title());
        listViewHolder.book_year.setText(String.valueOf(currentBook.getPub_year()));
        Picasso.with(listViewHolder.book_image.getContext()).load(currentBook.getImage_url()).placeholder(R.drawable.googleg_disabled_color_18).into(listViewHolder.book_image);
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView book_name, book_year;
        ImageView book_image;
        final ListAdapter mAdapter;

        public ListViewHolder(View itemView, ListAdapter adapter) {
            super(itemView);
            book_name = itemView.findViewById(R.id.book_name);
            book_year = itemView.findViewById(R.id.book_year);
            book_image = itemView.findViewById(R.id.book_image);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
