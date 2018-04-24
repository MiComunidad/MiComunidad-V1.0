package com.jhonatan.laboratorio_ii.Modelo;

/**
 * Created by Jhonatan on 10/04/2018.
 */

public class Usuarios {
    private String id, nombre, correo, lugar;

    public Usuarios(String id, String nombre, String correo, String lugar) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.lugar = lugar;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Usuarios() {};
}
