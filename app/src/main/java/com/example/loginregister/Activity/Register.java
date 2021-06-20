package com.example.loginregister.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.android.volley.toolbox.Volley;
import com.example.loginregister.R;
import com.example.loginregister.retrofit.ResponseDTO;
import com.example.loginregister.retrofit.RetrofitAdapter;
import com.example.loginregister.retrofit.RetrofitApi;
import com.example.loginregister.retrofit.UserInfo;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.Buffer;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Response;


public class Register extends AppCompatActivity {

    private EditText et_id, et_pass, et_name, et_number, et_email, et_birth, et_box_pass, et_pass_ch;
    private Button btn_register, btn_id_ch;
    private AlertDialog dialog;
    TextView txt_ch;
    private boolean validate = false;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) { // 액티비티 시작시 처음으로 실행되는 생명주기!
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initComponent();
    }

    private void initComponent() {
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

        et_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {   // 중복체크를 받은 상태에서 입력된 아이디값이 변경될 경우 다시 중복체크 하도록 설정
                if(validate) {
                    validate = false;
                    setButtonEnable(btn_register, validate);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        //비밀번호 확인 칸 메세지 변경
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

        //아이디 중복 버튼 클릭시(아무것도 입력 안할시)
        btn_id_ch = findViewById(R.id.btn_id_ch);
        btn_id_ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = et_id.getText().toString();

                if (userID.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                    dialog = builder.setMessage("아이디는 빈칸일수 없습니다.")
                            .setPositiveButton("확인", null).create();
                    dialog.show();
                }
                else {
                    startRxDuplicateIdCheck(userID);
                }
            }
        });

        // 회원가입 버튼 클릭 시 수행
        btn_register.setOnClickListener(view -> {
            String userId = et_id.getText().toString();
            String password = et_pass_ch.getText().toString();
            String name = et_name.getText().toString();
            String email = et_email.getText().toString();
            String phone = et_number.getText().toString();
            String birth = et_birth.getText().toString();
            String deliveryPassword = et_box_pass.getText().toString();

            // 생년월일을 yyyy-MM-dd 형식으로 변환
            StringBuilder sb = new StringBuilder(birth);
            sb.insert(4, "-");
            sb.insert(7, "-");

            UserInfo userInfo = new UserInfo(userId, password, name, email, phone, sb.toString(), deliveryPassword);
            startRxSignUp(userInfo);
        });
    }

    // 회원가입 통신 함수
    private void startRxSignUp(UserInfo userInfo) {
        RetrofitApi retrofitApi = RetrofitAdapter.getInstance().getServiceApi();
        Observable<Response<ResponseDTO>> observable = retrofitApi.signup(userInfo);

        compositeDisposable.add(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Response<ResponseDTO>>() {
                    @Override
                    public void onNext(@NonNull Response<ResponseDTO> response) {
                        int statusCode = response.code();

                        // 회원가입 성공
                        if(statusCode == 201) {
                            Toast.makeText(getApplicationContext(), "회원가입에 성공했습니다", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        // 중복된 아이디
                        else if(statusCode == 409) {
                            Toast.makeText(getApplicationContext(), "중복된 아이디입니다", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        catchException(e, getApplicationContext());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("@@@@@@@@@@@", "startRxSignUp onComplete");
                    }
                })
        );
    }

    private void setButtonEnable(Button button, boolean flag) {
        button.setEnabled(flag);
    }

    // 아이디 중복체크 통신 함수
    private void startRxDuplicateIdCheck(String uid) {
        RetrofitApi retrofitApi = RetrofitAdapter.getInstance().getServiceApi();
        Observable<Response<ResponseDTO>> observable = retrofitApi.checkDuplicateUid(new UserInfo(uid));

        compositeDisposable.add(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Response<ResponseDTO>>() {
                    @Override
                    public void onNext(@NonNull Response<ResponseDTO> response) {
                        int statusCode = response.code();

                        // 사용 가능한 아이디
                        if(statusCode == 303) {
                            Toast.makeText(getApplicationContext(), "사용가능한 아이디입니다", Toast.LENGTH_SHORT).show();
                            validate = true;
                        }
                        else if(statusCode == 409) {
                            Toast.makeText(getApplicationContext(), "중복된 아이디입니다", Toast.LENGTH_SHORT).show();
                            validate = false;
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        catchException(e, getApplicationContext());
                        validate = false;
                    }

                    @Override
                    public void onComplete() {
                        Log.d("@@@@@@@@@@@", "startRxDuplicateIdCheck onComplete");
                        setButtonEnable(btn_register, validate);
                    }
                })
        );
    }

    // Exception 처리(백엔드와 통신 오류시 메세지 발생)
    private void catchException(@NonNull Throwable e, Context context) {
        // HttpException 처리
        if(e instanceof HttpException) {
            Toast.makeText(context, "서버 내부에 오류가 발생했습니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
        }
        // 서버와의 통신 지연 처리 (네트워크 속도가 느리거나 서버가 꺼져있을 때 발생합니다)
        else if(e instanceof SocketTimeoutException) {
            Toast.makeText(getApplicationContext(), "서버와의 통신이 원활하지 않습니다", Toast.LENGTH_SHORT).show();
        }
        // syntax 에러
        else if(e instanceof IllegalStateException || e instanceof JsonSyntaxException) {
            Toast.makeText(getApplicationContext(), "프로토콜 파라미터가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
        }
        // 기타 서버 에러
        else {
            Toast.makeText(getApplicationContext(), "서버 내부에 오류가 발생했습니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

}


