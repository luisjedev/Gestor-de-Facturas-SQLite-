package com.example.facturasapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends AppCompatActivity {

    EditText usuario;
    CheckBox recuerdame;
    EditText contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recuerdame = (CheckBox) findViewById(R.id.recuerdame);
        usuario = (EditText) findViewById(R.id.usuario);
        contraseña = (EditText) findViewById(R.id.contraseña);


    }

    public void log(View view) {

        //Creamos instancia de nuestra clase
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "cuentas",null, 1);
        //Damos permisos de escritura a nuestra BD
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();


        String nombre_usuario = usuario.getText().toString();
        String contraseña_usuario = contraseña.getText().toString();
        Boolean recuerdo = false;

        if(!nombre_usuario.isEmpty() && !contraseña_usuario.isEmpty()){

                Cursor datos_usuario = BaseDeDatos.rawQuery("select pass from cuentas where nombre_usuario='"+nombre_usuario+"'", null);

                if (datos_usuario.moveToFirst() && contraseña_usuario.equals(datos_usuario.getString(0))){
                    Intent i = new Intent(this, MenuPrincipal.class);
                    if (recuerdame.isChecked()) {
                        recuerdo = true;
                        // enviamos datos a travez de activities,    nombre y contenido
                        i.putExtra("dato", usuario.getText().toString());
                        i.putExtra("nombre", nombre_usuario);
                        i.putExtra("recuerdo", recuerdo);
                        i.putExtra("pass", contraseña_usuario);
                    }
                    guardarPreferencias();

                    //limpiar();
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    datos_usuario.close();

                }else{
                    Toast.makeText(this, "El usuario o la contraseña no coinciden", Toast.LENGTH_SHORT).show();
                }

        }else {
            Toast.makeText(this, "Rellena los campos necesarios", Toast.LENGTH_SHORT).show();
        }
    }

    public void irRegistro(View view){
        Intent i = new Intent(this, Registro.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void guardarPreferencias(){

        SharedPreferences credenciales = getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor obj_editor = credenciales.edit();

        obj_editor.putString("usuario",usuario.getText().toString());
        obj_editor.commit();

    }

    public void limpiar(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "facturas",null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        BaseDeDatos.execSQL("delete from facturas");
    }

}