package com.jhonatan.laboratorio_ii.Modelo;

import java.io.Serializable;

/**
 * Created by Jhonatan on 25/04/2018.
 */

public class Servicios implements Serializable{
    private String id;
    private String Nombre;
    private String Direccion;
    private String Foto;
    private String Contacto;
    private String Descripcion;
    private String Horarios;
    private String lat;
    private String lng;
    private String Favoritos;

    public Servicios(String id, String nombre, String direccion, String foto, String contacto, String descripcion, String horarios, String lat, String lng) {
        this.id = id;
        Nombre = nombre;
        Direccion = direccion;
        Foto = foto;
        Contacto = contacto;
        Descripcion = descripcion;
        Horarios = horarios;
        this.lat = lat;
        this.lng = lng;
    }

    public Servicios(String id, String nombre, String direccion, String foto, String contacto, String descripcion, String horarios, String lat, String lng, String favoritos) {
        this.id = id;
        Nombre = nombre;
        Direccion = direccion;
        Foto = foto;
        Contacto = contacto;
        Descripcion = descripcion;
        Horarios = horarios;
        this.lat = lat;
        this.lng = lng;
        Favoritos = favoritos;
    }

    public String getFavoritos() {
        return Favoritos;
    }

    public void setFavoritos(String favoritos) {
        Favoritos = favoritos;
    }

    public void setHorarios(String horarios) {
        Horarios = horarios;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public Servicios(String nombre, String direccion, String foto, String contacto, String descripcion, String horarios) {
        Nombre = nombre;
        Direccion = direccion;
        Foto = foto;
        Contacto = contacto;
        Descripcion = descripcion;
        Horarios = horarios;
    }

    public Servicios(String nombre, String direccion, String foto) {
        Nombre = nombre;
        Direccion = direccion;
        Foto = foto;
    }

    public Servicios(String id) {
        this.id = id;
    }

    public Servicios(String id, String nombre, String direccion, String foto, String contacto, String descripcion, String horarios) {
        this.id = id;
        Nombre = nombre;
        Direccion = direccion;
        Foto = foto;
        Contacto = contacto;
        Descripcion = descripcion;
        Horarios = horarios;
    }

    public Servicios() {
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

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String foto) {
        Foto = foto;
    }

    public String getContacto() {
        return Contacto;
    }

    public void setContacto(String contacto) {
        Contacto = contacto;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getHorarios() {
        return Horarios;
    }

    public void setHorario(String horarios) {
        Horarios = horarios;
    }
}

