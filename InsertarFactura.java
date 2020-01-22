package com.example.facturasapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class InsertarFactura extends AppCompatActivity {


    Spinner tipo;
    EditText nombre_factura, fecha_factura, importe_factura;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_objeto);

        tipo=(Spinner) findViewById(R.id.spinnerTipo);

        nombre_factura=(EditText) findViewById(R.id.nombre_factura);
        fecha_factura=(EditText) findViewById(R.id.fecha_factura);
        importe_factura=(EditText) findViewById(R.id.importe_factura);

        String [] opciones = {"Luz", "Agua", "Internet", "Alquiler", "Varios"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones);


        tipo.setAdapter(adapter);


    }


    public void lanzarNotificacion(View view){
        SharedPreferences credenciales = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String dueño_actual = credenciales.getString("usuario","");
        //Creamos instancia de nuestra clase
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "facturas",null, 1);
        //Damos permisos de escritura a nuestra BD
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String tipo = this.tipo.getSelectedItem().toString();
        String concepto = nombre_factura.getText().toString();
        String fecha = fecha_factura.getText().toString();
        String importe = importe_factura.getText().toString();
        Double importe_num;
        if(importe.equals("")){
            importe_num = 0.0;
        }else{
            importe_num = Double.valueOf(importe).doubleValue();
        }
        //Duración larga
        if(nombre_factura.getText().toString().equals("") || fecha_factura.getText().toString().equals("")) {
            Toast.makeText(this, "Completa los campos necesarios", Toast.LENGTH_LONG).show();
        }else{
            Cursor existe_concepto = BaseDeDatos.rawQuery("select count(concepto) from facturas where concepto='"+concepto+"'", null);
            if (existe_concepto.moveToFirst() && existe_concepto.getInt(0)==0) {


                    //Creamos contenedor de valores y guardamos datos del usuario en él

                    ContentValues datos = new ContentValues();
                    datos.put("concepto", concepto);
                    datos.put("fecha_factura", fecha);
                    datos.put("importe", importe_num);
                    datos.put("tipo", tipo);
                    datos.put("dueño",dueño_actual);

                    //Insertamos los datos en la BD
                    BaseDeDatos.insert("facturas", null, datos);

                    //Cerramos conexión
                    BaseDeDatos.close();
                    existe_concepto.close();

                    this.nombre_factura.setText("");
                    this.fecha_factura.setText("");
                    this.importe_factura.setText("");

                    Toast.makeText(this, "Factura guardada", Toast.LENGTH_LONG).show();

            }else{
                Toast.makeText(this, "Ya existe una factura con ese nombre", Toast.LENGTH_SHORT).show();
            }
        }
    }



    public void volverEntrada(View view) {
        Intent e = new Intent(this, MenuPrincipal.class);
        startActivity(e);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }


}
