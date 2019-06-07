package com.tgt.karthik.book;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private final LayoutInflater mInflater;
    private List<Book> contactListFiltered;
    List<Book> data;

    public ListAdapter(Context context, List<Book> data) {
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
    public void onBindViewHolder(@NonNull final ListAdapter.ListViewHolder listViewHolder, int i) {
        final Book currentBook = contactListFiltered.get(i);
        Log.d("OnBind", "data is " + currentBook.getTitle());
        listViewHolder.book_name.setText(currentBook.getTitle());
        listViewHolder.book_year.setText(String.valueOf(currentBook.getAuthor()));
        Picasso.with(listViewHolder.book_image.getContext()).load(currentBook.getCoverUrl()).placeholder(R.drawable.placeholder).into(listViewHolder.book_image);

        listViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), BookDetails.class);
                intent.putExtra("book", currentBook);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
            }
        });
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
