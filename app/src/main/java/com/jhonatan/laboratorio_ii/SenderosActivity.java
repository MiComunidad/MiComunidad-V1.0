package com.jhonatan.laboratorio_ii;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SenderosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senderos);
    }

    public void onButtonClicked(View view) {

        int id = view.getId();
        if(id==R.id.bRegistrar){
            Intent intent = new Intent(SenderosActivity.this,RegistrarSenderoActivity.class);
            startActivity(intent);
        }
    }
}
