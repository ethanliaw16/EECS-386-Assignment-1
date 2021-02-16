package com.example.assignment1;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ContactContentProvider extends ContentProvider {
    private static final String AUTHORITY = "com.example.assignment1.contacts";
    private static final String BASE_PATH = "contacts";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int CONTACTS = 1;
    private static final int CONTACT = 2;
    static{
        uriMatcher.addURI(AUTHORITY, BASE_PATH, CONTACTS);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", CONTACT);
    }

    SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        db = new ContactDatabase(getContext()).getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.v("query uri: ", uri.toString());
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case 1:
                cursor = db.query(ContactDatabase.TABLE, ContactDatabase.ALL_COLUMNS, selection
                        , null, null, null, ContactDatabase.NAME + " ASC");
                break;
            default:
                throw new IllegalArgumentException("This is an Unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.v("insert uri: ", uri.toString());
        long id = db.insert(ContactDatabase.TABLE, null, values);

        if (id > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, id);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Insertion Failed for URI :" + uri);

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numToDelete = 0;
        switch (uriMatcher.match(uri)){
            case CONTACTS:
                numToDelete = db.delete(ContactDatabase.TABLE, selection,selectionArgs);
                break;
            case CONTACT:
                numToDelete = db.delete(ContactDatabase.TABLE, selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Error - unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return numToDelete;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numToUpdate = 0;
        switch (uriMatcher.match(uri)) {
            case 1:
                numToUpdate = db.update(ContactDatabase.TABLE, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("This is an Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return numToUpdate;
    }
}
