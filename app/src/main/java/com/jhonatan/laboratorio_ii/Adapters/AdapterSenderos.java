package com.jhonatan.laboratorio_ii.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jhonatan.laboratorio_ii.DetallesSenderosActivity;
import com.jhonatan.laboratorio_ii.Modelo.Senderos;
import com.jhonatan.laboratorio_ii.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Jhonatan on 26/05/2018.
 */

public class AdapterSenderos extends RecyclerView.Adapter<AdapterSenderos.SenderosViewHolder>
        implements View.OnClickListener{

    private ArrayList<Senderos> senderoslist;
    private int resource;
    private Activity activity;
    private View.OnClickListener listener;

    public AdapterSenderos(ArrayList<Senderos> senderoslist){
        this.senderoslist = senderoslist;
    }

    public AdapterSenderos(ArrayList<Senderos> senderoslist, int resource, Activity activity) {
        this.senderoslist = senderoslist;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public AdapterSenderos.SenderosViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(activity, "asd" ,Toast.LENGTH_SHORT).show();

            }
        });
        return new AdapterSenderos.SenderosViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterSenderos.SenderosViewHolder holder, int position) {

        final Senderos senderos  = senderoslist.get(position);
        holder.bindSenderos(senderos, activity);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,DetallesSenderosActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("senderos",senderos);
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return senderoslist.size();
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


    class SenderosViewHolder extends RecyclerView.ViewHolder {
        private TextView tNombre,tUbicacion;
        private ImageView iFoto;


        public SenderosViewHolder(View itemView) {
            super(itemView);

            tNombre = itemView.findViewById(R.id.tNombre);
            tUbicacion = itemView.findViewById(R.id.tDireccion);
            iFoto = itemView.findViewById(R.id.iFoto);


        }

        public void bindSenderos(Senderos senderos, Activity activity) {

            tNombre.setText(senderos.getNombre());
            tUbicacion.setText(senderos.getUbicacion());
            Picasso.get().load(senderos.getFoto()).into(iFoto);
        }

    }
}
