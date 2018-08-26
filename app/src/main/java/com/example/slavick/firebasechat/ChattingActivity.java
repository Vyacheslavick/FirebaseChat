package com.example.slavick.firebasechat;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;


import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;




public class ChattingActivity extends AppCompatActivity {

    private FirebaseListAdapter<Message> adapter;
    EditText newMessage;

    private void displayChatMessages() {
        ListView messageList = findViewById(R.id.messageList);
        Query query = FirebaseDatabase.getInstance().getReference();
        FirebaseListOptions<Message> options =
                new FirebaseListOptions.Builder<Message>()
                        .setQuery(query, Message.class)
                        .setLayout(R.layout.item_message)
                        .build();
        adapter = new FirebaseListAdapter<Message>(options) {
            @Override
            protected void populateView(View v, Message model, int position) {
                TextView messageText = v.findViewById(R.id.messageText);
                TextView author = v.findViewById(R.id.author);
                TextView time = v.findViewById(R.id.time);
                messageText.setText(model.getMessageText());
                author.setText(model.getMessageUser());
                time.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };
        messageList.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        displayChatMessages();
        newMessage = findViewById(R.id.newMessage);
        findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ChattingActivity.this, "You are signed out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                setResult(1, intent);
                onBackPressed();
            }
        });
        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().push().setValue(new Message(FirebaseAuth.getInstance()
                                .getCurrentUser().getEmail(),
                                newMessage.getText().toString()));
                newMessage.setText("");
            }
        });

    }
}

