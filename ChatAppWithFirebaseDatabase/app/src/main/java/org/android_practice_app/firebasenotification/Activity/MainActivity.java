package org.android_practice_app.firebasenotification.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.android_practice_app.firebasenotification.Model.ChatMessageModel;
import org.android_practice_app.firebasenotification.R;

public class MainActivity extends AppCompatActivity {

    Button btNotification;
    String message;
    EditText editText;
    ChatMessageModel chatMessageModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //btNotification = findViewById(R.id.bt_notification);
        editText = findViewById(R.id.edttxt);

        registerReceiver(broadcastReceiver, new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION));




    }
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("Braodcastreceive", "myMsg");

            if(intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)){
                NetworkInfo Info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                boolean connected = Info.isConnected();

                if(connected){
                    Toast.makeText(context, "WIFI is connected", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context, "Please Connect your Wifi", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };



    public void login(View view) {
        if(!editText.getText().toString().equals("")){
            startActivity(new Intent(MainActivity.this, ChatActivity.class).putExtra("name", editText.getText().toString()));

        }
        else{
            Toast.makeText(this, "Enter Your Name Please", Toast.LENGTH_SHORT).show();
        }
    }
}
