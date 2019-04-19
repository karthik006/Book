package com.tgt.karthik.book;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //public final TextView wordItemView;
        final ListAdapter mAdapter;

        public ListViewHolder(View itemView, ListAdapter adapter) {
            super(itemView);
            //wordItemView = itemView.findViewById(R.id.word);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
