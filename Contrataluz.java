package com.example.facturasapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Contrataluz extends AppCompatActivity {

    WebView wv;
    //EditText url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrata_luz);

        wv=(WebView) findViewById(R.id.contrataLuz);
        //url = (EditText) findViewById(R.id.url);

        wv.setWebViewClient(new WebViewClient());
        //wv.setWebChromeClient(new WebChromeClient());

        wv.loadUrl("https://www.iberdrola.es/informacion/alta-nuevo-suministro-electrico");

    }


}
