package com.tgt.karthik.book;

public class Books {

    private int book_id;
    private String authors;
    private String original_title;
    private int pub_year;
    private String image_url;
    private String language;
    private float avg_rating;

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

    public int getBook_id() {
        return book_id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public int getPub_year() {
        return pub_year;
    }

    public String getImage_url() {
        return image_url;
    }

    public float getAvg_rating() {
        return avg_rating;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setAvg_rating(float avg_rating) {
        this.avg_rating = avg_rating;
    }
}
