package com.example.loginregister.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitAdapter {
    private static final String BASE_URI_1 = "http://127.0.0.1:8000/";
    private static final String BASE_URI_2 = "http://10.0.0.1:8000/";
    private static final String BASE_URI_3 = "http://10.0.2.2:8000/";
    private static final String BASE_URI_4 = "http://192.168.0.3:8000/";    

    public RetrofitAdapter() {
    }
    private static class Singleton{
        private static final RetrofitAdapter instance= new RetrofitAdapter();
    }

    public static RetrofitAdapter getInstance(){
        return Singleton.instance;
    }
    public RetrofitApi getServiceApi() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(BASE_URI_3)
                .build();
        return retrofit.create(RetrofitApi.class);
    }
}
