package com.firetera.umaklibraryapp.extension;

import android.content.Context;
import android.database.Cursor;

import com.firetera.umaklibraryapp.Database.DatabaseHelper;

public class MySection {
    public static String name;

    DatabaseHelper databaseHelper;

    public MySection(Context context) {
        databaseHelper = new DatabaseHelper(context);
        Cursor getName = databaseHelper.getSection();

        if(getName.getCount() > 0){
            while (getName.moveToNext()){
                this.name = getName.getString(0);
            }
        }
    }

    public static String getName() {
        return name;
    }
}
