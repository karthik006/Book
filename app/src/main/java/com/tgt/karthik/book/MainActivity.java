package com.tgt.karthik.book;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ListAdapter mAdapter;
    List<Books> data = null;
    DatabaseReference bookReference;
    ValueEventListener bookListener;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        i = 0;
        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new ListAdapter(this, getData());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<Books> getData()
    {

        data = new ArrayList<>();
        bookReference = FirebaseDatabase.getInstance().getReference().child("Books");

        bookListener = bookReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot childSnapshot : dataSnapshot.getChildren())
                {
                    int bookID = childSnapshot.child("book_id").getValue(Long.class).intValue();
                    String authors = childSnapshot.child("authors").getValue(String.class);
                    String title = childSnapshot.child("original_title").getValue(String.class);
                    int year = childSnapshot.child("original_publication_year").getValue(Long.class).intValue();
                    String imageURL = childSnapshot.child("image_url").getValue(String.class);
                    String language = childSnapshot.child("language_code").getValue(String.class);
                    float avg = childSnapshot.child("average_rating").getValue(Double.class).floatValue();
                    Books currentBook = new Books(bookID, authors, title, year, imageURL, language, avg);

                    if (!isExist(bookID, imageURL, avg)) {
                        data.add(currentBook);
                    }

                    mAdapter.notifyDataSetChanged();
                    i++;
                    if(i>=25)
                        break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return data;
    }

    boolean isExist(int id, String tImage, float avg)
    {
        int x = data.size();
        x--;
        boolean flag = false;
        while(x>=0)
        {
            if(data.get(x).getBook_id() == id)
            {
                flag = true;
                if(!(data.get(x).getImage_url().equals(tImage) && data.get(x).getAvg_rating() == avg))
                {
                    data.get(x).setImage_url(tImage);
                    data.get(x).setAvg_rating(avg);
                }
                break;
            }
            else
                flag = false;
            x--;
        }
        return flag;
    }

    @Override
    public void onDestroy() {
        if(bookListener!=null)
            bookReference.removeEventListener(bookListener);
        super.onDestroy();
    }
}
