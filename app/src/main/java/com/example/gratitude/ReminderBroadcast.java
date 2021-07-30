package com.example.gratitude;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.example.gratitude.NotificationClass.CHANNEL_1_ID;

public class ReminderBroadcast extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {


        Intent intentToMain = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intentToMain,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManagerCompat notificationManager= NotificationManagerCompat.from(context);
        Notification notification=new NotificationCompat.Builder(context,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_menu_send)
                .setContentTitle("Gratitude")
                .setContentText("Have you been grateful for your day yet?")
                .setContentIntent(pendingIntent)
                .build();
        notificationManager.notify(1,notification);
    }
}
