package com.example.assignment1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

public class MyReceiver  extends BroadcastReceiver {
    private static String LOG_TAG = "Broadcast Receiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar calendar = Calendar.getInstance();
        StringBuilder time = new StringBuilder();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        String formattedTime = String.format("%d:%02d:%02d", hours, minutes,seconds);
        time.append(formattedTime);
        time.append(" - ");
        time.append("received message ");
        time.append(intent.getAction());
        Toast.makeText(context, time.toString(), Toast.LENGTH_LONG).show();
        Log.v(LOG_TAG, "Received Broadcast");
    }
}
