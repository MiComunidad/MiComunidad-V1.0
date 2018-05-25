package com.jhonatan.laboratorio_ii;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jhonatan.laboratorio_ii.Modelo.Servicios;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetallesActivity extends AppCompatActivity {

    TextView tDescripcion,tContacto,tHorario,tDireccion,tNombre;
    ImageView iServicio;
    private ArrayList<Servicios> serviciosList;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Button bFavoritos;
    int contador=0;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detalles);

            tDescripcion = findViewById(R.id.tDescripcion);
            tDireccion = findViewById(R.id.tDireccion);
            tContacto = findViewById(R.id.tContacto);
            tHorario = findViewById(R.id.tHorario);
            tNombre = findViewById(R.id.tNombre);
            iServicio = findViewById(R.id.iServicio);
            bFavoritos = findViewById(R.id.bFavoritos);


        Bundle serviciosel = getIntent().getExtras();
        Servicios servicios = null;

        if(serviciosel != null){

            servicios =(Servicios) serviciosel.getSerializable("servicios");

            tHorario.setText("Horarios: " + servicios.getHorarios().toString()+ "\n");
            tDireccion.setText("Dirección: " + servicios.getDireccion().toString() + "\n");
            tDescripcion.setText("Descripción: " + servicios.getDescripcion().toString() + "\n");
            tNombre.setText(servicios.getNombre().toString() + "\n");
            tContacto.setText("Contacto: " + servicios.getContacto().toString() + "\n");
            Picasso.get().load(servicios.getFoto()).into(iServicio);

        }

        if(contador == 0) {
            bFavoritos.setText("Añadir a favoritos");
            Drawable image = getApplicationContext().getResources().getDrawable(R.drawable.btn_star_big_off);
            int h = image.getIntrinsicHeight();
            int w = image.getIntrinsicWidth();
            image.setBounds(0, 0, w, h);
            bFavoritos.setCompoundDrawables(image, null, null, null);
        }
        else if(contador == 1){
            bFavoritos.setText("Quitar de favoritos");
            Drawable image = getApplicationContext().getResources().getDrawable(R.drawable.btn_star_big_on);
            int h = image.getIntrinsicHeight();
            int w = image.getIntrinsicWidth();
            image.setBounds(0, 0, w, h);
            bFavoritos.setCompoundDrawables(image, null, null, null);
        }
    }

    public void onButtonClicked(View view) {
        int id = view.getId();

        switch (id){
            case  R.id.bFavoritos:
                if(contador == 0) {

                    Bundle serviciosel = getIntent().getExtras();
                    Servicios servicios = null;

                    if (serviciosel != null) {
                        servicios = (Servicios) serviciosel.getSerializable("servicios");

                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                        FirebaseDatabase.getInstance();
                        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

                        final Servicios finalServicios = servicios;

                                            databaseReference.child("Usuarios").child(firebaseUser.getUid()).child("Favoritos").child(finalServicios.getId()).setValue(finalServicios.getId());
                                            Toast.makeText(DetallesActivity.this, "Se agregó a favoritos", Toast.LENGTH_SHORT).show();
                                            contador = contador +1;

                        bFavoritos.setText("Quitar de favoritos");
                        Drawable image = getApplicationContext().getResources().getDrawable(R.drawable.btn_star_big_on);
                        int h = image.getIntrinsicHeight();
                        int w = image.getIntrinsicWidth();
                        image.setBounds(0, 0, w, h);
                        bFavoritos.setCompoundDrawables(image, null, null, null);
                    }

                }else if(contador == 1) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(DetallesActivity.this);
                    builder.setIcon(R.mipmap.ic_launcher)
                            .setTitle("¿Desea quitar de favoritos?")
                            .setMessage("El servicio se eleminará de la lista de favoritos").
                            setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Bundle serviciosel = getIntent().getExtras();
                                    Servicios servicios = null;

                                    if (serviciosel != null) {
                                        servicios = (Servicios) serviciosel.getSerializable("servicios");

                                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                                        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                                        FirebaseDatabase.getInstance();
                                        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

                                        final Servicios finalServicios = servicios;

                                                            databaseReference.child("Usuarios").child(firebaseUser.getUid()).child("Favoritos").child(finalServicios.getId()).removeValue();
                                                            Toast.makeText(DetallesActivity.this, "se quito", Toast.LENGTH_SHORT).show();
                                                            contador = 0;
                                    }
                                    bFavoritos.setText("Añadir a favoritos");
                                    Drawable image = getApplicationContext().getResources().getDrawable(R.drawable.btn_star_big_off);
                                    int h = image.getIntrinsicHeight();
                                    int w = image.getIntrinsicWidth();
                                    image.setBounds(0, 0, w, h);
                                    bFavoritos.setCompoundDrawables(image, null, null, null);

                                }

                            }).
                            setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                    builder.create();
                    builder.show();
                }
            break;
            case R.id.bMapa:
                Bundle serviciosel1 = getIntent().getExtras();
                Servicios servicios1 = null;

                if(serviciosel1 != null){

                    servicios1 =(Servicios) serviciosel1.getSerializable("servicios");

                    Intent i1 = new Intent (DetallesActivity.this,MapsActivity.class);
                    Bundle favoritos = new Bundle();
                    favoritos.putSerializable("favoritos",servicios1);
                    i1.putExtras(favoritos);
                    startActivity(i1);
                }
        }


    }
}
