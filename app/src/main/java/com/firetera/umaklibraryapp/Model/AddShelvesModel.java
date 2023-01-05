package com.firetera.umaklibraryapp.Model;

public class AddShelvesModel {

    //upload to firebase
    String BORROWERID;
    String BOOKID;
    String IMAGE_URL;
    String NAME;
    String AUTHOR;

    public AddShelvesModel() {
    }

    public AddShelvesModel(String BORROWERID, String BOOKID, String IMAGE_URL, String NAME, String AUTHOR) {

        this.BORROWERID = BORROWERID;
        this.BOOKID = BOOKID;
        this.IMAGE_URL = IMAGE_URL;
        this.NAME = NAME;
        this.AUTHOR = AUTHOR;

    }

    public String getBORROWERID() {
        return BORROWERID;
    }

    public String getAUTHOR() {
        return AUTHOR;
    }

    public String getUSERID() {
        return BORROWERID;
    }

    public String getBOOKID() {
        return BOOKID;
    }

    public String getIMAGE_URL() {
        return IMAGE_URL;
    }

    public String getNAME() {
        return NAME;
    }
}
