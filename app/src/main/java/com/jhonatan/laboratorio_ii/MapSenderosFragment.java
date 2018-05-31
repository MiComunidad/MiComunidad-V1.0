package com.jhonatan.laboratorio_ii;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapSenderosFragment extends Fragment {
    private TextView tNombre,tUbicacion,tDescripcion;
    public MapSenderosFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_map_senderos, container, false);
        tNombre= view.findViewById(R.id.tNombre);
        tUbicacion = view.findViewById(R.id.tUbicacion);
        tDescripcion = view.findViewById(R.id.tDescripcion);



        return view;
    }

    public void mostrarInfo(String nom,String ubi, String des){
        tNombre.setText(nom);
        tUbicacion.setText("Ubicacion: " + ubi);
        tDescripcion.setText("Descripción: " + des);
    }
}
