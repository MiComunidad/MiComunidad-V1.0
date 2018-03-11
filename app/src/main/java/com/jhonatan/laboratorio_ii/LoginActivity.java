package com.jhonatan.laboratorio_ii;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText eUsuario,eContraseña;
    Button bAceptar;
    TextView tRegistrar;
    String usuario,contraseña,usuarioR,contraseñaR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        eUsuario= findViewById(R.id.eUsuario);
        eContraseña= findViewById(R.id.eContraseña);
        bAceptar = findViewById(R.id.bAceptar);
        tRegistrar = findViewById(R.id.tRegistrar);
    }
    //Función para llamar a la actividad de registro
    public void onClickedTextWiew(View view) {

            Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
            startActivityForResult(intent,1234);

    }
    //Funcion que compara los DatosUsuario con los escritos en los edittext
    public void onButtonClicked(View view) {
        usuario=eUsuario.getText().toString();
        contraseña=eContraseña.getText().toString();

        cargarDatos();
         if(usuario.equals(usuarioR) && contraseña.equals(contraseñaR)) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this,"Usuario o contraseña incorrecta",Toast.LENGTH_SHORT).show();
        }
    }
    //funcion que permite cargar los datos almacenados en DatosUsuarios
    private void cargarDatos() {
        SharedPreferences datos= getSharedPreferences("DatosUsuarios", Context.MODE_PRIVATE);

        String usuario= datos.getString("usuario","No hay datos");
        String contraseña= datos.getString("contraseña","No hay datos");

        usuarioR=usuario;
        contraseñaR=contraseña;
    }

    @Override
    //Respuesta del Registro
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==1234 && resultCode==RESULT_OK){
           // usuarioR = data.getExtras().getString("usuario");
           // contraseñaR = data.getExtras().getString("contraseña");
        }else{
            if(requestCode==1234 && resultCode==RESULT_CANCELED){
                Toast.makeText(this,"Cancelado",Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
