package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Element;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "The toast on startup", Toast.LENGTH_SHORT);
        toast.show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView mainText = findViewById(R.id.maintext);
        mainText.setText("The time is currently " + new Date());
        Button goToServiceButton = (Button) findViewById(R.id.servicebutton);
        goToServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ServiceActivity.class);
                startActivity(intent);
            }
        });
    }
}