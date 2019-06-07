package com.tgt.karthik.book;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class BookDetails extends AppCompatActivity {

    Book currentBook;
    TextView textTitle, ISBN, authors, pub, desc, sum;
    BookClient client;
    ImageView bookImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        currentBook = (Book) getIntent().getSerializableExtra("book");

        textTitle = findViewById(R.id.textTitle);
        ISBN = findViewById(R.id.textISBN);
        authors = findViewById(R.id.textAuthors);
        pub = findViewById(R.id.textPub);
        desc = findViewById(R.id.textDesc);
        sum = findViewById(R.id.textSum);
        bookImage = findViewById(R.id.bookImage);

        Picasso.with(this).load(currentBook.getLargeCoverUrl()).placeholder(R.drawable.placeholder).into(bookImage);

        textTitle.setText(currentBook.getTitle());
        ISBN.setText(currentBook.getOpenLibraryId());
        authors.setText(currentBook.getAuthor());

        client = new BookClient();
        client.getExtraBookDetails(currentBook.getOpenLibraryId(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.has("publishers")) {
                        // display comma separated list of publishers
                        final JSONArray publisher = response.getJSONArray("publishers");
                        final int numPublishers = publisher.length();
                        final String[] publishers = new String[numPublishers];
                        for (int i = 0; i < numPublishers; ++i) {
                            publishers[i] = publisher.getString(i);
                        }
                        pub.setText(TextUtils.join(", ", publishers));
                    }
                    if (response.has("number_of_pages")) {
                        desc.setText(Integer.toString(response.getInt("number_of_pages")) + " pages");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
