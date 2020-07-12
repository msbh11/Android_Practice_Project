package org.android_practice_app.firebasenotification.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.android_practice_app.firebasenotification.Adapter.ChatAdapter;
import org.android_practice_app.firebasenotification.Model.ChatMessageModel;
import org.android_practice_app.firebasenotification.Others.FirebaseDbCall;
import org.android_practice_app.firebasenotification.R;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {


    private static final String NOTIFICATION_CHANNEL_ID = "100001";
    public static String name;
    ArrayList<ChatMessageModel> chatList = new ArrayList<>();
    ChatAdapter adapter;
    RecyclerView recyclerview;
    LinearLayoutManager manager;
    EditText EdtMessage;
    ChatMessageModel chatMessageModel;
    String TAG = "firebase crud";
    int count = 0;
    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
    private DatabaseReference mDatabase;
    private NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        name = getIntent().getStringExtra("name");

        init();
        FirebaseDbCall.init();


        recyclerview = findViewById(R.id.activity_show_list);
        adapter = new ChatAdapter(this, chatList);
        adapter.setName(getIntent().getStringExtra("name"));
        manager = new LinearLayoutManager(this);

        recyclerview.setLayoutManager(manager);
        recyclerview.setAdapter(adapter);


        showMessage();

    }

    public void init() {
        EdtMessage = findViewById(R.id.messageArea);
        //chatMessageModel = getIntent().getParcelableExtra("ChatMessages");

    }

    public void showMessage() {

        FirebaseDbCall.init();
        //swipeRefreshLayout.setRefreshing(true);
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                //Post post = dataSnapshot.getValue(Post.class);
                // ...
                chatList.clear();
                chatList = new ArrayList<>();

                for (DataSnapshot value : dataSnapshot.getChildren()) {
                    ChatMessageModel value1 = value.getValue(ChatMessageModel.class);
                    //value1.setF_id(value.getRef().getKey());
                    chatList.add(value1);

                }
                adapter.setChatList(chatList);


                try {
                    if (!chatList.get(chatList.size() - 1).getName().equals(name)) {
                        sendNotification(chatList.get(chatList.size() - 1).getName(), chatList.get(chatList.size() - 1).getMsg());
                    }
                } catch (Exception e) {

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("FirebaseCall", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        FirebaseDbCall.getMyRef().addValueEventListener(postListener);


    }

    public void Add(View view) {
        chatMessageModel = new ChatMessageModel(name, EdtMessage.getText().toString());
//        chatMessageModel.setName(name);
//        chatMessageModel.setMsg();
        //chatMessageModel.setDate_time(chatMessageModel.getDate_time());

        FirebaseDbCall.insert(chatMessageModel, this);
        EdtMessage.setText("");


//        finish();
    }

    public void sendNotification(String nameOfSender, String message) {    //Get an instance of NotificationManager//
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                ChatActivity.this
        )
                .setSmallIcon(R.drawable.ic_textsms_black_24dp)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(nameOfSender)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0));
        //.setSound(alarmSound);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(0, builder.build());
    }


//    public void notification (String message){
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(
//                ChatActivity.this
//        )
//                .setSmallIcon(R.drawable.ic_textsms_black_24dp)
//                .setContentTitle("New Notification_activity")
//                .setContentText(msg)
//                .setAutoCancel(true);
//
//        Intent intent = new Intent(ChatActivity.this, Notification_activity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra("message", msg);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(ChatActivity.this,
//                0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(pendingIntent);
//        NotificationManager notificationManager = (NotificationManager)getSystemService(
//                Context.NOTIFICATION_SERVICE
//        );
//        notificationManager.notify(0, builder.build());
//    }


}
