package com.example.jesus.agendaoncologica;

/**
 * Created by Jesus on 1/31/2016.
 */

public class Medico {
    private String prefijo;
    private String nombre;
    private String apellido;
    private String email;
    private String numeroTelefonoOpcion1;
    private String numeroTelefonoOpcion2;
    private String ubicacion;


    public Medico(String prefijo, String nombre, String apellido, String email, String numeroTelefonoOpcion1, String numeroTelefonoOpcion2, String ubicacion) {
        this.prefijo = prefijo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.numeroTelefonoOpcion1 = numeroTelefonoOpcion1;
        this.numeroTelefonoOpcion2 = numeroTelefonoOpcion2;
        this.ubicacion = ubicacion;
    }

    public Medico() {
    }

    public String getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroTelefonoOpcion1() {
        return numeroTelefonoOpcion1;
    }

    public void setNumeroTelefonoOpcion1(String numeroTelefonoOpcion1) {
        this.numeroTelefonoOpcion1 = numeroTelefonoOpcion1;
    }

    public String getNumeroTelefonoOpcion2() {
        return numeroTelefonoOpcion2;
    }

    public void setNumeroTelefonoOpcion2(String numeroTelefonoOpcion2) {
        this.numeroTelefonoOpcion2 = numeroTelefonoOpcion2;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}

