package com.example.projectpart1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.projectpart1.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == "com.project1.homework") {
                String time = intent.getStringExtra("time");
                String message = intent.getStringExtra("message");
                String toastMessage = "Broadcast (time " + time + "): " + message;

                Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // register broadcast receiver
        IntentFilter intentFilter = new IntentFilter("com.project1.homework");
        registerReceiver(receiver, intentFilter);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button broadcastSwitch = findViewById(R.id.broadcastSwitch);
        broadcastSwitch.setOnClickListener(v -> {
            Intent intent = new Intent(this, BroadcastActivity.class);
            startActivity(intent);
        });

        Button serviceSwitch = findViewById(R.id.serviceSwitch);
        serviceSwitch.setOnClickListener(v -> {
            Intent intent = new Intent(this, ServiceActivity.class);
            startActivity(intent);
        });

        Button contentSwitch = findViewById(R.id.contentSwitch);
        contentSwitch.setOnClickListener(v -> {
            Intent intent = new Intent(this, ContentActivity.class);
            startActivity(intent);
        });

        String time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        TextView text = findViewById(R.id.mainTime);
        text.setText("Time: " + time);
    }
}