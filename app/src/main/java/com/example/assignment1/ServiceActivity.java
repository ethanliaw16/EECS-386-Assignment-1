package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ServiceActivity extends AppCompatActivity {
    TimeService timeService;
    boolean timeServiceBound = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        final TextView timestampText = (TextView) findViewById(R.id.messagefromservice);
        Button getMessageButton = (Button) findViewById(R.id.getmessagebutton);
        Button stopServiceButton = (Button) findViewById(R.id.stopservicebutton);
        Button startServiceButton = (Button) findViewById(R.id.startservicebutton);

        getMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timeServiceBound) {
                    String newText = "Message from service: " + timeService.getTime();
                    timestampText.setText(newText);
                }
                Toast messageReceivedToast = Toast.makeText(getApplicationContext(), "Recieved New Message", Toast.LENGTH_SHORT);
                messageReceivedToast.show();
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
                Toast stopServiceToast = Toast.makeText(getApplicationContext(), "Service Stopped", Toast.LENGTH_SHORT);
                stopServiceToast.show();
            }
        });

        startServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast startedServiceToast;
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
                startedServiceToast = Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT);
                startedServiceToast.show();
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