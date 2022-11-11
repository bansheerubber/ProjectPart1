package com.example.projectpart1;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CurrentTimeService extends Service {

    private final IBinder mBinder = new LocalBinder();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

    public String getFormattedTime() {
        while(true){
            return dateFormat.format(Calendar.getInstance().getTime());
        }
    }

    public class LocalBinder extends Binder {
        CurrentTimeService getService(){
            return CurrentTimeService.this;
        }
    }
    public CurrentTimeService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }
}