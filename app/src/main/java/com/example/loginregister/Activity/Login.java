package com.example.loginregister.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private EditText et_id, et_pass;
    private Button btn_login, btn_register;
    private TextView tx_find_pw;
    private CheckBox chb_find_id;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponent();
    }

    // 각 컴포넌트 및 리스너 선언
    private void initComponent() {

        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_pass);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        chb_find_id = findViewById(R.id.chb_find_id);
        tx_find_pw = findViewById(R.id.tx_find_pw);

        //아이디 저장 체크박스 설정
        //File이란 파일로 저장해둔 값을 가져오기위한 설정
        SharedPreferences sf = getSharedPreferences("File", MODE_PRIVATE);
        //text1에 값이 있으면 가져오고 두번째 인자는 없을경우 가져오는 값이다.
        String text1 = sf.getString("text1", "");
        if (!text1.equals("")) {
            et_id.setText(text1);
            chb_find_id.setChecked(true);
        }

        // 비밀번호 찾기 텍스트 클릭시 수행
        tx_find_pw.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, FindPassActivity.class);
            startActivity(intent);
        });

        // 회원가입 버튼을 클릭 시 수행
        btn_register.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        });

        //로그인 버튼 클릭시 이벤트 실행
        btn_login.setOnClickListener(view -> {
            String uid = et_id.getText().toString();
            String password = et_pass.getText().toString();

            UserInfo userInfo = new UserInfo(uid, password);
            startRxSignin(userInfo);
        });
    }

    // 로그인 통신 함수
    private void startRxSignin(UserInfo userInfo) {
        RetrofitApi retrofitApi = RetrofitAdapter.getInstance().getServiceApi();
        Observable<Response<ResponseDTO>> observable = retrofitApi.signin(userInfo);

        compositeDisposable.add(observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<Response<ResponseDTO>>() {
                            @Override
                            public void onNext(@NonNull Response<ResponseDTO> response) {
                                int statusCode = response.code();

                                // 로그인 성공
                                if (statusCode == 200) {
                                    // 아이디 저장이 체크되어 있으면 저장
                                    if (chb_find_id.isChecked()) {
                                        saveLoginId(userInfo.getService_id());
                                    } else {
                                        saveLoginId("");
                                    }
                                    // 메인 액티비티로 이동
                                    Intent toMainIntent = new Intent(Login.this, MainActivity.class);
//                            toMainIntent.putExtra("전달할 키 값", "전달할 값");
                                    startActivity(toMainIntent);
                                    finish();
                                }
                                // 로그인 실패
                                else if (statusCode == 404) {
                                    Toast.makeText(getApplicationContext(), "존재하지 않는 회원정보입니다", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                catchException(e, getApplicationContext());
                            }

                            //로그인 성공시 통신
                            @Override
                            public void onComplete() {
                                Log.d("@@@@@@@@@@@", "startRxSignin onComplete");
                            }
                        })
        );
    }
    //아이디 저장 체크박스 
    private void saveLoginId(String userId) {
        SharedPreferences sharedPreferences = getSharedPreferences("File", MODE_PRIVATE);
        //저장을 하기위해 editor를 이용하여 값을 저장시켜준다.
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("text1", userId);

        // 값을 다 넣었으면 commit으로 완료한다.
        editor.apply();
    }

    // Exception 처리(백엔드와 통신시 오류발생 메세지)
    private void catchException(@NonNull Throwable e, Context context) {
        // HttpException 처리
        if (e instanceof HttpException) {
            Toast.makeText(context, "서버 내부에 오류가 발생했습니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
        }
        // 서버와의 통신 지연 처리 (네트워크 속도가 느리거나 서버가 꺼져있을 때 발생합니다)
        else if (e instanceof SocketTimeoutException) {
            Toast.makeText(getApplicationContext(), "서버와의 통신이 원활하지 않습니다", Toast.LENGTH_SHORT).show();
        }
        // syntax 에러
        else if (e instanceof IllegalStateException || e instanceof JsonSyntaxException) {
            Toast.makeText(getApplicationContext(), "프로토콜 파라미터가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
        }
        // 기타 서버 에러
        else {
            Toast.makeText(getApplicationContext(), "서버 내부에 오류가 발생했습니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
        }
    }
}


