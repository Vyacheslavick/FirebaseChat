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

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChattingActivity extends AppCompatActivity {
    @BindView(R.id.newMessage)
    EditText newMessage;
    @BindView(R.id.messageList)
    ListView messageList;
    @BindView(R.id.messageText)
    TextView messageText;
    @BindView(R.id.author)
    TextView author;
    @BindView(R.id.time)
    TextView time;
    private FirebaseListAdapter<Message> adapter;


    private void displayChatMessages() {
        Query query = FirebaseDatabase.getInstance().getReference().child("chats");
        FirebaseListOptions<Message> options =
                new FirebaseListOptions.Builder<Message>()
                        .setQuery(query, Message.class)
                        .setLayout(android.R.layout.simple_list_item_1)
                        .build();
        adapter = new FirebaseListAdapter<Message>(options) {
            @Override
            protected void populateView(View v, Message model, int position) {
                messageText.setText(model.getMessageText());
                author.setText(model.getMessageUser());

                // Format the date before showing it
                time.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        ButterKnife.bind(this);

        messageList.setAdapter(adapter);
    }

    @OnClick(R.id.exit)
    public void onClick() {
        Toast.makeText(this, "You are signed out", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        setResult(1, intent);
        onBackPressed();
    }

    @OnClick(R.id.send)
    public void onSecondClick() {
        FirebaseDatabase.getInstance()
                .getReference()
                .push()
                .setValue(new Message(FirebaseAuth.getInstance()
                        .getCurrentUser()
                        .getDisplayName(),
                        newMessage.getText().toString(), new Date().getTime()));
        newMessage.setText("");
    }
}

