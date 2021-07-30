package com.example.gratitude;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.gratitude.ui.gallery.AddNewFragment;
import com.example.gratitude.ui.home.HomeFragment;
import com.example.gratitude.ui.slideshow.Review;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.gratitude.NotificationClass.CHANNEL_1_ID;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    SharedPreferences sharedPreferences;
    public static ArrayList<ArrayList<GratitudeObject>>preferences;
    int numberOfTypes=6;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_add_new, R.id.nav_review)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        sharedPreferences=getSharedPreferences("Gratitude",MODE_PRIVATE);
        Gson gson=new Gson();
        Type type=new TypeToken<ArrayList<ArrayList<GratitudeObject>>>(){}.getType();
        if(sharedPreferences==null){
            resetPreferenceArrayList();
        }else{
            if(sharedPreferences.getString("GratitudeArray","")==null || sharedPreferences.getString("GratitudeArray","").matches("")){
                resetPreferenceArrayList();
            }else{
                String json=(String)sharedPreferences.getString("GratitudeArray","");
                preferences=(ArrayList<ArrayList<GratitudeObject>>) gson.fromJson(json,type);
            }
        }
        sendNotif();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.clearData){
            new AlertDialog.Builder(this)
                    .setTitle("Clear Data?")
                    .setMessage("Are you sure you want to clear all your date?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            for(int i=0;i<preferences.size();i++){
                                preferences.get(i).clear();
                                Gson gson=new Gson();
                                String json=gson.toJson(preferences);
                                sharedPreferences.edit().putString("GratitudeArray",json).commit();
                                Toast.makeText(MainActivity.this, "Data cleared!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void resetPreferenceArrayList(){
        preferences=new ArrayList<>();
        for(int i=1;i<=numberOfTypes;i++){
            preferences.add(new ArrayList<GratitudeObject>());
        }
    }

    public void sendNotif() {
        Log.i("TAG","Entered send notif");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,10);
        Intent intent = new Intent(this, ReminderBroadcast.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_HALF_DAY, pendingIntent);
    }
}

