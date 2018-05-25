package com.jhonatan.laboratorio_ii.Modelo;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jhonatan on 22/05/2018.
 */

public class Senderos implements Serializable {
    private  String id, Nombre, Foto, Descripcion;
    private ArrayList<LatLng> listPoints;

    public Senderos(String id, String nombre, String foto, String descripcion, ArrayList<LatLng> listPoints) {
        this.id = id;
        Nombre = nombre;
        Foto = foto;
        Descripcion = descripcion;
        this.listPoints = listPoints;
    }

    public Senderos(String nombre, String descripcion, ArrayList<LatLng> listPoints) {
        Nombre = nombre;
        Descripcion = descripcion;
        this.listPoints = listPoints;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String foto) {
        Foto = foto;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public ArrayList<LatLng> getListPoints() {
        return listPoints;
    }

    public void setListPoints(ArrayList<LatLng> listPoints) {
        this.listPoints = listPoints;
    }
}
