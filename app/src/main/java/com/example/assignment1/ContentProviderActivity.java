package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.UserDictionary;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ContentProviderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);

        refresh(findViewById(R.id.refreshbutton));
    }

    public void refresh(View view){
        Uri uri = ContactContentProvider.CONTENT_URI;
        Cursor cursor = this.getContentResolver().query(uri,null,null,null,null);
        List<String> contacts = new ArrayList<String>();
        while (cursor.moveToNext()){
            StringBuilder contactEntry = new StringBuilder();
            contactEntry.append(cursor.getString(0) + " - ");
            contactEntry.append(cursor.getString(1) + " - ");
            contactEntry.append(cursor.getString(2));
            contacts.add(contactEntry.toString());
        }
        ArrayAdapter<String> newContactAdapter = new ArrayAdapter<String>(getBaseContext(),
                android.R.layout.simple_list_item_1,
                contacts.toArray(new String[0]));
        ListView contactListView = findViewById(R.id.contactlistview);
        contactListView.setAdapter(newContactAdapter);
    }

    public void addContact(View view){
        EditText nameField = findViewById(R.id.namefield);
        EditText phoneField = findViewById(R.id.phonefield);
        if(nameField.getText().length() == 0 || phoneField.getText().length() == 0){
            Log.v("content provider", "we saw empties");
            Toast.makeText(getBaseContext(), "Both Name and Phone Number are Required", Toast.LENGTH_SHORT).show();
        }
        else{
            ContentValues values  = new ContentValues();
            values.put(ContactDatabase.ID,new Random().nextInt(100));
            values.put(ContactDatabase.NAME,nameField.getText().toString());
            values.put(ContactDatabase.PHONE,phoneField.getText().toString());
            getApplicationContext().getContentResolver().insert(ContactContentProvider.CONTENT_URI,values);
            Toast.makeText(this,"Contact Added",Toast.LENGTH_SHORT).show();
            refresh(view);
        }
    }

    public void clearAll(View view){
        int numToDelete = getContentResolver().delete(ContactContentProvider.CONTENT_URI,null,null);
        Toast.makeText(this,"Deleting " + numToDelete + " row(s)",Toast.LENGTH_SHORT).show();
        refresh(view);
    }
}