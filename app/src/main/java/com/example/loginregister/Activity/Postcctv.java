package com.example.loginregister.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.loginregister.R;

public class Postcctv extends AppCompatActivity {

    private WebView mWebView;
    private WebSettings mWebSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postcctv);


        mWebView = (WebView) findViewById(R.id.webView);

        mWebView.setWebViewClient(new WebViewClient());// 클릭시 새창 안뜨게
        mWebSettings = mWebView.getSettings();// 세부 세팅 등록
        mWebSettings.setJavaScriptEnabled(true);// 웹페이지 자바스크립트 허용 여부
        mWebSettings.setSupportMultipleWindows(true); //새창 띄우기 허용 여부
        mWebSettings.setUseWideViewPort(true); // 화면 사이즈 맞추기 허용 여부
        mWebSettings.setSupportZoom(false); //화면 줌 허용 여부
        mWebSettings.setBuiltInZoomControls(false); // 화면 확대 축소 허용 여부
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 컨텐츠 사이즈 맞추기
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //브라우저 캐시 허용 여부
        mWebSettings.setDomStorageEnabled(true); //로컬 저장소 허용 여부

        mWebView.loadUrl("http://192.168.137.231:8081"); //웹뷰에 표시할 라즈베리파이 주소, 웹뷰 시작
    }
}