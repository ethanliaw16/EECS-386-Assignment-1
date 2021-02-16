package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class BroadcastActivity extends AppCompatActivity {
    private static String LOG_TAG = "Broadcast Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
        Button sendBroadcastButton = (Button)findViewById(R.id.broadcastintentbutton);
        sendBroadcastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                broadcastIntent(v);
            }
        });
    }

    public void broadcastIntent(View view) {
        Intent intent = new Intent("com.assignment1.MY_INTENT");
        //intent.setAction("com.assignment1.TIME_INTENT");
        sendBroadcast(intent);
        Log.v(LOG_TAG, "sent message " + intent.getAction().toString());
    }
}