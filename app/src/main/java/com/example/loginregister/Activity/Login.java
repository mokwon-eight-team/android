package com.example.loginregister.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.loginregister.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login extends AppCompatActivity {
    private EditText et_id, et_pass;
    private Button btn_login, btn_register;
    private TextView tx_find_id, tx_find_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_pass);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        tx_find_id = findViewById(R.id.tx_find_id);
        tx_find_pw = findViewById(R.id.tx_find_pw);


        // 회원가입 버튼을 클릭 시 수행
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        //아이디 찾기 텍스트 클릭시 수행
        tx_find_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, FindIdActivity.class);
                startActivity(intent);
            }
        });

        //비밀번호 찾기 텍스트 클릭시 수행
        tx_find_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, FindPassActivity.class);
                startActivity(intent);
            }
        });
        //로그인 버튼 클릭시 이벤트 실행
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String id = et_id.getText().toString().trim();
                    String pw = et_pass.getText().toString().trim();
                    JSONObject jsonOb = new JSONObject();
                    jsonOb.accumulate("et_id", id);
                    jsonOb.accumulate("et_pass", pw);

                    String url = "http://183.107.245.52:8000/contacts/login/:userID";
                    //String url = "http://127.0.0.1:4000/signin";

                    new JSONTask().execute(url, jsonOb.toString());
                    Log.d(jsonOb.toString(), "합니다");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public class JSONTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... urls) {
            HttpURLConnection nect = null;
            BufferedReader rea = null;
            try {
                URL url = new URL(urls[0]);
                nect = (HttpURLConnection) url.openConnection();
                nect.setRequestMethod("POST");
                nect.setRequestProperty("Cache-Conntrol", "no-cache");
                nect.setRequestProperty("Content-Type", "application/json");

                nect.setRequestProperty("Accept", "text/html");
                nect.setDoOutput(true);
                nect.setDoInput(true);
                nect.connect();

                OutputStream out = nect.getOutputStream();
                out.write(urls[1].getBytes("utf-8"));
                out.flush();
                out.close();

                InputStream stream = nect.getInputStream();
                rea = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = rea.readLine()) != null) {
                    buffer.append(line);
                }
                return buffer.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (nect != null) {
                    nect.disconnect();
                }
            }
            try {
                if (rea != null) {
                    rea.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result.equals("Success")) {
                finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            } else {
                AlertDialog.Builder alert = new AlertDialog.Builder(Login.this);
                alert.setTitle("Temp");
                alert.setMessage("No user or fail id, password");
                alert.setPositiveButton("check", null);
                alert.show();
            }
        }
    }
}


