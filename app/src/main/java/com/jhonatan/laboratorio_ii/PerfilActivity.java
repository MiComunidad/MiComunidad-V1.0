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
    TextView tNombre,tCorreo,tLugar,tFecha,tSexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        tNombre=findViewById(R.id.tNombre);
        tCorreo=findViewById(R.id.tCorreo);
        tLugar=findViewById(R.id.tLugar);
        tFecha=findViewById(R.id.tFecha);
        tSexo=findViewById(R.id.tSexo);

        cargarDatos();
    }
    //Funcion para cargar los datos almacenados en DatosUsuarios
    private void cargarDatos() {
         SharedPreferences datos= getSharedPreferences("DatosUsuarios", Context.MODE_PRIVATE);

         String usuario= datos.getString("usuario","No hay datos");
         String correo= datos.getString("correo","No hay datos");
         String lugar= datos.getString("lugar","No hay datos");
         String fecha= datos.getString("fecha","No hay datos");
         String sexo= datos.getString("sexo","No hay datos");

         tNombre.setText(usuario);
         tCorreo.setText(correo);
         tLugar.setText(lugar);
         tFecha.setText(fecha);
         tSexo.setText(sexo);
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
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.mCerrarS:
                Intent intent2 = new Intent(PerfilActivity.this, LoginActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}

