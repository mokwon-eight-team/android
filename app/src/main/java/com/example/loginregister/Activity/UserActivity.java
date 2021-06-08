package com.example.loginregister.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.loginregister.R;

public class UserActivity extends AppCompatActivity {

    private TextView tv_id, tv_pass, tv_name, tv_number, tv_email, tv_birth, tv_box_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        tv_id = findViewById(R.id.tv_id);
        tv_pass = findViewById(R.id.tv_pass);
        tv_name = findViewById(R.id.tv_name);
        tv_number = findViewById(R.id.tv_number);
        tv_email = findViewById(R.id.tv_email);
        tv_birth = findViewById(R.id.tv_birth);
        tv_box_pass = findViewById(R.id.tv_box_pass);

        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        String userPass = intent.getStringExtra("userPass");
        /*
        String userName = intent.getStringExtra("userName");
        String userNumber = intent.getStringExtra("userNumber");
        String userEmail = intent.getStringExtra("userEmail");
        String userBirth = intent.getStringExtra("userBirth");
        String userBox = intent.getStringExtra("userBox");
        */


        tv_id.setText(userID);
        tv_pass.setText(userPass);
        /*
        tv_name.setText(userName);
        tv_number.setText(userNumber);
        tv_email.setText(userEmail);
        tv_birth.setText(userBirth);
        tv_box_pass.setText(userBox);


         */

    }
}