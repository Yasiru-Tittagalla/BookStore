package com.example.bookstore;

public class Books {
    private String title;
    private String description;
    private String imageUrl;

    public Books(String title, String description, String imageUrl) {
        this.setTitle(title);
        this.setDescription(description);
        this.setImageUrl(imageUrl);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
