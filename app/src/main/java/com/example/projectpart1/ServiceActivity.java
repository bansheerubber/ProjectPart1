package com.example.projectpart1;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ServiceActivity extends AppCompatActivity {

    private CurrentTimeService mService;
    boolean mBound = false;
    private EditText formatTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        formatTime = findViewById(R.id.formatTime);
    }

    @Override
    public void onStart() {
        super.onStart();
        // bind to CurrentTimeService
        bindService(new Intent(this, CurrentTimeService.class),
                connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mBound) {
            unbindService(connection);
            mBound = false;
        }
    }

    public void onStartService(View v) {
        if (!mBound) {
            startService(new Intent(this, CurrentTimeService.class));
            bindService(new Intent(this, CurrentTimeService.class),
                    connection, Context.BIND_AUTO_CREATE);
        }
        String time = mService.getFormattedTime();
        formatTime.setText(time);
    }

    public void onStopService(View v) {
        if (mBound) {
            unbindService(connection);
            formatTime.setText("");
        }
        mBound = false;
        stopService(new Intent(this, CurrentTimeService.class));
    }

    // defines callbacks for service binding, passed to bindService()
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // bound to CurrentTimeService, case IBinder to get CurrentTimeService instance
            CurrentTimeService.LocalBinder binder = (CurrentTimeService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBound = false;
        }
    };
}