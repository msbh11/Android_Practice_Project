package org.android_practice_app.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import java.sql.Time;

public class MainActivity extends AppCompatActivity {

    Button BtnStart, BtnStop;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    int hour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BtnStart = (Button) findViewById(R.id.ButtonStart);
        BtnStop = (Button) findViewById(R.id.ButtonStop);


        BtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyService.class);
                pendingIntent = PendingIntent.getService(MainActivity.this, 0, intent, 0);
                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, setTime(), 6 * 1000, pendingIntent);
                if (alarmManager != null) {
                    alarmManager.cancel(pendingIntent);
                }
                else{
                    startService(intent);
                }


            }
        });

        BtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(MainActivity.this, MyService.class));
            }
        });

    }

    private int setTime() {
        hour = (int) ((System.currentTimeMillis() / (1000 * 60 * 60)) / 24);
        return hour;
    }


}
