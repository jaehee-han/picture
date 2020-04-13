package com.oasyss.picturebook.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DB_V = 1;

    public DBHelper(Context context){
        super(context, "app_picture", null, DB_V);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String pictureSQL = "CREATE TABLE APP_USER "
                          + "(_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                          + "DEVICE_ID, CHAR_NAME, CHAR_ID, FDATE, LDATE)";
        db.execSQL(pictureSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        if(newV == DB_V){
            db.execSQL("DROP TABLE APP_USER");
            onCreate(db);
        }
    }
}
