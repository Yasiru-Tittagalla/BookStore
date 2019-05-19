package com.example.bookstore;

public class Book {
    private String title;
    private String author;
    private String thumbnail;

    public Book(String title, String thumbnail){
        this.title = title;
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
