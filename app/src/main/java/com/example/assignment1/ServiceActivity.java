package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ServiceActivity extends AppCompatActivity {
    TimeService timeService;
    boolean timeServiceBound = false;
    Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        final TextView timestampText = (TextView) findViewById(R.id.messagefromservice);
        Context serviceActivityContext = getApplicationContext();
        Button getMessageButton = (Button) findViewById(R.id.getmessagebutton);
        Button stopServiceButton = (Button) findViewById(R.id.stopservicebutton);
        Button startServiceButton = (Button) findViewById(R.id.startservicebutton);
        mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {

            }
        });
        getMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timeServiceBound) {
                    String newText = "Message from service: " + timeService.getTime();
                    timestampText.setText(newText);
                    Toast.makeText(serviceActivityContext, "Recieved New Message", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(serviceActivityContext, "No messages - the service was stopped", Toast.LENGTH_SHORT).show();
                }
            }

        });

        stopServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timeServiceBound) {
                    unbindService(timeServiceConnection);
                    timeServiceBound = false;
                }
                Intent intent = new Intent(ServiceActivity.this,
                        TimeService.class);
                stopService(intent);
                Toast stopServiceToast = Toast.makeText(serviceActivityContext, "Service Stopped", Toast.LENGTH_SHORT);
                stopServiceToast.show();
            }
        });

        startServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast startServiceToast;
                String toastMessage;
                if(!timeServiceBound){
                    Context context = getApplicationContext();
                    Intent intent = new Intent(context, TimeService.class);
                    startService(intent);
                    bindService(intent, timeServiceConnection, Context.BIND_AUTO_CREATE);
                    timeServiceBound = true;
                    toastMessage = "Service Started";
                }
                else{
                    toastMessage = "Service is Already Running";
                }

                startServiceToast = Toast.makeText(serviceActivityContext, toastMessage, Toast.LENGTH_SHORT);
                startServiceToast.show();
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        Intent intent = new Intent(this, TimeService.class);
        startService(intent);
        bindService(intent, timeServiceConnection, Context.BIND_AUTO_CREATE);
        timeServiceBound = true;
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(timeServiceBound){
            unbindService(timeServiceConnection);
            timeServiceBound = false;
        }
        Intent intent = new Intent(this, TimeService.class);
        stopService(intent);
    }


    private ServiceConnection timeServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            timeServiceBound = false;
        }
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TimeService.TimeBinder myBinder = (TimeService.TimeBinder) service;
            timeService = myBinder.getService();
            timeServiceBound = true;
        }
    };
}