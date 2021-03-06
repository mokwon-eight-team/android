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

                        // ???????????? ?????? ??????
                        if(statusCode == 200) {
                            tv_password.setText(responseDTO.getValue());
                        }
                        // ???????????? ?????? ??????
                        else if(statusCode == 404) {
                            Toast.makeText(getApplicationContext(), "???????????? ?????? ??????????????????", Toast.LENGTH_SHORT).show();
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
    // Exception ??????
    private void catchException(@NonNull Throwable e, Context context) {
        // HttpException ??????
        if (e instanceof HttpException) {
            Toast.makeText(context, "?????? ????????? ????????? ??????????????????. ?????? ??? ?????? ??????????????????.", Toast.LENGTH_SHORT).show();
        }
        // ???????????? ?????? ?????? ?????? (???????????? ????????? ???????????? ????????? ???????????? ??? ???????????????)
        else if (e instanceof SocketTimeoutException) {
            Toast.makeText(getApplicationContext(), "???????????? ????????? ???????????? ????????????", Toast.LENGTH_SHORT).show();
        }
        // syntax ??????
        else if (e instanceof IllegalStateException || e instanceof JsonSyntaxException) {
            Toast.makeText(getApplicationContext(), "???????????? ??????????????? ???????????? ????????????", Toast.LENGTH_SHORT).show();
        }
        // ?????? ?????? ??????
        else {
            Toast.makeText(getApplicationContext(), "?????? ????????? ????????? ??????????????????. ?????? ??? ?????? ??????????????????.", Toast.LENGTH_SHORT).show();
        }
    }

}