package com.jhonatan.laboratorio_ii;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class PerfilActivity extends AppCompatActivity {
    TextView tNombre,tCorreo,tLugar,tFecha,tSexo,tContraseña;
    String usuario,contraseña,correo,sexo,lugar,fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        tNombre=findViewById(R.id.tNombre);
        tContraseña = findViewById(R.id.tContraseña);
        tCorreo=findViewById(R.id.tCorreo);
        tLugar=findViewById(R.id.tLugar);
        tFecha=findViewById(R.id.tFecha);
        tSexo=findViewById(R.id.tSexo);

        Bundle extras= getIntent().getExtras();
        if(extras != null) {
            usuario =extras.getString("usuario");
            contraseña = extras.getString("contraseña");
            correo=extras.getString("correo");
            sexo = extras.getString("sexo");
            lugar= extras.getString("lugar");
            fecha= extras.getString("fecha");
        }

        tNombre.setText(usuario);
        tContraseña.setText(contraseña);
        tCorreo.setText(correo);
        tSexo.setText(sexo);
        tLugar.setText(lugar);
        tFecha.setText(fecha);
    }

    //ASIGNA EL MENU PREVIAMENTE CREADO
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuperfil,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    //Seleccion del menu
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.mPrincipal:
                Intent intent = new Intent(PerfilActivity.this, MainActivity.class);
                intent.putExtra("usuario",usuario);
                intent.putExtra("contraseña",contraseña);
                intent.putExtra("correo",correo);
                intent.putExtra("sexo",sexo);
                intent.putExtra("lugar",lugar);
                intent.putExtra("fecha",fecha);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.mCerrarS:
                Intent intent2 = new Intent(PerfilActivity.this, LoginActivity.class);
                intent2.putExtra("usuario",usuario);
                intent2.putExtra("contraseña",contraseña);
                intent2.putExtra("correo",correo);
                intent2.putExtra("sexo",sexo);
                intent2.putExtra("lugar",lugar);
                intent2.putExtra("fecha",fecha);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}

