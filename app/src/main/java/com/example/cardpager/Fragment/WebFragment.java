package com.example.cardpager.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by admin on 2017/3/23.
 */
public class WebFragment extends CardBaseFragment{

    private WebView webView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        webView = new WebView(inflater.getContext());
        WebSettings setting = webView.getSettings();
        setting.setJavaScriptCanOpenWindowsAutomatically(true);
        setting.setJavaScriptEnabled(true);
        setting.setUseWideViewPort(true);
        setting.setSupportZoom(true);
        setting.setLoadsImagesAutomatically(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl("http://www.baidu.com");
        return webView;
    }

    @Override
    public boolean backPress() {
        if(webView.canGoBack()){
            webView.goBack();
            return true;
        }
        return super.backPress();
    }
}
