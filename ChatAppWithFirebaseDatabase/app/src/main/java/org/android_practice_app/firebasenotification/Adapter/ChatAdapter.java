package org.android_practice_app.firebasenotification.Adapter;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.android_practice_app.firebasenotification.Activity.ChatActivity;
import org.android_practice_app.firebasenotification.Activity.MainActivity;
import org.android_practice_app.firebasenotification.Model.ChatMessageModel;
import org.android_practice_app.firebasenotification.R;

import android.widget.LinearLayout.LayoutParams;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    Context context;
    ArrayList<ChatMessageModel> chatList = new ArrayList<>();
    String name;
    String msg;
    private NotificationManager mNotificationManager;

    public ChatAdapter(String name) {
        this.name = name;
    }

    public ChatAdapter(Context mContext, ArrayList<ChatMessageModel> chatLists) {
        context = mContext;
        chatList = chatLists;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChatList(ArrayList<ChatMessageModel> chatList) {
        this.chatList = chatList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.rv_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        RelativeLayout.LayoutParams buttonLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);



        if (chatList.get(position).getName() == name) {

            msg = chatList.get(position).getMsg();

            buttonLayoutParams.setMargins(250, 0, 0, 4);
            //buttonLayoutParams.leftMargin = 40 ;
            holder.linear_msg_profile.setVisibility(View.GONE);
            holder.message.setText("" + msg);
            holder.date_time.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", Long.parseLong(""+chatList.get(position).getDate_time())));
            holder.relativelayout.setLayoutParams(buttonLayoutParams);

        } else {
            buttonLayoutParams.setMargins(56, 0, 0, 4);
//            buttonLayoutParams.rightMargin = 56;

            holder.profile_name.setText(chatList.get(position).getName());
            holder.message.setText("" + chatList.get(position).getMsg());
            holder.date_time.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", new Date()));
            holder.relativelayout.setLayoutParams(buttonLayoutParams);


        }


    }


    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView profile_name;
        TextView message;
        TextView date_time;
        RelativeLayout relativelayout;
        LinearLayout linear_msg_profile;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            profile_name = itemView.findViewById(R.id.left_name_tv);
            message = itemView.findViewById(R.id.chat_left_msg_text_view);
            date_time = itemView.findViewById(R.id.left_DateTime_tv);
            relativelayout = itemView.findViewById(R.id.relative_chat_body);
            linear_msg_profile = itemView.findViewById(R.id.msg_profile_linear_layout);

        }
    }
}
