package com.tgt.karthik.book;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ListAdapter mAdapter;
    List<Book> data = null;
    EditText searchText;
    Button searchButton;
    BookClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchText = findViewById(R.id.editText);
        searchButton = findViewById(R.id.searchButton);
        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new ListAdapter(this, getData("Lord of the rings"));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchString = searchText.getText().toString().trim();

                mAdapter = new ListAdapter(getApplicationContext(), getData(searchString));
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }
        });

    }

    private List<Book> getData(String searchString)
    {

        data = new ArrayList<>();

        client = new BookClient();
        client.getBooks(searchString, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray docs ;
                    if(response != null) {
                        docs = response.getJSONArray("docs");
                        final ArrayList<Book> books = Book.fromJson(docs);
                        data.clear();
                        data.addAll(books);
                        if(!data.isEmpty())
                            Log.d("GetData", "onSuccess: " + data.get(0).getTitle() + " " + data.size());
                        mAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return data;
    }
}
