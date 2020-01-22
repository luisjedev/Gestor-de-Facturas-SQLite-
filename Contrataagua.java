package com.example.facturasapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Contrataagua extends AppCompatActivity {

    WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrata_agua);



        wv=(WebView) findViewById(R.id.contrataAgua);
        //url = (EditText) findViewById(R.id.url);

        wv.setWebViewClient(new WebViewClient());
        //wv.setWebChromeClient(new WebChromeClient());

        wv.loadUrl("https://www.emasagra.es/alta-suministro");

    }
}
