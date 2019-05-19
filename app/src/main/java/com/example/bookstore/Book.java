package com.example.bookstore;

public class Book {
    private String title;
    private String author;
    private String thumbnail;
    private String webReaderLink;

    public Book(String title, String thumbnail, String webReaderLink){
        this.title = title;
        this.thumbnail = thumbnail;
        this.webReaderLink = webReaderLink;
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

    public String getWebReaderLink() {
        return webReaderLink;
    }
}
