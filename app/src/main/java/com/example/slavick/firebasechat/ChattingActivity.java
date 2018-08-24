package com.example.slavick.firebasechat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChattingActivity extends AppCompatActivity {
    @BindView(R.id.newMessage)
    EditText newMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.exit)
    public void onClick(){
        Toast.makeText(this, "You are signed out", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }
    @OnClick(R.id.send)
    public void onSecondClick(){
        FirebaseDatabase.getInstance()
                .getReference()
                .push()
                .setValue(new Message(FirebaseAuth.getInstance()
                        .getCurrentUser()
                        .getDisplayName(),
                        newMessage.getText().toString(), new Date().getTime())
                        );
        newMessage.setText("");
    }

}
