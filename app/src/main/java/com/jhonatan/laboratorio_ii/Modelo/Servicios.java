package com.jhonatan.laboratorio_ii.Modelo;

/**
 * Created by Jhonatan on 25/04/2018.
 */

public class Servicios {
    private String id,Nombre,Direccion,foto,contacto,descripcion,horario;

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Servicios(String nombre, String direccion, String foto, String contacto, String descripcion, String horario) {
        Nombre = nombre;
        Direccion = direccion;
        this.foto = foto;
        this.contacto = contacto;
        this.descripcion = descripcion;
        this.horario = horario;
    }

    public Servicios(String id, String nombre, String direccion, String foto) {
        this.id = id;
        Nombre = nombre;
        Direccion = direccion;
        this.foto = foto;
    }

    public Servicios() {
    }

    public Servicios(String nombre, String direccion, String foto) {
        Nombre = nombre;
        Direccion = direccion;
        this.foto = foto;
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
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}

