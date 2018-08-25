package com.example.slavick.firebasechat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrationActivity extends AppCompatActivity {
    @BindView(R.id.e_mail)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.passwordRepeat)
    EditText passwordRepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.createAcc)
    public void onClick(){
        if (isPasswordValid(password.getText().toString())) {
            FirebaseAuth.getInstance().
                    createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Intent intent = new Intent(RegistrationActivity.this, ChattingActivity.class);
                            intent.putExtra("username" , email.getText().toString() );
                            email.setText("");
                            password.setText("");
                            startActivityForResult(intent, 1);
                        }
                    });
        }

    }
    @OnClick(R.id.haveAcc)
    public void onSecondClick(){
        Intent intent =  new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);

    }
    public boolean isPasswordValid(String password){
        boolean pass = false;
        if (password.length() > 6 & password.equals(passwordRepeat.getText().toString())){
            pass = true;
        }
        return pass;
    }
}
