package com.firetera.umaklibraryapp.Model;

public class AccountModel {
    //table name
    String ID;
    String NAME;
    String SECTION;
    String COURSE;
    String COLLEGE;
    String GENDER;
    String PHONENUMBER;
    String EMAIL;

    public AccountModel(String ID, String NAME, String SECTION, String COURSE, String COLLEGE, String GENDER, String PHONENUMBER, String EMAIL) {
        this.ID = ID;
        this.NAME = NAME;
        this.SECTION = SECTION;
        this.COURSE = COURSE;
        this.COLLEGE = COLLEGE;
        this.GENDER = GENDER;
        this.PHONENUMBER = PHONENUMBER;
        this.EMAIL = EMAIL;
    }

    public String getID() {
        return ID;
    }

    public String getNAME() {
        return NAME;
    }

    public String getSECTION() {
        return SECTION;
    }

    public String getCOURSE() {
        return COURSE;
    }

    public String getCOLLEGE() {
        return COLLEGE;
    }

    public String getGENDER() {
        return GENDER;
    }

    public String getPHONENUMBER() {
        return PHONENUMBER;
    }

    public String getEMAIL() {
        return EMAIL;
    }
}
