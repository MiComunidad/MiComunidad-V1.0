package com.jhonatan.laboratorio_ii.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jhonatan.laboratorio_ii.DetallesActivity;
import com.jhonatan.laboratorio_ii.Modelo.Servicios;
import com.jhonatan.laboratorio_ii.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Jhonatan on 25/04/2018.
 */

public class AdapterServicios  extends RecyclerView.Adapter<AdapterServicios.ServiciosViewHolder>
implements View.OnClickListener{

    private ArrayList<Servicios> servicioslist;
    private int resource;
    private Activity activity;
    private View.OnClickListener listener;

    public AdapterServicios(ArrayList<Servicios> servicioslist){
        this.servicioslist = servicioslist;
    }

    public AdapterServicios(ArrayList<Servicios> servicioslist, int resource, Activity activity) {
        this.servicioslist = servicioslist;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public ServiciosViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        Toast.makeText(activity, "asd" ,Toast.LENGTH_SHORT).show();

            }
        });
        return new ServiciosViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ServiciosViewHolder holder, int position) {

        final Servicios servicios  = servicioslist.get(position);
        holder.bindServicios(servicios, activity);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,DetallesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("servicios",servicios);
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return servicioslist.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }


    class ServiciosViewHolder extends RecyclerView.ViewHolder {
        private TextView tNombre,tDireccion;
        private ImageView iFoto;


        public ServiciosViewHolder(View itemView) {
            super(itemView);

            tNombre = itemView.findViewById(R.id.tNombre);
            tDireccion = itemView.findViewById(R.id.tDireccion);
            iFoto = itemView.findViewById(R.id.iFoto);


        }

        public void bindServicios(Servicios servicios, Activity activity) {

            tNombre.setText(servicios.getNombre());
            tDireccion.setText(servicios.getDireccion());
            Picasso.get().load(servicios.getFoto()).into(iFoto);
        }

    }
}
