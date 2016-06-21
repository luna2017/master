package com.example.me.master;

/**
 * Created by July on 2016/6/16.
 */

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MessageActivity extends Activity {
    private WebView webView;
    private  String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        webView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//设置可读取JavaScript网页
        webSettings.setAllowFileAccess(true);//可读取文件
        webSettings.setBuiltInZoomControls(true);
//        webView.loadUrl("http://blog.csdn.net/qq_29407877");//要跳转的网页地址
        url=getIntent().getStringExtra("url");
        webView.loadUrl(url);//要跳转的网页地址
        webView.setWebViewClient(new WebViewClient());
    }

    private class webViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}