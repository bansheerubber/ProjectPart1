package com.example.projectpart1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BroadcastActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        Button sendButton = findViewById(R.id.sendBroadcastButton);
        sendButton.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction("com.project1.homework");

            String time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
            intent.putExtra("time", time);

            TextView broadcastMessage = findViewById(R.id.broadcastMessage);
            intent.putExtra("message", broadcastMessage.getText().toString().trim());

            sendBroadcast(intent);
        });
    }
}