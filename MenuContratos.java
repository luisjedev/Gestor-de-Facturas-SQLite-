package com.example.facturasapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuContratos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipos_contrato);
    }


    public void contratarLuz(View view){
        Intent i = new Intent(this, Contrataluz.class);

        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }
    public void contratarInternet(View view){
        Intent i = new Intent(this, Contratainternet.class);

        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }
    public void contratarAgua(View view){
        Intent i = new Intent(this, Contrataagua.class);

        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }


    public void volverEntrada(View view) {
        Intent e = new Intent(this, MenuPrincipal.class);
        startActivity(e);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }
}
