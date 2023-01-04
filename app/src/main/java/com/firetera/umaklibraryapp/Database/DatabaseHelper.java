package com.firetera.umaklibraryapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.firetera.umaklibraryapp.Model.AccountModel;

public class DatabaseHelper extends SQLiteOpenHelper {

    //table name
    public static final String TABLESTUDENT = "TABLESTUDENT";
    public static final String ID = "ID";
    public static final String EMAIL = "EMAIl";
    public static final String NAME = "NAME";
    public static final String GENDER = "GENDER";
    public static final String PHONENUMBER = "PHONENUMBER";
    public static final String SECTION = "SECTION";
    public static final String COURSE = "COURSE";
    public static final String COLLEGE = "COLLEGE";

    public DatabaseHelper(@Nullable Context context) { super(context, "heronslibrary.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createStudents = "CREATE TABLE " + TABLESTUDENT + "(" + NAME + " STRING NOT NULL, " + ID + " STRING NOT NULL, " + SECTION + " STRING NOT NULL, " + COURSE + " STRING NOT NULL, " +  COLLEGE + " STRING DEFAULT NULL, " + GENDER + " STRING NOT NULL ," + PHONENUMBER + " STRING DEFAULT NULL, " + EMAIL + " STRING NOT NULL )";
        db.execSQL(createStudents);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLESTUDENT);
        onCreate(db);
    }


    //TODO START OF ADDING DATA
    //add students
    public boolean addAccount(AccountModel accountModel) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ID, accountModel.getID());
        cv.put(NAME, accountModel.getNAME());
        cv.put(SECTION, accountModel.getSECTION());
        cv.put(COURSE, accountModel.getCOURSE());
        cv.put(COLLEGE, accountModel.getCOLLEGE());
        cv.put(GENDER, accountModel.getGENDER());
        cv.put(PHONENUMBER, accountModel.getPHONENUMBER());
        cv.put(EMAIL, accountModel.getEMAIL());

        long insert = db.insert(TABLESTUDENT, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    //logout
    public boolean logout(String value){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLESTUDENT,ID  + "=? ",new String[] {value});
        return true;
    }

    public Cursor getValue(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLESTUDENT, null);
        return  data;
    }

    //getName
    public Cursor getName(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT  "+ NAME + " FROM " + TABLESTUDENT, null);
        return  data;
    }

    //getSection
    public Cursor getSection(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT "+ SECTION + " FROM " + TABLESTUDENT, null);
        return  data;
    }

    //get Course
    public Cursor getCourse(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT "+ COURSE + " FROM " + TABLESTUDENT, null);
        return  data;
    }

    //get Course
    public Cursor getEmail(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT "+ EMAIL + " FROM " + TABLESTUDENT, null);
        return  data;
    }

    //get Course
    public Cursor getCollege(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT "+ COLLEGE + " FROM " + TABLESTUDENT, null);
        return  data;
    }









}
