/*
This class represents a single book item
which is used all over the system
 */
package com.example.bookstore;

public class Book {
    private String title;
    private String author;
    private String thumbnail;
    private String webReaderLink;
    private String description;
    private String bookId;

    public Book(String title, String thumbnail, String webReaderLink, String description, String bookId){
        this.title = title;
        this.thumbnail = thumbnail;
        this.webReaderLink = webReaderLink;
        this.description = description;
        this.bookId = bookId;
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

    public String getWebReaderLink() { return webReaderLink; }

    public String getDescription() { return description; }

    public String getBookId() { return bookId; }
}
