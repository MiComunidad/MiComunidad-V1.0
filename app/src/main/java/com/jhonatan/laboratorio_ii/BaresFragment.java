package com.jhonatan.laboratorio_ii;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jhonatan.laboratorio_ii.Adapters.AdapterServicios;
import com.jhonatan.laboratorio_ii.Modelo.Servicios;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BaresFragment extends Fragment {
    private ArrayList<Servicios> serviciosList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterServicios;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public BaresFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bares,container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        serviciosList = new ArrayList<>();

        adapterServicios = new AdapterServicios(serviciosList,R.layout.cardview_servicios,getActivity());
        recyclerView.setAdapter(adapterServicios);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        databaseReference.child("Servicios").child("Restaurantes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                serviciosList.clear();
                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Servicios servicios= snapshot.getValue(Servicios.class);
                        serviciosList.add(servicios);
                    }
                    adapterServicios.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }

}
