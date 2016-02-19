package com.example.jesus.agendaoncologica;

/**
 * Created by Jesus on 2/2/2016.
 */
public class TipoExamen {
    private int idTipo;
    private String nombre;


    public TipoExamen(String nombre) {
        this.nombre = nombre;
    }

    public TipoExamen(int idTipo, String nombre) {
        this.idTipo = idTipo;
        this.nombre = nombre;
    }

    public TipoExamen() {
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
