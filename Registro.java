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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Registro extends AppCompatActivity {

    CheckBox terminos;
    EditText usuario;
    EditText contraseña;
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        terminos = (CheckBox) findViewById(R.id.terminos);
        usuario = (EditText) findViewById(R.id.usuarioR);
        contraseña = (EditText) findViewById(R.id.contraseñaR);
        email = (EditText) findViewById(R.id.emailR);

    }

    public void loguear(View view){
        Intent i = new Intent(this, MenuPrincipal.class);
        //Creamos instancia de nuestra clase
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "cuentas",null, 1);

        //Damos permisos de escritura a nuestra BD
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String nombre_usuario=usuario.getText().toString();
        String email_usuario=email.getText().toString();
        String contraseña_usuario=contraseña.getText().toString();

        if(terminos.isChecked() && nombre_usuario.equals("") || contraseña_usuario.equals("") || email_usuario.equals("")){
            Toast.makeText(this, "Rellena los campos necesarios", Toast.LENGTH_LONG).show();
        }else if(!terminos.isChecked()){
            Toast.makeText(this, "No has aceptado los términos", Toast.LENGTH_LONG).show();
        }else{
            Cursor existe_usuario = BaseDeDatos.rawQuery("select count(nombre_usuario) from cuentas where nombre_usuario='"+nombre_usuario+"'", null);
            if (existe_usuario.moveToFirst() && existe_usuario.getInt(0)==0) {
                Cursor existe_email = BaseDeDatos.rawQuery("select count(email) from cuentas where email='"+email_usuario+"'", null);
                    if (existe_email.moveToFirst() && existe_email.getInt(0)==0) {

                        //Creamos contenedor de valores y guardamos datos del usuario en él
                        ContentValues datos = new ContentValues();
                        datos.put("nombre_usuario", nombre_usuario);
                        datos.put("email", email_usuario);
                        datos.put("pass", contraseña_usuario);

                        //Insertamos los datos en la BD
                        BaseDeDatos.insert("cuentas", null, datos);

                        //Cerramos conexión
                        BaseDeDatos.close();

                        //Notificamos al usuario
                        Toast.makeText(this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show();

                        guardarPreferencias();
                        startActivity(i);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                        existe_usuario.close();
                        existe_email.close();

                    }else{
                        Toast.makeText(this, "El email está en uso", Toast.LENGTH_SHORT).show();
                    }
            }else{
                Toast.makeText(this, "El nombre de usuario no está disponible", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void guardarPreferencias(){

        SharedPreferences credenciales = getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor obj_editor = credenciales.edit();

        obj_editor.putString("usuario",usuario.getText().toString());
        obj_editor.commit();

    }


}
