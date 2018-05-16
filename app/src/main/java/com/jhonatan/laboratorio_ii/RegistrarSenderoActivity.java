package com.jhonatan.laboratorio_ii;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class RegistrarSenderoActivity extends AppCompatActivity {

    ArrayList<LatLng> listPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_sendero);
        listPoints = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            listPoints = (ArrayList<LatLng>) bundle.get("lat");
        }

        Log.d("punto1: " , listPoints.get(0).toString());
        Log.d("punto1: " , listPoints.get(1).toString());

    }
}
