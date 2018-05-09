package com.jhonatan.laboratorio_ii;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jhonatan.laboratorio_ii.Modelo.Servicios;
import com.jhonatan.laboratorio_ii.Modelo.Usuarios;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetallesActivity extends AppCompatActivity {

    TextView tDescripcion,tContacto,tHorario,tDireccion,tNombre;
    ImageView iServicio;
    private ArrayList<Servicios> serviciosList;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;


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

    }

    public void onButtonClicked(View view) {

        int id = view.getId();
        switch (id){
            case  R.id.bFavoritos:
            Bundle serviciosel = getIntent().getExtras();
            Servicios servicios = null;

            if(serviciosel != null){
                servicios =(Servicios) serviciosel.getSerializable("servicios");

                FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
                final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                FirebaseDatabase.getInstance();
                final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();

                final Servicios finalServicios = servicios;
                databaseReference.child("Usuarios").child(firebaseUser.getUid()).
                        addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()){
                                    Log.d("usuario", "Ok");
                                    databaseReference.child("Usuarios").child(firebaseUser.getUid()).child("Favoritos").child(finalServicios.getId()).setValue(finalServicios.getId());
                                }
                                else{
                                    Log.d("usuario", "No");
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }

            break;
            case R.id.bMapa:
                Bundle serviciosel1 = getIntent().getExtras();
                Servicios servicios1 = null;

                if(serviciosel1 != null){
                    servicios =(Servicios) serviciosel1.getSerializable("servicios");
                    Intent i1 = new Intent (DetallesActivity.this,MapsActivity.class);
                    Bundle favoritos = new Bundle();
                    favoritos.putSerializable("favoritos",servicios);
                    i1.putExtras(favoritos);
                    startActivity(i1);
                }
        }


    }
}
