package com.example.gratitude;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

public class NotificationClass extends Application {
    public static final String CHANNEL_1_ID="channel1";
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
    }

    public void createNotificationChannels(){
        if(Build.VERSION.SDK_INT>=28){
            NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_1_ID,"Notification", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("Description of the notification");

            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
