package com.jhonatan.laboratorio_ii;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    String usuario,contraseña,correo,sexo,lugar,fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras= getIntent().getExtras();
        if(extras != null) {
            usuario = extras.getString("usuario");
            contraseña = extras.getString("contraseña");
            correo = extras.getString("correo");
            sexo = extras.getString("sexo");
            lugar = extras.getString("lugar");
            fecha = extras.getString("fecha");
        }
        usuario = getIntent().getStringExtra("usuario");
    }

    //ASIGNA EL MENU PREVIAMENTE CREADO
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuap,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.mMiPerfil:
                Intent intent = new Intent(MainActivity.this, PerfilActivity.class);
                intent.putExtra("usuario",usuario);
                intent.putExtra("contraseña",contraseña);
                intent.putExtra("correo",correo);
                intent.putExtra("sexo",sexo);
                intent.putExtra("lugar",lugar);
                intent.putExtra("fecha",fecha);
                startActivity(intent);
                break;
            case R.id.mCerrarSe:
                Intent intent2 = new Intent(MainActivity.this, LoginActivity.class);
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
