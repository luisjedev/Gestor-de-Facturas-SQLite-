package com.example.facturasapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    //Constructor
    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //Método para crear BD
    @Override
    public void onCreate(SQLiteDatabase BaseDeDatos) {

        //Creamos instancia a nuestra clase de BD
        BaseDeDatos.execSQL("create table cuentas (nombre_usuario text primary key, email text, pass text)");
        BaseDeDatos.execSQL("create table facturas (concepto text primary key, fecha_factura date, importe real, tipo text, dueño text)");
    }

    //Método para actualizar BD
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }



}
