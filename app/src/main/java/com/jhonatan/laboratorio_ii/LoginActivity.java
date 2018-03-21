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
    String usuario,contraseña,usuarioR,contraseñaR,correo,sexo,lugar,fecha;

//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        eUsuario= findViewById(R.id.eUsuario);
        eContraseña= findViewById(R.id.eContraseña);
        bAceptar = findViewById(R.id.bAceptar);
        tRegistrar = findViewById(R.id.tRegistrar);

        Bundle extras= getIntent().getExtras();
        if(extras != null){
            usuario = extras.getString("usuario");
            contraseña = extras.getString("contraseña");
            correo = extras.getString("correo");
            sexo = extras.getString("sexo");
            lugar = extras.getString("lugar");
            fecha = extras.getString("fecha");
        }


    }
    //Función para llamar a la actividad de registro
    public void onClickedTextWiew(View view) {

            Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
            startActivityForResult(intent,1234);

    }
    //Funcion que compara los DatosUsuario con los escritos en los edittext
    public void onButtonClicked(View view) {

            usuarioR = eUsuario.getText().toString();
            contraseñaR = eContraseña.getText().toString();


            if (usuario != null  && contraseña != null && usuario.equals(usuarioR) && contraseña.equals(contraseñaR)) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("contraseña", contraseña);
                intent.putExtra("correo", correo);
                intent.putExtra("sexo", sexo);
                intent.putExtra("lugar", lugar);
                intent.putExtra("fecha", fecha);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show();
            }

    }

    @Override
    //Respuesta del Registro
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==1234 && resultCode==RESULT_OK){
            usuario = data.getExtras().getString("usuario");
            contraseña = data.getExtras().getString("contraseña");
            correo = data.getExtras().getString("correo");
            sexo =data.getExtras().getString("sexo");
            lugar = data.getExtras().getString("lugar");
            fecha = data.getExtras().getString("fecha");
        }else{
            if(requestCode==1234 && resultCode==RESULT_CANCELED){
                Toast.makeText(this,"Cancelado",Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
