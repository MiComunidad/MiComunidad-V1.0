package com.jhonatan.laboratorio_ii;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jhonatan.laboratorio_ii.Adapters.AdapterSenderos;
import com.jhonatan.laboratorio_ii.Modelo.Senderos;

import java.util.ArrayList;

public class MostrarSenderosActivity extends AppCompatActivity {

    private ArrayList<Senderos> senderosList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterSenderos;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_senderos);
        

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        senderosList = new ArrayList<>();

        adapterSenderos = new AdapterSenderos(senderosList,R.layout.cardview_servicios,this);
        recyclerView.setAdapter(adapterSenderos);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        databaseReference.child("Senderos").child("Informacion").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                senderosList.clear();
                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Senderos senderos= snapshot.getValue(Senderos.class);
                        senderosList.add(senderos);
                    }
                    adapterSenderos.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
