package com.example.assignment1;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.Nullable;

public class TimeService extends Service {
    private static String LOG_TAG = "BoundService";
    private IBinder timeBinder = new TimeBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.v(LOG_TAG, "in bind");
        return timeBinder;
    }

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public void onRebind(Intent intent){
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent){
        Log.v(LOG_TAG, "in onUnbind");
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(LOG_TAG, "in onDestroy");
    }

    public String getTime(){
        Calendar calendar = Calendar.getInstance();
        StringBuilder time = new StringBuilder();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        String formattedTime = String.format("%d:%02d", hours, minutes);
        time.append(formattedTime);
        return time.toString();
    }

    public class TimeBinder extends Binder {
        TimeService getService(){
            return TimeService.this;
        }
    }
}
