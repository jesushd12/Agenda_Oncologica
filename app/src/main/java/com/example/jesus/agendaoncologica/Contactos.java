package com.example.jesus.agendaoncologica;

/**
 * Created by Jesus on 2/1/2016.
 */
public class Contactos {
    private int id;
    private String nombre;
    private String numero;

    public Contactos() {
    }

    public Contactos(String nombre, String numero) {
        this.nombre = nombre;
        this.numero = numero;
    }

    public int getId(){return id;}

    public void setId(int id){this.id  = id;}

    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
