package com.example.facturasapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.LocusId;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class Facturas extends AppCompatActivity{

    TextView total,totalagua,totalluz,totalinter,totalcasa,totalvarios;
    ListView lista;
    public static ArrayList<Factura> biblioteca = new ArrayList<Factura>();
    private Adaptador adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facturas);
        lista =(ListView) findViewById(R.id.lista);
        total =(TextView) findViewById(R.id.total);
        totalagua=(TextView) findViewById(R.id.totalagua);
        totalcasa=(TextView) findViewById(R.id.totalcasa);
        totalluz=(TextView) findViewById(R.id.totalluz);
        totalinter=(TextView) findViewById(R.id.totalinter);
        totalvarios=(TextView) findViewById(R.id.totalvarios);

       // limpiarArray();
        cargarFacturas();
        adaptador = new Adaptador(this,biblioteca);
        lista.setAdapter(adaptador);
        totalGastado();
        totalAgua();
        totalLuz();
        totalInternet();
        totalCasa();
        totalVarios();

    }
    public void mostrarFacturas(){
        for (Factura item : biblioteca) {
            System.out.println(item.visualizar_facturas());
        }
    }
    public void cargarFacturas() {
        SharedPreferences credenciales = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String dueño_actual = credenciales.getString("usuario","");

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "facturas",null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor lista_facturas = BaseDeDatos.rawQuery("select concepto, fecha_factura, importe, tipo from facturas where dueño='"+dueño_actual+"'" +
                "order by fecha_factura desc", null);

        if(lista_facturas.getCount()>0) {
            lista_facturas.moveToFirst();

                for (int i = 0; i < lista_facturas.getCount(); i++) {

                    if (noExisteFactura(lista_facturas.getString(0))) {

                        String concepto = lista_facturas.getString(0);
                        String fecha = lista_facturas.getString(1);
                        double importe = Double.parseDouble(lista_facturas.getString(2));
                        String tipo = lista_facturas.getString(3);

                        biblioteca.add(crear_factura(concepto, fecha, importe, tipo, dueño_actual));
                    }
                    lista_facturas.moveToNext();
                }
            lista.invalidateViews();
            totalGastado();
            totalAgua();
            totalLuz();
            totalInternet();
            totalCasa();
            totalVarios();
            } else {
                Toast.makeText(this, "No hay facturas registradas", Toast.LENGTH_LONG).show();
        }
    }
    public void factura_mayor(View view){
        SharedPreferences credenciales = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String dueño_actual = credenciales.getString("usuario","");

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "facturas",null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor lista_facturas = BaseDeDatos.rawQuery("select concepto, fecha_factura, importe, tipo from facturas where dueño='"+dueño_actual+"' order by importe desc ", null);

        if(lista_facturas.getCount()>0) {

            lista_facturas.moveToFirst();
            //comprobar
            System.out.println(crear_factura(lista_facturas.getString(0), lista_facturas.getString(1), lista_facturas.getDouble(2)
                    , lista_facturas.getString(3),dueño_actual).visualizar_facturas());
        }else{
            Toast.makeText(this, "No hay facturas registradas", Toast.LENGTH_LONG).show();
        }

    }
    public Factura crear_factura(String concepto, String fecha_factura, double importe, String tipo, String dueno){
        SharedPreferences credenciales = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String dueño_actual = credenciales.getString("usuario","");

        Factura nueva_factura = new Factura(concepto, fecha_factura, importe, tipo, dueno);
        return nueva_factura;
    }
    public boolean noExisteFactura(String concepto){
        SharedPreferences credenciales = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String dueño_actual = credenciales.getString("usuario","");
        boolean res=true;

        for (Factura item:biblioteca){
            if(item.getConcepto().equals(concepto)){
                res=false;
                break;
            }
        }

        return res;
    }
    public void guardar(View view)  {
        SharedPreferences credenciales = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String dueño_actual = credenciales.getString("usuario","");
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "facturas",null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        String cadenaBackup="";

        for(Factura item:biblioteca){
            String concepto=item.getConcepto();
            String fecha=item.getFecha_factura();
            String importe=""+item.getImporte();
            String tipo=item.getTipo();
            String dueño=item.getDueño();

            cadenaBackup+=concepto+":"+fecha+":"+importe+":"+tipo+":"+dueño+"\n";
        }

        try {
            //Abrimos flujo de escritura
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput(dueño_actual+".txt", Activity.MODE_PRIVATE));
            //Escribimos el contenido de nuestro EditText

            archivo.write(cadenaBackup);
            //Limpiamos el buffer
            archivo.flush();
            //Cerramos el flujo de escritura
            archivo.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Notificamos
        Toast.makeText(this, "Archivo guardado correctamente",Toast.LENGTH_SHORT).show();
        //Cerramos la Activity
        BaseDeDatos.execSQL("delete from facturas");
        biblioteca.clear();
        total.setText("");
        totalagua.setText("");
        totalluz.setText("");
        totalinter.setText("");
        totalcasa.setText("");
        totalvarios.setText("");
        cargarFacturas();
        lista.invalidateViews();


    }
    public void cargarBackUp(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "facturas",null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        SharedPreferences credenciales = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String dueño_actual = credenciales.getString("usuario","");


        try {
            InputStreamReader lectura = new InputStreamReader(openFileInput(dueño_actual+".txt"));
            BufferedReader br = new BufferedReader(lectura);

            String linea="";
            String[] datos;

            while((linea = br.readLine())!= null){

                datos=linea.split(":");
               String concepto=datos[0];
               String fecha=datos[1];
               Double importe=Double.parseDouble(datos[2]);
               String tipo=datos[3];
               String dueño=datos[4];

                ContentValues datosbd = new ContentValues();
                datosbd.put("concepto", concepto);
                datosbd.put("fecha_factura", fecha);
                datosbd.put("importe", importe);
                datosbd.put("tipo", tipo);
                datosbd.put("dueño", dueño);

                //Insertamos los datos en la BD
                BaseDeDatos.insert("facturas", null, datosbd);

            }


            //FALTA INTRODUCIR LOS DATOS EN LA BASE DE DATOS
            mostrarFacturas();
            cargarFacturas();
            BaseDeDatos.close();
            br.close();
            lectura.close();
            lista.invalidateViews();
            //Introducimos el texto del archivo en el EditText


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void totalGastado(){
        double contador=0;
        for(Factura item:biblioteca){
            contador += item.getImporte();
        }
        total.setText(" "+contador+"0 €");
    }
    public void totalAgua(){
        int contador=0;
        for(Factura item:biblioteca){
            if(item.getTipo().equals("Agua")){
                contador+=item.getImporte();
            }
        }
        totalagua.setText(""+contador+" €");
    }
    public void totalLuz(){
        int contador=0;
        for(Factura item:biblioteca){
            if(item.getTipo().equals("Luz")){
                contador+=item.getImporte();
            }
        }
        totalluz.setText(""+contador+" €");
    }
    public void totalInternet(){
        int contador=0;
        for(Factura item:biblioteca){
            if(item.getTipo().equals("Internet")){
                contador+=item.getImporte();
            }
        }
        totalinter.setText(""+contador+" €");
    }
    public void totalCasa(){
        int contador=0;
        for(Factura item:biblioteca){
            if(item.getTipo().equals("Alquiler")){
                contador+=item.getImporte();
            }
        }
        totalcasa.setText(""+contador+" €");
    }
    public void totalVarios(){
        int contador=0;
        for(Factura item:biblioteca){
            if(item.getTipo().equals("Varios")){
                contador+=item.getImporte();
            }
        }
        totalvarios.setText(""+contador+" €");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        biblioteca.clear();
    }

    }


