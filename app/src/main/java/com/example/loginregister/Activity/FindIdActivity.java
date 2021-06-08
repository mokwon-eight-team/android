package com.example.loginregister.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.example.loginregister.R;

import org.json.JSONObject;

public class FindIdActivity extends AppCompatActivity {

    private EditText et_find_name, et_find_email;
    private Button btn_find_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id);

        et_find_name = findViewById(R.id.et_find_name);
        et_find_email = findViewById(R.id.et_find_email);
        btn_find_id = findViewById(R.id.btn_find_id);
    }


}