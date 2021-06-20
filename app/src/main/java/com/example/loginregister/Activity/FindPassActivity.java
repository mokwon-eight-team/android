package com.example.loginregister.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginregister.R;
import com.example.loginregister.retrofit.ResponseDTO;
import com.example.loginregister.retrofit.RetrofitAdapter;
import com.example.loginregister.retrofit.RetrofitApi;
import com.google.gson.JsonSyntaxException;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Response;

public class FindPassActivity extends AppCompatActivity {

    private EditText et_user_id, et_email;
    private TextView tv_password;
    private Button btn_confirm;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pass);

        initComponent();
    }

    private void initComponent() {
        et_user_id = findViewById(R.id.et_user_id);
        et_email = findViewById(R.id.et_email);
        tv_password = findViewById(R.id.tv_password);

        btn_confirm = findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(view -> {
            String userId = et_user_id.getText().toString();
            String email = et_email.getText().toString();

            startRxGetPassword(userId, email);
        });
    }

    private void startRxGetPassword(String userId, String email) {
        Map<String, String> body = new HashMap<>();
        body.put("service_id", userId);
        body.put("email", email);

        RetrofitApi retrofitApi = RetrofitAdapter.getInstance().getServiceApi();
        Observable<Response<ResponseDTO>> observable = retrofitApi.getPassword(body);

        compositeDisposable.add(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Response<ResponseDTO>>() {
                    @Override
                    public void onNext(@NonNull Response<ResponseDTO> response) {
                        int statusCode = response.code();
                        ResponseDTO responseDTO = response.body();

                        // 비밀번호 조회 성공
                        if(statusCode == 200) {
                            tv_password.setText(responseDTO.getValue());
                        }
                        // 비밀번호 조회 실패
                        else if(statusCode == 404) {
                            Toast.makeText(getApplicationContext(), "가입되지 않은 사용자입니다", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        catchException(e, getApplicationContext());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("@@@@@@@@@@@", "startRxGetPassword onComplete");
                    }
                })
        );
    }
    // Exception 처리
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