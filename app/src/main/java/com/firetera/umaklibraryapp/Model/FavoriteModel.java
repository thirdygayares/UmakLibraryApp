package com.firetera.umaklibraryapp.Model;

public class FavoriteModel {

    String id;
    String Title;
    String Image;
    String Author;

    public FavoriteModel(String id, String title, String image, String author) {
        this.id = id;
        Title = title;
        Image = image;
        Author = author;
    }

    public FavoriteModel(String id, String title, String image) {
        this.id = id;
        Title = title;
        Image = image;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return Title;
    }

    public String getImage() {
        return Image;
    }

    public String getAuthor() {
        return Author;
    }
}
