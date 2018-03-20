package com.jhonatan.laboratorio_ii;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class RegistroActivity extends AppCompatActivity {
    EditText eUsur,eContra,eRcontra,eCorreo,eFecha;
    Spinner sLugar;
    Button bRegistrar;
    int año,mes,dia;
    static final int DIALOGO=0;
    static DatePickerDialog.OnDateSetListener selectorfecha;

    String usuario,contraseña,rcontraseña,correo,sexo,fecha,lugar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Calendar fechaActual = Calendar.getInstance();
        año = fechaActual.get(Calendar.YEAR);
        mes = fechaActual.get(Calendar.MONTH);
        dia = fechaActual.get(Calendar.DAY_OF_MONTH);

        eUsur = findViewById(R.id.eUsu);
        eContra = findViewById(R.id.eContra);
        eRcontra = findViewById(R.id.eRcontra);
        eCorreo= findViewById(R.id.eCorreo);
        sLugar = findViewById(R.id.sLugar);
        eFecha = findViewById(R.id.eFecha);
        bRegistrar = findViewById(R.id.bRegistrar);


        selectorfecha = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                año = year;
                mes= monthOfYear;
                dia = dayOfMonth;
                eFecha.setText(dia+"/"+(mes +1)+"/"+año);

            }
        };

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.opciones,android.R.layout.simple_spinner_item);
        sLugar.setAdapter(adapter);
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch(id){
            case 0 :
                return new DatePickerDialog(this,selectorfecha,año,mes,dia);
        }
        return null;
    }

    public void onEditTextClicked(View view) {showDialog(DIALOGO);}


    public void onRadioButtonClicked(View view) {
        int id = view.getId();

        if ( id== R.id.rMasculino){
            sexo = "Masculino";
        }else{
            sexo = "Femenino";
        }
    }

    public void onButtonClicked(View view) {
        usuario= eUsur.getText().toString();
        contraseña= eContra.getText().toString();
        rcontraseña= eRcontra.getText().toString();
        correo= eCorreo.getText().toString();
        fecha= eFecha.getText().toString();
        lugar =sLugar.getSelectedItem().toString();

        if (usuario.equals("") || contraseña.equals("")|| rcontraseña.equals("")|| correo.equals("")|| fecha.equals("")|| sexo.equals("")|| lugar.equals("")) {
            Toast.makeText(this, "Debe llenar todos los datos", Toast.LENGTH_LONG).show();

        }else if(!(contraseña.equals(rcontraseña))){
            Toast.makeText(this, "Contraseñas diferentes", Toast.LENGTH_LONG).show();
        }else{

            Intent intent=new Intent();
            intent.putExtra("usuario",eUsur.getText().toString());
            intent.putExtra("correo",eCorreo.getText().toString());
            intent.putExtra("contraseña",eContra.getText().toString());
            intent.putExtra("sexo",sexo);
            intent.putExtra("fecha",eFecha.getText().toString());
            intent.putExtra("lugar",sLugar.getSelectedItem().toString());
            setResult(RESULT_OK,intent);
            finish();
        }

    }


    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
        super.onBackPressed();
    }
}
