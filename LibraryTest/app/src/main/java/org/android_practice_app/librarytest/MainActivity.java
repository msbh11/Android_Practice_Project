package org.android_practice_app.librarytest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.richit.chatbubblelibrary.Chat;
import org.richit.chatbubblelibrary.ChatAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ChatAdapter chatAdapter;
    RecyclerView recyclerView;
    ArrayList<Chat>chats = new ArrayList<>();

    EditText editTextName, editTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chatAdapter = new ChatAdapter(this, chats);
        recyclerView = findViewById(R.id.chatRv);
        recyclerView.setAdapter(chatAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        editTextName = findViewById(R.id.nameEt);
        editTextMessage = findViewById(R.id.messageEt);
    }

    public void sendMessage(View view){
        String name = editTextName.getText().toString();
        String message = editTextMessage.getText().toString();

        if(name.isEmpty() || message.isEmpty()){
            Toast.makeText(this, "Name and Message Required ", Toast.LENGTH_SHORT).show();
        }
        else{
            Chat chat = new Chat(name, message);
            chats.add(chat);
            chatAdapter.notifyDataSetChanged();
        }
    }
}
