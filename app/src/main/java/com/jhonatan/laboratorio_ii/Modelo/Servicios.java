package com.jhonatan.laboratorio_ii.Modelo;

/**
 * Created by Jhonatan on 25/04/2018.
 */

public class Servicios {
    private String id,Nombre,Direccion,Foto,Contacto,Descripcion,Horario;

    public Servicios(String nombre, String direccion, String foto, String contacto, String descripcion, String horario) {
        Nombre = nombre;
        Direccion = direccion;
        Foto = foto;
        Contacto = contacto;
        Descripcion = descripcion;
        Horario = horario;
    }

    public Servicios(String nombre, String direccion, String foto) {
        Nombre = nombre;
        Direccion = direccion;
        Foto = foto;
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

    public String getHorario() {
        return Horario;
    }

    public void setHorario(String horario) {
        Horario = horario;
    }
}

