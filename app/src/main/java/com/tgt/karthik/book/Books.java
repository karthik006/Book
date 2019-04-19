package com.tgt.karthik.book;

public class Books {

    int book_id;
    String authors;
    String original_title;
    int pub_year;
    String image_url;
    String language;
    float avg_rating;

    Books(int book_id, String authors, String original_title, int pub_year, String image_url, String language, float avg_rating)
    {
        this.book_id = book_id;
        this.authors = authors;
        this.original_title = original_title;
        this.pub_year = pub_year;
        this.image_url = image_url;
        this.language = language;
        this.avg_rating = avg_rating;
    }

}
