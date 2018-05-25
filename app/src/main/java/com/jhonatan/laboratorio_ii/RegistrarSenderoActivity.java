package com.jhonatan.laboratorio_ii;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class RegistrarSenderoActivity extends AppCompatActivity {

    private EditText eNombre, eDescripcion,  ePuntos ;
    private ImageView iFoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_sendero);

        eNombre = findViewById(R.id.eNombre);
        eDescripcion = findViewById(R.id.eDescripcion);
        ePuntos = findViewById(R.id.ePuntos);
        iFoto = findViewById(R.id.iFoto);

    }

    public void onButtonClicked(View view) {
        Intent intent = new Intent(RegistrarSenderoActivity.this, MapsRescomendacionesActivity.class);
        intent.putExtra("nombre", eNombre.getText().toString());
        intent.putExtra("puntos",ePuntos.getText().toString());
        intent.putExtra("descripcion", eDescripcion.getText().toString());
        startActivity(intent);
        finish();
    }
}
