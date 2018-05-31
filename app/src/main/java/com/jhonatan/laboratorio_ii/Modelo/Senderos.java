package com.jhonatan.laboratorio_ii.Modelo;


import java.io.Serializable;
import java.util.ArrayList;
/**
 * Created by Jhonatan on 22/05/2018.
 */


public class Senderos implements Serializable {
    private String id, nombre, foto, descripcion, ubicacion;
    private ArrayList<com.google.android.gms.maps.model.LatLng> listPoints;


    public Senderos(String nombre, String foto, String descripcion, String ubicacion, ArrayList<com.google.android.gms.maps.model.LatLng> listPoints) {
        this.nombre = nombre;
        this.foto = foto;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.listPoints = listPoints;
    }

    public Senderos(String nombre, String foto, String descripcion, String ubicacion) {
        this.nombre = nombre;
        this.foto = foto;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
    }

    public Senderos(String nombre, String foto, String ubicacion) {
        this.nombre = nombre;
        this.foto = foto;
        this.ubicacion = ubicacion;
    }

    public Senderos() {
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public ArrayList<com.google.android.gms.maps.model.LatLng> getListPoints() {
        return listPoints;
    }

    public void setListPoints(ArrayList<com.google.android.gms.maps.model.LatLng> listPoints) {
        this.listPoints = listPoints;
    }
}