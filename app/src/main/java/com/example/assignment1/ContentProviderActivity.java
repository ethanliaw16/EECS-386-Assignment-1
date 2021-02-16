package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.UserDictionary;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ContentProviderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);

        List<String> wordsFromDictionary = new ArrayList<String>();
        ContentResolver resolver = getContentResolver();
        String[] projection = new String[]{BaseColumns._ID, UserDictionary.Words.WORD};
        Cursor cursor =
                resolver.query(UserDictionary.Words.CONTENT_URI,
                        projection,
                        null,
                        null,
                        null);
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(0);
                String word = cursor.getString(1);
                wordsFromDictionary.add(word);
                Log.v("content provider", word);
                // do something meaningful
            } while (cursor.moveToNext());
        }
        wordsFromDictionary.add("Next");
        wordsFromDictionary.add("Next Next");
        ArrayAdapter contactAdapter = new ArrayAdapter<String>(this,
                R.layout.activity_content_provider,
                wordsFromDictionary.toArray(new String[0]));
        ListView contactListView = findViewById(R.id.contactlistview);
        contactListView.setAdapter(contactAdapter);
    }
}