package com.firetera.umaklibraryapp.Model;

public class Model1 {
    String Id;
    String title;
    int image;
    String imagestring;

    public Model1(String id, String title, String imagestring) {
        Id = id;
        this.title = title;
        this.imagestring = imagestring;
    }

    public Model1(String title, int image) {
        this.title = title;
        this.image = image;
    }

    public Model1(String title, String imagestring) {
        this.title = title;
        this.imagestring = imagestring;
    }

    public String getImagestring() {
        return imagestring;
    }

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }

    public String getId() {
        return Id;
    }
}
