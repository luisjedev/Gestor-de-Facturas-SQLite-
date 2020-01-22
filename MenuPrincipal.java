package com.example.facturasapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MenuPrincipal extends AppCompatActivity {

    TextView nombre;
    Boolean recuerdo;
    String nombre_usuario = "";
    String contraseña_usuario = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //recuerdame
        contraseña_usuario = getIntent().getStringExtra("pass");
        nombre_usuario = getIntent().getStringExtra("nombre");
        recuerdo = getIntent().getBooleanExtra("recuerdo", false);


        nombre = (TextView) findViewById(R.id.nombre);
        //Recogemos datos enviado desde la otra Activity y sustituimos el texto
        //nombre.setText("Hola " + getIntent().getStringExtra("dato"));


    }


    public void volverInicio(View view) {
        Intent e = new Intent(this, Login.class);
        //  e.putExtra("recuerdo",recuerdo);

        if (recuerdo) {
            e.putExtra("nombre", this.nombre_usuario);
            e.putExtra("pass", contraseña_usuario);
        }
        //Lanzamos nueva Activity
        startActivity(e);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


    }


    public void añadir_factura(View view) {

        Intent i = new Intent(this, InsertarFactura.class);

        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


    }


    public void contratar(View view) {
        Intent i = new Intent(this, MenuContratos.class);

        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }

    public void consultar_facturas(View view) {

        Intent i = new Intent(this, Facturas.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }
}
