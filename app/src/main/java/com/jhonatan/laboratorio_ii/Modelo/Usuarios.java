package com.jhonatan.laboratorio_ii.Modelo;

/**
 * Created by Jhonatan on 10/04/2018.
 */

public class Usuarios {
    private String id, nombre, correo, foto;

    public Usuarios(String id, String nombre, String correo, String foto) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.foto = foto;
    }

    public Usuarios(String id, String nombre, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Usuarios() {};
}
