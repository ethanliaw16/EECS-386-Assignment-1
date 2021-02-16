package com.example.assignment1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactDatabase extends SQLiteOpenHelper {
    public static final String DB_NAME = "contact_data";
    public static final String TABLE = "contact";
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String PHONE = "phone";

    private static final String CREATE_TABLE_QUERY = "create table " + TABLE + " (" + ID + " integer primary key autoincrement, " +
            NAME + " text, " + PHONE + " text);";

    private static final String dbSchema = CREATE_TABLE_QUERY;

    public static final String[] ALL_COLUMNS = {ID, NAME, PHONE};

    public ContactDatabase(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(dbSchema);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }
}
