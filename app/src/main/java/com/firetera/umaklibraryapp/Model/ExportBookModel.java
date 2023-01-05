package com.firetera.umaklibraryapp.Model;

public class ExportBookModel {

        String BOOK_ID;
        String TITLE;
        String IMAGE_URL;
        String AUTHOR;
        String CATEGORY;
        String ADDEDBY;


    public ExportBookModel(String BOOK_ID, String TITLE, String IMAGE_URL, String AUTHOR, String CATEGORY, String ADDEDBY) {
        this.BOOK_ID = BOOK_ID;
        this.TITLE = TITLE;
        this.IMAGE_URL = IMAGE_URL;
        this.AUTHOR = AUTHOR;
        this.CATEGORY = CATEGORY;
        this.ADDEDBY = ADDEDBY;
    }

    public String getADDEDBY() {
        return ADDEDBY;
    }

    public String getBOOK_ID() {
        return BOOK_ID;
    }

    public String getTITLE() {
        return TITLE;
    }

    public String getIMAGE_URL() {
        return IMAGE_URL;
    }

    public String getAUTHOR() {
        return AUTHOR;
    }

    public String getCATEGORY() {
        return CATEGORY;
    }
}
