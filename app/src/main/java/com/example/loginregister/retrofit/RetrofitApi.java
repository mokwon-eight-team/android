package com.example.loginregister.retrofit;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitApi {
    @Headers({"Accept: application/json", "content-type:application/json"})

    // 테스트
    @GET("users/health/check")
    Observable<ResponseDTO> healthCheck();

    // 아이디 중복체크
    @POST("users/id/duplica")
    Observable<Response<ResponseDTO>> checkDuplicateUid(@Body UserInfo body);

    // 회원가입
    @PUT("users")
    Observable<Response<ResponseDTO>> signup(@Body UserInfo body);

    // 로그인
    @POST("users/signin")
    Observable<Response<ResponseDTO>> signin(@Body UserInfo body);

    // 비밀번호 찾기
    @POST("users/loss/secret")
    Observable<Response<ResponseDTO>> getPassword(@Body Map<String, String> body);
}
