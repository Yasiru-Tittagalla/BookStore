package com.example.bookstore;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class userWishList {
    String userName;
    String bookId;
    String wish;

    public userWishList(String userName, String bookId, String wish) {
        this.userName = userName;
        this.bookId = bookId;
        this.wish = wish;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getWish() {
        return wish;
    }

    public void setWish(String wish) {
        this. wish = wish;
    }
}
