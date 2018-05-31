package com.jhonatan.laboratorio_ii;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
public class FavoritosHFragment extends Fragment {

    private ArrayList<Servicios> serviciosList;
    private ArrayList<String> idList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterServicios;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private String id;

    public FavoritosHFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hoteles,container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        serviciosList = new ArrayList<>();
        idList = new ArrayList<>();

        adapterServicios = new AdapterServicios(serviciosList,R.layout.cardview_servicios,getActivity());
        recyclerView.setAdapter(adapterServicios);

        loadData();

        adapterServicios = new AdapterServicios(serviciosList,R.layout.cardview_servicios,getActivity());
        recyclerView.setAdapter(adapterServicios);

        return view;
    }

    @Override
    public void onResume() {
      //  Log.d("OnStop","si");
      //  loadData();
        idList.clear();
        super.onResume();
    }

    private void loadData() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference.child("Usuarios").child(firebaseUser.getUid()).child("Favoritos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                idList.clear();
                if (dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                        id=snapshot.getValue().toString();
                        idList.add(id);

                        databaseReference.child("Servicios").child("Hoteles").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                serviciosList.clear();

                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        Servicios servicios = snapshot.getValue(Servicios.class);

                                        for(int i= 0;i < idList.size(); i++) {
                                            if (servicios.getId().toString().equals(idList.get(i).toString())) {

                                                serviciosList.add(servicios);
                                            }
                                        }
                                    }
                                    adapterServicios.notifyDataSetChanged();
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
