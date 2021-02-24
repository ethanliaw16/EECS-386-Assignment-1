package com.example.assignment1;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.Nullable;

public class TimeService extends Service {
    private static String LOG_TAG = "BoundService";
    private IBinder timeBinder = new TimeBinder();
    private HandlerThread mHandlerThread;
    private Handler mHandler;

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
    public int onStartCommand(Intent intent, int flags, int startId) {

        mHandlerThread = new HandlerThread("LocalServiceThread");
        mHandlerThread.start();

        mHandler = new Handler(mHandlerThread.getLooper());


        mHandler.post(new Runnable() {
            @Override
            public void run() {
                int i = 10;
                /*
                while (true){

                    new Handler(Looper.getMainLooper()).post(() -> {
                        Calendar calendar = Calendar.getInstance();
                        StringBuilder time = new StringBuilder();
                        int hours = calendar.get(Calendar.HOUR_OF_DAY);
                        int minutes = calendar.get(Calendar.MINUTE);
                        String formattedTime = String.format("%d:%02d", hours, minutes);
                        time.append(formattedTime);
                        Toast.makeText(TimeService.this,"Time reported from service: " + time.toString(), Toast.LENGTH_SHORT).show();
                    });

                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        i--;
                    }
                }
                */
                Calendar calendar = Calendar.getInstance();
                StringBuilder time = new StringBuilder();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int minutes = calendar.get(Calendar.MINUTE);
                String formattedTime = String.format("%d:%02d", hours, minutes);
                time.append(formattedTime);
                Toast.makeText(TimeService.this,"Time reported from service: " + time.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        ;

        return super.onStartCommand(intent, flags, startId);
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
