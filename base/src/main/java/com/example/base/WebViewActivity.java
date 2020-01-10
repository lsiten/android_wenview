package com.example.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * @Author:zalenlee
 * @Date: 2020-01-10 10:52
 * @Description:
 */
public class WebViewActivity extends AppCompatActivity {

    private ContentLoadingProgressBar progressBar;
    private WebView mWebView;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        url = getIntent().getStringExtra("url");

        progressBar = findViewById(R.id.progress_bar);
        mWebView = findViewById(R.id.web_view);

        configureWebView();

        mWebView.loadUrl(TextUtils.isEmpty(url) ? "http://www.bilibili.com" : url);
    }

    private void configureWebView() {
        WebSettings webSettings = mWebView.getSettings();

        webSettings.setJavaScriptEnabled(true);

        //设置自适应屏幕，两者合用
        //将图片调整到适合webview的大小
        webSettings.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        webSettings.setLoadWithOverviewMode(true);

        //设置编码格式
        webSettings.setDefaultTextEncodingName("utf-8");

        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        //设置缓存的模式
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。

        webSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
        webSettings.setDatabaseEnabled(true);   //开启 database storage API 功能
        webSettings.setAppCacheEnabled(true);//开启 Application Caches 功能

        String cacheDirPath = getFilesDir().getAbsolutePath() + "/webview/cache";
        webSettings.setAppCachePath(cacheDirPath); //设置  Application Caches 缓存目录

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(String.valueOf(request.getUrl()));
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }
        });

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
