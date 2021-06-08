package com.example.loginregister.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
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
import java.nio.Buffer;


public class Register extends AppCompatActivity {

    private EditText et_id, et_pass, et_name, et_number, et_email, et_birth, et_box_pass, et_pass_ch;
    private Button btn_register, btn_id_ch;
    private AlertDialog dialog;
    TextView txt_ch;
    private boolean validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // 액티비티 시작시 처음으로 실행되는 생명주기!
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 아이디 값 찾아주기
        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_pass);
        et_name = findViewById(R.id.et_name);
        et_number = findViewById(R.id.et_number);
        et_email = findViewById(R.id.et_email);
        et_birth = findViewById(R.id.et_birth);
        et_box_pass = findViewById(R.id.et_box_pass);
        et_pass_ch = findViewById(R.id.et_pass_ch);
        txt_ch = findViewById(R.id.txt_ch);
        btn_id_ch = findViewById(R.id.btn_id_ch);
        btn_register = findViewById(R.id.btn_register);

        et_pass_ch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_pass.getText().toString().equals(et_pass_ch.getText().toString())) {
                    txt_ch.setText("일치합니다.");
                } else {
                    txt_ch.setText("일치하지 않습니다.");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        btn_id_ch = findViewById(R.id.btn_id_ch);
        btn_id_ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = et_id.getText().toString();
                if (validate) {
                    return;
                }
                if (userID.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                    dialog = builder.setMessage("아이디는 빈칸일수 없습니다.")
                            .setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }

            }
        });


        // 회원가입 버튼 클릭 시 수행

        btn_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {

                    // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
                    String userID = et_id.getText().toString().trim();
                    String userPassword = et_pass.getText().toString().trim();
                    String userName = et_name.getText().toString().trim();
                    String userPhone = et_number.getText().toString().trim();
                    String userEmail = et_email.getText().toString().trim();
                    String userBirth = et_birth.getText().toString().trim();
                    String userBoxPw = et_box_pass.getText().toString().trim();

                    JSONObject jsonob = new JSONObject();

                    jsonob.accumulate("id", userID);
                    jsonob.accumulate("pw", userPassword);
                    jsonob.accumulate("name", userName);
                    jsonob.accumulate("email", userEmail);
                    jsonob.accumulate("phone", userPhone);
                    jsonob.accumulate("birth", userBirth);
                    jsonob.accumulate("boxpass", userBoxPw);

                    String url = "http://183.107.245.52:8000/contacts";
                    //String url ="http://127.0.0.1:4000/singup";

                    new JSONTask().execute(url, jsonob.toString());
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
            try{
                URL url = new URL(urls[0]);
                nect = (HttpURLConnection)url.openConnection();

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
            }finally {
                if (nect != null) {
                    nect.disconnect();
                }
                try {
                    if (rea != null) {
                        rea.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            Log.d(result,"nono");
            if(result.equals("Create Success")){
                finish();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }else{
                Toast.makeText(Register.this, "Used ID", Toast.LENGTH_SHORT);
            }
    }
}

}


