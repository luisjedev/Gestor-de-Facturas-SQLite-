package com.example.facturasapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Contratainternet extends AppCompatActivity {

    WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrata_internet);



        wv=(WebView) findViewById(R.id.contrataInternet);
        //url = (EditText) findViewById(R.id.url);

        wv.setWebViewClient(new WebViewClient());
        //wv.setWebChromeClient(new WebChromeClient());

        wv.loadUrl("https://www.jazztel.com/");



    }
}
